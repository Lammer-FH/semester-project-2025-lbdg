<template>
  <ion-header class="navbar">
    <ion-toolbar>
      <ion-button class="logo-container" fill="clear" @click="navigateTo('/home')">
        <img src="@/../assets/logo.png" alt="Logo" class="logo-img"/>
      </ion-button>
      <ion-title>

      </ion-title>

      <!-- User Icon-Button-->
      <ion-buttons class="login-button" slot="end" @click="openPopover($event)">
          <ion-icon :icon="personCircleOutline" slot="icon-only" size="large"></ion-icon>
      </ion-buttons>
    </ion-toolbar>
  </ion-header>

  <!-- Popover-Dropdown -->
  <ion-popover :is-open="showPopover" :event="popoverEvent" @didDismiss="showPopover = false">
    <ion-content class="popover-content ion-padding" style="width: 200px;">
      <ion-list>
        <ion-item v-if="!userStore.name" button @click="goToLogin">Anmelden</ion-item>
        <ion-item v-else button @click="logout"><div>
          <div>Logout:</div>
            <div>{{ userStore.name }}</div>
            <div>{{ userStore.role }}</div>
          </div>
        </ion-item>
      </ion-list>
    </ion-content>
  </ion-popover>

  <ion-content>
    <slot></slot>
  </ion-content>
</template>

<script setup lang="ts">
import {
	IonHeader,
	IonToolbar,
	IonTitle,
	IonButtons,
  IonButton,
	IonIcon,
	IonContent,
  IonPopover,
  IonList,
  IonItem
} from '@ionic/vue';
import { personCircleOutline } from 'ionicons/icons';
import {ref} from "vue";
import {useNavigation} from "@/services/navigationService";
import {useUserStore} from "@/stores/userStore";
const showPopover = ref(false)
const popoverEvent = ref()
const { navigateTo } = useNavigation()
const userStore = useUserStore()

function openPopover(event: any) {
  popoverEvent.value = event
  showPopover.value = true
}

function goToLogin() {
  showPopover.value = false
  navigateTo('/login') // oder zu deiner Login-Auswahlseite
}

function logout() {
  userStore.clearUser()
  showPopover.value = false
  navigateTo('/home') // optional zurück zur Startseite
}
</script>

<style scoped>
  .logo-container {

  }

  .logo-img {
    height: 24px;
    width: auto;
  }

  .login-button{
    margin-right: 10px;
  }

  .navbar {
    --margin-bottom: 60px;
  }

  .popover-content {
    --background: white;
    background-color: white;
  }
</style>