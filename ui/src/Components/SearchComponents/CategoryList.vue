/* this component is responsible for displaying the different categories - Torah, Prophets , Writings,
and getting the user's choice. The chosen category will then be passed on to the next component. */

<template>
    <div>
        <select v-model="selectedCategory">
            <option  v-for="category in categoryList" v-bind:value="category" :key="category" > {{category}}</option>
        </select>
        <span>Selected: {{ selectedCategory }}</span>
        <book-list
            :selectedCategory = selectedCategory>
        </book-list>
    </div>
</template>

<script lang="ts">
import {Component, Emit, Vue} from 'vue-property-decorator';
import {TORAH, PROPHETS, WRITINGS} from "@/api/TANAKH";
import BookList from "@/Components/SearchComponents/BookList.vue";

@Component({
    components:{
        BookList
    }
})
    export default class App extends Vue {

        categoryList = ["TORAH","PROPHETS","WRITINGS"] //  contain the list of categories
        selectedCategory = "book"


        /*getCategories(){
            //will obtain data via an api call in the future
            this.categoryList.push("Torah");
            this.categoryList.push("Prophets");
            this.categoryList.push("Writings");
        }*/


    }

</script>