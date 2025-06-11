<template>
  <ion-page class="edit-page">
    <ion-content class="ion-padding">
      <ion-item class="header">
        <ion-label>Buch {{ id == null ? 'erstellen' : 'bearbeiten' }}</ion-label>
      </ion-item>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="author">Autor*:</label>
          <input id="author" type="text" autocomplete="off" v-model="form.author" required />
        </div>

        <div class="form-group">
          <label for="title">Titel*:</label>
          <input id="title" type="text" autocomplete="off" v-model="form.title" required />
        </div>

        <div class="form-group">
          <label for="publishedYear">Erscheinungsjahr:</label>
          <ion-datetime-button class="datetime-button" datetime="publishedYear"></ion-datetime-button>
          <ion-modal keep-contents-mounted="true">
            <ion-datetime
                id="publishedYear"
                presentation="year"
                v-model="form.publishedYear"
                :prefer-wheel="false"
                :show-default-buttons="true"
                :highlighted-dates="[]"
            />
          </ion-modal>
        </div>

        <div class="form-group">
          <label for="publisher">Verlag*:</label>
          <input id="publisher" type="text" autocomplete="off" v-model="form.publisher" required />
        </div>

        <div class="form-group">
          <label for="shortDescription">Kurzbeschreibung:</label>
          <textarea
              id="shortDescription"
              v-model="form.shortDescription"
              rows="4"
              autocomplete="off"
              class="short-description-textarea"
          ></textarea>
        </div>

        <div class="form-group">
          <label for="isbn">ISBN*:</label>
          <input id="isbn" type="text" autocomplete="off" v-model="form.isbn" required />
        </div>

        <div class="form-group">
          <label for="image">Bild:</label>
          <input type="file" accept="image/*" @change="handleImageUpload" />
        </div>

        <div class="form-actions">
          <ion-buttons slot="start">
            <ion-back-button default-href="defaultHref" />
            <button type="submit" class="submit-button">{{ id == null ? 'Erstellen' : 'Ändern' }}</button>
          </ion-buttons>
        </div>
      </form>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonContent,
  IonPage,
  IonLabel,
  IonItem,
  IonButtons,
  IonBackButton,
  IonDatetime, IonModal, IonDatetimeButton
} from "@ionic/vue";
import {onMounted, ref} from "vue";
import {Book} from "@/models/book";
import {useNavigation} from "@/services/navigationService";
import { useBookStore } from '@/stores/bookStore'
import router from "@/router";
const { getIdFromUrl } = useNavigation()

const bookStore = useBookStore()
const book = ref<Book| null>()
const id = getIdFromUrl("id");
const libraryId = getIdFromUrl("libraryId");

const defaultHref = id == null ? "libaries/" + libraryId + "/books" : "/books/" + id

const form = ref<Partial<Book>>({
  libraryId: libraryId,
  author: '',
  image: '',
  isbn: '',
  publishedYear: 2025,
  publisher: '',
  shortDescription: '',
  title: '',
});

onMounted(async () => {
  if(id){
    book.value = await bookStore.fetchEditDetails(id)
    if(book.value){
      form.value = book.value;
    }
  }
})

const handleImageUpload = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onloadend = () => {
    const result = reader.result;
    if (typeof result === 'string') {
      form.value.image = result.split(',')[1]; // remove "data:image/...;base64,"
    } else {
      console.warn('FileReader result was not a string:', result);
    }
  };
  reader.readAsDataURL(file);
};

async function submitForm() {
  const newBook: Pick<Book, any> = {
    libraryId: 1,
    author: form.value.author ?? '',
    image: form.value.image,
    isbn: form.value.isbn ?? '',
    publishedYear: form.value.publishedYear ?? 2025,
    publisher: form.value.publisher ?? '',
    shortDescription: form.value.shortDescription ?? '',
    title: form.value.title ?? '',
  }
  if (id == null) {
    let new_id;
    // eslint-disable-next-line prefer-const
    new_id = await bookStore.createBook(newBook);
    if (new_id > 0) {
      router.push(`/book/${new_id}`);
    }
  } else {
    const response = await bookStore.updateBook(id, newBook);
    if (response) {
      router.push(`/book/${id}`);
    }
  }
}

</script>

<style scoped>
.edit-page{
  margin-top: 70px;
}

.submit-button {
  width: 100px;
  height: 40px;
  background-color: #0046cc;
  color: white;
  text-transform: uppercase;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;            /* Innenabstand */
  cursor: pointer;
  letter-spacing: 0.5px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.2s ease, background-color 0.2s ease;
}

.header{
  margin-bottom: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
}

.form-group label {
  margin-bottom: 0.25rem;
  font-weight: 500;
}

.form-actions {
  margin-top: 1.5rem;
}

.datetime-button{
  display: flex;
  justify-content: flex-start;
}
</style>