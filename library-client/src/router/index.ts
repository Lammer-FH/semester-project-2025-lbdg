import { createRouter, createWebHistory } from '@ionic/vue-router';
import {RouteLocationNormalized, RouteRecordRaw} from 'vue-router';
import Home from "@/components/Home.vue";
import Login from "@/components/Login.vue";
import Books from "@/components/Books.vue";
import BookDetail from "@/components/BookDetail.vue";
import {BookDTO} from "@/DTOs/bookDTO";

const routes: Array<RouteRecordRaw> = [
  {
    path: '',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/books',
    name: 'books',
    component: Books,
  },
  {
    path: '/book/:id',
    name: 'BookDetail',
    component: BookDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
