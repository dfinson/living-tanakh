<template >
  <v-app >

    <v-main  >
      <dashboard />
    </v-main>

  </v-app>
</template>

<script lang="ts">
import Vue from 'vue';
import {Component} from 'vue-property-decorator';
import Dashboard from "@/Components/Dashboard.vue";


export default Vue.extend({
  name: 'App',

  components: {
    Dashboard,

  },

  data: () => ({
    //
  }),
});
</script>
