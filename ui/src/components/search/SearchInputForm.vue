<template>

    <div class="sidebar-page">
    <b-menu>
        <h1 class="card-header-title-is-centered" >Search Options:</h1>
        <b-field label="Select Category" >
            <b-select placeholder="Select a name"  v-model="searchCriteria.category" @input="updateCategorySelection">
                <option
                        v-for="category in categories"
                        :value="category"
                        :key="category">
                    {{ getHebNameOfCategory(category) }}
                </option>
            </b-select>
        </b-field>
        <b-field label="Select Book">
            <b-select placeholder="Select a name" v-model="searchCriteria.book" @input="updateBookSelection">
                <option
                        v-for="book in bookList"
                        :value="book"
                        :key="book">
                    {{ getHebNameOfBook(book) }}
                </option>
            </b-select>
        </b-field>
        <b-field label="Select Chapter">
            <b-select placeholder="Select a name" v-model="searchCriteria.chapter" @input="updateChapterSelection">
                <option
                        v-for="chapter in chaptersList"
                        :value="chapter.number"
                        :key="chapter.id">
                    {{ chapter.hebrewNumeral }}
                </option>
            </b-select>
        </b-field>
        <b-field label="Free Search">
            <b-input v-model="searchCriteria.searchTerm"></b-input>
        </b-field>
        <b-button type="is-primary is-light" @click="updateSearchTermSelection">Search</b-button>
    </b-menu>
    </div>
</template>

<script lang = "ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Book,Chapter,SearchCriteria} from "@/api/dto";
import {TORAH,WRITINGS,PROPHETS,hebrewBooksInTorah,HEBREW_NAMES_DICT,toHebrewBookName,
    hebrewBooksInProphets,hebrewBooksInWritings,toEnglishBookName,stripPrefix} from "@/api/TANAKH";

@Component
export default class SearchInputForm extends Vue{

    //this object is shared via emits to the controller
    public searchCriteria = new SearchCriteria()

    //the list of chapters depends on the chosen category/book path, and is populated by an api call in the controller
    @Prop({default: 'Example'})
        chaptersList: Chapter[]

    //
     public categories = ["TORAH","PROPHETS","WRITINGS"]
     public bookList: string[] = []



    public updateCategorySelection(): void{  // update to the category selected by the user, and send it up to the controller...
        this.$emit('update-category-selection',this.searchCriteria.category);
        console.log(this.searchCriteria.category);
            this.getBookList();

    }

    public updateBookSelection(): void{ //update to the book selected by the user, and send it up to the controller...
        console.log("updating book selection to " + this.searchCriteria.book)
        this.$emit('update-book-selection',this.searchCriteria.book);
    }

    public updateChapterSelection(): void{ //update to the chapter selected by the user, and send it up to the controller...
       /* for(const [key,value] of Object.entries(this.searchCriteria.chapter)) {
            if ((`${key}` === "number"))
                this.searchCriteria.chapter.number = parseInt(`${value}`);
                }*/


        this.$emit('update-chapter-selection',this.searchCriteria.chapter);
    }

    public updateSearchTermSelection(): void{ //update to the searchTerm selected by the user, and send it up to the controller...
        this.$emit('update-search-term-selection',this.searchCriteria.searchTerm);
    }

    public getBookList(): void{
        this.bookList = [];
        this.searchCriteria.chapter = "";
        const tempList: string[] = []
        switch (this.searchCriteria.category) {
            case "TORAH":
                for(const [key, value] of Object.entries(TORAH)) {
                    this.bookList.push(`${value}`.toString());
                    //console.log(toHebrewBookName(`${value}`.toString()));
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

    public getHebNameOfCategory(category: string){
        let returnString = ""
        switch (category) {
            case "TORAH":
                returnString =  "תורה";
                break;
            case "PROPHETS":
                returnString =  "נביאים";
                break;
            case "WRITINGS":
                returnString =  "כתובים";
                break;
        }
        return returnString;
    }







}
</script>

<style scoped>
</style>