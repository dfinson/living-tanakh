<template>
  <div v-if="displayOptions">
      <search-result  v-for="verse in searchResults" v-bind:value="verse" :key="verse.number"
                      :result="verse"
                      @result-selected="sendControllerSelectedChapter($event)"

      ></search-result>
  </div>
</template>

<script lang = "ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import {Verse} from "@/api/dto";
import SearchResult from "@/Components/search/SearchResult.vue";

@Component({
  components:{
    SearchResult
  }
})
export default class SearchResultsList extends Vue{
  //the two lists of results we receive from the controller..
  @Prop({default: 'Example'})
  searchResults: Verse[]
  @Prop({default: 'Example'})
  displayOptions: boolean

  public sendControllerSelectedChapter(selectedChapter: string): void{
    this.$emit('result-selected',selectedChapter);
  }



}
</script>

<style scoped>

</style>