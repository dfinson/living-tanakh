<template>
<!-- the search Input form-->
    <div id='stacks_out_339' class='stacks_out' >
        <div id='stacks_in_339' class='stacks_in com_elixir_stacks_foundryJumbotron_stack' >
            <div class="nav_pairing" style="background-color:#4f4f4f; text-align: center;   box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);  ">

                        <h1 class="h1" style="color: white; background-color: #0d68ce; text-align: center; font-size: 2.2em; font-family: 'Trebuchet MS'" v-if="englishSearch">{{maayanSearch}}</h1>
                <h1 class="h1" style="color: white; background-color: #0d68ce; text-align: center; font-size: 2.2em; font-family: Arial" v-else>חיפוש מעיין</h1>

                <div class="field" style="display: inline-block">


                                <b-switch  :value="true" @input="changeLanguage" style="margin-right: 70px; max-width: 70px">
                                    <strong style="color: white; font-family: 'Trebuchet MS';font-size: 12px">E</strong><strong style="color: white; font-family: Arial ;font-size: 14px">/ע</strong>
                                </b-switch>
                                <b-switch type="is-success" @input="changeTrop" :value="true" style="max-width: 150px; "><strong v-if="englishSearch" style="color: white;font-size: 14px;">Nikud</strong>
                                    <strong v-else style="color: white;font-size: 14px; font-family: Arial">עם\בלי ניקוד</strong>
                                </b-switch>
                            </div>
                <p v-if="englishSearch" style="color: white; font-family: 'Trebuchet MS'" >{{explantion}}</p>
                <p v-else style="color: white; font-family: Arial" >הקלד טקסט לחיפוש בכל התנך, או סמן אופצייה אחת לחיפוש לפי ספר\פרק</p>
                        <div id='stacks_out_209' class='stacks_out'>
    <div class="form-horizontal" role="form"   action="#stacks_in_372"  style="position: relative">

        <div class="form-group " >
            <div id='stacks_in_209' class='stacks_in com_elixir_stacks_foundryButtonGroup_stack' >
                <div class="button-group-wrapper  text-xs-center">
                    <div class="button_group_wrapper btn-group " role="group" >
                        <div class="free-search-input" >
                                <b-field style="margin-left: 15px; display: inline-block;">
                                    <b-button  style="width:50px; height: 39px;" type="is-info" @click="displayKeypad = !displayKeypad" >עברית</b-button>
                                    <div class="notification" v-if="displayKeypad">
                                        <button class="delete" @click="displayKeypad = !displayKeypad"></button>
                                        <b-input v-bind:placeholder="searchLabel" dir="rtl" v-model="tempSearchTerm" :disabled="!torahSelected && !prophetsSelected && !writingsSelected" />
                                        <hebrew-keypad
                                                @add-character="addChar($event)"
                                        ></hebrew-keypad>
                                    </div>
                                    <b-input v-if="!displayKeypad" v-bind:placeholder="searchLabel" dir="rtl" v-model="tempSearchTerm" :disabled="!torahSelected && !prophetsSelected && !writingsSelected" style=" max-width: 150px; float: right" />
                                </b-field>
                            <!--<b-collapse :open="false" aria-id="contentIdForA11y1">
                                <b-switch
                                        slot="trigger"
                                        aria-controls="contentIdForA11y1"></b-switch>
                                <div class="notification">
                                    <div class="content">
                                      <hebrew-keypad></hebrew-keypad>
                                    </div>
                                </div>
                                src="https://maayan-assets.s3.eu-central-1.amazonaws.com/hebkeyb.JPG"
                            </b-collapse>-->


                        </div>
                        <div class="btn-group-stacks_in_213_dropdown" style="margin-top: 10px" role="group" >
                            <strong style="color:ghostwhite; font-family: 'Trebuchet MS'" v-if="englishSearch">
                                {{torahLabel}}
                            </strong>
                            <strong style="color:ghostwhite; font-family: Arial;" v-else>
                                תורה
                            </strong>
                            <b-checkbox v-model="torahSelected"
                                        type="is-white" @input="updateCategorySelection('TORAH')" >

                            </b-checkbox>
                        </div> <!-- writings dd-->
                        <div class="btn-group-stacks_in_221_dropdown" role="group">
                           <strong style="color:ghostwhite; font-family: 'Trebuchet MS'" v-if="englishSearch">
                                {{prophetsLabel}}
                            </strong>
                            <strong style="color:ghostwhite; font-family: Arial ;" v-else>
                               נביאים
                            </strong>
                            <b-checkbox v-model="prophetsSelected"
                                        type="is-white" @input="updateCategorySelection('PROPHETS')">

                            </b-checkbox>
                        </div><!-- prophets dd-->
                        <div  class="btn-group-stacks_in_224_dropdown" role="group">
                            <strong style="color:ghostwhite; font-family: 'Trebuchet MS'" v-if="englishSearch">
                                {{writingsLabel}}
                            </strong>
                            <strong style="color:ghostwhite; font-family: Arial;" v-else>
                                כתובים
                            </strong>
                            <b-checkbox v-model="writingsSelected"
                                        type="is-white" @input="updateCategorySelection('WRITINGS')">

                            </b-checkbox>

                        </div><!-- torah dd-->
                    </div> <!-- dropdown buttons-->
                </div>

            </div>
        </div> <!-- dd's for t/n/k-->

        <div class="second-dd-list" >
            <div class="sefer-dd">
                <div class="custom-select">
                        <b-select :placeholder="bookLabel" :expanded="true" style="max-width: 120px; margin-left: 115px; " v-model="searchCriteria.book" :disabled="!bookEnabled" @input="updateBookSelection">
                            <option
                                    style="font-family: Arial; font-size: 15px"
                                    v-for="book in bookList"
                                    :value="book"
                                    :key="book">
                                {{ getHebNameOfBook(book) }}
                            </option>
                        </b-select>
                </div>
            </div> <!-- sefer dd-->

            <div class="perek-dd ">

                   <b-input :loading="chapLoading" :placeholder="chapterLabel" @input="updateChapterSelection" v-model="chapterInput" :disabled="!searchCriteria.book|| !bookEnabled" type="text" style="max-width: 122px; margin-top: 10px" ></b-input>
            </div> <!-- perek dd-->



        </div> <!--sefer, perek and passuk dd's-->

      <!-- Free text search-->
        <!-- send and clear buttons-->

        <div  class="search-and-clear-bttns" style="margin-right: 45px">
            <b-button type="is-danger" class="clear_btn" @click="clearAllResults" style="width: 85px" >{{clearLabel}}</b-button>
            <b-button type="is-info" class="search_btn" @click="updateSearchTermSelection" v-on:keydown.native.enter="updateSearchTermSelection"  >{{searchLabel}}</b-button>
            </div>
    </div>
                        </div>
                    </div>
                </div>
            </div>
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

    public explantion = "Enter text to search all of Tanach or chose only one checkbox to search by book / chapter";
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

    public englishSearch = true;

    public changeLanguage(): void {
        this.englishSearch = !this.englishSearch;
        if(!this.englishSearch) {
            this.bookLabel = "ספר";
            this.searchLabel = "חיפוש";
            this.clearLabel = "נקה";
            this.chapterLabel = "פרק"

        }
        if(this.englishSearch) {
            this.bookLabel = "Book";
            this.searchLabel = "Search";
            this.clearLabel = "Clear";
            this.chapterLabel = "Chapter"

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
        this.searchCriteria.book = "";
        this.searchCriteria.chapter = "";
        this.searchCriteria.searchTerm = "";
        this.chapterSelected = false;
        this.bookList = [];
        this.torahSelected = false;
        this.writingsSelected = false;
        this.prophetsSelected = false;
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


    @Watch('chaptersList')
        onchanged(){
        if(this.chaptersList.length > 0){
            this.chapLoading = false;
        }
    }

        @Watch('tempSearchTerm')
        atChange(){
            this.searchCriteria.searchTerm = this.tempSearchTerm;
        }



//endregion


}
</script>

<style scoped   >
.form-horizontal{

}
.btn-group stacks_in_213_dropdown{
    white-space: nowrap;
    display: inline-block;

}
.btn-group-stacks_in_221_dropdown{
    display: inline-block;

}
.btn-group-stacks_in_213_dropdown{
    display: inline-block;

}
.btn-group-stacks_in_224_dropdown{
    display: inline-block;

    }
.second-dd-list{
    white-space: nowrap;
    margin-left: 10px;

}


.perek-dd{
    display: inline-block;
}
.passuk-dd{
    display: inline-block;
}
    .search-and-clear-bttns{
        margin-top: 15px;
        margin-left: 50px;
        margin-bottom: 80px;
    }

    .clear_btn{
        margin-left:20px;
    }
    .search_btn{
        margin-left: 5px;
        margin-right: 20px;
    }
    .nav_pairing{

        transition-delay: 2s;
    }



</style>