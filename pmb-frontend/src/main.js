import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//use bootstrap
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle.js'

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
