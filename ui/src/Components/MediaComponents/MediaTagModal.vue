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

  @Prop()//({required: true})
  private tag: MediaTag;

  mounted(){
    apifiClient.getMediaTagById(345054, `{linkedContent}`)
  }

}
</script>

<style scoped>

</style>