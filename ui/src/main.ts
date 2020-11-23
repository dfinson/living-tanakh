import Vue from 'vue'
import App from './App.vue'
import store from './store'
import apifiClient from "@/api/apifiClient";
import {localhost, aws} from './api-urls'

Vue.config.productionTip = false

apifiClient.setApiUrl(aws)

new Vue({
  store,
  render: h => h(App)
}).$mount('#app')
