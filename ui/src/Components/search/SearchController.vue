<template>
  <div>
  <search-input-form
          @update-category-selection="updateCategorySelection($event)"
          @update-book-selection="updateBookSelection($event)"
          @update-chapter-selection="updateChapterSelection($event)"
          @update-search-term-selection="updateSearchTermSelection($event)"
          :chapters-list="chaptersList"
  ></search-input-form>
  </div>
</template>

<script lang = "ts">

/*
  Overview:

    Encapsulates the search & navigation functionality of the app by
    offering the user a search / navigation form, fetching the resulting data from
    the backend API and then displaying a list of the search results.

  Complexity:
    - Handle the emitted data from SearchInputForm (make api call, then pass result as prop to SearchResultsList)

  Parents:
    - Dashboard
// @update-category-selection = "updateCategorySelection($event)"
  Children:
    - SearchInputForm
    - SearchResultsList

  props:
    None

  emits:
    - searchResultSelected(selectedVerse)

  data:
    None

*/

import { Component, Vue,Prop } from 'vue-property-decorator';
import {Book, Chapter, SearchCriteria} from "@/api/dto";
import SearchInputForm from "@/Components/search/SearchInputForm.vue";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import {TORAH} from "@/api/TANAKH";
import apifiClient from "@/api/apifiClient";


@Component({
  components:{
    SearchInputForm
  }
})
export default class SearchController extends Vue{


  public searchCriteria = new SearchCriteria();
  public chaptersList: Chapter[] = []


    //an api call is made, which populates the chaptersList (with objects of type 'Chapter'), which is passed as a prop to the form.
  public getChapterList(): void{
      this.chaptersList = [];
      apifiClient.findBookByUniquePath(this.searchCriteria.book,
              `{

      chapters{
        hebrewNumeral
        id
        number
        path
      }



            }`).then( res => {
                let i;
                for (i = 0; i < res['data'].findBookByUniquePath.chapters.length; i++) {
                  const ch = new Chapter();
                  ch.hebrewNumeral = res['data'].findBookByUniquePath.chapters[i].hebrewNumeral;
                  ch.id = res['data'].findBookByUniquePath.chapters[i].id;
                  ch.number = res['data'].findBookByUniquePath.chapters[i].number;
                  ch.path = res['data'].findBookByUniquePath.chapters[i].path;
                  this.chaptersList.push(ch);
                }
              });



  }

  public freeTextSearchWithPath(searchPath: string){
    console.log("searching with path" + searchPath);
    //the api call:
    apifiClient.verseFreeTextSearch({
      customArgs: {
        validPathPrefixes:  [searchPath]
      },
      pageSize: 20,
      pageNumber: 0,
      searchTerm: this.searchCriteria.searchTerm

    }, `{
            content{
           chapter{
            path
           }
           fullHebrewText
           hebrewNumeral

          } }`).then(res => {
      let i;
      for(i = 0; i < 20; i++)
        console.log(res['data'].verseFreeTextSearch.content[i]);
    });
  }

  public freeTextSearchWithoutPath(){
    console.log("Searching without path");
    //the api call:
    apifiClient.verseFreeTextSearch({
      customArgs: {
        validPathPrefixes: ["TORAH", "PROPHETS", "WRITINGS"]
      },
      pageSize: 20,
      pageNumber: 0,
      searchTerm: this.searchCriteria.searchTerm

    }, `{
            content{
           chapter{
            path
           }
           fullHebrewText
           hebrewNumeral
           }}`).then(res => {
              let i
              for(i = 0; i < 20; i++)
                console.log(res['data'].verseFreeTextSearch.content[i]);
            }
    );
  }

  public freeTextSearchSorter(): void{
    let searchPath = ""
    if(this.searchCriteria.category !== "" && this.searchCriteria.category !== undefined)    //if there is a category selected:
      searchPath = this.searchCriteria.category;

    if(this.searchCriteria.book !== "" && this.searchCriteria.book !== undefined) // if there is a book selected
      searchPath = this.searchCriteria.book;

    if(this.searchCriteria.chapter !== "" && this.searchCriteria.chapter !== undefined) //if there is chapter selected
      searchPath += "/" + this.searchCriteria.chapter;

    if(searchPath === "") //there is no Path to narrow down the FreeTextSearch
      this.freeTextSearchWithoutPath();

    if(searchPath !== "") //there is a path to narrow down the freeTextSearch
      this.freeTextSearchWithPath(searchPath);

  }

  public getChapterFromPathSearch(){
    console.log("getting chapter from path");
    //the api call:
    apifiClient.findChapterByUniquePath(this.searchCriteria.book + "/" + this.searchCriteria.chapter.toString(), `{
             hebrewNumeral
    id
    number
    path
    verses{
      fullHebrewText
      hebrewNumeral
      number
    }
            }`).then(res => console.log(res));
  }

  public generalSearchSorter(): void{
    //if there is a search term, we'll call the free text search function - (which will also make use of the search path..)
      if(this.searchCriteria.searchTerm !== "" && this.searchCriteria.searchTerm !== undefined){
        this.freeTextSearchSorter();
      }

      //if there's no search term, we can just search for a chapter based on the path..
      else{
        if(this.searchCriteria.chapter !== "" && this.searchCriteria.book !== "" && this.searchCriteria.category !== "")
          this.getChapterFromPathSearch()
          return;
      }
  }

  //these functions get the data from the form and update the fields of the searchCriteria object accordingly...
  public updateCategorySelection(selectedCategory: string): void{
    this.searchCriteria.category = selectedCategory;
    //console.log(this.searchCriteria.category + " from controller");

  }

  public updateBookSelection(selectedBook: string){
    this.searchCriteria.book = selectedBook;
    //console.log(this.searchCriteria.book + " from controller");
    this.getChapterList();
  }

  public updateChapterSelection(selectedChapter: string){
    this.searchCriteria.chapter = selectedChapter;
    //console.log(this.searchCriteria.chapter + " from controller");
  }

  public updateSearchTermSelection(searchTerm: string){
    this.searchCriteria.searchTerm = searchTerm;
   // console.log(this.searchCriteria.searchTerm + " from controller")
    this.generalSearchSorter();
  }




}
</script>

<style scoped>

</style>