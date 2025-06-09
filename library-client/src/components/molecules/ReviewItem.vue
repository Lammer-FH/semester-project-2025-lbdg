<template>
  <div class="review-card">
    <div class="review-header">
      <ion-icon :icon="avatarIcon" class="user-avatar" />
      <span class="user-name">{{ review.userFullName }}</span>
      <RatingStars :value="review.rating" />
      <div v-if="isOwn" class="own-actions">
        <IconButton icon="pencil" @click="$emit('edit', review)" />
        <IconButton icon="trash" color="danger" @click="$emit('delete', review.id)" />
      </div>
    </div>
    <p class="comment">{{ review.comment }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { Rating } from '@/models/rating'
import RatingStars from '@/components/molecules/RatingStars.vue'
import IconButton  from '@/components/atoms/IconButton.vue'
import { personCircleOutline } from 'ionicons/icons'
import { useUserStore }    from '@/stores/userStore'

const props = defineProps<{
  review: Rating
}>()

const userStore = useUserStore()
const isOwn     = computed(() => props.review.userId === userStore.id)
const avatarIcon= personCircleOutline
</script>

<style scoped>
.review-card    { border:1px solid rgba(0,0,0,0.2); border-radius:8px; padding:12px; }
.review-header  { display:flex; align-items:center; gap:8px; margin-bottom:8px; }
.user-avatar    { font-size:24px; color:#666; }
.user-name      { font-weight:600; font-size:0.9rem; }
.comment        { margin:0; font-size:0.9rem; line-height:1.4; }
.own-actions    { margin-left:auto; display:flex; gap:0px; }
</style>
