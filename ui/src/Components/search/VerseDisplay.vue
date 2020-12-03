<template>
 <p style="text-align:right;" class="par">
   <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;">{{"(" + verseToDisplay.hebrewNumeral  +    ")"}}</span>
     <span  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-for="segment in verseToDisplay.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.prefix">
       <span  class="paragraph-span" style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefix(segment) && displayTrop">{{segment.prefix}}</span>
      <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-else-if="hasPrefix(segment) && !displayTrop" >{{segment.plainHebrewPrefix}}</span>
       <a v-if="hasHighlighted(segment) && displayTrop" @click="sendTagToChapterDisplay(segment)" style="color: crimson" >{{segment.highlightedKeyword}}</a>
        <a v-else-if="hasHighlighted(segment) && !displayTrop" style="color: crimson">{{segment.plainHebrewHighlightedKeyword}}</a></span>
     <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && displayTrop">{{ verseToDisplay.highlightedVerseSegments.finalSuffix}}</span>
     <span style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-else-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && !displayTrop">{{verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>
 </p>
</template>

<script lang="ts">
    import {Vue,Component,Prop} from "vue-property-decorator";
    import {Verse, HighlightedVerseSegments, PrefixedVerseSegment} from "../../api/dto";
    @Component
    export default class VerseDisplay extends Vue{
        @Prop({default:'Example'})
            verseToDisplay: Verse;
        @Prop()
        displayTrop: boolean;

        @Prop()
        searchTerm: string;

        public hasPrefix(segment: PrefixedVerseSegment): boolean{
            if(segment.prefix !== ""){
                console.log(segment.prefix);
                return true;
            }
            else
                return false;
        }

        public hasHighlighted(segment: PrefixedVerseSegment): boolean {
            if(segment.highlightedKeyword !== ""){
             //  console.log(segment.highlightedKeyword);
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

        public sendTagToChapterDisplay(segment: PrefixedVerseSegment): void{
           // console.log(segment.tag.id + " from verse");
            this.$emit('send-tag-to-chapter-display',segment.tag.id);
        }

//<span>{{verseToDisplay.hebrewNumeral}}</span>

    }

</script>

<style scoped>
    /*! CSS Used from: http://www.foundationstone.org/Maayan/page-5/files/stacks_page_page38.css?rwcache=628456071 ; media=all */

    @media all {
        @media print {
            *,
            *::before,
            *::after {
                text-shadow: none!important;
                box-shadow: none!important;
            }
        }
        *,
        *::before,
        *::after {
            box-sizing: inherit;
        }
    }
</style>