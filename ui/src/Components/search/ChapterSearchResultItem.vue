<template>
  <v-container>
    <v-row v-if="getChapterSearchResults !== undefined" justify="center"  style="margin-top: 10px">
      <span class="title" style="color: darkcyan;">{{"Chapter Selected:"}}</span>
    </v-row>
    <v-row justify="center">
      <b-button  :type="categoryColorCode" >{{getPath()}}</b-button>
    </v-row>
  </v-container>
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


  public getPath(): string{
    if(this.getChapterSearchResults !== undefined)
        return  this.getChapterSearchResults.book?.hebrewName +" " +  this.getChapterSearchResults.hebrewNumeral.replace(/['"]+/g, '') + " " + this.selectedVerseNumeral.replace(/['"]+/g, '');
  else
    return '';
  }

  public get categoryColorCode(): string {
      if (this.getChapterSearchResults && this.getChapterSearchResults.path.includes('TORAH') ) {
        return 'is-primary is-light'
      }
      if (this.getChapterSearchResults && this.getChapterSearchResults.path.includes('PROPHETS')) {
        return 'is-info is-light'

      } else {
        return 'is-success is-light';
      }
  }

}





</script>

<style scoped>
</style>