<template >
<div>
    <button @click="getBookList">Get Books</button>
    <select v-model="selectedBook" >
        <option  v-for="book in bookList" v-bind:value="book" :key="book"> {{book}}</option>
    </select>
    <span>{{selectedBook}}</span>
    <chapter-list
            :selected-book="selectedBook"
            :selected-category="selectedCategory"
    ></chapter-list>
</div>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {TORAH, PROPHETS, WRITINGS} from "@/api/TANAKH";
    import ChapterList from "@/Components/SearchComponents/ChapterList.vue";

    @Component({
        components:{
            ChapterList
        }
    })
    export default class App extends Vue {

        @Prop({default:"Nothing"})
        selectedCategory: string

        bookList: string[] = []
        selectedBook = ""

        getBookList(){ //method to populate the list of books from the ts file
            if(this.selectedCategory.toUpperCase() === "TORAH"){
                this.bookList = [];
                for(const book in TORAH)
                    this.bookList.push(book);
                console.log(this.bookList);
            }
            if(this.selectedCategory.toUpperCase() === "PROPHETS"){
                this.bookList = [];
                for(const book in PROPHETS)
                    this.bookList.push(book);
                console.log(this.bookList);

            }
            if(this.selectedCategory.toUpperCase() === "WRITINGS"){
                this.bookList = [];
                for(const book in WRITINGS)
                    this.bookList.push(book);
                console.log(this.bookList);
            }

        }


    }

</script>