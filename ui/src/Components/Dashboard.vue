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
    <MediaTagModal/>
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
import {Chapter, MediaTag, SearchCriteria, Verse} from "@/api/dto";
import MediaTagModal from "@/Components/MediaComponents/MediaTagModal.vue";
import apifiClient from "@/api/apifiClient";

@Component({
  components: {
    MediaTagModal,
    ChapterDisplay,
    SearchController
  }
})
export default class Dashboard extends Vue{

  private tag: MediaTag;

  mounted(){
    apifiClient.getMediaTagById()
  }

  searchCriteria: SearchCriteria
  public selectedChapter = new Chapter();
  public displayResults = false

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