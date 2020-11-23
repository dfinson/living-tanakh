<template>
  <div>
    <!--<h1>The Ma'ayan Tanach Project</h1>
    <search-controller class="split right"
            @display-selected-chapter="sendChapterToChapterDisplay($event)"
            @stop-chapter-display="stopDisplay($event)"
    ></search-controller>
    <chapter-display class="split left"
            :selected-chapter="this.selectedChapter"
            :display-results="this.displayResults"
    ></chapter-display>-->
    <MediaTagModal
        v-if="displayMediaTagModal"
        :tag-id="selectedMediaTagId"
        @closed-media-tag-modal="displayMediaTagModal = false"/>
  </div>
</template>

<script lang = "ts">

/*
  Overview:

    Is the primary UI container for this application.
    Consists of a search & navigation bar,
    as well as a main text display area.

  Children:
    - SearchController
    - ChapterDisplay
    - AggregatedMediaDisplay (popup / modal)

  Parents:
    - App

  props:
    None

  emits:
    None

  data:
    - chapter: Chapter
    - highlightedVersePath: string
    - searchCriteria: SearchCriteria
*/

import { Component, Vue } from 'vue-property-decorator';
import SearchController from "@/Components/search/SearchController.vue";
import ChapterDisplay from "@/Components/search/ChapterDisplay.vue";
import {Chapter} from "@/api/dto";
import MediaTagModal from "@/Components/MediaComponents/MediaTagModal.vue";

@Component({
  components: {
    MediaTagModal,
    ChapterDisplay,
    SearchController
  }
})
export default class Dashboard extends Vue{

  public selectedChapter = new Chapter();
  public displayResults = false;

  private displayMediaTagModal = false;
  private selectedMediaTagId: number;

  mounted(){
    this.handleMediaTagIdSelected(369131);
  }

  private handleMediaTagIdSelected(id: number): void{
    this.selectedMediaTagId = id;
    this.displayMediaTagModal = true;
  }

  public sendChapterToChapterDisplay(selectedChapter: Chapter): void{
    this.selectedChapter = selectedChapter;
    this.displayResults = true;
  }

  public stopDisplay(displayChapter: boolean){
    this.displayResults = displayChapter;
  }



}

</script>

<style scoped>

</style>