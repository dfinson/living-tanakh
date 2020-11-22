<template>
  <div>

  </div>
</template>

<script lang = "ts">

/*
  Overview:

    Encapsulates the search & navigation functionality of the app by
    offering the user a search / navigation form, fetching the resulting data from
    the backend API and then displaying a list of the search results.

  Complexity:
    - Handle the emitted data from SearchInputForm (make api call, then pass result as prop to SearchResultsList)

  Parents:
    - Dashboard

  Children:
    - SearchInputForm
    - SearchResultsList

  props:
    None

  emits:
    - searchResultSelected(selectedVerse)

  data:
    None

*/

import { Component, Vue } from 'vue-property-decorator';

@Component
export default class SearchController extends Vue{

}
</script>

<style scoped>

</style>