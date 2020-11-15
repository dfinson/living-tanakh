<template>
  <div>

  </div>
</template>

<script lang="ts">

/*
  Overview:

    Display a single chapter

  Complexity:
    - Toggle nekudot and taamim on or off
    - Highlight relevant verse / search terms if chapter is being displayed after user double-clicks on a search result
    - Size adjustable text
    - Scrollable text reader
    - Auto scroll to highlighted search result(?)

  Parents:
    - Dashboard

  Children:
    None

  props:
    chapter: Chapter

  emits:
    - mediaTagSelected(selectedMediaTag);

  data:
    selectedMediaTag: MediaTag
*/

import { Component, Vue } from 'vue-property-decorator';

@Component
export default class ChapterDisplay extends Vue{

}
</script>

<style scoped>

</style>