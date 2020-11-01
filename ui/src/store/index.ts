import Vue from 'vue'
import Vuex from 'vuex'
import api from "@/api_clients_and_helpers/api";
import SefariaTextsApiRequest from "@/dto/SefariaTextsApiRequest";
import SefariaTextsApiResponse from "@/dto/SefariaTextsApiResponse";

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
