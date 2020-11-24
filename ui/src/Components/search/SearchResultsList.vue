<template>
  <base-card>
      <h1 class="title" style="color: darkcyan" v-if="searchResults.length !== 0">{{searchResults.length.toString() + " Results:"}}</h1>
      <search-result  v-for="verse in searchResults" v-bind:value="verse" :key="verse.id"
                      :result="verse"
                      @result-selected="sendControllerSelectedChapter($event)"

      ></search-result>
  </base-card>
</template>

<script lang = "ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import {Verse} from "@/api/dto";
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
  @Prop({default: 'Example'})
  searchResults: Verse[]
  @Prop({default: 'Example'})
  displayOptions: boolean;


  public sendControllerSelectedChapter(selectedChapter: string): void{
    this.$emit('result-selected',selectedChapter);
    //console.log(this.searchResults);
  }



}
</script>

<style scoped>

</style>