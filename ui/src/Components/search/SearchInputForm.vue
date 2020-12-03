<template>
<!-- the search Input form-->
    <div id='stacks_out_339' class='stacks_out'>
        <div id='stacks_in_339' class='stacks_in com_elixir_stacks_foundryJumbotron_stack'>
            <div class="nav_pairing">
                <div class="jumbotron theme_style jumbotron-fluid">
                    <div class="container-fluid jumbotron_container_padding">
                        <h1 class="jumbotron_title  display-4 text-xs-left">1. Begin Search Here...</h1>
                        <p class="jumbotron_sub_title lead text-xs-left">Select the text for your search and select the items you'd like to download.</p>
                        <div id='stacks_out_209' class='stacks_out'>
    <div class="form-horizontal" role="form"  action="#stacks_in_372">

        <div class="form-group ">
            <div id='stacks_in_209' class='stacks_in com_elixir_stacks_foundryButtonGroup_stack'>
                <div class="button-group-wrapper  text-xs-center">
                    <div class="button_group_wrapper btn-group " role="group">
                        <div class="btn-group-stacks_in_213_dropdown" role="group" >
                            <b-field label="כתובים">
                                <b-select placeholder="Select a Writing" v-model="selectedWritingSefer" @input="updateBookSelection('WRITINGS')">
                                    <option
                                            v-for="writing in writingList"
                                            :value="writing"
                                            :key="writing">
                                        {{ getHebNameOfBook(writing)}}
                                    </option>
                                </b-select>
                            </b-field>
                        </div> <!-- writings dd-->
                        <div class="btn-group-stacks_in_221_dropdown" role="group">
                            <b-field label="נביאים">
                                <b-select placeholder="Select a Navi" v-model="selectedNaviSefer" @input="updateBookSelection('PROPHETS')">
                                    <option
                                            v-for="navi in naviList"
                                            :value="navi"
                                            :key="navi">
                                        {{ getHebNameOfBook(navi) }}
                                    </option>
                                </b-select>
                            </b-field>
                        </div><!-- prophets dd-->
                        <div class="btn-group-stacks_in_224_dropdown" role="group">
                            <b-field label="תורה">
                                <b-select placeholder='Select a Chumash' type="is-info" v-model="selectedChumashSefer" @input="updateBookSelection('TORAH')">
                                    <option style="background-color: white"
                                            v-for="chumash in chumashList"
                                            :value="chumash"
                                            :key="chumash">
                                        {{ getHebNameOfBook(chumash) }}
                                    </option>
                                </b-select>
                            </b-field>
                        </div><!-- torah dd-->
                    </div> <!-- dropdown buttons-->
                </div>

            </div>
        </div> <!-- dd's for t/n/k-->

        <div class="second-dd-list">
            <div class="passuk-dd">
            <b-field label="פסוק">
                <b-select placeholder="Select a Passuk" v-model="searchCriteria.passuk" @input="updateVerseSelection">
                    <option
                            v-for="passuk in getChapterSearchResults.verses"
                            :value="passuk.number"
                            :key="passuk.id">
                        {{ passuk.hebrewNumeral }}
                    </option>
                </b-select>
            </b-field>
        </div><!-- passuk dd-->
            <div class="perek-dd ">
                <b-field label="פרק">
                    <b-select placeholder="Select a Perek" v-model="searchCriteria.chapter" @input="updateChapterSelection">
                        <option
                                v-for="chapter in chaptersList"
                                :value="chapter.number"
                                :key="chapter.id">
                            {{ chapter.hebrewNumeral }}
                        </option>
                    </b-select>
                </b-field>
            </div> <!-- perek dd-->

        </div><!-- perek and passuk dd's-->

        <div class="free-search-input">
            <b-field label="Free Search">
                <b-input v-model="searchCriteria.searchTerm"></b-input> <!--free text search-->
            </b-field>
        </div> <!-- Free text search-->
        <!-- send and clear buttons-->

        <div class="search-and-clear-bttns">
            <b-button type="is-primary is-light" class="search_btn" @click="updateSearchTermSelection" >Search</b-button>
            <b-button type="is-danger is-light" class="clear_btn" @click="clearAllResults">Clear</b-button>
            </div>
    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang = "ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Book, Chapter, SearchCriteria, Verse} from "@/api/dto";
import {TORAH,WRITINGS,PROPHETS,hebrewBooksInTorah,HEBREW_NAMES_DICT,toHebrewBookName,
    hebrewBooksInProphets,hebrewBooksInWritings,toEnglishBookName,stripPrefix} from "@/api/TANAKH";

@Component
export default class SearchInputForm extends Vue{

    //this object is shared via emits to the controller
    public searchCriteria = new SearchCriteria()

    //the list of chapters depends on the chosen category/book path, and is populated by an api call in the controller
    @Prop({default: 'Example'})
        chaptersList: Chapter[];
    @Prop({default: 'Example'})
    getChapterSearchResults: Chapter;

    public selectedChumashSefer = "";
    public selectedNaviSefer = "";
    public selectedWritingSefer = "";

    public chumashList: string[] = [];
    public naviList: string[] = [];
    public writingList: string[] = [];

    mounted(){
        this.getBookList("TORAH");
        this.getBookList("PROPHETS");
        this.getBookList("WRITINGS");

    }


    public updateBookSelection(category: string): void{ //update to the book selected by the user, and send it up to the controller...
        switch(category){
            case "TORAH":
                this.selectedWritingSefer = "";
                this.selectedNaviSefer = "";
                this.searchCriteria.book = this.selectedChumashSefer;
                break;
            case "PROPHETS":
                this.selectedWritingSefer = "";
                this.selectedChumashSefer = "";
                this.searchCriteria.book = this.selectedNaviSefer;
                break;
            case "WRITINGS":
                this.selectedNaviSefer = "";
                this.selectedChumashSefer = "";
                this.searchCriteria.book = this.selectedWritingSefer;
                break;
        }
        console.log(this.searchCriteria.book);
        this.$emit('update-book-selection',this.searchCriteria.book);
    }

    public updateChapterSelection(): void{ //update to the chapter selected by the user, and send it up to the controller...
        this.$emit('update-chapter-selection',this.searchCriteria.chapter);
    }

    public updateVerseSelection(): void{
        this.$emit('update-verse-selection', this.searchCriteria.passuk);
    }

    public updateSearchTermSelection(): void{ //update to the searchTerm selected by the user, and send it up to the controller...
        this.$emit('update-search-term-selection',this.searchCriteria.searchTerm);
    }


    public getBookList(category: string): void{
       // this.searchCriteria.chapter = "";
        switch (category) {
            case "TORAH":
                for(const [key, value] of Object.entries(TORAH)) {
                    this.chumashList.push(`${value}`.toString());
                  //  console.log(toHebrewBookName(`${value}`.toString()));
                }
                break;
            case "PROPHETS":
                for(const [key, value] of Object.entries(PROPHETS)) {
                    this.naviList.push(`${value}`);
                   // console.log(toHebrewBookName(`${value}`));
                }
                break;
            case "WRITINGS":
                for(const [key, value] of Object.entries(WRITINGS)) {
                    this.writingList.push(`${value}`.toString());
                    //console.log(toHebrewBookName(`${value}`.toString()));
                }
                break;

        }

    }

    public getHebNameOfBook(book: string): string{
        return toHebrewBookName(book);
    }

    public clearAllResults(): void{
        this.searchCriteria.category = "";
        this.searchCriteria.book = "";
        this.searchCriteria.chapter = "";
        this.searchCriteria.searchTerm = "";
        this.$emit('clear-all-results');
    }







}
</script>

<style scoped>

.btn-group stacks_in_213_dropdown{
    white-space: nowrap;
    overflow-x: auto;
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
    overflow-x: auto;
}
.perek-dd{
    display: inline-block;

}
.passuk-dd{
display: inline-block;

}

</style>