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



      <!-- Suche -->
      <!--<h2 class="label">Kunden suchen</h2>
      <div class="search-section">
        <div class="search-input-wrapper">
          <ion-icon :icon="searchCircleOutline" class="search-icon"></ion-icon>
          <input v-model="searchQuery" placeholder="Mustermann" class="search-input" />
        </div>

        <button class="search-button">
          <ion-icon :icon="searchCircleOutline" class="icon-only"></ion-icon>
        </button>

        <button class="filter-button">
          Filter
        </button>
      </div>-->

        <!-- Datum -->
        <div class="timeSelection">
          <div>
            <p class="label">Start Datum</p>
            <ion-datetime-button datetime="datetime"></ion-datetime-button>
            <ion-modal :keep-contents-mounted="true">
              <ion-datetime
                  id="datetime"
                  presentation="date"
                  value="2023-11-02"
                  :format-options="formatOptions"
              ></ion-datetime>
            </ion-modal>
          </div>
          <div>
            <p class="label">End Datum</p>
            <ion-datetime-button datetime="datetime"></ion-datetime-button>
            <ion-modal :keep-contents-mounted="true">
              <ion-datetime
                  id="datetime"
                  presentation="date"
                  value="2023-11-02"
                  :format-options="formatOptions"
              ></ion-datetime>
            </ion-modal>
          </div>
        </div>





      <!-- Trefferliste -->
        <div class="results">
          <!--<p>{{searchQuery}} : {{users?.value?.length ?? 0}} Treffer</p>-->

          <ion-list class="customer-list">
            <ion-card
                v-for="user in users"
                :key="user.id"
                class="customer-card"
                button
                @click="selectUser()">



              <div class="card-body">

                <div class="text">
                  <div class="userId">{{ user.id }}</div>
                  <p class="userFullName">{{ user.userFullName }}</p>
                </div>
              </div>

            </ion-card>
          </ion-list>
        </div>
      <!-- Bestätigen -->
      <ion-button expand="block" @click="confirmBooking">Bestätigen</ion-button>
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
import defaultCover from "../../assets/default_book_cover.jpg";
import {Book} from "@/models/book";
import {User} from "@/models/user";
import {searchCircleOutline} from "ionicons/icons";
import {IonIcon} from "@ionic/vue";
import { IonDatetime, IonDatetimeButton, IonModal } from '@ionic/vue';
import { defineComponent } from 'vue';
const { getIdFromUrl } = useNavigation();

const bookStore = useBookStore();
const userStore = useUserStore();
const bookingStore = useBookingStore();

const bookId = getIdFromUrl("bookId");

const book = ref<Book>()
const users = ref<User[]>();
const searchQuery = ref('');
const startDate = ref('');
const endDate = ref('');
const selectedUser = ref(null);

onMounted(async () => {
  try {
    if(bookId) {
      book.value = await bookStore.fetchDetails(bookId)
    }
  } catch (err) {
    console.error('Fehler beim Laden der Buch-Details:', err)
  }
  try{
    users.value = await userStore.getUsers();
  }
  catch (err){
    console.error('Fehler beim Laden der Users:', err)
  }
});

const searchUsers = async () => {
  users.value = await userService.searchUsers(searchQuery.value);
};

const selectUser = (user) => {
  selectedUser.value = user;
};

const confirmBooking = async () => {
  if (!selectedUser.value || !startDate.value || !endDate.value) return;
  await bookingStore.createBooking({
    bookId,
    userId: selectedUser.value.id,
    startDate: startDate.value,
    endDate: endDate.value
  });
  router.push('/books'); // zurück zur Übersicht
};

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

.searchButton{
  color :black;
}

.searchbar{
  border: black solid 1px;
  border-radius: 10px;
  height: 10px;
  padding: 0px;
}



.search-section {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-bottom: 12px;
  flex-wrap: nowrap;
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

.search-icon {
  font-size: 1.8rem;
  margin-right: 0px;
  color: #666;
  width: 35px;
}

.search-input {
  border: none;
  outline: none;
  font-size: 1rem;
  color: #444;
  width: 90%;
}


.icon-only {
  font-size: 4rem;

  color: black;
}

.search-input-wrapper {
  display: flex;
  align-items: center;
  border: 2px solid black;
  border-radius: 30px;
  padding: 1px 5px;
  flex-grow: 1;
  min-width: 0; /* verhindert Überbreite */
  background-color: transparent;
}

.search-button {
  flex-shrink: 0;
  width: 35px;
  height: 35px;
  margin-left: 0px;
  margin-right: 0px;
  background-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
}

.filter-button {
  flex-shrink: 0;
  white-space: nowrap;
  background-color: white;
  border: 2px solid black;
  border-radius: 12px;
  padding: 4px 12px;
  font-size: 1rem;
  box-shadow: 2px 2px 0 rgba(0, 0, 0, 0.2);
}

.card-body {
  display: flex;
  gap: 12px;
  align-items: center;
  background-color: #fef1bc;
}

.customer-card {
  position: relative;
  background-color: #fef1bc;
  border: 1px solid rgba(0,0,0,0.2);
  border-radius: 8px;
  padding: 16px;
  padding-top: 32px;
  margin: 12px 0;
}

.results{
  border-radius: 15px;
  height: 400px;
  overflow-y: scroll;
}

.customer-list{
  background-color: transparent;
}
</style>