<template>
    <div>
<strong>
    <span>{{verseToDisplay.hebrewNumeral + ": "}}</span>
<span  v-for="segment in verseToDisplay.highlightedVerseSegments.segments" v-bind:value="segment" :key="segment.prefix">

    <span v-if="hasPrefix(segment) && displayTrop">{{segment.prefix}}</span>
    <span v-else-if="hasPrefix(segment) && !displayTrop" >{{segment.plainHebrewPrefix}}</span>
    <a v-if="hasHighlighted(segment) && displayTrop" @click="sendTagToChapterDisplay(segment)" >{{segment.highlightedKeyword}}</a>
    <a v-else-if="hasHighlighted(segment) && !displayTrop" >{{segment.plainHebrewHighlightedKeyword}}</a>
        </span>
        <span v-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && displayTrop">{{ verseToDisplay.highlightedVerseSegments.finalSuffix}}</span>
        <span v-else-if="hasSuffix(verseToDisplay.highlightedVerseSegments) && !displayTrop">{{verseToDisplay.highlightedVerseSegments.plainHebrewFinalSuffix}}</span>
</strong>
    </div>
</template>

<script lang="ts">
    import {Vue,Component,Prop} from "vue-property-decorator";
    import {Verse, HighlightedVerseSegments, PrefixedVerseSegment} from "../../api/dto";
    @Component
    export default class VerseDisplay extends Vue{
        @Prop({default:'Example'})
            verseToDisplay: Verse
        @Prop()
        displayTrop: boolean;

        @Prop()
        searchTerm: string;

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
               // console.log(segment.highlightedKeyword);
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
            console.log(segment.tag.id + " from verse");
            this.$emit('send-tag-to-chapter-display',segment.tag.id);
        }

//<span>{{verseToDisplay.hebrewNumeral}}</span>

    }

</script>

<style scoped>
span{
    font-family: 'StamAshkenaz', serif;
    font-size: 20pt;
}
</style>