<template>
  <v-container>
    <v-row v-if="getChapterSearchResults.book !== undefined" justify="center"  style="margin-top: 10px">
      <span class="title" style="color: darkcyan;">{{"Chapter Selected:"}}</span> 
      <!-- not a big fan of free standing strings best practice is to manage them all in a files suggestion: assets/texts.js -->
    </v-row>
    <v-row justify="center">
      <b-button  :type="categoryColorCode" >{{getPath()}}</b-button>
    </v-row>
  </v-container>
</template>
<script lang="ts">
import {Vue,Component, Prop } from "vue-property-decorator";
import {Chapter } from "../../api/dto";
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


  public getPath(): string{
    return  this.getChapterSearchResults.book?.hebrewName +" " +  this.getChapterSearchResults.hebrewNumeral.replace(/['"]+/g, '') + " " + this.selectedVerseNumeral.replace(/['"]+/g, '');
  }

  public get categoryColorCode(): string{
    // these strings should be saved as constants somewhere (maybe in assets) and imported
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

}
</script>
