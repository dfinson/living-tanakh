<template>
  <div>
    <p v-if="textDataObject">
      <span v-for="(verse, index) in textDataObject.hebrewText" :key="index">{{verse}}</span>
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import api from "@/api_clients_and_helpers/api";
import SefariaTextsApiRequest from "@/dto/SefariaTextsApiRequest";
import SefariaTextsApiResponse from "@/dto/SefariaTextsApiResponse";

@Component
export default class PrimaryTextDisplay extends Vue{

  // props
  @Prop({required: true})
  private book: string;
  @Prop({required: true})
  private chapter: number;

  // data
  private textDataObject: SefariaTextsApiResponse;

  // methods
  public setText(): void{
    api .fetchText(new SefariaTextsApiRequest(this.book, this.chapter))
        .then(response => {
          this.textDataObject = response.data.sefariaText;
        })
  }

  // lifecycle hooks
  mounted(){
    this.setText();
  }


}
</script>