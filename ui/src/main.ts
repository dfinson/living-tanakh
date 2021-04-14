import Vue from 'vue'
import App from './App.vue'
import apifiClient from "@/api/apifiClient";
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import Galleria from 'primevue/galleria';



Vue.use(Buefy);
Vue.component('Galleria', Galleria);

import { aws } from "./api/api-urls";
import vuetify from './plugins/vuetify';


apifiClient.setApiUrl(aws);



new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')

