from os import listdir
from threading import Thread
from typing import Any
from requests import put
from python_graphql_client import GraphqlClient
import getopt
import sys


class Verse:
    def __init__(self, json_dict):
        self.id = json_dict['id']

    def to_json(self):
        return to_json(self)


class MediaTag:
    def __init__(self, json_dict):
        self.id = json_dict.get('id', None)
        self.title = json_dict['title']
        self.description = json_dict.get('description', None)
        self.verses = json_dict.get('verses', None)
        if self.verses:
            self.verses = [Verse(val) for val in self.verses]

    def to_json(self):
        result = to_json(self)
        if 'verses' in result:
            result['verses'] = [verse.to_json() for verse in self.verses]
        return result


class MediaContent:
    def __init__(self, json_dict):
        self.id = json_dict.get('id', None)
        self.mediaTag = json_dict['mediaTag']
        self.mediaType = json_dict.get('mediaType', None)
        self.key = json_dict['key']
        self.signedUploadUrl = json_dict.get('signedUploadUrl', None)
        self.mimeType = json_dict.get('mimeType', None)

    def to_json(self):
        result = to_json(self)
        if 'mediaTag' in result:
            result['mediaTag'] = self.mediaTag.to_json()
        return result


def to_json(obj):
    return {k: v for k, v in obj.__dict__.items() if v is not None}


def get_verses_by_search_term(search_term):
    global client, valid_path_prefixes
    query = """
            query{
                verseFreeTextSearch(input:{
                    fetchAll: true,
                        searchTerm: "%s",
                        customArgs:{
                            validPathPrefixes: ["%s"]
                        }
                    }){
                  content{
                    id
                  }
                }
            }
    """ % (search_term, ", ".join(valid_path_prefixes))
    data = client.execute(query)
    content = data['data']['verseFreeTextSearch']['content']
    return [Verse(entry) for entry in content]


def associate_media_tag_with_verse(verse, media_tag):
    global client
    query = """
        mutation associateMediaTagsWithVerse($owner: VerseInput, $input: [MediaTagInput]) { 
            associateMediaTagsWithVerse(owner: $owner, input: $input){
                id
                title
                description
                verses{
                    id
                }
            }
        }
        """
    variables = {"owner": verse.to_json(), "input": [media_tag.to_json()]}
    data = client.execute(query, variables)
    return MediaTag(data['data']['associateMediaTagsWithVerse'][0])


def associate_tag_with_verses(verses, title):
    media_tag = MediaTag({"title": title})
    for verse in verses:
        media_tag = associate_media_tag_with_verse(verse, media_tag)
    return media_tag


def associate_media_contents_with_tag(media_tag, dir_name):
    global client
    global base_dir_path
    files = listdir(base_dir_path + dir_name)
    media_contents = [MediaContent({"key": filename, "mediaType": 'IMAGE', "mediaTag": media_tag}) for filename in
                      files]
    query = """"
        mutation associateLinkedContentWithMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { 
            associateLinkedContentWithMediaTag(owner: $owner, input: $input){
                id
                mediaTag{
                    id
                }
                mediaType
                mimeType
                key
                signedUploadUrl
            } 
        }
    """
    variables = {"owner": media_tag.to_json(), "input": [media_content.to_json() for media_content in media_contents]}
    data = client.execute(query, variables)['data']['associateLinkedContentWithMediaTag']
    return [MediaContent(json_dict) for json_dict in data]


class MediaUploader(Thread):

    def __init__(self, media_content, target_dir):
        Thread.__init__(self)
        self.media_content = media_content
        self.target_dir = target_dir

    def run(self):
        self.upload_file_to_s3()

    def upload_file_to_s3(self) -> Any:
        print(f'uploading {self.media_content.key} on thread {self.getName()}\n')
        global base_dir_path
        target_dir = self.target_dir
        item = self.media_content
        data = open(base_dir_path + target_dir + '/' + item.key, 'rb')
        url = item.signedUploadUrl
        response = put(url=url, data=data, headers={'Content-type': item.mimeType})
        if response.reason != 'OK':
            print(f'Error uploading object {item.key} to Amazon S3: \n {response}')
        print(f'done uploading {self.media_content.key} on thread {self.getName()}')


def upload_media_contents(media_contents_to_upload, target_dir):
    print(f'starting upload of {len(media_contents_to_upload)} items for {target_dir}')
    tasks = []
    for item in media_contents_to_upload:
        task = MediaUploader(item, target_dir)
        tasks.append(task)
        task.start()
    for task in tasks:
        task.join()


client = GraphqlClient('http://localhost:5000/api')
base_dir_path = '/home/dave01/Downloads/17/Maayan/'
valid_path_prefixes = ['PROPHETS/Samuel I/17']
terms_to_process = [('socho','שכה'), ('azekah','עזקה'), ('shaarayim','שערים'), ('gat','גת')]


def process_terms():
    global terms_to_process
    for target_dir, search_term in terms_to_process:
        verses = get_verses_by_search_term(search_term)
        tag = associate_tag_with_verses(verses, search_term)
        media_contents = associate_media_contents_with_tag(tag, target_dir)
        upload_media_contents(media_contents, target_dir)


process_terms()

