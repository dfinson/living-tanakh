<template>
 <p style="text-align:right; margin-right: 10px;" class="par">
     <a @click="sendPassukToPassukDisplay" style="color: yellow">
         <a style="font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1; font-size:15px; float: right; margin-left: 5px; margin-top: 4px" >{{"(" + verseToDisplay.hebrewNumeral  +    ") "}}</a>
     <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-for="segment in verseToDisplay.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.prefix">
       <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefix(segment) && displayTrop">{{segment.prefix}}</span>
      <span style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-else-if="hasPrefix(segment) && !displayTrop" >{{segment.plainHebrewPrefix}}</span>
       <span v-if="hasHighlighted(segment) && displayTrop" >{{segment.highlightedKeyword}}</span>
        <span v-else-if="hasHighlighted(segment) && !displayTrop" >{{segment.plainHebrewHighlightedKeyword}}</span></span>
     <span style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && displayTrop">{{ verseToDisplay.highlightedVerseSegments.finalSuffix}}</span>
         <span style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-else-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && !displayTrop">{{verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>
     </a>
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

        public sendPassukToPassukDisplay(verse: Verse): void{
           // console.log(segment.tag.id + " from verse");
            this.$emit('send-passuk-to-passuk-display',this.verseToDisplay);
        }

//<span>{{verseToDisplay.hebrewNumeral}}</span>

    }

</script>

<style scoped>
    /*! CSS Used from: http://www.foundationstone.org/Maayan/page-5/files/stacks_page_page38.css?rwcache=628456071 ; media=all */
</style>