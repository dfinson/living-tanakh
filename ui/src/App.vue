<template>
  <div id="app">
    <Dashboard/>
  </div>

</template>

<script lang="ts">

import {Component, Emit, Vue} from 'vue-property-decorator';
import apifiClient from "./api/apifiClient.js";
import {Chapter, Book, Verse} from "./api/dto.js";
import {PROPHETS, TORAH, WRITINGS} from "./api/TANAKH.js";
import BaseCard from "./Components/BaseComponents/BaseCard.vue";
import CategoryList from "@/Components/SearchComponents/CategoryList.vue";
import FindVerseByPath from "@/Components/SearchComponents/FindVerseByPath.vue";
import FormPage from "@/Components/SearchComponents/FormPage.vue";
import Dashboard from "@/components/Dashboard.vue";

@Component(
    {
        components:{
          BaseCard,
          CategoryList,
          FindVerseByPath,
          FormPage,
          Dashboard
        }
    }
)
export default class App extends Vue {

  searchString = ""
  verse = ""
  books = ["TORAH","PROPHETS","WRITINGS"]
  selectedBook =""







}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
header {
  background-color: #3a0061;
  color: white;
  width: 100%;
  padding: 1rem;
}
button,
a {
  text-decoration: none;
  padding: 0.75rem 1.5rem;
  font: inherit;
  background-color: #3a0061;
  border: 1px solid #3a0061;
  color: white;
  cursor: pointer;
  border-radius: 30px;
  margin-right: 0.5rem;
  display: inline-block;
}

a:hover,
a:active,
button:hover,
button:active {
  background-color: #270041;
  border-color: #270041;
}

.flat {
  background-color: transparent;
  color: #064661;
  border: none;
}

.outline {
  background-color: transparent;
  border-color: #270041;
  color: #270041;
}

.flat:hover,
.flat:active,
.outline:hover,
.outline:active {
  background-color: #edd2ff;
}

header {
  margin-left: auto;
  margin-right: auto;
  border-radius: 10px;
  width: 80%;
  height: 5rem;
  background-color: #07688d;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
}

header a {
  text-decoration: none;
  color: #777af3;
  display: inline-block;
  padding: 0.75rem 1.5rem;
  border: 1px solid transparent;
}

</style>
