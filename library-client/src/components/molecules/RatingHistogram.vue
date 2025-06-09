<template>
  <div class="bars">
    <div v-for="r in [5,4,3,2,1]" :key="r" class="bar-row">
      <span class="bar-label">{{ r }} {{ r === 1 ? 'Stern' : 'Sterne' }}</span>
      <ion-progress-bar :value="percent[r]" />
      <span class="bar-pct">{{ Math.round(percent[r] * 100) }}%</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
defineProps<{
  percent: Record<number, number>
}>();
</script>

<style scoped>
.bars     { margin-top: 12px; }
.bar-row  { display:flex; align-items:center; gap:8px; margin:4px 0; }
.bar-label{ width:75px; font-size:0.8rem; white-space:nowrap; }
.bar-pct  { width:32px; text-align:right; font-size:0.8rem; }
</style>
