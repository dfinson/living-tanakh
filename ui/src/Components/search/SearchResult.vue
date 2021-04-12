<template>
  <v-container>
    <v-card shaped tile elevation="4" style="max-width: content-box; flex-shrink: 3" >
      <v-row justify="center">
        <v-chip  outlined style="font-size: 0.94vw" :color="categoryColorCode"  @click="pathSelected">{{result.humanReadablePath}}</v-chip>
      </v-row>
      <v-card-text >
        <strong>
          <p style="direction: rtl; font-size: 0.94vw">
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
      </v-card-text>
    </v-card>
  </v-container>
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
      return 'indigo accent-4'
    }
    if(this.result.path.includes('PROPHETS')){
      return 'purple accent-4'

    }
    else{
      return 'green accent-4';
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
.v-btn {
  min-width: 0;
  padding-left: 12px;
  padding-right: 12px;
}
</style>