import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/transfer',
    name: 'Transfer',
    component: () => import(/* webpackChunkName: "signup" */ '../views/Transfer.vue')
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import(/* webpackChunkName: "signup" */ '../views/Signup.vue')
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import(/* webpackChunkName: "signup" */ '../views/Contact.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import(/* webpackChunkName: "signup" */ '../views/Profile.vue')
  },
  {
    path: '/add_connection',
    name: 'AddConnection',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AddConnection.vue')
  },
  {
    path: '/home',
    name: 'Home',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
