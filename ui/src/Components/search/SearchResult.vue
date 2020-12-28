<template>
  <div >
<base-card>
  <strong>
    <b-button  :type="categoryColorCode" @click="pathSelected">{{result.humanReadablePath}}</b-button>
<p style="direction: rtl; font-size: 15px">
  <span v-for="segment in result.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.id">
    <span v-if="hasPrefix(segment) && !displayTropToSearchResult" style="font-family: Arial">{{segment.plainHebrewPrefix}}</span>
        <span v-else-if="hasPrefix(segment) && displayTropToSearchResult" style="font-family: Arial">{{segment.prefix}}</span>
    <span v-if="!displayTropToSearchResult" style="background-color: yellow;font-family: Arial">{{segment.plainHebrewHighlightedKeyword + " "}}</span>
     <span v-else-if="displayTropToSearchResult" style="background-color: yellow; font-family: Arial">{{segment.highlightedKeyword + " "}}</span>
  </span>
  <span v-if="hasSuffix(result.highlightedVerseSegments)&& !displayTropToSearchResult" style="font-family: Arial">{{ result.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>
  <span v-else-if="hasSuffix(result.highlightedVerseSegments)&& displayTropToSearchResult" style="font-family: Arial">{{ result.highlightedVerseSegments.finalSuffix}}</span>
</p>
  </strong>
</base-card>
  </div>
</template>

<script lang="ts">

import {Vue,Component,Prop} from "vue-property-decorator";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import {HighlightedVerseSegments, PrefixedVerseSegment, Verse} from "@/api/dto";

@Component( {
  components:{
    BaseCard
  }
})
export default class SearchResult extends Vue{
  public counter = 0;
  @Prop({default:'Example'})
  result: Verse;
  @Prop()
  displayTropToSearchResult: boolean;

  public get categoryColorCode(): string{
    if(this.result.path.includes('TORAH')){
      return 'is-primary is-light'
    }
    if(this.result.path.includes('PROPHETS')){
      return 'is-info is-light'

    }
    else{
      return 'is-success is-light';
    }
  }

  public pathSelected(): void{
    this.$emit('result-selected',[this.result.chapter?.path,this.result.number.toString()]);
  }
  public hasPrefix(segment: PrefixedVerseSegment): boolean{
    if(segment.prefix !== ""){
      //console.log(segment.prefix);
      return true;
    }
    else
      return false;
  }

  public hasHighlighted(segment: PrefixedVerseSegment): boolean {
    if(segment.highlightedKeyword !== ""){
       //console.log(segment.highlightedKeyword + this.counter);
       this.counter ++;
      return true;}
    else
      return false;
  }

  public hasSuffix(highlightedVerseSegment: HighlightedVerseSegments): boolean{
    if(highlightedVerseSegment.finalSuffix !== ""){
      // console.log(highlightedVerseSegment.finalSuffix);
      return true;}
    else
      return false;
  }
}
</script>

<style scoped>

</style>