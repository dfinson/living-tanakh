<template>
  <div id="app">
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import apifiClient from "@/api/apifiClient";
import {Chapter} from "@/api/dto";
import {PROPHETS} from "@/api/TANAKH";


@Component
export default class App extends Vue {

  /*private chapter = new Chapter();
  mounted(){
    apifiClient.findChapterByUniquePath(PROPHETS.AMOS + ".2", `{
                                            book{
                                              id
                                              hebrewName
                                            }
                                            hebrewNumeral
                                            id
                                            number
                                            path
                                            verses{
                                              id
                                              path
                                              fullHebrewText
                                              fullEnglishText
                                            }
                                          }`)
    .then(res => this.chapter = res.data.findChapterByUniquePath)
  }*/
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
</style>
