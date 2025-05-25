<template>
  <ion-page class="home-page">
    <ion-content class="ion-padding">
      <!-- select libraries -->
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

      <!-- list Books of selected library -->
      <ion-list v-if="books.length > 0" class="book-list">
        <ion-card
            v-for="book in books"
            :key="book.id"
            class="book-card">
          <ion-grid>
            <ion-row>
              <ion-col size="4">
                <img :src="book.image" alt="Cover" class="book-image" />
              </ion-col>
              <ion-col size="8">
                <div class="book-meta">
                  <p class="isbn">ISBN {{ book.isbn }}</p>
                  <p class="author">{{ book.author }}</p>
                  <h3 class="title">{{ book.title }}</h3>
                </div>
              </ion-col>
            </ion-row>
          </ion-grid>
        </ion-card>
      </ion-list>
    </ion-content>

    <!-- searchbar -->
    <ion-footer>
      <ion-toolbar>
        <ion-searchbar></ion-searchbar>
      </ion-toolbar>
    </ion-footer>
    </ion-page>
</template>

<script setup lang="ts">
import {
  IonContent,
  IonPage,
  IonSelectOption,
  IonLabel,
  IonItem,
  IonSelect
} from "@ionic/vue";
import {onMounted, ref} from "vue";
import {LibraryDTO} from "@/DTOs/libraryDTO";
import {libraryService} from "@/services/librariesService";
import {BookDTO} from "@/DTOs/bookDTO";
const libraries = ref<LibraryDTO[]>([])
const books = ref<BookDTO[]>([])
const selectedLibrary = ref<number | null>(null)

onMounted(async () => {
  try {
    libraries.value = await libraryService.getLibraries();
  } catch (err) {
    //error.value = err instanceof Error ? err.message : 'An error occurred';
  }
})

function onLibraryChange(event: CustomEvent) {
  const selectedId = event.detail.value
  console.log('Ausgewählte Bibliothek ID:', selectedId)

  // Optional: direkt Bücher laden
  loadBooksForLibrary(selectedId)
}

async function loadBooksForLibrary(libraryId: number) {
  try {
    books.value = await libraryService.getBooksOfLibrary(libraryId)
  } catch (err) {
    console.error('Fehler beim Laden der Bücher:', err)
  }
}

</script>

<style scoped>
  .home-page{
    margin-top: 70px;
  }

  .book-list {
    margin-top: 1rem;
  }

  .book-card {
    background-color: #fff9dc;
    border-radius: 12px;
    padding: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .book-image {
    width: 100%;
    border-radius: 8px;
  }

  .book-meta {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  .isbn {
    font-size: 0.8rem;
    color: #666;
  }

  .author {
    font-weight: bold;
    font-size: 1rem;
    margin-top: 4px;
  }

  .title {
    margin: 4px 0;
    font-size: 1.1rem;
  }
</style>