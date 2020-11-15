<template>
    <div>
        <span>Select Category </span>
        <select v-model="selectedCategory" @change="getBookList">
            <option  v-for="category in categoryList" v-bind:value="category" :key="category" > {{category}}</option>
        </select>
        <span>Select Book </span>
        <select v-model="selectedBook" @change="getChapterList" >
            <option  v-for="book in bookList" v-bind:value="book" :key="book"> {{book}}</option>
        </select>
        <span>Select Chapter </span>
        <select v-model="selectedChapter"  >
            <option  v-for="chapter in chapterList" v-bind:value="chapter" :key="chapter"> {{chapter}}</option>
        </select>
        <span>Free Text Search:</span>
        <input v-model="freeSearchText" placeholder="Example - 'בראשית'" >

        <button @click="getTextFromPathOrSendToFreeTextSearch">get Text</button>
        <p>{{requestedText.toString()}}</p>

    </div>
</template>

<script lang="ts">
    import {Vue, Component} from "vue-property-decorator";
    import {PROPHETS, TORAH, WRITINGS} from "@/api/TANAKH";
    import apifiClient from "@/api/apifiClient";
    import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
    @Component({
        components:{
            BaseCard
        }
    })
    export default class App extends Vue {

        categoryList = ["TORAH", "PROPHETS", "WRITINGS"]   //  contains the list of categories
        selectedCategory = ""
        bookList: string[] = []    //will contain the list of books in the selected category after the api call
        selectedBook = ""
        chapterList: string[] = []    //will contain the list of all chapters in the chosen book, will be populated by an api call
        selectedChapter = ""
        categoryAndBookSearchPath = ""
        completeSearchPath = ""
        requestedText: string[] = []    //will contain the verses of the requested path
        freeSearchText = ""


        getBookList() { //method to populate the list of books from the ts file
            this.bookList = [];
            this.selectedChapter = "";
            if (this.selectedCategory.toUpperCase() === "TORAH") {
                for (const book in TORAH)
                    this.bookList.push(book);
               // console.log(this.bookList);
            }
            if (this.selectedCategory.toUpperCase() === "PROPHETS") {
                for (const book in PROPHETS)
                    this.bookList.push(book);
                //console.log(this.bookList);

            }
            if (this.selectedCategory.toUpperCase() === "WRITINGS") {
                for (const book in WRITINGS)
                    this.bookList.push(book);
              //  console.log(this.bookList);
            }

        }

        //gets the path by concatenating all the path elements, not including chapter number (TBFL...)
        getCurrentPath() {
            //the search path format is as follows : "TORAH/Genesis/1"
            const a = this.selectedBook.toLowerCase();
            const b = a.slice(1, a.length)
            const c = a.charAt(0).toUpperCase() + b;
            this.categoryAndBookSearchPath = this.selectedCategory + "/" + c;
        }


        //calls the api using the given book and category, returns the list of chapters, and populates the list data property...
        getChapterList() {
            this.getCurrentPath();
            this.chapterList = [];
            this.selectedChapter = "";
            apifiClient.findBookByUniquePath(this.categoryAndBookSearchPath,
                `{

      chapters{
        hebrewNumeral
        number
      }
      englishName


            }`).then( res =>{
                let i;
                for(i = 0; i < res['data'].findBookByUniquePath.chapters.length; i++)
                   this.chapterList.push(res['data'].findBookByUniquePath.chapters[i].number);

                }





             );


        }


        //using the complete path, this function returns the text via an api call, and pushes it to the array.
        getTextFromPathOrSendToFreeTextSearch() {
            if(this.selectedCategory !== "" && this.selectedBook !== "" && this.selectedChapter !== "" && this.freeSearchText === "") {
                this.completeSearchPath = this.categoryAndBookSearchPath + "/" + this.selectedChapter.toString();
                this.requestedText = [];
                console.log(this.completeSearchPath);
                apifiClient.findChapterByUniquePath(this.completeSearchPath, `{
             hebrewNumeral
    id
    number
    path
    verses{
      fullHebrewText
      hebrewNumeral
      number
    }
            }`).then(res => {
                    let i;
                    const tempRes: any[] = []
                    for (i = 0; i < res['data'].findChapterByUniquePath.verses.length; i++)
                        tempRes.push({
                            fullHebrewText: res['data'].findChapterByUniquePath.verses[i].fullHebrewText,
                            hebrewNumeral: res['data'].findChapterByUniquePath.verses[i].hebrewNumeral,
                            number: res['data'].findChapterByUniquePath.verses[i].number
                        });
                    tempRes.sort((a, b) => parseFloat(a.number) - parseFloat(b.number));
                    let j;
                    for(j = 0; j < tempRes.length; j++)
                        this.requestedText.push(tempRes[j]["fullHebrewText"]);

                });
            }
            else {
                if (this.freeSearchText !== "") //in the event we have a search term, we need to call the freeTextSearch method
                    this.freeTextSearch();
                else { //in this case - we have an incomplete search path, and no search term - thus data is missing, and a search cannot be started
                    let categorySelected = "";
                    let bookSelected = "";
                    let chapterSelected = "";

                    if (this.selectedCategory === "")
                        categorySelected = "Category\n ";

                    if (this.selectedBook === "")
                        bookSelected = "Book\n";

                    if (this.selectedChapter === "")
                        chapterSelected = "Chapter";

                    alert("Please Select:\n" + categorySelected + " " + bookSelected + " " + chapterSelected + "\n Or enter a search term");


                }
            }


            }



        freeTextSearch() {
            let validSearchPath = ""
            this.requestedText = [];
            this.getCurrentPath();
            //Sorting is required for the search path specified for the search term..
            if(this.selectedCategory !== "") //A category has been selected
            {
                if(this.selectedBook !== "") //A book has been selected
                {
                    if(this.selectedChapter !== "") //A chapter has been selected
                    {
                        validSearchPath = this.categoryAndBookSearchPath   +"/" + this.selectedChapter; //the text search will be confined to the selected path
                        console.log(validSearchPath);
                    }
                    else{ //a book but no chapter
                        validSearchPath = this.categoryAndBookSearchPath;
                        console.log(validSearchPath);
                    }
                }
                else{ //category but no book
                    validSearchPath = this.selectedCategory;
                    console.log(validSearchPath)
                }


            }
            else{ //nothing selected  - an api call is made with the entire tanach as a valid search path
                console.log("TORAH,PROPHETS,WRITINGS");
                apifiClient.verseFreeTextSearch({
                    customArgs: {
                        validPathPrefixes: ["TORAH","PROPHETS","WRITINGS"]
                    },
                    fetchAll: true,
                    pageSize: 20,
                    pageNumber: 0,
                    searchTerm: this.freeSearchText

                }, `{
           content{
           fullHebrewText
           hebrewNumeral
           number
           }
           }`).then(res => {
                    let i;
                    const tempRes: any[] = []
                    for (i = 0; i < res['data'].verseFreeTextSearch.content.length; i++) {
                        console.log(res['data'].verseFreeTextSearch.content[i]);
                        tempRes.push({
                            fullHebrewText: res['data'].verseFreeTextSearch.content[i].fullHebrewText,
                            hebrewNumeral: res['data'].verseFreeTextSearch.content[i].hebrewNumeral,
                            number: res['data'].verseFreeTextSearch.content[i].number
                        });
                        //tempRes.sort((a, b) => parseFloat(a.number) - parseFloat(b.number));
                        let j;
                        for(j = 0; j < tempRes.length; j++)
                            this.requestedText.push(tempRes[j]["fullHebrewText"]);
                    }
           });
                    /*for (i = 0; i < res['data'].verseFreeTextSearch.content.length; i++)
                        tempRes.push({
                            fullHebrewText: res['data'].findChapterByUniquePath.content[i].fullHebrewText,
                            hebrewNumeral: res['data'].findChapterByUniquePath.content[i].hebrewNumeral,
                            number: res['data'].findChapterByUniquePath.content[i].number
                        });
                    tempRes.sort((a, b) => parseFloat(a.number) - parseFloat(b.number));
                    let j;
                    for(j = 0; j < tempRes.length; j++)
                        this.requestedText.push(tempRes[j]["fullHebrewText"]);
                }*/

            }
            if(validSearchPath !== ""){ // if we do have a search path, the path is used as the valid search path for the search term
                apifiClient.verseFreeTextSearch({
                    customArgs: {
                        validPathPrefixes: [validSearchPath]
                    },
                    fetchAll: true,
                    pageSize: 20,
                    pageNumber: 0,
                    searchTerm: this.freeSearchText

                }, `{
           content{
            fullHebrewText
           hebrewNumeral
           number
           }
           }`).then(res => console.log(res));
            }


            }



    }
</script>

<style scoped>
   span {
    color: #1318b4;
    font-weight: bold;
        font-family: Arial;
    display: block;
       width: 150px;

    }

   select {
       border-radius: 10px;
       box-shadow: 0 2px 8px rgba(0.30, 0.30, .30, 0.30);
       padding: 0rem;
       max-width: 20rem;
       margin:  1rem auto;
   }

    input {
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0.30, 0.30, .30, 0.30);
        padding: 0rem;
        margin:  1rem auto;
    }
    p{
        line-height: 1.6;
        word-spacing: 2px;
    }
</style>

//

//       ;
