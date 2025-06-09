<template>
  <section class="reviews">
    <h3>Kundenrezensionen</h3>
    <RatingStars :value="avgRating" />

    <RatingHistogram :percent="ratingPercent" />

    <div class="toggle-wrapper">
      <ion-button
          fill="clear"
          class="toggle-btn"
          @click="$emit('toggle', !expanded)"
      >
        <ion-icon :icon="expanded ? chevronUp : chevronDown" />
      </ion-button>
      <ion-button
          fill="outline"
          size="small"
          @click="$emit('add')"
      >
        Bewerten
      </ion-button>
    </div>

    <div v-if="expanded" class="review-list">
      <ReviewItem
          v-for="r in ratings"
          :key="r.id"
          :review="r"
          @edit="$emit('edit', $event)"
          @delete="$emit('delete', $event)"
      />
    </div>
  </section>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import ReviewItem       from '@/components/molecules/ReviewItem.vue'
import RatingStars      from '@/components/molecules/RatingStars.vue'
import RatingHistogram  from '@/components/molecules/RatingHistogram.vue'
import {
  chevronDownOutline, chevronUpOutline
} from 'ionicons/icons'
defineProps<{
  ratings:        import('@/models/rating').Rating[],
  avgRating:      number,
  ratingPercent: Record<number,number>,
  expanded:       boolean
}>();
const chevronDown = chevronDownOutline
const chevronUp   = chevronUpOutline
</script>

<style scoped>
.reviews         { margin:24px 0; }
.toggle-wrapper  { display:flex; align-items:center; justify-content:flex-end; gap:8px; margin:8px 0 16px; }
.review-list     { display:flex; flex-direction:column; gap:12px; }
</style>
