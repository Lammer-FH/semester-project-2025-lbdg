<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-back-button default-href="/books" />
        </ion-buttons>
        <ion-title>Details</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="detail-page">
      <div v-if="book" class="detail-container">
        <div class="detail-card">
          <div class="top">
            <img
                :src="defaultCover"
                alt="Cover"
                class="detail-image"
            />
            <div class="heading">
              <p class="author">{{ book.author ?? 'Platzhalter Author' }}</p>
              <h2 class="title">{{ book.title }}</h2>
              <section class="info">
                <p>Erscheinungsjahr: {{ book?.publishedYear }}</p>
                <p>Verlag: {{ book?.publisher }}</p>
              </section>
            </div>
          </div>
          <ion-button
              v-if="userStore.role == 'LIBRARIAN'"
              @click="navigateTo({ name:'BookForm', params:{ id: book.id, libraryId: book.libraryId} })">
            <ion-icon :icon="editIcon"></ion-icon>
          </ion-button>
          <ion-button
              v-if="userStore.role == 'LIBRARIAN'"
              color="danger"
              @click="confirmDelete(book.id, book.libraryId)">
            <ion-icon :icon="deleteIcon"></ion-icon>
          </ion-button>
          <section class="desc">
            <h3>Beschreibung:</h3>
          </section>

          <section class="reviews" v-if="ratings.length">
            <h3>Kundenrezensionen</h3>

            <div class="stars">
              <ion-icon
                  v-for="n in fullStars"
                  :key="`full-${n}`"
                  :icon="starIcon"
              />
              <ion-icon
                  v-if="halfStar"
                  :icon="starHalfIcon"
              />
              <span class="avg">{{ avgRating.toFixed(1) }} von 5</span>
            </div>

            <div class="bars">
              <div
                  v-for="r in [5,4,3,2,1]"
                  :key="r"
                  class="bar-row"
              >
                <span class="bar-label">{{ r }} {{ r === 1 ? 'Stern' : 'Sterne' }}</span>
                <ion-progress-bar :value="ratingPercent[r]" />
                <span class="bar-pct">
                  {{ Math.round(ratingPercent[r] * 100) }}%
                </span>
              </div>
            </div>

            <ion-button
                fill="clear"
                class="toggle-btn"
                @click="expanded = !expanded"
                :aria-label="expanded ? 'Einklappen' : 'Ausklappen'"
            >
              <ion-icon
                  :icon="expanded ? chevronUp : chevronDown"
                  class="toggle-icon"
              />
            </ion-button>

            <div v-if="expanded" class="review-list">
              <div
                  v-for="rev in ratings"
                  :key="rev.id"
                  class="review-card"
              >
                <div class="review-header">
                  <div class="user-avatar">
                    <ion-icon :icon="avatarIcon" />
                  </div>
                  <span class="user-name">{{ rev.userFullName }}</span>
                  <div class="user-stars">
                    <ion-icon
                        v-for="i in rev.rating"
                        :key="`s${rev.id}-${i}`"
                        :icon="starIcon"
                    />
                    <ion-icon
                        v-for="i in (5 - rev.rating)"
                        :key="`o${rev.id}-${i}`"
                        :icon="starOutlineIcon"
                    />
                  </div>
                </div>
                <p class="comment">{{ rev.comment }}</p>
              </div>
            </div>
          </section>

          <div class="footer">
            <div class="left">
                <p class="isbn-line">
                  ISBN {{ book.isbn }}
                </p>
            </div>
            <div class="right">
                <div class="status-line">
                  <span class="status-text">
                    {{ book.available
                      ? 'ausleihbar'
                      : 'Buch bereits ausgeliehen' }}
                  </span>
                  <span
                      class="status-indicator"
                      :class="book.available ? 'green' : 'red'"
                  ></span>
                </div>
                 <ion-button
                    size="small"
                    @click="navigateTo({ name:'BookingForm', params:{ bookId: book.id} })"
                 >

                    {{ book.available ? 'Buchung vornehmen' : 'Buchung bearbeiten' }}
                </ion-button>
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
  IonContent,
  IonIcon,
  IonProgressBar,
  IonButton
} from '@ionic/vue'
import { onMounted, ref, computed } from 'vue'
import { bookService } from '@/services/bookService'
import type { Rating } from '@/models/rating'
import {
  chevronDownOutline,
  chevronUpOutline,
  star,
  starHalf,
  starOutline,
  personCircleOutline,
  pencil,
  trash,
} from 'ionicons/icons'
import defaultCover from '../../assets/default_book_cover.jpg'
import {Book} from "@/models/book";
import {useNavigation} from "@/services/navigationService";
import router from "@/router";
import {useUserStore} from "@/stores/userStore";
const { navigateTo } = useNavigation()
const { getIdFromUrl } = useNavigation()
const userStore = useUserStore()

const book = ref<Book>()
const ratings = ref<Rating[]>([])
const expanded = ref(false)
const id = getIdFromUrl("id");

const chevronDown = chevronDownOutline
const chevronUp = chevronUpOutline
const starIcon = star
const starHalfIcon = starHalf
const starOutlineIcon = starOutline
const avatarIcon = personCircleOutline
const editIcon = pencil
const deleteIcon = trash

onMounted(async () => {

  try {
    if(id){
      book.value = await bookService.getBookDetails(id)
    }
  } catch (err) {
    console.error('Fehler beim Laden der Buch-Details:', err)
  }

  try {
    if(id){
      ratings.value = await bookService.getRatingsForBook(id)
    }
  } catch (err) {
    console.error('Fehler beim Laden der Bibliotheken:', err)
  }
})

async function confirmDelete(bookId: number, libraryId: number) {
  if (confirm("Buch wirklich löschen?")) {
    await bookService.deleteBook(bookId);
    router.push(`/libraries/${libraryId}/books`);
  }
}

const avgRating = computed(() => {
  if (!ratings.value.length) return 0
  const sum = ratings.value.reduce((acc, r) => acc + r.rating, 0)
  return sum / ratings.value.length
})

const fullStars = computed(() => Math.floor(avgRating.value))
const halfStar  = computed(() => avgRating.value % 1 >= 0.5)

const ratingPercent = computed(() => {
  const counts: Record<number, number> = { 5:0,4:0,3:0,2:0,1:0 }
  ratings.value.forEach(r => {
    counts[r.rating] = (counts[r.rating] || 0) + 1
  })
  const total = ratings.value.length || 1
  const pct: Record<number, number> = {}
  Object.entries(counts).forEach(([k,v]) => {
    pct[+k] = v / total
  })
  return pct
})
</script>

<style scoped>
.detail-page {
  padding: 16px;
  background: #f4b980;
}

.detail-container {
  display: flex;
  justify-content: center;
  padding: 0 16px;
}

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

.info,
.desc {
  margin-bottom: 16px;
}

.desc h3 {
  margin: 0 0 8px;
  font-size: 1rem;
}

.desc p {
  margin: 4px 0;
  font-size: 0.9rem;
  line-height: 1.4;
}

.reviews {
  margin: 24px 0;
}

.reviews h3 {
  margin: 0 0 12px;
  font-size: 1rem;
}

.stars {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 1.2rem;
}

.avg {
  margin-left: 8px;
  font-size: 0.9rem;
  font-weight: 600;
}

.bars {
  margin-top: 12px;
}

.bar-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 4px 0;
}

.bar-label {
  width: 75px;
  font-size: 0.8rem;
  white-space: nowrap;
}

.bar-pct {
  width: 32px;
  text-align: right;
  font-size: 0.8rem;
}

.toggle-btn {
  display: flex;
  justify-content: center;
  margin: 8px 0 16px;
}

.toggle-icon {
  font-size: 1.5rem;
  color: var(--ion-color-primary);
}

.review-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.review-card {
  border: 1px solid rgba(0,0,0,0.2);
  border-radius: 8px;
  padding: 12px;
}

.review-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}

.user-avatar ion-icon {
  font-size: 24px;
  color: #666;
}

.user-name {
  font-weight: 600;
  font-size: 0.9rem;
}

.user-stars ion-icon {
  color: #f5a623;
  font-size: 1rem;
}

.comment {
  margin: 0;
  font-size: 0.9rem;
  line-height: 1.4;
}

.footer {
  display: flex;
  align-items: flex-end;
  gap: 12px;
  flex-wrap: nowrap;
  margin-top: 24px;
}

.left {
  display: flex;
  flex: 1;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 6px;
  height: 100%;
}

.isbn-line {
  margin: 0;
  font-size: 0.8rem;
  color: #555;
  white-space: nowrap;
}

.status-line {
  display: flex;
  align-items: center;
  gap: 4px;
}

.status-text {
  font-size: 0.9rem;
  white-space: nowrap;
}

.status-indicator {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  border: 1px solid #333;
}

.status-indicator.green {
  background: #4caf50;
}

.status-indicator.red {
  background: #d32f2f;
}

.booking-button {
  margin-top: 4px;
  text-transform: none !important;
  --background: #007bff;
  --color: white;
}

.no-book {
  text-align: center;
  margin-top: 40px;
  color: #888;
}
</style>
