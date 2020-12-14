<template>
  <div>
<!--    <Gallery :tag="tag" @closed-media-tag-modal="$emit('closed-media-tag-modal')"/>-->
    <MediaPresenter :tags="tags"/>
  </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
import {MediaTag} from "@/api/dto";
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

    @Watch('tagIds')
    onPropertyChanged() {
        this.tags = [];
        apifiClient.getMediaTagsByIds(this.tagIds, this.tagExpectedReturn).then(response => {
           // console.log(response);
        if(response['data'].getMediaTagsByIds){
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

        }});

    }


  }
</script>

<style scoped>

</style>