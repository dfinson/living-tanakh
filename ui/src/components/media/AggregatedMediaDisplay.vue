<template>
  <div>

  </div>
</template>

<script lang="ts">
/*
  Overview:

    Display all media items associated with a MediaTag object

  Complexity:
    - Display all relevant media to the user
    - User can select specific media object

  Children:
    - MediaPresenter

  props:
    - MediaTag selectedMediaTag

  emits:
    None

  data:
    - mediaContents: MediaContent[]

*/
import {Component, Vue} from "vue-property-decorator";

@Component
export default class AggregatedMediaDisplay extends Vue{

}
</script>

<style scoped>

</style>