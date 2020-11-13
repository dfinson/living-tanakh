import Vue from 'vue'
import App from './App.vue'
import store from './store'
import apifiClient from "@/api/apifiClient";

Vue.config.productionTip = false

apifiClient.setApiUrl('http://livingtanakhapplicationde-env.eba-i3mkpska.eu-central-1.elasticbeanstalk.com/api')

new Vue({
  store,
  render: h => h(App)
}).$mount('#app')
