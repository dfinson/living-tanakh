<template>
  <div>
<base-card>
  <strong>
<button @click="pathSelected">{{result.path}}</button>
<p>{{result.fullHebrewText}}</p>
  </strong>
</base-card>
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
  result: Verse

  public pathSelected(): void{
    this.$emit('result-selected',this.result.chapter?.path);
  }

}
</script>

<style scoped>

</style>