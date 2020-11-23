<template>
<div>
  <base-card class="title" style="color: darkcyan">The Ma'ayan Tanach Project</base-card>
  <div class="columns">
    <div class="column is-two-thirds">
    <chapter-display
            :selected-chapter="this.selectedChapter"
            :display-results="this.displayResults"
            :search-term="this.searchTerm"
             @send-tag-to-dashboard="setTagValue($event)"
    ></chapter-display>
    </div>
    <div class="column">
    <search-controller
            @display-selected-chapter="sendChapterToChapterDisplay($event)"
            @stop-chapter-display="stopDisplay($event)"
            @send-search-term-to-dashboard="sendSearchTermToChapterDisplay($event)"
    ></search-controller>
        @closed-media-tag-modal="displayMediaTagModal = false"/>
        :tag-id="selectedMediaTagId"
        v-if="displayMediaTagModal"
    <MediaTagModal
  </div>
    </div>
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
import MediaTagModal from "@/Components/MediaComponents/MediaTagModal.vue";
import {Chapter, SearchCriteria, Verse} from "@/api/dto";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";

@Component({
  components: {
    ChapterDisplay,
    SearchController,
    BaseCard
  }
})
export default class Dashboard extends Vue{
  private displayMediaTagModal = false;
  private selectedMediaTagId: number;
    this.handleMediaTagIdSelected(369131);
  mounted(){
  }
  private handleMediaTagIdSelected(id: number): void{
    this.selectedMediaTagId = id;
    this.displayMediaTagModal = true;
  }
  searchCriteria: SearchCriteria
  public selectedChapter = new Chapter();
  public displayResults = false;
  public searchTerm = "";

  public sendChapterToChapterDisplay(selectedChapter: Chapter): void{
    this.selectedChapter = selectedChapter;
    this.displayResults = true;
  }

  public stopDisplay(displayChapter: boolean){
    this.displayResults = displayChapter;
  }

  public sendSearchTermToChapterDisplay(searchTerm: string): void{
    this.searchTerm = searchTerm;
    console.log(this.searchTerm + "dashboard");
  }

  public setTagValue(tag: number){
   alert(tag + " from dashboard");
  }



}

</script>

<style scoped>

</style>