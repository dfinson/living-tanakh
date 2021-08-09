<template>
    <div id='stacks_in_95' class='stacks_in text_stack'>
        <p style="text-align:center;">
            <span style="font:19px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="selectedVerse.hebrewNumeral !== ''">{{ "(" + selectedVerse.hebrewNumeral  +    ") "}}</span>
            <span  style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-for="segment in selectedVerse.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.prefix">
       <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-if="hasPrefix(segment) && displayTrop">{{segment.prefix}}</span>
      <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:black;" v-else-if="hasPrefix(segment) && !displayTrop" >{{segment.plainHebrewPrefix}}</span>



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
        if(segment.prefix !== "" && segment.prefix !== undefined){
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
        if(highlightedVerseSegment.finalSuffix !== "" && highlightedVerseSegment.finalSuffix !== undefined){
            // console.log(highlightedVerseSegment.finalSuffix);
            return true;}
        else
            return false;
    }

    public sendTagToDashBoard(segment: PrefixedVerseSegment): void {

        this.$emit('send-tag-to-dashboard',  segment.tag.id);

    }




    }
</script>

<style scoped>
a:hover{
    background-color: #ffdf5e;
    border-radius: 4px;
}
</style>