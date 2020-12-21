<template>
  <div>
<base-card>
  <strong>
    <b-button type="is-primary is-light" @click="pathSelected">{{result.humanReadablePath}}</b-button>
<p>
  <span v-for="segment in result.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.id">
    <span v-if="hasPrefix(segment) && !displayTropToSearchResult">{{segment.plainHebrewPrefix}}</span>
        <span v-else-if="hasPrefix(segment) && displayTropToSearchResult">{{segment.prefix}}</span>
    <span v-if="!displayTropToSearchResult" style="background-color: yellow">{{segment.plainHebrewHighlightedKeyword + " "}}</span>
     <span v-else-if="displayTropToSearchResult" style="background-color: yellow">{{segment.highlightedKeyword + " "}}</span>
  </span>
  <span v-if="hasSuffix(result.highlightedVerseSegments)&& !displayTropToSearchResult">{{ result.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>
  <span v-else-if="hasSuffix(result.highlightedVerseSegments)&& displayTropToSearchResult">{{ result.highlightedVerseSegments.finalSuffix}}</span>

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