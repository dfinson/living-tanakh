<template>
    <div>
        <h1>Book selected: {{selectedBook}}</h1>
        <select v-model="selectedChumash">
            <option  v-for="chumash in chumashim"
        :key="chumash" v-bind:value="chumash"> {{chumash}}
           </option>
        </select>
        <h1 v-show="selectedChumash !== '' ">Selected Chumash: {{selectedChumash}}</h1>
        <input type="text" v-show="selectedBook === 'TORAH' && selectedChumash !== ''" v-model="selectedChapter"/>
<h2>{{selectedChapter}}</h2>
        <button @click="findByChapter">Search</button>
    </div>
</template>

<script lang="ts">
import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
import apifiClient from "@/api/apifiClient";
@Component

export default class FindVerseByPath extends Vue{
    @Prop({default:"Nothing"})
    selectedBook: string

    selectedChumash = ""
    chumashim = ['Genesis','Exodus','Leviticus','Numbers','Deuteronomy']
    selectedChapter = ''


   // }




}

</script>

<style scoped>
    select {
        border-radius: 15px;
        box-shadow: 0 2px 8px rgba(0.30, 0.30, .30, 0.30);
        padding: 0rem;
        margin: 1rem auto;
        max-width: 20rem;
    }

</style>