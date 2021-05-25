<template>

    <SimpleGallery :tags="tags" ref="simpleGalleryRef"
                   @send-image-to-preview-selector="sendImageToPreviewSelector($event)"
                   @remove-image-from-preview-selector="removeImageFromPreviewSelector($event)"
    ></SimpleGallery>
  
</template>

<script lang="ts">
import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
import {GalleriaImageItem, MediaTag} from "@/api/dto";
import apifiClient from "@/api/apifiClient";
import Gallery from "@/Components/MediaComponents/Gallery.vue";
import MediaPresenter from "@/Components/MediaComponents/MediaPresenter.vue";
import {WRITINGS} from "@/api/TANAKH";
import SimpleGallery from "@/Components/MediaComponents/SimpleGallery.vue";

@Component({
  components: {SimpleGallery, MediaPresenter, Gallery}
})
export default class MediaTagModal extends Vue {

  @Prop({required: true})
  private tagIds: number[];

  @Prop()
  public selectedMediaTagId: number;

  @Prop()
  public searchTerm: string;

  @Prop()
  public passukSelectedFromChapterDisplay: boolean;


  public uncheckAllImages(): void{
      (this.$refs.simpleGalleryRef as any).uncheckAll();

  }

  public tags: MediaTag[] = [];
  private isCardModalActive = true;

  public tellGalleryRemoveImages = false;
  private readonly tagExpectedReturn = `{
    description
    id
    key
    linkedContent{
      key
      signedDownloadUrl
      description
      id
    }
  }`;

  public updateMediaTagList(tag: MediaTag): void{
    this.tags.push(tag);
    //  console.log(tag);
    console.log("Media Tag List Updated");
  }

  //receive image to be added to previewSelector (via dashboard):
  public sendImageToPreviewSelector(image: GalleriaImageItem): void{
    //console.log("sending to dashboard from tag modal")
    this.$emit('send-image-to-preview-selector', image);
  }

  //receive title of image to be removed from previewSelector:
  public removeImageFromPreviewSelector(title: string): void{
    console.log("MTM delete image " + title )
    this.$emit('remove-image-from-preview-selector', title);
  }


  @Watch('selectedMediaTagId')
  onChange() {
    if (this.selectedMediaTagId !== undefined && this.selectedMediaTagId > 0) {
      this.tags = [];
      apifiClient.getMediaTagsByIds([this.selectedMediaTagId], this.tagExpectedReturn).then(response => {
        console.log(response);
        if (response['data'].getMediaTagsByIds) {
          for (let i = 0; i < response['data'].getMediaTagsByIds.length; i++) {
            //if there was no specific search term - we display all the images associated with a chapter
            const newTag = new MediaTag();
            newTag.id = response['data'].getMediaTagsByIds[i].id;
            newTag.description = response['data'].getMediaTagsByIds[i].description;
            newTag.key = response['data'].getMediaTagsByIds[i].key;
            newTag.linkedContent = [];
            for (let j = 0; j < response['data'].getMediaTagsByIds[i].linkedContent.length; j++) {
              //temporary
              if (response['data'].getMediaTagsByIds[i].linkedContent[j].key !== 'SamA-c17-Socho07.jpeg')
                newTag.linkedContent.push(response['data'].getMediaTagsByIds[i].linkedContent[j]);
            }
            this.updateMediaTagList(newTag);
          }
        }
      })
    }
  }

  @Watch('tagIds')
  onPropertyChanged() {
    this.tags = [];
    if (this.tagIds.length !== 0) {
      console.log(this.tagIds + " tag id's from media modal");
      apifiClient.getMediaTagsByIds(this.tagIds, this.tagExpectedReturn).then(response => {
        console.log(response);
        console.log(response['data'].getMediaTagsByIds.length > 0);
        if (response['data'].getMediaTagsByIds.length > 0) {
          for (let i = 0; i < response['data'].getMediaTagsByIds.length; i++) {
            //if there was no specific search term - we display all the images associated with a chapter
            if (this.searchTerm === '' || this.searchTerm === undefined || this.passukSelectedFromChapterDisplay) {
              const newTag = new MediaTag();
              newTag.id = response['data'].getMediaTagsByIds[i].id;
              newTag.description = response['data'].getMediaTagsByIds[i].description;
              newTag.key = response['data'].getMediaTagsByIds[i].key;
              newTag.linkedContent = [];
              for (let j = 0; j < response['data'].getMediaTagsByIds[i].linkedContent.length; j++) {
                //temporary
                if (response['data'].getMediaTagsByIds[i].linkedContent[j].key !== 'SamA-c17-Socho07.jpeg')
                  newTag.linkedContent.push(response['data'].getMediaTagsByIds[i].linkedContent[j]);
              }
              this.updateMediaTagList(newTag);
            }
            //if we have a search term we only want to include the tags relevant to that term
            else {
              console.log("inside condition search term?")
              console.log(this.searchTerm)
              console.log(response['data'].getMediaTagsByIds[i].key)
              if (response['data'].getMediaTagsByIds[i].key === this.searchTerm) {

                const newTag = new MediaTag();
                newTag.id = response['data'].getMediaTagsByIds[i].id;
                newTag.description = response['data'].getMediaTagsByIds[i].description;
                newTag.key = response['data'].getMediaTagsByIds[i].key;
                newTag.linkedContent = [];
                for (let j = 0; j < response['data'].getMediaTagsByIds[i].linkedContent.length; j++) {
                  //temporary
                  if (response['data'].getMediaTagsByIds[i].linkedContent[j].key !== 'SamA-c17-Socho07.jpeg')
                    newTag.linkedContent.push(response['data'].getMediaTagsByIds[i].linkedContent[j]);
                }
                this.updateMediaTagList(newTag);
              }

            }
          }

        }
      });

    }
  }


}
</script>

<style scoped>

</style>