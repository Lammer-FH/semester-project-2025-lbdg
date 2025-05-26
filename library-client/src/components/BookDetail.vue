<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-back-button default-href="/" />
        </ion-buttons>
        <ion-title>Details</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="detail-page">
      <div v-if="bookStore.current" class="detail-container">
        <div class="detail-card">
          <!-- top row: cover + author/title -->
          <div class="top">
            <img
                :src="defaultCover"
                alt="Cover"
                class="detail-image"
            />
            <div class="heading">
              <p class="author">{{ bookStore.current.author ?? 'Platzhalter Author' }}</p>
              <h2 class="title">{{ bookStore.current.title }}</h2>
              <!-- metadata -->
              <section class="info">
                <p>Erscheinungsjahr: {{ bookStore.current.publishedYear }}</p>
                <p>Verlag: {{ bookStore.current.publisher }}</p>
              </section>
            </div>
          </div>



          <!-- description -->
          <section class="desc">
            <h3>Beschreibung:</h3>
            <p>{{ bookStore.current.shortDescription }}</p>
          </section>

          <!-- footer row: ISBN left, status right -->
          <div class="footer">
            <p class="isbn-line">
              ISBN {{ bookStore.current.isbn }}
            </p>
            <div class="status-line">
              <span class="status-text">
                {{ bookStore.current.available
                  ? 'ausleihbar'
                  : 'Buch bereits ausgeliehen' }}
              </span>
              <span
                  class="status-indicator"
                  :class="bookStore.current.available ? 'green' : 'red'"
              ></span>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="no-book">
        <p>Keine Buch-Daten vorhanden.</p>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonBackButton,
  IonContent
} from '@ionic/vue'
import { useBookStore } from '@/stores/bookStore'
const bookStore = useBookStore()

// relative import up out of src/ to project-root/assets
import defaultCover from '../../assets/book_cover.png'
</script>

<style scoped>
.detail-page {
  padding: 16px;
  background: #f4b980; /* match your app’s peach */
}

/* center & constrain the white card */
.detail-container {
  display: flex;
  justify-content: center;
  padding: 0 16px; /* give some side gutters on mobile */
}

.detail-card {
  background: #fff;
  border: 1px solid #000;
  border-radius: 8px;
  padding: 16px;
  max-width: 600px;
  margin: 32px auto 0;   /* ← add top space */
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

/* top row */
.top {
  display: flex;
  gap: 16px;
  align-items: center;
  margin-bottom: 16px;
}

.detail-image {
  width: 100px;
  height: auto;
  border-radius: 4px;
  object-fit: cover;
}

.heading {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.author {
  margin: 0;
  font-size: 1rem;
  font-weight: 600;
}

.title {
  margin: 0;
  font-size: 1.4rem;
  font-weight: normal;
}

/* metadata & description */
.info, .desc {
  margin-bottom: 16px;
}

.info h3, .desc h3 {
  margin: 0 0 8px;
  font-size: 1rem;
}

.info p, .desc p {
  margin: 4px 0;
  font-size: 0.9rem;
  line-height: 1.4;
}

/* footer row */
.footer {
  display: flex;
  align-items: center;
  gap: 12px;         /* space between ISBN and status */
  flex-wrap: nowrap; /* prevent wrapping onto two lines */
  margin-top: 24px;
}

.isbn-line {
  margin: 0;
  font-size: 0.8rem;
  color: #555;
  white-space: nowrap; /* never break the ISBN text */
}

.status-line {
  display: flex;
  align-items: center;
  gap: 4px;          /* tighten up circle to the text */
  margin: 0;
}

.status-text {
  font-size: 0.9rem;
  white-space: nowrap; /* keep “Buch bereits…” all on one line */
}

.status-indicator {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  border: 1px solid #333;
}

.status-indicator.green { background: #4caf50; }
.status-indicator.red   { background: #d32f2f; }

/* fallback */
.no-book {
  text-align: center;
  margin-top: 40px;
  color: #888;
}
</style>