import Vue from 'vue'
import Router from 'vue-router'

import Login from '../views/Login.vue'
import SignUp from '../views/Signup.vue'
import Home from '../views/Home.vue'
import Transfer from '../views/Transfer.vue'
import Connection from '../views/Connection.vue'
import Summary from '../views/Summary.vue'
import Profile from '../views/Profile.vue'

Vue.use(Router)

export const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Login
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/signup',
      component: SignUp
    },
    {
      path: '/home',
      component: Home,
    },
    {
      path: '/transfer',
      name: 'Transfer',
      component: Transfer,
    },
    {
      path: '/connection',
      name: 'Connection',
      component: Connection,
    },
    {
      path: '/summary',
      name: 'Summary',
      component: Summary
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    }
  ]
});
