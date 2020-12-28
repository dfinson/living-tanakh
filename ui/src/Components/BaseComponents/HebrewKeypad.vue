<template>
  <div class="grid-container">
        <div class="grid-item1">
            <b-button class="btn" @click="emitLetter('א')">א</b-button>
            <b-button  class="btn" @click="emitLetter('ט')">ט</b-button>
            <b-button  class="btn" @click="emitLetter('ו')">ו</b-button>
            <b-button  class="btn" @click="emitLetter('ן')">ן</b-button>
            <b-button class="btn" @click="emitLetter('ם')">ם</b-button>
            <b-button class="btn" @click="emitLetter('פ')">פ</b-button>
        </div>
      <div class="grid-item2">
          <b-button  class="btn" @click="emitLetter('י')">י</b-button>
          <b-button  class="btn" @click="emitLetter('ח')">ח</b-button>
          <b-button  class="btn" @click="emitLetter('ל')">ל</b-button>
          <b-button  class="btn" @click="emitLetter('ך')">ך</b-button>
            <b-button  class="btn" @click="emitLetter('ק')">ק</b-button>
            <b-button  class="btn" @click="emitLetter('ר')">ר</b-button>

        </div>
      <div class="grid-item3">


          <b-button  class="btn" @click="emitLetter('ץ')">ץ</b-button>
          <b-button  class="btn" @click="emitLetter('ש')">ש</b-button>
          <b-button  class="btn" @click="emitLetter('ד')">ד</b-button>
          <b-button  class="btn" @click="emitLetter('ג')">ג</b-button>
            <b-button  class="btn" @click="emitLetter('כ')">כ</b-button>
            <b-button  class="btn" @click="emitLetter('ע')">ע</b-button>

        </div>
      <div class="grid-item4">
          <b-button  class="btn" @click="emitLetter('ב')">ב</b-button>
          <b-button  class="btn" @click="emitLetter('ה')">ה</b-button>
          <b-button  class="btn" @click="emitLetter('נ')">נ</b-button>
          <b-button  class="btn" @click="emitLetter('מ')">מ</b-button>
          <b-button  class="btn" @click="emitLetter('צ')">צ</b-button>
          <b-button  class="btn" @click="emitLetter('ת')">ת</b-button>
      </div>
      <div class="grid-item5">

          <b-button  style="width: 45px; height: 30px" @click="emitLetter('D')">DEL</b-button>
          <b-button  class="btn" style="width: 70px" @click="emitLetter(' ')">Space</b-button>
          <b-button  class="btn" @click="emitLetter('ז')">ז</b-button>
          <b-button  class="btn" @click="emitLetter('ס')">ס</b-button>
      </div>
  </div>
</template>

<script lang="ts">
import {Component, Vue,Prop} from "vue-property-decorator";

@Component
export default class HebrewKeypad extends Vue{


    public emitLetter(letter: string): void{
            this.$emit('add-character',letter);

    }

}
</script>

<style scoped>
   .grid-container{
       display: grid;
       grid-gap: 1px 1px;

   }

   .grid-item1{
       grid-row: 1;
       grid-column: 1/span 6;
   }
   .grid-item2{
       grid-row: 2;
       grid-column: 1/span 6;
   }

   .grid-item3{
       grid-row: 3;
       grid-column: 1/span 6;
   }
   .grid-item4{
       grid-row: 4;
       grid-column: 1/span 6;
   }
   .grid-item5{
       grid-row: 5;
       grid-column: 1/span 6;
   }
.btn{

    width: 30px;
    height: 30px;
}
</style>