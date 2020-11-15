<template>
  <div>
    <SearchController/>
    <ChapterDisplay/>
  </div>
</template>

<script lang = "ts">

/*
  Overview:

    Is the primary UI container for this application.
    Consists of a search & navigation bar,
    as well as a main text display area.

  Children:
    - SearchController
    - ChapterDisplay
    - AggregatedMediaDisplay (popup / modal)

  Parents:
    - App

  props:
    None

  emits:
    None

  data:
    - chapter: Chapter
    - highlightedVersePath: string
    - searchCriteria: SearchCriteria
*/

import { Component, Vue } from 'vue-property-decorator';
import SearchController from "@/components/search/SearchController.vue";
import ChapterDisplay from "@/components/search/ChapterDisplay.vue";

@Component({
  components: {ChapterDisplay, SearchController}
})
export default class Dashboard extends Vue{

}
</script>

<style scoped>

</style>