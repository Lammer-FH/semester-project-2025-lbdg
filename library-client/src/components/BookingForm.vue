<template>
  <ion-page class="booking-form">
    <ion-header>
      <ion-toolbar>

      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <!-- Buchinfo -->
      <div class="detail-card">
        <h1 class="header">Buch ausleihen</h1>
        <div class="top">
          <ImageCoverComponent/>
          <div class="heading">
            <h2 class="title">{{book?.title }}</h2>
            <p class="isbn">{{ book?.isbn}}</p>
          </div>
        </div>
        <form @submit.prevent="submitForm">
          <!-- Datum -->
          <div class="timeSelection">
            <div>
              <p class="label">Start Datum</p>
              <ion-datetime-button datetime="startDate"></ion-datetime-button>
              <ion-modal keep-contents-mounted="true">
                <ion-datetime
                    id="startDate"
                    presentation="date"
                    v-model="form.startDate"
                    :format-options="formatOptions"
                    :disabled="true"
                />
              </ion-modal>
            </div>
            <div>
              <p class="label">End Datum</p>
              <ion-datetime-button datetime="endDate"></ion-datetime-button>
              <ion-modal keep-contents-mounted="true">
                <ion-datetime
                    id="endDate"
                    presentation="date"
                    v-model="form.endDate"
                    :format-options="formatOptions"
                    :min="minEndDate"
                />
              </ion-modal>
            </div>
          </div>

          <!-- User Select -->
          <select v-model="form.userId" required :disabled="id !== 0">
            <option disabled value="">Benutzer auswählen</option>
            <option v-for="student in students" :key="student.id" :value="student.id">
              {{ student.userFullName }}
            </option>
          </select>

        <!-- Bestätigen -->
          <div style="display:flex">
            <ion-button style="width:100%" expand="block" type="submit">{{ id == 0 ? 'Erstellen' : 'Ändern' }}</ion-button>
            <ion-button v-if="id !== 0" color="danger" @click="confirmDelete(id)">
              <ion-icon :icon="deleteIcon" />
            </ion-button>
          </div>
        </form>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang='ts'>
import {onMounted, ref} from 'vue';
import {useBookStore} from '@/stores/bookStore';
import {useUserStore} from '@/stores/userStore';
import {useBookingStore} from '@/stores/bookingStore';
import {useNavigation} from "@/services/navigationService";
import ImageCoverComponent from "@/components/ImageCoverComponent.vue";
import {Book} from "@/models/book";
import {User} from "@/models/user";
import {IonButton, IonDatetime, IonDatetimeButton, IonIcon, IonModal} from '@ionic/vue';
import router from "@/router";
import {Booking} from "@/models/booking";
import {trash} from "ionicons/icons";
const deleteIcon = trash
const { getIdFromUrl } = useNavigation();

const bookStore = useBookStore();
const userStore = useUserStore();
const bookingStore = useBookingStore();

const bookId = getIdFromUrl("bookId");
let id: number = 0;
let minEndDate: string = '';

const booking = ref<Booking| null>()
const book = ref<Book| null>()
const students = ref<User[]>();

function getISODateNDaysFromToday(n: number): string {
  const date = new Date();
  date.setDate(date.getDate() + n);
  return date.toISOString();
}

function formatDateOnly(dateString: string): string {
  return new Date(dateString).toISOString().split('T')[0];
}

const form = ref<Partial<Booking>>({
  startDate: getISODateNDaysFromToday(0),
  endDate: getISODateNDaysFromToday(14),
  userId: null,
  bookId: bookId,
});

onMounted(async () => {
  try {
    if(bookId) {
      book.value = await bookStore.fetchDetails(bookId);
      if(book.value && book.value.bookingId){
        id = book.value.bookingId;
        booking.value = await bookingStore.fetchEditDetails(id)
        if(booking.value){
          form.value = booking.value;
        }
      }
    }
  } catch (err) {
    console.error('Fehler beim Laden der Buch-Details:', err)
  }
  try{
    students.value = await userStore.getStudents();
  }
  catch (err){
    console.error('Fehler beim Laden der Users:', err)
  }
  minEndDate = id === 0 ? getISODateNDaysFromToday(0) : booking.value?.startDate ?? '';
});

async function submitForm() {
  const newBooking: Pick<Booking, any> = {
    startDate: formatDateOnly(form.value.startDate ?? ''),
    endDate: formatDateOnly(form.value.endDate ?? ''),
    userId: form.value.userId,
    bookId: bookId,
  }
  if (id == 0) {
    await bookingStore.createBooking(newBooking);
    router.push(`/book/${bookId}`);
  } else {
    const response = await bookingStore.updateBooking(id, newBooking);
    if (response) {
      router.push(`/book/${bookId}`);
    }
  }
}

async function confirmDelete(id: number) {
  if (confirm('Buchung wirklich löschen?')) {
    await bookingStore.deleteBooking(id)
    router.push(`/book/${bookId}`)
  }
}

const formatOptions = {
  date: {
    weekday: 'short',
    month: '2-digit',
    day: '2-digit',
  }
};
</script>
<style>
.detail-card {
  background: #fff;
  border: 1px solid white;
  border-radius: 8px;
  padding: 16px;
  max-width: 600px;
  margin: 32px auto 0;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.top {
  display: flex;
  gap: 16px;
  align-items: center;
  margin-bottom: 16px;
}

.title {
  margin: 0;
  font-size: 1.2rem;
  font-weight: bold;
}

.header{
  text-align: left;
  width: 100%;
  margin-top: 0px;
  margin-bottom: 5px;
  font-size: 1.5rem;
}

.timeSelection{
  justify-content: left;
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 10px;
  flex-wrap: nowrap;
}

.label{
  margin-bottom: 2px;
  margin-top: 2px;
  font-size: 1.1rem;
  font-weight: bold;
}

</style>