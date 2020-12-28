<template>
  <div class="mediaStuff">
<!--    <Gallery :tag="tag" @closed-media-tag-modal="$emit('closed-media-tag-modal')"/>-->
    <MediaPresenter :tags="tags"
    @send-image-to-preview-selector="sendImageToPreviewSelector($event)"
    @remove-image-from-preview-selector="removeImageFromPreviewSelector($event)"
    :selected-media-tag-id="selectedMediaTagId"
    ></MediaPresenter>
  </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import {GalleriaImageItem, MediaTag} from "@/api/dto";
import apifiClient from "@/api/apifiClient";
import Gallery from "@/Components/MediaComponents/Gallery.vue";
import MediaPresenter from "@/Components/MediaComponents/MediaPresenter.vue";
    import {WRITINGS} from "@/api/TANAKH";

@Component({
  components: {MediaPresenter, Gallery}
})
export default class MediaTagModal extends Vue {

    @Prop({required: true})
    private tagIds: number[];

    @Prop()
    public selectedMediaTagId: number;


    public tags: MediaTag[] = [];
    private isCardModalActive = true;

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

    public sendImageToPreviewSelector(image: GalleriaImageItem): void{
        //console.log("sending to dashboard from tag modal")
        this.$emit('send-image-to-preview-selector', image);
    }

    @Watch('tagIds')
    onPropertyChanged() {
        this.tags = [];
        if (this.tagIds.length !== 0) {
            console.log(this.tagIds + " tag id's from media modal");
            apifiClient.getMediaTagsByIds(this.tagIds, this.tagExpectedReturn).then(response => {
                 console.log(response);
                if (response['data'].getMediaTagsByIds) {
                    for (let i = 0; i < response['data'].getMediaTagsByIds.length; i++) {
                        const newTag = new MediaTag();
                        newTag.id = response['data'].getMediaTagsByIds[i].id;
                        newTag.description = response['data'].getMediaTagsByIds[i].description;
                        newTag.key = response['data'].getMediaTagsByIds[i].key;
                        newTag.linkedContent = [];
                        for (let j = 0; j < response['data'].getMediaTagsByIds[i].linkedContent.length; j++) {
                            newTag.linkedContent.push(response['data'].getMediaTagsByIds[i].linkedContent[j]);
                        }
                        this.updateMediaTagList(newTag);
                    }

                }
            });

        }
    }

    public removeImageFromPreviewSelector(image: GalleriaImageItem): void{
        this.$emit('remove-image-from-preview-selector', image);
    }

  }
</script>

<style scoped>

</style>