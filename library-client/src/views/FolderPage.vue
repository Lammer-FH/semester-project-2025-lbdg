<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-menu-button color="primary"></ion-menu-button>
        </ion-buttons>
        <ion-title>{{ $route.params.id }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">{{ $route.params.id }}</ion-title>
        </ion-toolbar>
      </ion-header>

      <div id="container">
        <div>
          <h2>Bücherliste</h2>
          <table>
            <thead>
            <tr>
              <th>ID</th>
              <th>Titel</th>
              <th>Autor</th>
              <th>ISBN</th>
              <th>Verlag</th>
              <th>Jahr</th>
              <th>Beschreibung</th>
              <th>Bild</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="book in books" :key="book.isbn">
              <td>{{ book.libraryId }}</td>
              <td>{{ book.title }}</td>
              <td>{{ book.author }}</td>
              <td>{{ book.isbn }}</td>
              <td>{{ book.publisher }}</td>
              <td>{{ book.publishedYear }}</td>
              <td>{{ book.shortDescription }}</td>
              <td>
                <img v-if="book.imageUrl" :src="book.imageUrl" alt="Buchbild" width="80"/>
                <span v-else>Kein Bild</span>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonButtons, IonContent, IonHeader, IonMenuButton, IonPage, IonTitle, IonToolbar } from '@ionic/vue';
import axios from 'axios'
import { ref, onMounted } from 'vue';

  interface Book {
    libraryId: number
    author: string
    title: string
    isbn: string
    publisher: string
    shortDescription: string
    publishedYear: number
    imageUrl: string | null
  }

  const books = ref<Book[]>([])

  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/library-system/books')

      // ✅ Antwortdaten in die books-Variable schreiben
      books.value = response.data

      console.log('Geladene Bücher:', books.value)
    } catch (error) {
      console.error("Fehler beim Abrufen:", error)
    } finally {
      console.log("Anfrage abgeschlossen")
    }
  }

  // Call automatically when component is mounted
  onMounted(() => {
    fetchData();
  });
</script>

<style scoped>
#container {
  text-align: center;
  position: absolute;
  left: 0;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}

#container strong {
  font-size: 20px;
  line-height: 26px;
}

#container p {
  font-size: 16px;
  line-height: 22px;
  color: #8c8c8c;
  margin: 0;
}

#container a {
  text-decoration: none;
}
</style>
