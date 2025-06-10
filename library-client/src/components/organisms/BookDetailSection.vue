<template>
  <div class="book-detail-section">
    <div class="info-header">
      <img :src="cover" alt="Cover" class="detail-image" />

      <div class="meta">
        <p class="author">{{ book.author }}</p>
        <h2 class="title">{{ book.title }}</h2>
        <p>Erscheinungsjahr: {{ book.publishedYear }}</p>
        <p>Verlag: {{ book.publisher }}</p>
      </div>

      <div class="librarian-actions">
        <IconButton
            v-if="role === 'LIBRARIAN'"
            icon="pencil"
            @click="$emit('edit-book', book)"
        />
        <IconButton
            v-if="role === 'LIBRARIAN'"
            icon="trash"
            color="danger"
            @click="$emit('delete-book', book.id)"
        />
      </div>
    </div>

    <p class="short-description">{{ book.shortDescription }}</p>

    <ReviewSection
        :ratings="ratings"
        :avgRating="avgRating"
        :ratingPercent="ratingPercent"
        :expanded="expanded"
        @toggle="$emit('toggle', $event)"
        @add="$emit('add-rating')"
        @edit="$emit('edit-rating', $event)"
        @delete="$emit('delete-rating', $event)"
    />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { Book }   from '@/models/book'
import type { Rating } from '@/models/rating'

import IconButton    from '@/components/atoms/IconButton.vue'
import ReviewSection from '@/components/organisms/ReviewSection.vue'
import defaultCover  from '../../../assets/default_book_cover.jpg'
import { useUserStore } from '@/stores/userStore'

const props = defineProps<{
  book:     Book
  ratings:  Rating[]
  expanded: boolean
}>()
defineEmits<{
  (e: 'toggle', val: boolean): void
  (e: 'add-rating'): void
  (e: 'edit-rating', r: Rating): void
  (e: 'delete-rating', id: number): void
  (e: 'edit-book', b: Book): void
  (e: 'delete-book', id: number): void
}>();
const role = useUserStore().role
const cover = computed(() => props.book.image ?? defaultCover)

const avgRating = computed(() => {
  if (!props.ratings.length) return 0
  return props.ratings.reduce((s, r) => s + r.rating, 0) / props.ratings.length
})

const ratingPercent = computed<Record<number,number>>(() => {
  const counts = { 5:0,4:0,3:0,2:0,1:0 }
  props.ratings.forEach(r => (counts[r.rating] += 1))
  const total = props.ratings.length || 1
  return Object.fromEntries(
      Object.entries(counts).map(([k,v]) => [k, v/total])
  ) as Record<number,number>
})
</script>

<style scoped>
.book-detail-section {
  background: #fff;
  border-radius: 8px;
  padding: 16px;
  margin: 82px 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

/* top row: image, meta, librarian buttons */
.info-header {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  margin-bottom: 16px;
}

.detail-image {
  width: 100px;
  height: auto;
  object-fit: cover;
  border-radius: 4px;
}

.meta {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.author { font-weight: 600; margin: 0; }
.title  { margin: 0; font-size: 1.4rem; }

.librarian-actions {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.short-description {
  margin-bottom: 24px;
  font-size: 0.9rem;
  line-height: 1.4;
}
</style>
