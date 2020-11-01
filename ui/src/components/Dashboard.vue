<template>
  <div>
    <PrimaryTextDisplay :book='this.book' :chapter='this.chapter'/>
  </div>
</template>

<script lang="ts">


import {Component, Vue} from 'vue-property-decorator'
import PrimaryTextDisplay from "@/components/PrimaryTextDisplay.vue";

@Component({
  components: {PrimaryTextDisplay}
})
export default class Dashboard extends Vue{

  private book = 'Amos';
  private chapter = 9;

}
</script>