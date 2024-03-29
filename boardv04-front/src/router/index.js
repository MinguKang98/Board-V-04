import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ListView from "@/views/ListView";
import BoardView from "@/views/BoardView";
import WriteView from "@/views/WriteView";
import PasswordCheckView from "@/views/PasswordCheckView";
import ModifyView from "@/views/ModifyView";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/list',
    name: 'list',
    component: ListView
  },
  {
    path: '/board/:id',
    name: 'board',
    component: BoardView
  },
  {
    path: '/write',
    name: 'write',
    component: WriteView
  },
  {
    path: '/passwordCheck/:id',
    name: 'passwordCheck',
    component: PasswordCheckView
  },
  {
    path: '/board/:id/modify',
    name: 'modify',
    component: ModifyView
  },
]

const router = new VueRouter({
  routes
})

export default router
