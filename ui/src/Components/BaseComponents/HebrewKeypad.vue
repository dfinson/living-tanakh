<template>
    <div style="display: inline-block">
        <span class="first-row" style="display: inline-block">

            <b-button class="btn" @click="emitLetter('ק')">ק</b-button>
            <b-button class="btn" @click="emitLetter('ר')">ר</b-button>
            <b-button class="btn" @click="emitLetter('א')">א</b-button>
            <b-button  class="btn" @click="emitLetter('ט')">ט</b-button>
            <b-button  class="btn" @click="emitLetter('ו')">ו</b-button>
            <b-button  class="btn" @click="emitLetter('ן')">ן</b-button>
            <b-button  class="btn" @click="emitLetter('ם')">ם</b-button>
            <b-button  class="btn" @click="emitLetter('פ')">פ</b-button>
        </span>
        <span class="second-row" style="display: inline-block">

            <b-button  class="btn" @click="emitLetter('ש')">ש</b-button>
            <b-button  class="btn" @click="emitLetter('ד')">ד</b-button>
            <b-button  class="btn" @click="emitLetter('ג')">ג</b-button>
            <b-button  class="btn" @click="emitLetter('כ')">כ</b-button>
            <b-button  class="btn" @click="emitLetter('ע')">ע</b-button>
            <b-button  class="btn" @click="emitLetter('י')">י</b-button>
            <b-button  class="btn" @click="emitLetter('ח')">ח</b-button>
            <b-button  class="btn" @click="emitLetter('ל')">ל</b-button>
            <b-button  class="btn" @click="emitLetter('ך')">ך</b-button>
            <b-button  class="btn" @click="emitLetter('ף')">ף</b-button>

        </span>
        <span class="third-row" style="display: inline-block">

            <b-button  class="btn" @click="emitLetter('ז')">ז</b-button>
            <b-button  class="btn" @click="emitLetter('ס')">ס</b-button>
            <b-button  class="btn" @click="emitLetter('ב')">ב</b-button>
            <b-button  class="btn" @click="emitLetter('ה')">ה</b-button>
            <b-button  class="btn" @click="emitLetter('נ')">נ</b-button>
            <b-button  class="btn" @click="emitLetter('מ')">מ</b-button>
            <b-button  class="btn" @click="emitLetter('צ')">צ</b-button>
            <b-button  class="btn" @click="emitLetter('ת')">ת</b-button>
            <b-button  class="btn" @click="emitLetter('ץ')">ץ</b-button>
            <b-button  class="btn" @click="emitLetter('BackSpace')">Delete</b-button>
             <b-button  class="btn" style="width: 50px" @click="emitLetter(' ')">Space</b-button>

        </span>
    </div>
</template>

<script lang="ts">
import {Component, Vue} from "vue-property-decorator";

@Component
export default class HebrewKeypad extends Vue{

    public emitLetter(letter: string): void{
        if(letter !== 'BackSpace'){
            this.$emit('add-character',letter);
        }
        if(letter === 'BackSpace'){
            this.$emit('remove-character');
        }
    }

}
</script>

<style scoped>
.btn{

    width: 35px;
    height: 35px;
}
</style>