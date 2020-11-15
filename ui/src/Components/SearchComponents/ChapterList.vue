<template>
    <div>
        <h1>{{ selectedCategory + "/" + selectedBook}}</h1>
    </div>
</template>


<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {TORAH, PROPHETS, WRITINGS} from "@/api/TANAKH";
    import BookList from "@/Components/SearchComponents/BookList.vue";
    import apifiClient from "@/api/apifiClient";

    @Component({
        components:{
            BookList
        }
    })
    export default class App extends Vue {

        @Prop({default:"Nothing"})
        selectedBook: string//the book selected from the previous component
        @Prop({default:"Nothing"})
        selectedCategory: string //the category, inherited from the grandfather component




    }



</script>