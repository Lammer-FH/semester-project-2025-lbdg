<template>
  <div class="stars">
    <RatingStar
        v-for="n in 5"
        :key="n"
        :position="n"
        :value="value"
    />
    <span class="avg">{{ value.toFixed(1) }} von 5</span>
  </div>
</template>

<script setup lang="ts">
import RatingStar from '@/components/atoms/RatingStar.vue'
import { defineProps } from 'vue'
defineProps<{
  value: number
}>();
</script>

<style scoped>
.stars {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 1.2rem;
}
.avg {
  margin-left: 8px;
  font-size: 0.9rem;
  font-weight: 600;
}
</style>
