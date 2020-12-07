<template>
        <div id='stacks_in_72' class='stacks_in text_stack'>
            <b-button @click="displayTrop = ! displayTrop" class="buttonSw" size="is-small" type="is-info" outlined>Click To View with\out ta'amim and nikud</b-button>
        <h1 style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;">{{selectedChapter.book.hebrewName + "/" + selectedChapter.hebrewNumeral}}</h1>



                      <verse-display v-for="verse in selectedChapter.verses" v-bind:value="verse" :key="verse.number" class="verse-display"
                                     :verse-to-display="verse"
                                     :display-trop="displayTrop"
                                     :search-term="searchTerm"
                                     @send-tag-to-chapter-display="sendTagToDashboard($event)"
                      ></verse-display>

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

    public buttonLab = "";
  public sendTagToDashboard(tag: number): void{
    console.log(tag + " from chapter")
    this.$emit('send-tag-to-dashboard',tag);
  }

  public buttonLabel(): void{
      if(this.displayTrop)
          this.buttonLab = "Click to view without ta'amim and nikud";
      else
          this.buttonLab = "Click to view with ta'amim and nikud";
  }

}
</script>

<style scoped>
.buttonSw{
    margin-right: 300px;
}
    /*! CSS Used from: http://www.foundationstone.org/rw_common/plugins/stacks/stacks.css?rwcache=628456071 ; media=all */

</style>