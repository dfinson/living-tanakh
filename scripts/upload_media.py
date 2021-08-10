from os import listdir
import os
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
        self.key = json_dict['key']
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
        self.description = json_dict.get('description', None)
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
    global client
    query = """
            query{
                verseFreeTextSearch(input:{
                    fetchAll: true,
                        searchTerm: "%s"
                    }){
                  content{
                    id
                  }
                }
            }
    """ % search_term
    data = client.execute(query)
    content = data['data']['verseFreeTextSearch']['content']
    return [Verse(entry) for entry in content]


def associate_media_tag_with_verse(verse, media_tag):
    global client
    query = """
        mutation associateMediaTagsWithVerse($owner: VerseInput, $input: [MediaTagInput]) { 
            associateMediaTagsWithVerse(owner: $owner, input: $input){
                id
                key
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


def associate_tag_with_verses(verses, key):
    media_tag = MediaTag({"key": key})
    for verse in verses:
        media_tag = associate_media_tag_with_verse(verse, media_tag)
    return media_tag


def associate_media_contents_with_tag(media_tag, search_entry):
    global client
    global base_dir_path
    media_contents = [
        MediaContent({"key": image_path, "mediaType": 'IMAGE', "mediaTag": media_tag, "description": image_description})
        for image_path, image_description in
        search_entry.images.items()
    ]
    query = """"
        mutation associateLinkedContentWithMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { 
            associateLinkedContentWithMediaTag(owner: $owner, input: $input){
                id
                description
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

    def __init__(self, media_content, search_entry):
        Thread.__init__(self)
        self.media_content = media_content
        self.search_entry = search_entry

    def run(self):
        self.upload_file_to_s3()

    def upload_file_to_s3(self) -> Any:
        print(f'uploading {self.media_content.key} on thread {self.getName()}\n')
        global base_dir_path
        item = self.media_content
        data = open(self.search_entry.original_image_paths[item.key], 'rb')
        url = item.signedUploadUrl
        response = put(url=url, data=data, headers={'Content-type': item.mimeType})
        if response.reason != 'OK':
            print(f'Error uploading object {item.key} to Amazon S3: \n {response}')
        print(f'done uploading {self.media_content.key} on thread {self.getName()}')


def upload_media_contents(media_contents_to_upload, search_entry):
    print(f'starting upload of {len(media_contents_to_upload)} items for {search_entry.search_term}')
    tasks = []
    for item in media_contents_to_upload:
        task = MediaUploader(item, search_entry)
        tasks.append(task)
        task.start()
    for task in tasks:
        task.join()


client = GraphqlClient('http://livingtanakhapplicationde-env.eba-i3mkpska.eu-central-1.elasticbeanstalk.com/api')
base_dir_path = 'C:/Maayan/'


class SearchEntry:
    def __init__(self, search_term, file_paths):
        self.search_term = search_term
        self.images = {file_path.replace(f'{search_term}/', ''): self.get_file_description(file_path) for file_path in file_paths}
        self.original_image_paths = {image_name: base_dir_path + file_paths[i] for i, image_name in enumerate(self.images.keys())}

    def get_file_description(self, file_path):
        file_path_prefix, _ = os.path.splitext(base_dir_path + file_path)
        description_file_path = file_path_prefix + ".txt"
        if os.path.isfile(description_file_path):
            return open(description_file_path, 'rb').read().decode("utf-8")
        else:
            return None


def get_terms_to_process():
    result = []
    for _, subdirs, _ in os.walk(base_dir_path):
        for subdir in subdirs:
            file_paths = [subdir + '/' + file_name for file_name in listdir(base_dir_path + subdir) if not file_name.endswith('.txt')]
            result.append(SearchEntry(search_term=subdir, file_paths=file_paths))
    return result


def process_terms():
    terms_to_process = get_terms_to_process()
    for search_entry in terms_to_process:
        verses = get_verses_by_search_term(search_entry.search_term)
        tag = associate_tag_with_verses(verses, search_entry.search_term)
        media_contents = associate_media_contents_with_tag(tag, search_entry)
        upload_media_contents(media_contents, search_entry)


def clear_existing_media():
    global client
    query = """
        mutation{
          deleteAllMediaTags
        }
        """
    client.execute(query)


clear_existing_media()
process_terms()
