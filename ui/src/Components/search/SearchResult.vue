<template>
  <div class="box">
<b-tabs>
  <strong>
    <b-button type="is-primary is-light" @click="pathSelected">{{result.humanReadablePath}}</b-button>
<p>{{result.fullHebrewText}}</p>
  </strong>
</b-tabs>
  </div>
</template>

<script lang="ts">

import {Vue,Component,Prop} from "vue-property-decorator";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import {Verse} from "@/api/dto";

@Component( {
  components:{
    BaseCard
  }
})
export default class SearchResult extends Vue{
  @Prop({default:'Example'})
  result: Verse;

  public pathSelected(): void{
    this.$emit('result-selected',this.result.chapter?.path);
  }

}
</script>

<style scoped>

</style>