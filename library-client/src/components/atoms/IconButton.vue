<template>
  <ion-button
      :fill="fill"
      :size="size"
      :color="color"
      class="icon-button"
      @click="$emit('click')"
  >
    <ion-icon :icon="icon" />
  </ion-button>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'

const props = defineProps<{
  icon: any
  fill?: 'clear' | 'outline'
  size?: 'small' | 'default'
  color?: string
}>()

const fill      = props.fill      ?? 'clear'
const size      = props.size      ?? 'small'
const color     = props.color     ?? undefined
</script>

<style scoped>
.icon-button {
  --padding-start: 4px;
  --padding-end:   4px;
}
</style>
