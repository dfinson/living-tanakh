<template>
 <div style="text-align:right; margin-right: 10px; line-height: 1.5em; " class="par">
     <a class="paraA" @click="sendPassukToPassukDisplay" style="text-decoration: none;">
         <a class="paraA" style="text-decoration: none; font:23px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1; font-size:15px; float: right; margin-left: 5px; margin-top: 4px" >{{"(" + verseToDisplay.hebrewNumeral  +    ") "}}</a>
     <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-for="segment in verseToDisplay.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.prefix">
       <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefix(segment) && displayTrop">{{segment.prefix}}</span>
      <span style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-else-if="hasPrefix(segment) && !displayTrop" >{{segment.plainHebrewPrefix}}</span>

       <!-- a patch to add שוכה for demo purposes to prefix-->
       <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefixWithSocho(segment) && displayTrop">{{segment.prefix.slice(0,segment.prefix.indexOf("שׂוֹכֹ֥ה"))}}</span>
             <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:darkorange;" v-if="hasPrefixWithSocho(segment) && displayTrop">שׂוֹכֹ֥ה </span>
              <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefixWithSocho(segment) && displayTrop">{{segment.prefix.slice(segment.prefix.lastIndexOf("שׂוֹכֹ֥ה") + 8,segment.prefix.length)}}</span>

             <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefixWithSocho(segment) && !displayTrop">{{segment.plainHebrewPrefix.slice(0,segment.plainHebrewPrefix.indexOf("שוכה") )}}</span>
             <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:darkorange;" v-if="hasPrefixWithSocho(segment) && !displayTrop">שוכה </span>
              <span  class="paragraph-span" style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasPrefixWithSocho(segment) && !displayTrop">{{segment.plainHebrewPrefix.slice(segment.plainHebrewPrefix.lastIndexOf("שוכה") + 4,segment.plainHebrewPrefix.length )}}</span>



       <span v-if="hasHighlighted(segment) && displayTrop" style="color: darkorange" >{{segment.highlightedKeyword}}</span>
        <span v-else-if="hasHighlighted(segment) && !displayTrop" style="color: darkorange"  >{{segment.plainHebrewHighlightedKeyword}}</span></span>

     <span style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && displayTrop">{{ verseToDisplay.highlightedVerseSegments.finalSuffix}}</span>
         <span style="font:20px 'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:#ECECE1;" v-else-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && !displayTrop">{{verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>

       <!--rest of socho patch for demo-->
       <span style="font:20px 'Lucida Grande',
        LucidaGrande, Verdana, sans-serif; color:#ECECE1;"
             v-if="hasSuffixWithSocho(verseToDisplay.highlightedVerseSegments) && displayTrop">
          {{verseToDisplay.highlightedVerseSegments.finalSuffix.slice(0,verseToDisplay.highlightedVerseSegments.finalSuffix.indexOf("שׂוֹכֹ֥ה") )}}
         <span style="font:20px 'Lucida Grande',
        LucidaGrande, Verdana,
         sans-serif; color:darkorange;">"שׂוֹכֹ֥ה"</span>
         <span style="font:20px 'Lucida Grande',
        LucidaGrande, Verdana,
         sans-serif; color:#ECECE1;">
           {{verseToDisplay.highlightedVerseSegments.finalSuffix.slice(verseToDisplay.highlightedVerseSegments.finalSuffix.indexOf("שׂוֹכֹ֥ה") + 1,verseToDisplay.highlightedVerseSegments.finalSuffix.length )
         </span>
       </span>
       <span style="font:20px 'Lucida Grande',
        LucidaGrande, Verdana,
         sans-serif; color:#ECECE1;"
             v-else-if="hasSuffixWithSocho(verseToDisplay.highlightedVerseSegments) && !displayTrop">
         {{verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix.slice(0,verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix.indexOf("שוכה") )}}
         <span style="font:20px 'Lucida Grande',
        LucidaGrande, Verdana,
         sans-serif; color:darkorange;">"שוכה"</span>
         <span style="font:20px 'Lucida Grande',
        LucidaGrande, Verdana,
         sans-serif; color:#ECECE1;">
           {{verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix.slice(verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix.indexOf("שוכה") + 1,verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix.length -1)
         </span>
       </span>

     </a>
 </div>
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
            if(segment.prefix !== "" && !this.hasPrefixWithSocho(segment)){
                return true;
            }
            else
                return false;
        }

        public hasHighlighted(segment: PrefixedVerseSegment): boolean {
            if(segment.highlightedKeyword !== ""){
             //  console.log(segment.highlightedKeyword);
                return true;
            }
            else
                return false;
        }



        public hasSuffix(highlightedVerseSegment: HighlightedVerseSegments): boolean{
            if(highlightedVerseSegment.finalSuffix !== "" && !this.hasSuffixWithSocho(highlightedVerseSegment)){
               // console.log(highlightedVerseSegment.finalSuffix);
                return true;}
            else
                return false;
        }

        public hasPrefixWithSocho(segment: PrefixedVerseSegment): boolean{
          if(segment.plainHebrewPrefix?.includes("שוכה")){
            console.log("prefix with socho");
            return true;
          }
          else return false;
        }

        public hasSuffixWithSocho(highlightedVerseSegment: HighlightedVerseSegments): boolean{
          if(highlightedVerseSegment.finalSuffix.includes("שוכה")){
            console.log("suffix with socho");
            return true;}
          else
            return false;
        }

        public sendPassukToPassukDisplay(verse: Verse): void{
           // console.log(verse.humanReadablePath + " from verse");
            this.$emit('send-passuk-to-passuk-display',this.verseToDisplay);
        }

//<span>{{verseToDisplay.hebrewNumeral}}</span>

    }

</script>

<style scoped>
    .paraA:hover {
        background-color: #2680ff;
        border-radius: 4px;

    }
</style>