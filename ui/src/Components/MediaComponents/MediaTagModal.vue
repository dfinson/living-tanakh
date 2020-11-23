<template>
  <div>
    <Gallery :tag="tag" @closed-media-tag-modal="$emit('closed-media-tag-modal')"/>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import {MediaTag} from "@/api/dto";
import apifiClient from "@/api/apifiClient";
import Gallery from "@/Components/MediaComponents/Gallery.vue";

@Component({
  components: {Gallery}
})
export default class MediaTagModal extends Vue{

  @Prop({required: true})
  private tagId: number;

  private tag: MediaTag = new MediaTag();
  private isCardModalActive = true;

  private readonly tagExpectedReturn = `{
    description
    id
    key
    linkedContent{
      key
      signedDownloadUrl
    }
  }`;

  mounted(){
    apifiClient.getMediaTagById(this.tagId, this.tagExpectedReturn).then(response => {
      console.log(response);
      this.tag = response.data.getMediaTagById;
    })
  }

}
</script>

<style scoped>

</style>