<template>
  <!-- the search Input form allows to select a category, a book and chapter, as well as a free text search -->
  <v-container style="background-color:#4f4f4f; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);  ">
    <!--the header for the search form-->
    <v-row justify="center" style="background-color: #0d68ce;">
      <h1 class="h1" style="color: white;  font-size: 2.2em; font-family: 'Trebuchet MS'" v-if="englishSearch">{{maayanSearch}}</h1>
      <h1 class="h1" style="color: white;  font-size: 2.2em; font-family: Arial" v-else>חיפוש מעיין</h1>
    </v-row>

    <!--row #1 - switches to change to Hebrew/English, and to turn off trop and nikud - in 2 columns-->
    <v-row>
      <v-col sm5 class="d-flex justify-center">
        <b-switch  :value="true" @input="changeLanguage" style="max-width: 70px">
          <strong v-if="!englishSearch" style="color: white; font-family: 'Trebuchet MS';font-size: 0.9vm">E</strong>
          <strong v-else style="color: white; font-family: Arial ;font-size: 0.9vm">ע</strong>
        </b-switch>
      </v-col>
      <v-col  sm5 class="d-flex justify-center">
        <b-switch type="is-success" @input="changeTrop" :value="true" style="max-width: 150px; "><strong v-if="englishSearch" style="color: white;font-size: 14px;">Nikud</strong>
          <strong v-else style="color: white;font-size: 0.9vm; font-family: Arial">עם\בלי ניקוד</strong>
        </b-switch>
      </v-col>
    </v-row>

    <!--search instructions-->
    <v-col cols="12">
      <p v-if="englishSearch" style="color: white; font-family: 'Trebuchet MS'" >{{explantion}}</p>
      <p v-else style="color: white; font-family: Arial" dir="rtl" >הקלד טקסט לחיפוש בכל התנך, או סמן אופצייה אחת לחיפוש לפי ספר\פרק</p>
    </v-col>

    <!-- Search bar and check boxes element (rows 2,3)-->
    <div id='stacks_out_209' class='stacks_out'  @keypress.enter="updateSearchTermSelection">
      <!--row #2 - search bar with built-in hebrew keypad-->
      <v-row style="margin-top: 5px"  v-if="!displayKeypad"  justify="center" no-gutters>
<v-col cols="2" class="d-flex justify-end">
        <v-tooltip
            bottom
        >
          <template v-slot:activator="{ on }">
            <v-btn
                style="margin-right: 2px"
                small
                fab
                color="primary"
                dark
                @click="updateSearchTermSelection"
            >
              <v-icon v-on="on" >
                mdi-magnify
              </v-icon>
            </v-btn>
          </template>
          {{ searchLabel }}
        </v-tooltip>
</v-col>
        <v-col cols="9" class="d-flex justify-start">
            <v-text-field
                ref="searchInputBar"
                dense
                full-width
                :placeholder="searchInputLabel"
                solo
                background-color="white"
                filled
                :key="reloadKey"  dir="rtl" v-model="tempSearchTerm" :disabled="!torahSelected && !prophetsSelected && !writingsSelected"  style="max-width: 13vw"
            >
              <template v-slot:prepend-inner>
                <v-btn
                    color="secondary"
                  x-small

                    @click="displayKeypad = !displayKeypad" v-if="!displayKeypad"
                >
                  <v-icon v-on="on" >
                    mdi-keyboard
                  </v-icon></v-btn>
              </template>

            </v-text-field>
        </v-col>




      </v-row>
      <!--row #2.1 - hebrew keypad if activated-->
      <v-layout row v-if="displayKeypad" >
        <v-flex md11 lg11 xl11>
          <v-card style="background-color: lightgray">
            <v-card-title>
              <button class="delete" @click="displayKeypad = !displayKeypad" ></button>
              <v-btn x-small @click="tempSearchTerm = ''">CLR</v-btn>
            </v-card-title>
            <v-card-subtitle>
              <b-input ref="kepadSearchInputBar" :key="reloadKey" v-bind:placeholder="searchLabel" dir="rtl" v-model="tempSearchTerm" :disabled="!torahSelected && !prophetsSelected && !writingsSelected" />
            </v-card-subtitle>
            <v-card-text>
              <hebrew-keypad
                  @add-character="addChar($event)"
              ></hebrew-keypad>
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>

      <!--row #3  - 3 checkboxes to select torah, neviim or ketuvim-->
      <v-row no-gutters justify="center"  >

        <!--torah cb-->


        <v-checkbox v-model="torahSelected" dark
                    class="shrink mr-2 mt-0" @click="updateCategorySelection('TORAH')" >
          <template v-slot:label>
            <strong style="color:ghostwhite; font-family: 'Trebuchet MS';  font-size: 0.8vw" v-if="englishSearch">
              TORAH
            </strong>
            <strong style="color:ghostwhite; font-family: Arial;  font-size: 0.8vw" v-if="!englishSearch">
              תורה
            </strong>
          </template>
        </v-checkbox>


        <!--prophets cb-->

        <v-checkbox v-model="prophetsSelected" dark
                    class="shrink mr-2 mt-0" @click="updateCategorySelection('PROPHETS')">
          <template v-slot:label>
            <strong style="color:ghostwhite; font-family: 'Trebuchet MS';  font-size: 0.8vw" v-if="englishSearch">
              PROPHETS
            </strong>
            <strong style="color:ghostwhite; font-family: Arial ;  font-size: 0.8vw" v-if="!englishSearch">
              נביאים
            </strong>
          </template>

        </v-checkbox>


        <!--writings cb-->


        <v-checkbox v-model="writingsSelected"    class="shrink mr-2 mt-0" dark
                    @click="updateCategorySelection('WRITINGS')">

          <template v-slot:label>
            <strong style="color:ghostwhite; font-family: 'Trebuchet MS'; font-size: 0.8vw" v-if="englishSearch">
             WRITINGS
            </strong>
            <strong style="color:ghostwhite; font-family: Arial;  font-size: 0.8vw" v-if="!englishSearch">
              כתובים
            </strong>
          </template>
        </v-checkbox>


      </v-row>
    </div>

    <!--row #4 - dropdown list to select book once (if) one (only) category selected (i.e - torah, neviim or ketuvim-->
    <v-row  >
      <v-col class="d-flex justify-center">
      <div class="sefer-dd" >

        <b-select :placeholder="bookLabel" dir="rtl" :expanded="true"   v-model="searchCriteria.book" :disabled="!bookEnabled" @input="updateBookSelection">
          <option
              style="font-family: Arial; font-size: 12px"
              v-for="book in bookList"
              :value="book"
              :key="book">
            {{ getHebNameOfBook(book) }}
          </option>
        </b-select>


      </div> <!-- sefer dd-->
      </v-col>
      <v-col class="d-flex justify-start">
      <div class="perek-dd" style="max-width: 100px"  @keypress.enter="updateSearchTermSelection()">
        <b-input :loading="chapLoading" dir="rtl" :placeholder="chapterLabel" @input="updateChapterSelection" v-model="chapterInput" :disabled="!searchCriteria.book|| !bookEnabled" type="text" style="max-width: 120px" ></b-input>
      </div> <!-- perek dd-->
      </v-col>
<v-col  class="d-flex justify-center">
  <b-button type="is-danger" class="clear_btn" @click="clearAllResults">{{clearLabel}}</b-button>
</v-col>
    </v-row>




  </v-container>
</template>

<script lang = "ts">
import {Component, Prop, Vue,Watch} from 'vue-property-decorator';
import {Book, Chapter, SearchCriteria, Verse} from "@/api/dto";
import {TORAH,WRITINGS,PROPHETS,hebrewBooksInTorah,HEBREW_NAMES_DICT,toHebrewBookName,
  hebrewBooksInProphets,hebrewBooksInWritings,toEnglishBookName,stripPrefix} from "@/api/TANAKH";
import HebrewKeypad from "@/Components/BaseComponents/HebrewKeypad.vue";
@Component({
  components: {HebrewKeypad}
})
//:disabled="!(searchCriteria.searchTerm === '') ||!((searchCriteria.category === '')&& searchCriteria.book === '')"
export default class SearchInputForm extends Vue{
//region members
  //this object is shared via emits to the controller
  public searchCriteria = new SearchCriteria();


  //the list of chapters depends on the chosen category/book path, and is populated by an api call in the controller
  @Prop({default: 'Example'})
  chaptersList: Chapter[];
  @Prop({default: 'Example'})
  getChapterSearchResults: Chapter;

  public torahSelected = true;
  public prophetsSelected = true;
  public writingsSelected = true;
  public bookList: string[] = [];
  public chapterSelected = false;
  public bookEnabled = false;
  public searchEnabled = true;
  public chapterInput = '';
  public chapLoading = false;
  public displayKeypad = false;
  public displayTrop = true;
  public tempSearchTerm = "";
  //endregion

  //region language Labels

  public explantion = "Enter text to search all of Tanach or choose only one checkbox to search by book/chapter";
  public maayanSearch = "MA'AYAN SEARCH";
  public allLabel = "ALL";
  public writingsLabel = "KETUVIM";
  public prophetsLabel = "NEVI’IM";
  public torahLabel = "TORAH";
  public freeSearchLabel = "WORD SEARCH";
  public bookLabel = "Book";
  public chapterLabel = "Chapter";
  public verseLabel = "VERSE";
  public searchLabel = "Search";
  public clearLabel = "Clear";
  public keyBoardLabel = "keyboard";
  public searchInputLabel = "Keyword"

  public englishSearch = true;

  public changeLanguage(): void {
    this.englishSearch = !this.englishSearch;
    if(!this.englishSearch) {
      this.bookLabel = "ספר";
      this.searchLabel = "חיפוש";
      this.clearLabel = "נקה";
      this.chapterLabel = "פרק"
      this.keyBoardLabel = "מקלדת"
      this.searchInputLabel = "מילת חיפוש"

    }
    if(this.englishSearch) {
      this.bookLabel = "Book";
      this.searchLabel = "Search";
      this.clearLabel = "Clear";
      this.chapterLabel = "Chapter"
      this.keyBoardLabel = "keyboard"
      this.searchInputLabel = "Keyword"

    }
  }



//endregion

  //region methods



  public updateCategorySelection(category: string): void{
    this.searchCriteria.chapter = "";
    this.chapterInput = this.searchCriteria.chapter;
    this.bookList = [];
    //put the newly chosen category in the array, while making sure to avoid duplicate values
    let repeat = false;
    for(const cat in this.searchCriteria.category){
      if(cat === category)
        repeat = true;
    }
    if(!repeat){
      this.searchCriteria.category.push(category);
    }
    //remove all unselected items
    if(!this.torahSelected){
      for (let i =  this.searchCriteria.category.length - 1; i >= 0; i--) {
        if ( this.searchCriteria.category[i] === 'TORAH') {
          this.searchCriteria.category.splice(i, 1);
        }
      }
    }
    if(!this.prophetsSelected){
      for (let i =  this.searchCriteria.category.length - 1; i >= 0; i--) {
        if ( this.searchCriteria.category[i] === 'PROPHETS') {
          this.searchCriteria.category.splice(i, 1);
        }
      }
    }
    if(!this.writingsSelected){
      for (let i =  this.searchCriteria.category.length - 1; i >= 0; i--) {
        if ( this.searchCriteria.category[i] === 'WRITINGS') {
          this.searchCriteria.category.splice(i, 1);
        }
      }
    }

    //the book and chapter controls are disabled if there is more than 1 category or less than 1
    if(this.searchCriteria.category.length !== 1){
      this.bookEnabled = false;
      this.searchCriteria.book = null;
    }
    else{
      //the book and chapter controls are enabled if there is only one selected category.Also, we load the booklist.
      this.bookEnabled = true;
      this.getBookList(this.searchCriteria.category[0]);
    }
    this.$emit('update-category-selection', this.searchCriteria.category);
  }




  public updateBookSelection(): void{ //update to the book selected by the user, and send it up to the controller...
    //wipe any previously selected chapter
    this.searchCriteria.chapter = "";
    console.log(this.chapterInput);
    this.chapLoading = true;
    this.chapterInput = this.searchCriteria.chapter;


    this.$emit('update-book-selection',this.searchCriteria.book);


  }

  public updateChapterSelection(): void{ //update to the chapter selected by the user, and send it up to the controller...
    this.searchCriteria.chapter = this.chapterInput;
    console.log(this.searchCriteria.chapter)
    let chap = false;
    for(let i = 0; i < this.chaptersList.length; i++){
      if(parseInt(this.searchCriteria.chapter) === this.chaptersList[i].number)
        chap = true;
    }
    if(chap) {
      this.$emit('update-chapter-selection', this.searchCriteria.chapter);
      this.chapterSelected = true;
    }
    else{
      this.$buefy.notification.open({
        duration: 1000,
        message: `Invalid Chapter Number.`,
        position: 'is-top-left',
        type: 'is-danger',
        hasIcon: true
      });}
  }


  /*public updateVerseSelection(): void{
      let verse = false;
      for(let i = 0; i < this.getChapterSearchResults.verses.length; i++){
          if(parseInt(this.searchCriteria.passuk) === this.getChapterSearchResults.verses[i].number)
              verse = true;
      }
      if(verse)
          this.$emit('update-verse-selection', this.searchCriteria.passuk);
      else{
          this.$buefy.notification.open({
              duration: 1000,
              message: `Invalid Verse Number.`,
              position: 'is-bottom-right',
              type: 'is-danger',
              hasIcon: true
          });}

  }*/

  public updateSearchTermSelection(): void{ //update to the searchTerm selected by the user, and send it up to the controller...
    this.$emit('update-search-term-selection',this.searchCriteria.searchTerm);
  }


  public getBookList(category: string): void{
    switch (category) {
      case "TORAH":
        for(const [key, value] of Object.entries(TORAH)) {
          this.bookList.push(`${value}`.toString());
          //  console.log(toHebrewBookName(`${value}`.toString()));
        }
        break;
      case "PROPHETS":
        for(const [key, value] of Object.entries(PROPHETS)) {
          this.bookList.push(`${value}`);
          // console.log(toHebrewBookName(`${value}`));
        }
        break;
      case "WRITINGS":
        for(const [key, value] of Object.entries(WRITINGS)) {
          this.bookList.push(`${value}`.toString());
          //console.log(toHebrewBookName(`${value}`.toString()));
        }
        break;

    }

  }

  public getHebNameOfBook(book: string): string{
    return toHebrewBookName(book);
  }

  public clearAllResults(): void{
    this.searchCriteria.category = [];
    this.updateCategorySelection('TORAH');
    this.updateCategorySelection('PROPHETS');
    this.updateCategorySelection('WRITINGS');
    this.searchCriteria.book = null;
    this.searchCriteria.chapter = "";
    this.searchCriteria.searchTerm = "";
    this.tempSearchTerm = "";
    this.chapterSelected = false;
    this.bookList = [];
    this.torahSelected = true;
    this.writingsSelected = true;
    this.prophetsSelected = true;
    this.chapterInput = this.searchCriteria.chapter;

    this.$emit('clear-all-results');
  }

  public addChar(letter: string): void{
    if(letter === 'D'){
      this.tempSearchTerm =  this.tempSearchTerm.slice(0,-1);
    }
    else{
      this.tempSearchTerm += letter;
    }

      (this.$refs["kepadSearchInputBar"] as HTMLElement).focus();

  }

  public changeTrop(): void{
    this.displayTrop = !this.displayTrop;
    this.$emit('change-trop', this.displayTrop);
  }

  mounted(){
    this.searchCriteria.category = [];
    this.updateCategorySelection('TORAH');
    this.updateCategorySelection('PROPHETS');
    this.updateCategorySelection('WRITINGS');
    this.searchCriteria.book = null;

  }


  public mapToHebrewSearchTerm(): void{

    //letters in english that map to a hebrew letter:
    this.tempSearchTerm = this.tempSearchTerm.replace("t", "א")
    this.tempSearchTerm = this.tempSearchTerm.replace("c","ב")
    this.tempSearchTerm = this.tempSearchTerm.replace("d","ג")
    this.tempSearchTerm = this.tempSearchTerm.replace("s","ד")
    this.tempSearchTerm = this.tempSearchTerm.replace("v","ה")
    this.tempSearchTerm = this.tempSearchTerm.replace("u","ו")
    this.tempSearchTerm = this.tempSearchTerm.replace("z","ז")
    this.tempSearchTerm = this.tempSearchTerm.replace("j","ח")
    this.tempSearchTerm = this.tempSearchTerm.replace("y","ט")
    this.tempSearchTerm = this.tempSearchTerm.replace("h","י")
    this.tempSearchTerm = this.tempSearchTerm.replace("f","כ")
    this.tempSearchTerm = this.tempSearchTerm.replace("k","ל")
    this.tempSearchTerm = this.tempSearchTerm.replace("i","ן")
    this.tempSearchTerm = this.tempSearchTerm.replace("l","ך")
    this.tempSearchTerm = this.tempSearchTerm.replace(";","ף")
    this.tempSearchTerm = this.tempSearchTerm.replace(".","ץ")
    this.tempSearchTerm = this.tempSearchTerm.replace("n","מ")
    this.tempSearchTerm = this.tempSearchTerm.replace("b","נ")
    this.tempSearchTerm = this.tempSearchTerm.replace("x","ס")
    this.tempSearchTerm = this.tempSearchTerm.replace("g","ע")
    this.tempSearchTerm = this.tempSearchTerm.replace("p","פ")
    this.tempSearchTerm = this.tempSearchTerm.replace("m","צ")
    this.tempSearchTerm = this.tempSearchTerm.replace("e","ק")
    this.tempSearchTerm = this.tempSearchTerm.replace("r","ר")
    this.tempSearchTerm = this.tempSearchTerm.replace("a","ש")
    this.tempSearchTerm = this.tempSearchTerm.replace(",","ת")

    //letters in english that don't map to a hebrew letter:
    this.tempSearchTerm = this.tempSearchTerm.replace(/w/ig,'')
    this.$nextTick(() => {
      (this.$refs["searchInputBar"] as HTMLElement).focus();

    });
    this.tempSearchTerm = this.tempSearchTerm.replace(/q/ig,'')
    this.$nextTick(() => {
      (this.$refs["searchInputBar"] as HTMLElement).focus();

    });
    console.log(this.tempSearchTerm)

  }


  @Watch('chaptersList')
  onchanged(){
    if(this.chaptersList.length > 0){
      this.chapLoading = false;
    }
  }

  public reloadKey = 0;
  @Watch('tempSearchTerm')
  atChange(){
    if(this.tempSearchTerm.includes("q") || this.tempSearchTerm.includes("w")) {
      this.tempSearchTerm = this.tempSearchTerm.replace(/w/ig, '')
      this.tempSearchTerm = this.tempSearchTerm.replace(/q/ig, '')
      this.reloadKey++;
    }
  this.mapToHebrewSearchTerm();

   this.searchCriteria.searchTerm = this.tempSearchTerm;
  }



//endregion


}
</script>

<style scoped   >
</style>