import { createRouter, createWebHistory } from '@ionic/vue-router';
import {RouteRecordRaw} from 'vue-router';
import Home from "@/components/Home.vue";
import Login from "@/components/Login.vue";
import Books from "@/components/Books.vue";
import BookDetail from "@/components/BookDetail.vue";
import BookForm from "@/components/BookForm.vue";
import BookingForm from "@/components/BookingForm.vue";

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
    path: '/libraries/:libraryId?/books',
    name: 'books',
    component: Books,
  },
  {
    path: '/book/:id',
    name: 'BookDetail',
    component: BookDetail,
    props: true
  },
  {
    path: '/libraries/:libraryId?/books/form/:id?',
    name: 'BookForm',
    component: BookForm,
  },
  {
     path: '/bookings/:bookId',
     name: 'BookingForm',
     component: BookingForm,
   }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
