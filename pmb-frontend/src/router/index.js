import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../views/Login.vue'
import SignUp from '../views/Signup.vue'
import Home from '../views/Home.vue'
import Transfer from '../views/Transfer.vue'
import Connection from '../views/Connection.vue'
import Summary from '../views/Summary.vue'
import Profile from '../views/Profile.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignUp
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import('../views/Contact.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    alias: '/home/:email'
  },
  {
    path: '/transfer',
    name: 'Transfer',
    component: Transfer,
    alias: '/transfer/:email'
  },
  {
    path: '/connection',
    name: 'Connection',
    component: Connection,
    alias: '/connection/:email'
  },
  {
    path: '/summary',
    name: 'Summary',
    component: Summary,
    props: true
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  }
]

const router = new VueRouter({
  routes
})

export default router
