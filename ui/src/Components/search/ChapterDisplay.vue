<template>
  <div id='stacks_in_70' class='stacks_in com_cosculture_stack_scrollbar_1_stack'>
   <div id="scrollbar_stacks_in_70"  style="height: 135px; overflow: auto;" >
    <div class="mCustomScrollBox mCS-light-2" id="mCSB_1" style="position:relative; height:100%; overflow:hidden; max-width:100%;">
     <div class="mCSB_container" style="position: relative; top: -896px;">
    <div id='stacks_out_72' class='stacks_out'>
        <div id='stacks_in_72' class='stacks_in text_stack'>
        <b-switch @input="displayTrop = ! displayTrop"  class="toggle-switch" type="is-info"></b-switch>
                    <h1 style="font:15px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:white;" v-if="displayTrop" ><strong>Toggle to view without ta'amim and nikud</strong></h1>
                    <h1 style="font:15px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:white;" class="tab-item" v-if=" !displayTrop" ><strong>Toggle to view with ta'amim and nikud</strong></h1>
        <h1 style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;">{{selectedChapter.book.hebrewName + "/" + selectedChapter.hebrewNumeral}}</h1>
                    <!--verse display-->
                      <verse-display v-for="verse in selectedChapter.verses" v-bind:value="verse" :key="verse.number" class="verse-display"
                                     :verse-to-display="verse"
                                     :display-trop="displayTrop"
                                     :search-term="searchTerm"
                                     @send-tag-to-chapter-display="sendTagToDashboard($event)"
                      ></verse-display>

  </div>

    </div>
     </div>
        <div class="mCSB_scrollTools" style="position: absolute; display: block;">
            <a class="mCSB_buttonUp" oncontextmenu="return false;"></a>
            <div class="mCSB_draggerContainer">
                <div class="mCSB_dragger" style="position: absolute; height: 30px; top: 0px;" oncontextmenu="return false;">
                    <div class="mCSB_dragger_bar" style="position: relative; line-height: 30px;"></div>
                </div>
                <div class="mCSB_draggerRail"></div>
            </div>
            <a class="mCSB_buttonDown" oncontextmenu="return false;"></a>
        </div>
    </div>
   </div>
  </div>
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
  selectedChapter: Chapter;
  @Prop()
  searchTerm: string;
  @Prop()
  displayTrop: boolean;


  sendTagToDashboard(tag: number): void{
    console.log(tag + " from chapter")
    this.$emit('send-tag-to-dashboard',tag);
  }

}
</script>

<style scoped>

    /*! CSS Used from: http://www.foundationstone.org/rw_common/plugins/stacks/stacks.css?rwcache=628456071 ; media=all */

</style>