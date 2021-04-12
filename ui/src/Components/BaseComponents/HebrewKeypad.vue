<template>
  <v-container >

    <!--row #1-->
    <v-layout row justify-space-around>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ק')">ק</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ר')">ר</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block @click="emitLetter('א')">א</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ט')">ט</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ו')">ו</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ן')">ן</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ם')">ם</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('פ')">פ</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('D')">
          <span class="iconify" data-icon="ic-baseline-keyboard-backspace" data-inline="false"></span>
        </v-btn>
      </v-flex>
    </v-layout>

    <!--row #2-->
    <v-layout row  justify-space-around>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block @click="emitLetter('ש')">ש</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ד')">ד</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ג')">ג</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('כ')">כ</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ע')">ע</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('י')">י</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ח')">ח</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ל')">ל</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ך')">ך</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ף')">ף</v-btn>
      </v-flex>
    </v-layout>

    <!--row #3-->
    <v-layout row  justify-space-around>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block @click="emitLetter('ז')">ז</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ס')">ס</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ב')">ב</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ה')">ה</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('נ')">נ</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('מ')">מ</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('צ')">צ</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block @click="emitLetter('ת')">ת</v-btn>
      </v-flex>
      <v-flex md1 lg1 xl1>
        <v-btn  x-small  block  @click="emitLetter('ץ')">ץ</v-btn>
      </v-flex>
    </v-layout>

    <!--row #4-->
    <v-layout row justify-center>
      <v-flex md4 lg4 xl4>
        <v-btn  x-small  block  @click="emitLetter(' ')"><span class="iconify" data-icon="ic:baseline-space-bar" data-inline="false"></span></v-btn>
      </v-flex>
    </v-layout>
  </v-container>
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
.td{

}
.primary{

  font-size: 0.2vm;

}
</style>