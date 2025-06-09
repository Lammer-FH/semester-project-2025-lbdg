<template>
  <div class="detail-card">
    <div class="top">
      <img :src="cover" class="detail-image" alt="Cover" />
      <div class="heading">
        <p class="author">{{ book.author }}</p>
        <h2 class="title">{{ book.title }}</h2>
        <p>Erscheinungsjahr: {{ book.publishedYear }}</p>
        <p>Verlag: {{ book.publisher }}</p>
      </div>
    </div>
    <slot name="librarian-actions" />
    <section class="desc">
      <h3>{{ book.shortDescription }}</h3>
    </section>
  </div>
</template>

<script setup lang="ts">
import type { Book } from '@/models/book'
import defaultCover   from '../../../assets/default_book_cover.jpg'
import { defineProps } from 'vue'

const props = defineProps<{
  book: Book
}>()

const cover = props.book.image ?? defaultCover
</script>

<style scoped>
.detail-card { /* same styles as before */ }
.detail-image{ width:100px; border-radius:4px; object-fit:cover; }
.heading    { display:flex; flex-direction:column; gap:4px; }
/* …etc… */
</style>
