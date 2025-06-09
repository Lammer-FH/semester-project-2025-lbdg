<template>
  <ion-page class="home-page">
    <ion-content class="ion-padding">
      <ion-item>
        <ion-label>Bibliothek</ion-label>
        <ion-select
            interface="popover"
            placeholder="Bibliothek wählen"
            v-model="selectedLibrary"
            @ionChange="onLibraryChange">
          <ion-select-option
              v-for="library in libraries"
              :key="library.id"
              :value="library.id">
          {{ library.name }}
          </ion-select-option>
        </ion-select>
      </ion-item>
      <ion-button
          v-if="selectedLibrary != null && userStore.role == 'LIBRARIAN'"
          button
          @click="navigateTo({ name:'BookForm', params:{ libraryId: selectedLibrary } })">
        <ion-icon :icon="addIcon"></ion-icon>
      </ion-button>
      <div v-if="books.length" class="cards-container">
        <ion-list class="book-list">
          <ion-card
              v-for="book in books"
              :key="book.id"
              class="book-card"
              button
              @click="navigateTo({ name:'BookDetail', params:{ id: book.id } })">

            <div class="isbn">ISBN {{ book.isbn }}</div>

            <div class="card-body">
              <img :src="defaultCover"
                   alt="Cover"
                   class="book-image" />
              <div class="text">
                <p class="author">{{ book.author ? book.author : 'Platzhalter Author' }}</p>
                <h3 class="title">{{ book.title }}</h3>
              </div>
            </div>
            <div class="status">
        <span class="status-text">
          {{ book.available ? 'ausleihbar' : 'ausgeborgt' }}
        </span>
              <span
                  class="status-indicator"
                  :class="book.available ? 'green' : 'red'"></span>
            </div>
          </ion-card>
        </ion-list>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonContent,
  IonPage,
  IonSelectOption,
  IonLabel,
  IonItem,
  IonSelect,
  IonIcon
} from "@ionic/vue";
import {
  add
} from 'ionicons/icons'
import defaultCover from '../../assets/default_book_cover.jpg'
import {onMounted, ref} from "vue";
import {Library} from "@/models/library";
import {useLibraryStore} from "@/stores/libraryStore";
import {Book} from "@/models/book";
import {useNavigation} from "@/services/navigationService";
import {useUserStore} from "@/stores/userStore";
const { navigateTo } = useNavigation()
const { setIdToUrl } = useNavigation()
const { getIdFromUrl } = useNavigation()
const libraries = ref<Library[]>([])
const books = ref<Book[]>([])
const selectedLibrary = ref<number | null>(null)
const addIcon = add
const userStore = useUserStore()
const libraryStore = useLibraryStore()

onMounted(async () => {
  try {
    libraries.value = await libraryStore.fetchAll();
    const libraryId = getIdFromUrl("libraryId");
    console.log("id", libraryId)
    if (libraryId) {
      const match = libraries.value.find(lib => lib.id === libraryId)
      if (match) {
        selectedLibrary.value = match.id
        loadBooksForLibrary(libraryId)
      }
    }
  } catch (err) {
    console.error('Fehler beim Laden der Bibliotheken:', err)
  }
})

function onLibraryChange(event: CustomEvent) {
  const selectedId = event.detail.value
  console.log('Ausgewählte Bibliothek ID:', selectedId)

  loadBooksForLibrary(selectedId)
  setIdToUrl(selectedId, "books");
}

async function loadBooksForLibrary(libraryId: number) {
  try {
    books.value = await libraryStore.fetchBooks(libraryId)
  } catch (err) {
    console.error('Fehler beim Laden der Bücher:', err)
  }
}

</script>

<style scoped>
  .home-page{
    margin-top: 70px;
  }

  .book-image {
    width: 100%;
    border-radius: 8px;
  }

  .book-card {
    position: relative;
    background-color: #fef1bc;
    border: 1px solid rgba(0,0,0,0.2);
    border-radius: 8px;
    padding: 16px;
    padding-top: 32px;
    margin: 12px 0;
  }

  .isbn {
    position: absolute;
    top: 8px;
    right: 12px;
    font-size: 0.75rem;
    color: #333;
  }

  .card-body {
    display: flex;
    gap: 12px;
    align-items: center;
  }

  .book-image {
    width: 60px;
    border-radius: 4px;
    object-fit: cover;
  }

  .text {
    display: flex;
    flex-direction: column;
    gap: 4px;
    padding-bottom: 8px;
  }

  .title {
    font-size: 1rem;
    margin: 0;
  }

  .author {
    font-size: 0.85rem;
    margin: 0;
    color: #555;
  }

  .status {
    position: absolute;
    bottom: 8px;
    right: 12px;
    display: flex;
    align-items: center;
    gap: 6px;
  }

  .status-text {
    font-size: 0.8rem;
  }

  .status-indicator {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    border: 1px solid #333;
  }

  .status-indicator.green {
    background-color: #4caf50;
  }

  .status-indicator.red {
    background-color: #d32f2f;
  }

  .cards-container {
    background: #ffffff;
    padding: 16px;
    border-radius: 8px;
    margin: 16px auto;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
  }
</style>