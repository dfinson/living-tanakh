<template>
    <div>
        <h1>Search Options:</h1>
        <span>Select Category </span>
        <select v-model="searchCriteria.category" @change="updateCategorySelection">
            <option  v-for="category in categories" v-bind:value="category" :key="category" > {{category}}</option>
        </select>
        <span>Select Book </span>
        <select v-model="searchCriteria.book" @change="updateBookSelection">
            <option  v-for="book in bookList" v-bind:value="book" :key="book" > {{book}}</option>
        </select>
        <span>Select Chapter </span>
        <select v-model="searchCriteria.chapter" @change="updateChapterSelection">
            <option  v-for="chapter in chaptersList" v-bind:value="chapter.number" :key="chapter.number" > {{chapter.hebrewNumeral}}</option>
        </select>
        <span> Free Text Search:</span>
        <input v-model="searchCriteria.searchTerm" placeholder="Example - 'בראשית'" >
        <button @click="updateSearchTermSelection">Search</button>


    </div>
</template>

<script lang = "ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Book,Chapter,SearchCriteria} from "@/api/dto";
import {TORAH,WRITINGS,PROPHETS,hebrewBooksInTorah,
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
            this.getBookList();

    }

    public updateBookSelection(): void{ //update to the book selected by the user, and send it up to the controller...
        this.$emit('update-book-selection',this.searchCriteria.book.toString());
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
                for(const [key, value] of Object.entries(TORAH))
                   this.bookList.push(`${value}`.toString());
                break;
            case "PROPHETS":
                for(const [key, value] of Object.entries(PROPHETS))
                    this.bookList.push(`${value}`.toString());
                break;
            case "WRITINGS":
                for(const [key, value] of Object.entries(WRITINGS))
                    this.bookList.push(`${value}`.toString());
                break;
        }
    }








}
</script>

<style scoped>

</style>