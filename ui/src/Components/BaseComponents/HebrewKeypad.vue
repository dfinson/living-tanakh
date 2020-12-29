<template>
    <table class="is-fullwidth" >
        <tbody>
        <tr>
            <td>
            <b-button  class="btn" @click="emitLetter('ק')">ק</b-button>
            </td>
            <td>
            <b-button  class="btn" @click="emitLetter('ר')">ר</b-button>
            </td>
            <td>
            <b-button class="btn" @click="emitLetter('א')">א</b-button>
            </td>
            <td>
            <b-button  class="btn" @click="emitLetter('ט')">ט</b-button>
            </td>
            <td>
            <b-button  class="btn" @click="emitLetter('ו')">ו</b-button>
            </td>
            <td>
            <b-button  class="btn" @click="emitLetter('ן')">ן</b-button>
            </td>
            <td>
            <b-button class="btn" @click="emitLetter('ם')">ם</b-button>
            </td>
            <td>
            <b-button class="btn" @click="emitLetter('פ')">פ</b-button>
            </td>
        </tr>
      <tr>
          <td>
          <b-button  class="btn" @click="emitLetter('ש')">ש</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ד')">ד</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ג')">ג</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('כ')">כ</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ע')">ע</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('י')">י</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ח')">ח</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ל')">ל</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ך')">ך</b-button>
          </td>
        </tr>
      <tr>
          <td>
          <b-button  class="btn" @click="emitLetter('ז')">ז</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ס')">ס</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ב')">ב</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ה')">ה</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('נ')">נ</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('מ')">מ</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('צ')">צ</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ת')">ת</b-button>
          </td>
          <td>
          <b-button  class="btn" @click="emitLetter('ץ')">ץ</b-button>
          </td>
        </tr>
      <tr>
          <td>
          <b-button  style="width: 25px; height: 25px" @click="emitLetter('D')">DEL</b-button>
          </td>

      </tr>
        </tbody>
  </table>
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
.btn{

    width: 30px;
    height: 30px;
}
</style>