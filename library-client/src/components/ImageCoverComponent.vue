<template>
  <img
      :src="imageSrc"
      alt="Cover"
      class="book-image"
  />
</template>

<script setup lang='ts'>

import {computed} from "vue";

interface Props {
  bookImage?: string // Base64 string
}

const props = defineProps<Props>()


const defaultCover = '../../assets/default_book_cover.jpg'
const imageSrc = computed(() => {
  if (props.bookImage) {
    return `data:image/jpeg;base64,${props.bookImage}`
  }
  return defaultCover
})
</script>