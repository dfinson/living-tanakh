<template>
    <div id='stacks_in_95' class='stacks_in text_stack'>
        <p style="text-align:center;">
            <span style="font:19px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="selectedVerse.hebrewNumeral !== ''">{{ "(" + selectedVerse.hebrewNumeral  +    ") "}}</span>
            <span  style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-for="segment in selectedVerse.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.prefix">
       <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="hasPrefix(segment) && displayTrop">{{segment.prefix}}</span>
      <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-else-if="hasPrefix(segment) && !displayTrop" >{{segment.plainHebrewPrefix}}</span>
              <!-- a patch to add שוכה for demo purposes to prefix-->
       <span  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="hasPrefixWithSocho(segment) && displayTrop">{{segment.prefix.slice(0,segment.prefix.indexOf("שׂוֹכֹ֥ה"))}}</span>
             <a  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:darkorange; text-decoration: none" v-if="hasPrefixWithSocho(segment) && displayTrop"  @click="sendTagToDashBoard(segment)">שׂוֹכֹ֥ה </a>
              <span  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="hasPrefixWithSocho(segment) && displayTrop">{{segment.prefix.slice(segment.prefix.lastIndexOf("שׂוֹכֹ֥ה") + 8,segment.prefix.length)}}</span>

             <span  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;"  v-if="hasPrefixWithSocho(segment) && !displayTrop">{{segment.plainHebrewPrefix.slice(0,segment.plainHebrewPrefix.indexOf("שוכה") )}}</span>
             <a  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:darkorange; text-decoration: none" @click="sendTagToDashBoard(segment)" v-if="hasPrefixWithSocho(segment) && !displayTrop">שוכה </a>
              <span  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="hasPrefixWithSocho(segment) && !displayTrop">{{segment.plainHebrewPrefix.slice(segment.plainHebrewPrefix.lastIndexOf("שוכה") + 4,segment.plainHebrewPrefix.length )}}</span>

       <a v-if="hasHighlighted(segment) && displayTrop" style="color: darkorange; text-decoration: none"  @click="sendTagToDashBoard(segment)">{{segment.highlightedKeyword}}</a>
        <a v-else-if="hasHighlighted(segment) && !displayTrop" style="color: darkorange; text-decoration: none" @click="sendTagToDashBoard(segment)">{{segment.plainHebrewHighlightedKeyword}}</a></span>
            <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="hasSuffix(selectedVerse.highlightedVerseSegments) && displayTrop">{{ selectedVerse.highlightedVerseSegments.finalSuffix}}</span>
            <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-else-if="hasSuffix(selectedVerse.highlightedVerseSegments) && !displayTrop">{{selectedVerse.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>
        </p>
    </div>
</template>

<script lang="ts">

 import {Vue,Prop,Component} from "vue-property-decorator";
 import {HighlightedVerseSegments, PrefixedVerseSegment, Verse} from "@/api/dto";

@Component
   export default class PassukDisplay extends Vue{

    @Prop()
    selectedVerse: Verse;

    @Prop()
    displayTrop: boolean;


    public hasPrefix(segment: PrefixedVerseSegment): boolean{
        if(segment.prefix !== "" && segment.prefix !== undefined && !this.hasPrefixWithSocho(segment)){
            //console.log(segment.prefix);
            return true;
        }
        else
            return false;
    }

    public hasHighlighted(segment: PrefixedVerseSegment): boolean {
        if(segment.highlightedKeyword !== "" && segment.highlightedKeyword !== undefined  ){
            // console.log(segment.highlightedKeyword);
            return true;}
        else
            return false;
    }

    public hasSuffix(highlightedVerseSegment: HighlightedVerseSegments): boolean{
        if(highlightedVerseSegment.finalSuffix !== "" && highlightedVerseSegment.finalSuffix !== undefined && !this.hasSuffixWithSocho(highlightedVerseSegment)){
            // console.log(highlightedVerseSegment.finalSuffix);
            return true;}
        else
            return false;
    }

    public sendTagToDashBoard(segment: PrefixedVerseSegment): void {

        this.$emit('send-tag-to-dashboard',  segment.tag.id);

    }

  public hasPrefixWithSocho(segment: PrefixedVerseSegment): boolean{
    if(segment.plainHebrewPrefix?.includes("שוכה")){
     // console.log("prefix with socho");
      return true;
    }
    else return false;
  }

  public hasSuffixWithSocho(highlightedVerseSegment: HighlightedVerseSegments): boolean{
    if(highlightedVerseSegment.finalSuffix.includes("שוכה")){
     // console.log("suffix with socho");
      return true;}
    else
      return false;
  }


    }
</script>

<style scoped>
a:hover{
    background-color: #ffdf5e;
    border-radius: 4px;
}
</style>