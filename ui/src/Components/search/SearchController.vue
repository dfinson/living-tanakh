<template>
    <div id='stacks_in_372' class='stacks_in com_elixir_stacks_foundryForm_stack'  >
        <a name="stacks_in_372-"></a>
        <!-- the actual form - dd's and inputs -->
        <search-input-form
                @update-category-selection="updateCategorySelection($event)"
                @update-book-selection="updateBookSelection($event)"
                @update-chapter-selection="updateChapterSelection($event)"
                @update-search-term-selection="updateSearchTermSelection($event)"
                @update-verse-selection="updateVerseSelectionAndSendVerseToPassukDisplay($event)"
                :chapters-list="listOfChaptersInSelectedBook"
                :get-chapter-search-results="getChapterSearchResults"
                @clear-all-results="clearAllResults"
                @change-trop = changeTrop($event)
        ></search-input-form>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2" style="margin-top: 18px;">
            </div>
        </div> <!-- will contain searchResults-->
        <b-loading :is-full-page="false" v-model="isLoading" :can-cancel="true"></b-loading>
        <div style="height: 400px; overflow-y: auto; width: 359px">
            <chapter-search-result-item
                    :get-chapter-search-results="getChapterSearchResults"
            ></chapter-search-result-item>
    <search-results-list v-if="freeTextSearchResultsVerseArray.length > 0"
            :search-results="freeTextSearchResultsVerseArray"
            :display-options="displayOptions"
            @result-selected="sendResultQuery($event)"
            :is-loading="isLoading"
            :display-trop-to-search-result="displayTropToSearchResult"
    ></search-results-list>

        </div>

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

  import { Component, Vue } from 'vue-property-decorator';
  import {Book, Chapter, SearchCriteria, Verse} from "@/api/dto";
  import SearchInputForm from "@/Components/search/SearchInputForm.vue";
  import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
  import {toHebrewBookName, TORAH} from "@/api/TANAKH";
  import apifiClient from "@/api/apifiClient";
  import SearchResultsList from "@/Components/search/SearchResultsList.vue";
  import ChapterDisplay from "@/Components/search/ChapterDisplay.vue";
  import ChapterSearchResultItem from "@/Components/search/ChapterSearchResultItem.vue";


  @Component({
    components:{
        ChapterSearchResultItem,
      SearchInputForm,
      BaseCard,
      SearchResultsList,
      ChapterDisplay

    }
  })
  export default class SearchController extends Vue{


      //region members
    public searchCriteria = new SearchCriteria(); //will store all the search parameters the controller has to keep track of...
    public listOfChaptersInSelectedBook: Chapter[] = []; //will contain the list of chapters in the selected book, to be sent down to the form..
    public freeTextSearchResultsVerseArray: Verse[] = []; //will contain the results of our path search - I.e a chapter of verses...
    public displayOptions = false//we will need to let the searchResults know if it should display links, or we've already done with it, and are now moving to chapter display
    public displayResults = false //if we have a final chapter selected, we should only display the chapter and not the options
    public getChapterSearchResults = new Chapter();
    public isLoading = false;
    public displayTropToSearchResult = true;
    //endregion

      //region methods
    //an api call is made, which populates the chaptersList (with objects of type 'Chapter'), which is passed as a prop to the form.
    public getChapterList(): void{
      this.listOfChaptersInSelectedBook = [];
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
          this.listOfChaptersInSelectedBook.push(ch);
          //console.log(this.chaptersList);
        }
      });



    }

    //searching for a word when a search path has been specified
    public freeTextSearchWithOnlyOneCategory(searchPath: string){
      this.isLoading = true;
      this.freeTextSearchResultsVerseArray = [] //wipe the searchResults array clean, to get rid of any previous results...
      console.log("searching with path " + searchPath + " " + this.searchCriteria.searchTerm);
      //the api call:
      apifiClient.verseFreeTextSearch({
        customArgs: {
          validPathPrefixes:  [searchPath]
        },
       fetchAll:true,
        searchTerm: this.searchCriteria.searchTerm,
          sortBy:'path'

      }, `{
               content{
             humanReadablePath
             highlightedVerseSegments{
             finalSuffix
             plainHebrewFinalSuffix
             segments{
             highlightedKeyword
             plainHebrewHighlightedKeyword
             prefix
             plainHebrewPrefix
             }
             }
            chapter{
            path

            }
           fullHebrewText
           hebrewNumeral
           id
           number
           path
           } }`).then(res => {
        if (res['data'].verseFreeTextSearch.content.length > 0) {
          let i;

          for (i = 0; i < res['data'].verseFreeTextSearch.content.length; i++){
            //console.log(res['data'].verseFreeTextSearch.content[i]);
            const verse = new Verse();
            verse.hebrewNumeral = res['data'].verseFreeTextSearch.content[i].hebrewNumeral;
            verse.number = res['data'].verseFreeTextSearch.content[i].number;
            verse.path = res['data'].verseFreeTextSearch.content[i].path;
            verse.id = res['data'].verseFreeTextSearch.content[i].id;
            verse.humanReadablePath = res['data'].verseFreeTextSearch.content[i].humanReadablePath;
            verse.fullHebrewText = res['data'].verseFreeTextSearch.content[i].fullHebrewText;
            verse.chapter = res['data'].verseFreeTextSearch.content[i].chapter;
            verse.highlightedVerseSegments = res['data'].verseFreeTextSearch.content[i].highlightedVerseSegments;
            for(let i = 0; i < verse.highlightedVerseSegments.segments.length; i++)
                verse.highlightedVerseSegments.segments[i].id = i;
            this.freeTextSearchResultsVerseArray.push(verse);
          }
          console.log(this.searchCriteria.searchTerm + "controller");
            this.freeTextSearchResultsVerseArray.sort((a,b) => {
                if(a.path.includes('TORAH') && !b.path.includes('TORAH'))
                    return -1;
                else{
                    if(!a.path.includes('TORAH') && b.path.includes('TORAH'))
                        return 1;
                    else return 0;
                }
            })

          this.$emit("send-search-term-to-dashboard",this.searchCriteria.searchTerm);

        }
        else {
            if (this.freeTextSearchResultsVerseArray.length === 0) {
                this.$buefy.notification.open({
                    duration: 5000,
                    message: `No results have been found. Please enter a different search term, or expand the search path.`,
                    position: 'is-bottom-right',
                    type: 'is-danger',
                    hasIcon: true
                });
            }
        }
      this.isLoading = false;});
    }

    //searching for a word when no search path has been specified
    public freeTextSearchWithMultipleCategories(categories: string[]): void{
      console.log("Searching from:" + categories);
      this.freeTextSearchResultsVerseArray = []; //wipe the searchResults array clean..
      //the api call:
        this.isLoading = true;
      apifiClient.verseFreeTextSearch({
        customArgs: {
          validPathPrefixes: categories
        },
        fetchAll:true,
        searchTerm: this.searchCriteria.searchTerm,
          sortBy:'path'

      }, `{
            content{
             humanReadablePath
             highlightedVerseSegments{
             finalSuffix
             plainHebrewFinalSuffix
             segments{
             highlightedKeyword
             plainHebrewHighlightedKeyword
             prefix
             plainHebrewPrefix
             }
             }
            chapter{
            path

            }
           fullHebrewText
           hebrewNumeral
           id
           number
           path
           }}`).then(res => {
                if (res['data'].verseFreeTextSearch.content.length > 0) {
                  let i
                  for (i = 0; i < res['data'].verseFreeTextSearch.content.length; i++){
                    //console.log(res['data'].verseFreeTextSearch.content[i]);
                    const verse = new Verse();
                    verse.hebrewNumeral = res['data'].verseFreeTextSearch.content[i].hebrewNumeral;
                    verse.number = res['data'].verseFreeTextSearch.content[i].number;
                    verse.path = res['data'].verseFreeTextSearch.content[i].path;
                    verse.id = res['data'].verseFreeTextSearch.content[i].id;
                    verse.fullHebrewText = res['data'].verseFreeTextSearch.content[i].fullHebrewText;
                    verse.chapter =  res['data'].verseFreeTextSearch.content[i].chapter;
                    verse.humanReadablePath = res['data'].verseFreeTextSearch.content[i].humanReadablePath;
                    verse.highlightedVerseSegments = res['data'].verseFreeTextSearch.content[i].highlightedVerseSegments;
                    this.freeTextSearchResultsVerseArray.push(verse);
                  }
                    this.freeTextSearchResultsVerseArray.sort((a,b) => {
                        if(a.path.includes('TORAH') && !b.path.includes('TORAH'))
                            return -1;
                        else{
                            if(!a.path.includes('TORAH') && b.path.includes('TORAH'))
                                return 1;
                            else return 0;
                        }
                    });
                  this.$emit("send-search-term-to-dashboard",this.searchCriteria.searchTerm);
                }
                else{
                    if(this.freeTextSearchResultsVerseArray.length === 0)
                        this.$buefy.notification.open({
                            duration: 5000,
                            message: `No results found. Please enter a different search Term or expand the search parameters....`,
                            position: 'is-bottom-right',
                            type: 'is-danger',
                            hasIcon: true
                        });
                }
                this.isLoading = false;
              }

      );
      /*users.sort(function(a, b){
        if(a.firstname < b.firstname) { return -1; }
        if(a.firstname > b.firstname) { return 1; }
        return 0;
    })*/
      // a.path.includes('PROPHETS') && b.path.includes('TORAH')
        // a.path.includes('WRITINGS') && b.path.includes('WRITINGS')

    }

    //gets a chapter based on a complete path. can be called by the user, and is also called when a searchResult is selected.
    public getChapterFromPathSearch(path: string): void{
      console.log("getting chapter from path " + path);
      this.isLoading = true;
    //  this.searchResults = []; //wipe the searchResults array clean...
      //the api call:
      apifiClient.findChapterByUniquePath(path, `{
             hebrewNumeral
    id
    number
    path
    book{
    hebrewName
    }
    verses{
    highlightedVerseSegments{
    segments{
      prefix
      highlightedKeyword
      plainHebrewPrefix
      plainHebrewHighlightedKeyword
      tag{
      id
      }
    }
    finalSuffix
    plainHebrewFinalSuffix
    }
      fullHebrewText
      hebrewNumeral
      number
      path
      id
      searchableHebrewText
      mediaTags{
        id
      }

    }
            }`).then(res => {
        //  if(res['data'].findChapterByUniquePath.verses.length > 0) {//making sure there are results
      //  console.log(res)
        const ch = new Chapter();
        ch.id = res['data'].findChapterByUniquePath.id;
        ch.hebrewNumeral = res['data'].findChapterByUniquePath.hebrewNumeral;
        ch.number = res['data'].findChapterByUniquePath.number;
        ch.path = res['data'].findChapterByUniquePath.path;
        ch.book =  res['data'].findChapterByUniquePath.book;
        ch.verses = [];
        let i;
        for(i = 0; i < res['data'].findChapterByUniquePath.verses.length; i++ ) {
          ch.verses.push(res['data'].findChapterByUniquePath.verses[i]);
        }
        this.getChapterSearchResults = ch;
        this.getChapterSearchResults.verses.sort((a, b) => a.number - b.number);
        console.log("chapter aquired!!");
        this.$emit('display-selected-chapter',this.getChapterSearchResults);
        this.isLoading = false;
            }
      );
    }

    //decides which kind of search - free text or chapter based on path
    public generalSearchSorter(): void{
            //there are two archetypal cases:

            //1. There is no search term: (and the search will have to be via a direct path)
            if(this.searchCriteria.searchTerm === '' || this.searchCriteria.searchTerm === undefined)
            {
                let errorMessage = '';
                //there are two options: 1 - there is a valid direct path and we will load a chapter, 2- there is no valid path and we will send an error message to the user...
                //we must have only one category
                if(this.searchCriteria.category.length === 1 ){
                    //we also must have a book
                    if(this.searchCriteria.book !== ''){
                        //we also must have a chapter
                        if(this.searchCriteria.chapter !== ''){
                            //now we have a complete path -> Category/Book/Chapter and can make an api call:
                            this.freeTextSearchResultsVerseArray = [];
                            this.getChapterFromPathSearch(this.searchCriteria.book + "/" + this.searchCriteria.chapter.toString());
                        }
                        else{
                            errorMessage = "Please select a chapter"
                        }
                    }
                    else{
                        errorMessage = "Please select a book";
                    }
                }
                else{
                    errorMessage = "Please select one of the following: 1. Only one checkbox, followed by a book and chapter,\n 2. One or more checkboxes, and enter a search Term ";
                }
                if(errorMessage !== ''){
                    this.$buefy.notification.open({
                        duration: 10000,
                        message: errorMessage,
                        position: 'is-top-left',
                        type: 'is-warning',
                        hasIcon: true
                    });
                }
            }


            //2.There is a search Term:
          if(this.searchCriteria.searchTerm !== '' && this.searchCriteria.searchTerm !== undefined) {
                //Ensure the search term is in hebrew only
                if (!(/^[A-Za-z]\S*$/.test(this.searchCriteria.searchTerm))) {
                    let searchPath = "";

                    //Only one category has been selected:
                    if (this.searchCriteria.category !== [""] && this.searchCriteria.category !== undefined && this.searchCriteria.category.length === 1) {
                        searchPath = this.searchCriteria.category[0];
                    }

                    //a book has also been selected
                    if (this.searchCriteria.book !== "" && this.searchCriteria.book !== undefined) // if there is a book selected
                        searchPath = "/" + this.searchCriteria.book;

                    //a chapter has been selected
                    if (this.searchCriteria.chapter !== "" && this.searchCriteria.chapter !== undefined) //if there is chapter selected
                        searchPath += "/" + this.searchCriteria.chapter;


                    //more than one category has been selected (and therefore no book / chapter is selected)...
                    if (this.searchCriteria.category.length > 1) {
                        searchPath = "ALL";
                        //immediately a search without a path is initiated
                        this.freeTextSearchResultsVerseArray = [];
                        this.freeTextSearchWithMultipleCategories(this.searchCriteria.category);
                    }


                    //the resulting path will now be sent along with the search term to the api call:
                    if (searchPath !== "" && searchPath !== "ALL")
                    {  this.freeTextSearchResultsVerseArray = [];
                    this.freeTextSearchWithOnlyOneCategory(searchPath);}

                    // we have a search term but not a valid path
                    if (searchPath === "") {
                        this.$buefy.notification.open({
                            duration: 5000,
                            message: `Invalid Search Path. Please enter a Valid search path.`,
                            position: 'is-top-left',
                            type: 'is-danger',
                            hasIcon: true
                        });
                    }
                }
                // a non hebrew Search Term was entered:
                else{
                    this.$buefy.notification.open({
                        duration: 10000,
                        message: 'Search Terms Must Be In Hebrew Only',
                        position: 'is-top-left',
                        type: 'is-danger',
                        hasIcon: true
                    });
                }
            }

    }

    //these functions get the data from the form and update the fields of the searchCriteria object accordingly...
    public updateCategorySelection(selectedCategory: string[]): void{
      this.searchCriteria.category = selectedCategory;
      this.listOfChaptersInSelectedBook = [];
      this.searchCriteria.book = "";
      this.searchCriteria.passuk = "";
      this.searchCriteria.chapter = "";
    }

    public updateBookSelection(selectedBook: string): void{
      this.searchCriteria.book = selectedBook;
      this.searchCriteria.passuk = "";
        this.searchCriteria.chapter = "";

      //console.log(this.searchCriteria.book + " from controller");
      this.getChapterList();
    }

    public updateChapterSelection(selectedChapter: string): void{
      this.searchCriteria.chapter = selectedChapter;
     // console.log(this.searchCriteria.book + "/" + this.searchCriteria.chapter + " from controller");
      //this.getChapterFromPathSearch(this.searchCriteria.book + "/" + this.searchCriteria.chapter);
    }


    public updateSearchTermSelection(searchTerm: string): void{
            this.searchCriteria.searchTerm = searchTerm;
            this.getChapterSearchResults = new Chapter();
            console.log(this.searchCriteria.searchTerm + " from controller, path: " + this.searchCriteria.book + "/ " + this.searchCriteria.chapter);
            this.generalSearchSorter();
    }

    public updateVerseSelectionAndSendVerseToPassukDisplay(selectedVerseNumber: string): void{
        //  console.log("looking for passuk " + this.searchCriteria.book + "/" + this.searchCriteria.chapter + "/" + selectedVerseNumber);
          for(let i = 0; i < this.getChapterSearchResults.verses.length; i++){
              if(this.getChapterSearchResults.verses[i].number === parseInt(selectedVerseNumber)){
                  //console.log(this.getChapterSearchResults.verses[i]);
                  this.$emit('send-selected-verse-to-passuk-display',this.getChapterSearchResults.verses[i] );
              }
          }
      }


    //gets the selected verse from the search Result component, and sends it to the getChapterFromPath function.
    public sendResultQuery(pathArr: string[]): void{
        //pathArr[0] = chapter path
     this.getChapterFromPathSearch(pathArr[0]);
     //pathArr[1] = verse number (in string format)
     setTimeout(()=>{this.updateVerseSelectionAndSendVerseToPassukDisplay(pathArr[1])},1000);

    }

    public clearAllResults(): void{
      this.searchCriteria.searchTerm = "";
      this.searchCriteria.chapter = "";
      this.searchCriteria.book = "";
      this.searchCriteria.category = [];
      this.searchCriteria.passuk = "";
      this.listOfChaptersInSelectedBook = [];
      this.freeTextSearchResultsVerseArray = [];
    }


  public changeTrop(trop: boolean): void{
        this.displayTropToSearchResult = trop;
        this.$emit('change-trop',trop);
  }




    //when the page is created we will automatically display Genesis/1 for chapter, and the first verse of that chapter as the selected verse
    created(){
        this.getChapterFromPathSearch('TORAH/Genesis/1');

        //a timeout is necessary because we want to make sure that the chapter is loaded first
       setTimeout(()=>{this.updateVerseSelectionAndSendVerseToPassukDisplay('1')},2000);
    }

    //endregion







  }
</script>

<style scoped>

</style>