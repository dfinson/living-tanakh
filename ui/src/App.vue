<template>
  <div id="app">
    <div id='stacks_out_1' class='stacks_top'>
      <div id='stacks_in_1' class=''>
        <div id='stacks_out_153' class='stacks_out'>
          <div id='stacks_in_153' class='stacks_in com_elixir_stacks_foundry_stack'>
            <!-- Foundry -->
          </div>
        </div>
        <div id='stacks_out_374' class='stacks_out'>
          <div id='stacks_in_374' class='stacks_in com_elixir_stacks_foundryPartitions_stack'>
            <div id="stacks_in_374-slide-out-sidebar" class="slideout color-picker"></div>
            <div class="fc-wrapper">
              <span class="fc-open-button"><i class="fa fa-plus"></i></span>
    <Dashboard/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script lang="ts">

import {Component, Vue} from 'vue-property-decorator';
import BaseCard from "./Components/BaseComponents/BaseCard.vue";
import FormPage from "@/Components/SearchComponents/FormPage.vue";
import Dashboard from "@/Components/Dashboard.vue";


@Component(
    {
        components:{
          BaseCard,
          FormPage,
          Dashboard
        }
    }
)
export default class App extends Vue {


}
</script>

<style>


/*button,
a {
  text-decoration: none;
  padding: 0.75rem 1.5rem;
  font: inherit;
  background-color: #0c2e61;
  border: 1px solid #3a0061;
  color: #e8fffc;
  cursor: pointer;
  border-radius: 30px;
  margin-right: 0.5rem;
  display: inline-block;
}

a:hover,
a:active,
button:hover,
button:active {
  background-color: #170341;
  border-color: #270041;
}

.flat {
  background-color: transparent;
  color: #064661;
  border: none;
}

.outline {
  background-color: transparent;
  border-color: #270041;
  color: #410310;
}

.flat:hover,
.flat:active,
.outline:hover,
.outline:active {
  background-color: #edd2ff;
}

header {
  margin-left: auto;
  margin-right: auto;
  border-radius: 10px;
  width: 80%;
  height: 5rem;
  background-color: #07688d;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
}

header a {
  text-decoration: none;
  color: #777af3;
  display: inline-block;
  padding: 0.75rem 1.5rem;
  border: 1px solid transparent;
}*/

</style>
