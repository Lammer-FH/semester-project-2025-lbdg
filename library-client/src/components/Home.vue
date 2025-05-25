<template>
  <ion-page class="home-page">
    <ion-content class="ion-padding">
      <div class="white-box">
        <h1 v-if="!userStore.name">Willkommen</h1>
        <h1 v-else>Willkommen, {{ userStore.name }}</h1>
        <section class="library-hours">
          <h3>Öffnungszeiten</h3>
          <p>
            Unsere Bibliothek steht Ihnen zu folgenden Zeiten zur Verfügung:
          </p>
          <ul>
            <li><strong>Montag – Freitag:</strong> 08:00 – 18:00 Uhr</li>
            <li><strong>Samstag:</strong> 10:00 – 14:00 Uhr</li>
            <li><strong>Sonn- und Feiertage:</strong> geschlossen</li>
          </ul>
        </section>
        <section class="library-rules">
          <h3>Regeln für die Nutzung unserer Bibliothek</h3>
          <b><p class="rules-text">
            Um allen Besucherinnen und Besuchern ein angenehmes und konzentriertes Arbeiten zu ermöglichen,
            bitten wir um die Einhaltung folgender Regeln:
          </p></b>
          <p class="rules-text">
            Bitte verhalten Sie sich im gesamten Bibliotheksbereich ruhig und verzichten Sie auf laute Gespräche oder Telefonate.
            Mobiltelefone sind lautlos zu stellen.
          </p>
          <p class="rules-text">
            Das Essen ist nur in ausgewiesenen Bereichen gestattet – Getränke in verschließbaren Behältern dürfen mitgeführt werden.
            Medien, die ausgeliehen werden, sind sorgfältig zu behandeln und fristgerecht zurückzugeben.
          </p>
          <p class="rules-text">
            Die Nutzung der Computerarbeitsplätze ist primär für wissenschaftliches Arbeiten vorgesehen.
            Unser Bibliotheksteam steht Ihnen jederzeit gerne für Fragen zur Verfügung.
            Vielen Dank für Ihr Verständnis!
          </p>
        </section>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonContent, IonPage} from "@ionic/vue";
import {useUserStore} from "@/stores/userStore";

const userStore = useUserStore()
</script>

<style scoped>
  ion-content {
    --padding-top: 0;
  }

  .home-page{
    margin-top: 70px;
  }

  .white-box {
    background-color: white;
    padding: 24px;
    border-radius: 16px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    max-width: 800px;
    margin: 0 auto;
    padding-top: 5px;
  }
</style>