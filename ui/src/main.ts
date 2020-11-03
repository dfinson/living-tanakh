import Vue from 'vue'
import App from './App.vue'
import store from './store'
import apifiClient from "@/api/apifiClient";

Vue.config.productionTip = false

apifiClient.setApiUrl('http://tanakhproject-env.eba-24em36yg.us-east-2.elasticbeanstalk.com//api')

new Vue({
  store,
  render: h => h(App)
}).$mount('#app')
