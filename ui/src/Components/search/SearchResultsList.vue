<template>
  <v-container>

    <search-result  v-for="verse in sortedSearchResults" v-bind:value="verse" :key="verse.id"
                    :result="verse"
                    @result-selected="sendControllerSelectedChapterAndVerse($event)"
                    :display-trop-to-search-result="displayTropToSearchResult"
    ></search-result>
  </v-container>
</template>

<script lang = "ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import {Chapter, SortingObject, Verse} from "@/api/dto";
import SearchResult from "@/Components/search/SearchResult.vue";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";

@Component({
  components:{
    SearchResult,
    BaseCard
  }
})
export default class SearchResultsList extends Vue{
  //the two lists of results we receive from the controller..
  @Prop({default: 'Object'})
  searchResults: Verse[];

  @Prop()
  displayTropToSearchResult: boolean;


  public sendControllerSelectedChapterAndVerse(pathArr: string[]): void{
    this.$emit('result-selected',pathArr);
    console.log(pathArr);
  }

  //really long method for sorting based on tanach order - a patch for a backend issue...
  public get sortedSearchResults(): Verse[]{
    console.log("sorting!!")
    const torah: SortingObject[] = [];
    const prophets: SortingObject[] = [];
    const writings: SortingObject[] = [];
    for(let i = 0; i < this.searchResults.length; i++){
      if(this.searchResults[i].path.includes('TORAH')) {
        const obj = new SortingObject();
        obj.verse = new Verse();
        obj.verse.path = this.searchResults[i].path;
        obj.verse.number = this.searchResults[i].number;
        obj.verse.id = this.searchResults[i].id;
        obj.verse.fullHebrewText = this.searchResults[i].fullHebrewText;
        obj.verse.chapter = this.searchResults[i].chapter;
        obj.verse.humanReadablePath = this.searchResults[i].humanReadablePath;
        obj.verse.highlightedVerseSegments = this.searchResults[i].highlightedVerseSegments;

        if( obj.verse.path.includes("Genesis"))
          obj.categoryNumber = 0;
        if( obj.verse.path.includes("Exodus"))
          obj.categoryNumber = 1;
        if( obj.verse.path.includes("Leviticus"))
          obj.categoryNumber = 2;
        if( obj.verse.path.includes("Numbers"))
          obj.categoryNumber = 3;
        if( obj.verse.path.includes("Deuteronomy"))
          obj.categoryNumber = 4;
        torah.push(obj);
      }
      if(this.searchResults[i].path.includes('PROPHETS') && !this.searchResults[i].path.includes('Daniel') &&
          !this.searchResults[i].path.includes('Ezra') && !this.searchResults[i].path.includes('Nehemiah') && !this.searchResults[i].path.includes('Chronicles'))  {
        const obj = new SortingObject();
        obj.verse = new Verse();
        obj.verse.path = this.searchResults[i].path;
        obj.verse.number = this.searchResults[i].number;
        obj.verse.id = this.searchResults[i].id;
        obj.verse.fullHebrewText = this.searchResults[i].fullHebrewText;
        obj.verse.chapter = this.searchResults[i].chapter;
        obj.verse.humanReadablePath = this.searchResults[i].humanReadablePath;
        obj.verse.highlightedVerseSegments = this.searchResults[i].highlightedVerseSegments;
        if( obj.verse.path.includes("Joshua")) {
          obj.categoryNumber = 0;
          obj.verse.humanReadablePath = obj.verse.humanReadablePath.replace("יהושוע", "יהושע")
        }
        if( obj.verse.path.includes("Judges"))
          obj.categoryNumber = 1;
        if( obj.verse.path.includes("Samuel I"))
          obj.categoryNumber = 2;
        if( obj.verse.path.includes("Samuel II"))
          obj.categoryNumber = 3;
        if( obj.verse.path.includes("Kings I"))
          obj.categoryNumber = 4;
        if( obj.verse.path.includes("Kings II"))
          obj.categoryNumber = 5;
        if( obj.verse.path.includes("Isaiah"))
          obj.categoryNumber = 6;
        if( obj.verse.path.includes("Jeremiah"))
          obj.categoryNumber = 7;
        if( obj.verse.path.includes("Ezekiel"))
          obj.categoryNumber = 8;
        if( obj.verse.path.includes("Hosea"))
          obj.categoryNumber = 9;
        if( obj.verse.path.includes("Joel"))
          obj.categoryNumber = 10;
        if( obj.verse.path.includes("Amos"))
          obj.categoryNumber = 11;
        if( obj.verse.path.includes("Obadiah"))
          obj.categoryNumber = 12;
        if( obj.verse.path.includes("Jonah"))
          obj.categoryNumber = 13;
        if( obj.verse.path.includes("Micha"))
          obj.categoryNumber = 14;
        if( obj.verse.path.includes("Nahum"))
          obj.categoryNumber = 15;
        if( obj.verse.path.includes("Habakkuk"))
          obj.categoryNumber = 16;
        if( obj.verse.path.includes("Zephania"))
          obj.categoryNumber = 17;
        if( obj.verse.path.includes("Haggai"))
          obj.categoryNumber = 18;
        if( obj.verse.path.includes("Zechariah"))
          obj.categoryNumber = 19;
        if( obj.verse.path.includes("Malachi"))
          obj.categoryNumber = 20;
        prophets.push(obj);

      }
      if(this.searchResults[i].path.includes('WRITINGS') ||
          this.searchResults[i].path.includes('Daniel') ||
          this.searchResults[i].path.includes('Ezra') || this.searchResults[i].path.includes('Nehemiah') || this.searchResults[i].path.includes('Chronicles')) {
        const obj = new SortingObject();
        obj.verse = new Verse();
        obj.verse.path = this.searchResults[i].path;
        obj.verse.number = this.searchResults[i].number;
        obj.verse.id = this.searchResults[i].id;
        obj.verse.fullHebrewText = this.searchResults[i].fullHebrewText;
        obj.verse.chapter = this.searchResults[i].chapter;
        obj.verse.humanReadablePath = this.searchResults[i].humanReadablePath;
        obj.verse.highlightedVerseSegments = this.searchResults[i].highlightedVerseSegments;
        if( obj.verse.path.includes("Psalms"))
          obj.categoryNumber = 0;
        if( obj.verse.path.includes("Proverbs"))
          obj.categoryNumber = 1;
        if( obj.verse.path.includes("Job"))
          obj.categoryNumber = 2;
        if( obj.verse.path.includes("Ruth"))
          obj.categoryNumber = 3;
        if( obj.verse.path.includes("Lamentations"))
          obj.categoryNumber = 4;
        if( obj.verse.path.includes("Ecclesiastes"))
          obj.categoryNumber = 5;
        if( obj.verse.path.includes("Esther"))
          obj.categoryNumber = 6;
        if( obj.verse.path.includes("Daniel"))
          obj.categoryNumber = 7;
        if( obj.verse.path.includes("Ezra"))
          obj.categoryNumber = 8;
        if( obj.verse.path.includes("Nehemiah"))
          obj.categoryNumber = 9;
        if( obj.verse.path.includes("Chronicles"))
          obj.categoryNumber = 10;
        writings.push(obj);
      }
    }
    torah.sort((a,b) => {return a.categoryNumber - b.categoryNumber})
    prophets.sort((a, b) => {return a.categoryNumber - b.categoryNumber})
    writings.sort((a,b) => {return a.categoryNumber - b.categoryNumber})

    const sortedResultArr: Verse[] = [];
    for(let i = 0; i < torah.length; i++){
      const obj = new Verse();
      obj.path = torah[i].verse.path;
      obj.number = torah[i].verse.number;
      obj.id =  torah[i].verse.id;
      obj.fullHebrewText =  torah[i].verse.fullHebrewText;
      obj.chapter =  torah[i].verse.chapter;
      obj.humanReadablePath =  torah[i].verse.humanReadablePath;
      obj.highlightedVerseSegments =  torah[i].verse.highlightedVerseSegments;
      sortedResultArr.push(obj);
    }
    for(let i = 0; i < prophets.length; i++){
      const obj = new Verse();
      obj.path = prophets[i].verse.path;
      obj.number = prophets[i].verse.number;
      obj.id =  prophets[i].verse.id;
      obj.fullHebrewText =  prophets[i].verse.fullHebrewText;
      obj.chapter =  prophets[i].verse.chapter;
      obj.humanReadablePath =  prophets[i].verse.humanReadablePath;
      obj.highlightedVerseSegments =  prophets[i].verse.highlightedVerseSegments;
      sortedResultArr.push(obj);
    }
    for(let i = 0; i < writings.length; i++){
      const obj = new Verse();
      obj.path = writings[i].verse.path;
      obj.number = writings[i].verse.number;
      obj.id =  writings[i].verse.id;
      obj.fullHebrewText =  writings[i].verse.fullHebrewText;
      obj.chapter =  writings[i].verse.chapter;
      obj.humanReadablePath =  writings[i].verse.humanReadablePath;
      obj.highlightedVerseSegments =  writings[i].verse.highlightedVerseSegments;
      sortedResultArr.push(obj);
    }

    return sortedResultArr;

  }

}
</script>

<style scoped>

</style>