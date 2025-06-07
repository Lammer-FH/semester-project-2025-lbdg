<template>
  <ion-page class="home-page">
    <ion-content class="ion-padding">
      <ion-item>
        <ion-label>Buch {{ id == null ? 'erstellen' : 'bearbeiten' }}</ion-label>
      </ion-item>
      <form @submit.prevent="submitForm">
        <table>
          <tr>
            <td><label for="author">Autor:</label></td>
            <td><input id="author" type="text" v-model="form.author" required/></td>
          </tr>
          <tr>
            <td><label for="title">Titel:</label></td>
            <td><input id="title" type="text" v-model="form.title" required/></td>
          </tr>
          <tr>
            <td><label for="publishedYear">Erscheinungsjahr:</label></td>
            <td><input id="publishedYear" type="number" v-model="form.publishedYear" /></td>
          </tr>
          <tr>
            <td><label for="publisher">Verlag:</label></td>
            <td><input id="publisher" type="text" v-model="form.publisher" required/></td>
          </tr>
          <tr>
            <td><label for="shortDescription">Kurzbeschreibung:</label></td>
            <td><input id="shortDescription" type="text" v-model="form.shortDescription" /></td>
          </tr>
          <tr>
            <td><label for="isbn">ISBN:</label></td>
            <td><input id="isbn" type="text" v-model="form.isbn" required/></td>
          </tr>
          <!--
          <tr>
            <td><label for="image">Bild:</label></td>
            <td><input id="image" type="text" v-model="form.image" /></td>
          </tr>
          -->
          <tr>
            <td></td>
            <td><button type="submit" class="submit-button">{{ id == null ? 'Erstellen' : 'Ändern' }}</button></td>
          </tr>
        </table>
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
} from "@ionic/vue";
import {onMounted, ref} from "vue";
import {Book} from "@/models/book";
import {useNavigation} from "@/services/navigationService";
import {bookService} from "@/services/bookService";
import router from "@/router";
const { getIdFromUrl } = useNavigation()
const book = ref<Book>()
const id = getIdFromUrl("id");
const libraryId = getIdFromUrl("libraryId");

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

  try {
    if(id){
      book.value = await bookService.getBookEditDetails(id);
      form.value = book.value;
    }
  } catch (err) {
    console.error('Fehler beim Laden der Buch Daten:', err)
  }
})

async function submitForm() {
  const newBook: Pick<Book, any> = {
    libraryId: 1,
    author: form.value.author ?? '',
    image: null,
    isbn: form.value.isbn ?? '',
    publishedYear: form.value.publishedYear ?? 2025,
    publisher: form.value.publisher ?? '',
    shortDescription: form.value.shortDescription ?? '',
    title: form.value.title ?? '',
  }
  if (id == null) {
    let new_id;
    // eslint-disable-next-line prefer-const
    new_id = await bookService.createBook(newBook);
    if (new_id > 0) {
      router.push(`/book/${new_id}`);
    }
  } else {
    await bookService.editBook(id, newBook);
    router.push(`/book/${id}`);
  }

}

</script>

<style scoped>
.home-page{
  margin-top: 70px;
}

.submit-button {
  width: 100px;
  height: 40px;
}
</style>