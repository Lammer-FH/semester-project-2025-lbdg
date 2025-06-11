<template>
  <ion-app>
    <navbar></navbar>
    <ion-page>
      <ion-content class="pages">
        <router-view></router-view>
      </ion-content>
    </ion-page>
  </ion-app>
</template>

<script setup lang="ts">
import {
  IonApp, IonContent, IonPage,
} from '@ionic/vue';
import Navbar from '@/components/Navbar.vue'
</script>
<style scoped>

</style>
