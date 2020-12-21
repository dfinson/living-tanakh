<template>
    <div v-if="getChapterSearchResults.book !== undefined">
        <h1 class="title" style="color: darkcyan">{{"Search Result:"}}</h1>
    <base-card class="baseCard">
        <strong>
       <b-button type="is-primary is-light" style="float: right">{{getPath()}}</b-button>
        </strong>
    </base-card></div>
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
      /*@Prop()
        displayTrop: boolean;*/


        public getPath(): string{
           return  this.getChapterSearchResults.book?.hebrewName + "/" + this.getChapterSearchResults.hebrewNumeral;
        }

 }





</script>

<style scoped>
</style>