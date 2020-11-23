<template>
  <base-card>
  <div class="sidebar-page" v-if="displayResults">
    <base-card class="title" style="color: #0f4361">{{selectedChapter.book.hebrewName + "/" + selectedChapter.hebrewNumeral}}</base-card>
    <h1 class="tab-item" v-if="displayTrop"><strong>Toggle to view without ta'amim</strong></h1>
    <h1 class="tab-item" v-if=" !displayTrop"><strong>Toggle to view with ta'amim</strong></h1>
      <b-switch @input="displayTrop = ! displayTrop" style="size: A4"></b-switch>
    <verse-display v-for="verse in selectedChapter.verses" v-bind:value="verse" :key="verse.number"
                    :verse-to-display="verse"
                   :display-trop="displayTrop"
                   :search-term="searchTerm"
                   @send-tag-to-chapter-display="sendTagToDashboard($event)"
    ></verse-display>
  </div>
  </base-card>
</template>
<script lang="ts">

/*
  Overview:
//:verse="verse"
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
import VerseDisplay from "@/Components/search/VerseDisplay.vue";
import {Chapter} from "@/api/dto";


@Component({
  components:{
    BaseCard,
    VerseDisplay
  }
})
export default class ChapterDisplay extends Vue{
  @Prop({default:'Example'})
  selectedChapter: Chapter
  @Prop()
  displayResults: boolean;
  @Prop()
  searchTerm: string;

  public displayTrop = true;


  sendTagToDashboard(tag: number): void{
    console.log(tag + " from chapter")
    this.$emit('send-tag-to-dashboard',tag);
  }

}
</script>

<style scoped>
card{
  overflow: scroll;
}
  .slider.round {
    border-radius: 34px;
  }

  .slider.round:before {
    border-radius: 50%;
  }
  .switch {
    position: relative;
    display: inline-block;
    width: 60px;
    height: 34px;
  }

  /* Hide default HTML checkbox */
  .switch input {
    opacity: 0;
    width: 0;
    height: 0;
  }

  /* The slider */
  .slider {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #ccc;
    -webkit-transition: .4s;
    transition: .4s;
  }

  .slider:before {
    position: absolute;
    content: "";
    height: 26px;
    width: 26px;
    left: 4px;
    bottom: 4px;
    background-color: white;
    -webkit-transition: .4s;
    transition: .4s;
  }

  input:checked + .slider {
    background-color: #2196F3;
  }

  input:focus + .slider {
    box-shadow: 0 0 1px #2196F3;
  }

  input:checked + .slider:before {
    -webkit-transform: translateX(26px);
    -ms-transform: translateX(26px);
    transform: translateX(26px);
  }
</style>