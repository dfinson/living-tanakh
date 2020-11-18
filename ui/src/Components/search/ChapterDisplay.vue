<template>
  <div v-show="displayResults">
  <base-card>
    <p  v-for="verse in selectedChapter.verses"  v-bind:value="verse" :key="verse.number">{{verse.fullHebrewText + "\n"}}</p>
  </base-card>
  </div>
</template>

<script lang="ts">

/*
  Overview:

    Display a single chapter

  Complexity:
    - Toggle nekudot and taamim on or off
    - Highlight relevant verse / search terms if chapter is being displayed after user double-clicks on a search result
    - Size adjustable text
    - Scrollable text reader
    - Auto scroll to highlighted search result(?)

  Parents:
    - Dashboard

  Children:
    None

  props:
    chapter: Chapter

  emits:
    - mediaTagSelected(selectedMediaTag);

  data:
    selectedMediaTag: MediaTag
*/

import { Component, Vue, Prop } from 'vue-property-decorator';
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import {Chapter} from "@/api/dto";


@Component({
  components:{
    BaseCard
  }
})
export default class ChapterDisplay extends Vue{
  @Prop({default:'Example'})
  selectedChapter: Chapter
  @Prop()
  displayResults: boolean

}
</script>

<style scoped>

</style>