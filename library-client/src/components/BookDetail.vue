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
      <div class="detail-card">
        <div class="top">
          <img :src="coverSrc" alt="Cover" class="detail-image" />
          <div class="heading">
            <h2 class="author">{{ book.author }}</h2>
            <h1 class="title">{{ book.title }}</h1>
          </div>
        </div>

        <section class="info">
          <h3>Details</h3>
          <p>Erscheinungsjahr: {{ book.publishedYear }}</p>
          <p>Verlag: {{ book.publisher }}</p>
        </section>

        <section class="desc">
          <h3>Beschreibung:</h3>
          <p>{{ book.shortDescription }}</p>
        </section>

        <div class="status-line">
          <span class="status-text">
            {{ book.available ? 'ausleihbar' : 'Buch bereits ausgeliehen' }}
          </span>
          <span
              class="status-indicator"
              :class="book.available ? 'green' : 'red'">
          </span>
        </div>

        <p class="isbn-line">ISBN {{ book.isbn }}</p>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonPage, IonHeader, IonToolbar, IonTitle,
  IonButtons, IonBackButton, IonContent
} from '@ionic/vue'
import {computed, defineProps} from 'vue'
import type { BookDTO } from '@/DTOs/bookDTO'

//this pulls in the book as prop from the router
const props = defineProps<{ book: BookDTO }>()
const { book } = props

const coverSrc = computed(() => book.image?.trim() ? book.image : '../../assets/book_cover.png');

</script>

<style scoped>
.detail-page {
  padding: 16px;
}

.detail-card {
  background: #fff;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.top {
  display: flex;
  gap: 16px;
  align-items: center;
  margin-bottom: 16px;
}

.detail-image {
  width: 100px;
  border-radius: 4px;
}

.heading .author {
  margin: 0;
  font-size: 1rem;
  font-weight: 600;
}

.heading .title {
  margin: 4px 0 0;
  font-size: 1.4rem;
}

.info, .desc {
  margin: 12px 0;
}

.status-line {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;
  margin: 12px 0;
}

.status-indicator {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  border: 1px solid #333;
}
.status-indicator.green { background: #4caf50; }
.status-indicator.red   { background: #d32f2f; }

.isbn-line {
  font-size: 0.8rem;
  color: #666;
  text-align: right;
  margin: 8px 0 0;
}
</style>
