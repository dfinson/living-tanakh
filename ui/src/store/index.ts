import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

const baseUrl = 'http://localhost:5000'

export default new Vuex.Store({
  state: {
    textApiBaseUrl: `${baseUrl}/text`,
    mediaApiBaseUrl: `${baseUrl}/media`,
  },
  mutations: {

  },
  actions: {
  },
  modules: {
  }
})
