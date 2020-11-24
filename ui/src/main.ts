import Vue from 'vue'
import App from './App.vue'
import store from './store'
import apifiClient from "@/api/apifiClient";
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
Vue.use(Buefy);
import {localhost, aws} from "./api-urls"


apifiClient.setApiUrl(localhost)



new Vue({
  store,
  render: h => h(App)
}).$mount('#app')

