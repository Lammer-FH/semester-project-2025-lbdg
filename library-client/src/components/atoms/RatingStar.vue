<template>
  <ion-icon :icon="icon" class="rating-star" />
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { star, starHalf, starOutline } from 'ionicons/icons'
import { defineProps } from 'vue'

/**
 * value: the average (or user) rating (e.g. 3.7)
 * position: 1…5
 */
const props = defineProps<{
  value: number
  position: number
}>()

const icon = computed(() => {
  if (props.value >= props.position) return star
  if (props.value >= props.position - 0.5) return starHalf
  return starOutline
})
</script>

<style scoped>
.rating-star {
  font-size: 1.2rem;
  color: var(--ion-color-warning);
}
</style>
