<template>
  <ion-page>
    <ion-content class="login-page" fullscreen>
      <div class="container">
        <h2>Wählen Sie einen User aus</h2>
        <div v-if="users">
          <ion-button v-for="user in users"
                      :key="user.id"
                      @click="selectUser(user.id, user.userFullName, user.role)"
                      expand="block"
                      class="user-button"
                      fill="clear">
            {{ Roles[user.role as keyof typeof Roles] }} {{ user.userFullName }}
          </ion-button>
        </div>
        <div v-else>
          <p>Keine User gefunden</p>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonContent, IonButton } from '@ionic/vue'
import {useNavigation} from "@/services/navigationService";
import {useUserStore} from "@/stores/userStore";
import {onMounted, ref} from "vue";
import {Roles, User} from "@/models/user";
const users = ref<User[]>([])

const { navigateTo } = useNavigation()
const userStore = useUserStore()

function selectUser(id: number, name: string, role: string) {
  userStore.setUser(id, name, role)
  navigateTo('/home')
}

onMounted(async () => {
users.value = await userStore.getUsers()

})

</script>

<style scoped>
  .login-page {
    --background: #f4c291;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .container {
    text-align: center;
    width: 80%;
    margin: 0 auto;
    margin-top: 20%;
  }

  h2 {
    margin-bottom: 2rem;
    font-size: 1.2rem;
  }

  .user-button {
    margin: 1rem 0;
    border: 1px solid #444;
    background-color: white;
    color: black;
    font-weight: bold;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
  }

  .user-button {
    width: 100%;
    padding: 12px;
    margin: 1rem 0;
    background-color: white;
    --background-color: white;
    color: #333;
    border: none;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-weight: normal;
    font-size: 1rem;
    cursor: pointer;
    transition: transform 0.1s ease, box-shadow 0.1s ease;
    text-transform: none;
  }

  .user-button:active {
    transform: scale(0.98);
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  }
</style>