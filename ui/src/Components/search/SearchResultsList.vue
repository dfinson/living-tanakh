<template>
    <div class="searchResCon" style="text-align: center;">

        <span class="title" style="color: darkcyan; font-size: 22px; margin-top: 5px" v-if="searchResults.length !== 0">{{searchResults.length.toString() + " Results:"}}</span>
      <search-result  v-for="verse in searchResults" v-bind:value="verse" :key="verse.id"
                      :result="verse"
                      @result-selected="sendControllerSelectedChapterAndVerse($event)"
                      :display-trop-to-search-result="displayTropToSearchResult"
      ></search-result>
    </div>
</template>

<script lang = "ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import {Chapter, Verse} from "@/api/dto";
import SearchResult from "@/Components/search/SearchResult.vue";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";

@Component({
  components:{
    SearchResult,
      BaseCard
  }
})
export default class SearchResultsList extends Vue{
  //the two lists of results we receive from the controller..
  @Prop({default: 'Object'})
  searchResults: Verse[];
  @Prop({type: Boolean})
  displayOptions: boolean;

  @Prop()
  displayTropToSearchResult: boolean;


  public sendControllerSelectedChapterAndVerse(pathArr: string[]): void{
    this.$emit('result-selected',pathArr);
    //console.log(this.searchResults);
  }



}
</script>

<style scoped>

</style>