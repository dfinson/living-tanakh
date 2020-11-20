<template>
  <div>

  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import {MediaTag} from "@/api/dto";
import apifiClient from "@/api/apifiClient";

@Component
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
    console.log(this.tagId)
    apifiClient.getMediaTagById(this.tagId, this.tagExpectedReturn).then(response => {
      console.log(response);
      this.tag = response.data.getMediaTagById;
    })
  }

}
</script>

<style scoped>

</style>