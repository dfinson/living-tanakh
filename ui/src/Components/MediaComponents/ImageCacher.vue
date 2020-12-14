<template>
  <div>
    <img v-for="(content, index) in tag.linkedContent" :key="index" :src="content.signedDownloadUrl" style="display: none;" alt=""/>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {MediaTag} from "../../api/dto";

@Component
export default class ImageCacher extends Vue{
  @Prop({required: true})
  private tag: MediaTag;
}
</script>

<style scoped>

</style>