<template>
        <div id='stacks_in_72' class='stacks_in text_stack' >
        <h1 style="font-size:20px; margin-right: 5px; 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1; text-align: right" >{{selectedChapter.book.hebrewName + "/" + selectedChapter.hebrewNumeral}}</h1>



                      <verse-display v-for="verse in selectedChapter.verses" v-bind:value="verse" :key="verse.number" class="verse-display"
                                     :verse-to-display="verse"
                                     :display-trop="displayTrop"
                                     :search-term="searchTerm"
                                     @send-passuk-to-passuk-display="sendPassukToDashboard($event)"
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
import {Chapter, Verse} from "@/api/dto";


@Component({
  components:{
    BaseCard,
    VerseDisplay,
}
})
export default class ChapterDisplay extends Vue{

  @Prop({required:true})
  selectedChapter: Chapter;
  @Prop()
  searchTerm: string;
  @Prop({default:true})
  displayTrop: boolean;

    public buttonLab = "";
    public index = null;
  public sendPassukToDashboard(verse: Verse): void{
   // console.log(tag + " from chapter")
    this.$emit('send-passuk-to-passuk-display',verse);
  }


}
</script>

<style scoped>
    .image {
        float: left;
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center center;
        border: 1px solid #ebebeb;
        margin: 5px;
    }
.buttonSw{
    margin-right: 300px;
}

</style>