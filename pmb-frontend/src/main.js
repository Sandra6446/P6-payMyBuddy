import Vue from 'vue'
import VueCookies from 'vue-cookies'
import App from './App.vue'
import {router} from './router'
import store from './store'

//use bootstrap
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle.js'

Vue.use(VueCookies)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
