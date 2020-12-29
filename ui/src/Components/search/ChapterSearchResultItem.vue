<template>
    <div v-if="getChapterSearchResults.book !== undefined" style="display: inline">
        <span class="title" style="color: darkcyan; font-size: 22px; margin-top: 10px;">{{"Chapter Selected:"}}</span>
        <strong>
       <b-button :type="categoryColorCode" style="height: 22px; margin-left: 10px;">{{getPath()}}</b-button>
        </strong></div>
</template>
<script lang="ts">
 import {Vue,Component, Prop,Watch} from "vue-property-decorator";
 import {Chapter, Verse} from "../../api/dto";
 import BaseCard from '@/Components/BaseComponents/BaseCard.vue';


 @Component({
     components:{
         BaseCard
     }
 })
    export default class ChapterSearchResultItem extends Vue{
        @Prop({required:true})
        getChapterSearchResults: Chapter;
      @Prop()
        selectedVerseNumeral: string;

        public colon = "";
        public getPath(): string{
            return  this.getChapterSearchResults.book?.hebrewName + " " +  this.getChapterSearchResults.hebrewNumeral + this.colon +  this.selectedVerseNumeral;
        }

            public get categoryColorCode(): string{
         if(this.getChapterSearchResults.path.includes('TORAH')){
             return 'is-primary is-light'
         }
         if(this.getChapterSearchResults.path.includes('PROPHETS')){
             return 'is-info is-light'

         }
         else{
             return 'is-success is-light';
         }
     }

     @Watch('selectedVerseNumeral')
     onChanged(){
            if(this.selectedVerseNumeral !== "" && this.selectedVerseNumeral !== undefined)
                this.colon = ":";
            else{
                this.colon = "";
            }
     }

 }





</script>

<style scoped>
</style>