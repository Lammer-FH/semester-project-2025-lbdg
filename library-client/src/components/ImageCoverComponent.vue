<template>
  <img
      :src="defaultCover"
      alt="Cover"
      class="detail-image"
  />
</template>

<script setup lang='ts'>
//TODO: add bookCover as Input, use default cover as fallback.
  import defaultCover from '../../assets/default_book_cover.jpg'
</script>

<style>
  .detail-image {
    width: 100px;
    height: auto;
    border-radius: 4px;
    object-fit: cover;
  }
</style>