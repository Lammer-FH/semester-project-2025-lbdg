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
            <ImageCoverComponent :bookImage="book.image"/>
            <div class="heading">
              <p class="author">{{ book.author ?? 'Platzhalter Author' }}</p>
              <h2 class="title">{{ book.title }}</h2>
              <section class="info">
                <p>Erscheinungsjahr: {{ book.publishedYear }}</p>
                <p>Verlag: {{ book.publisher }}</p>
              </section>
            </div>
          </div>

          <ion-button
              v-if="userStore.role === 'LIBRARIAN'"
              @click="navigateTo({ name: 'BookForm', params: { id: book.id, libraryId: book.libraryId } })"
          >
            <ion-icon :icon="editIcon" />
          </ion-button>
          <ion-button
              v-if="userStore.role === 'LIBRARIAN'"
              color="danger"
              @click="confirmDelete(book.id, book.libraryId)"
          >
            <ion-icon :icon="deleteIcon" />
          </ion-button>

          <section class="desc">
            <h3>{{ book.shortDescription }}</h3>
          </section>

          <section class="reviews" v-if="ratings.length">
            <h3>Kundenrezensionen</h3>

            <div class="stars">
              <ion-icon
                  v-for="n in fullStars"
                  :key="`full-${n}`"
                  :icon="starIcon"
              />
              <ion-icon v-if="halfStar" :icon="starHalfIcon" />
              <span class="avg">{{ avgRating.toFixed(1) }} von 5</span>
            </div>

            <div class="bars">
              <div
                  v-for="r in [5,4,3,2,1]"
                  :key="r"
                  class="bar-row"
              >
                <span class="bar-label">
                  {{ r }} {{ r === 1 ? 'Stern' : 'Sterne' }}
                </span>
                <ion-progress-bar :value="ratingPercent[r]" />
                <span class="bar-pct">
                  {{ Math.round(ratingPercent[r] * 100) }}%
                </span>
              </div>
            </div>

            <div class="toggle-wrapper">
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

              <ion-button
                  v-if="userStore.role === 'STUDENT'"
                  fill="outline"
                  size="small"
                  class="add-rating-btn"
                  @click="openCreateModal"
              >
                Bewerten
              </ion-button>
            </div>

            <div v-if="expanded && ratings" class="review-list">
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
                        v-for="i in 5 - rev.rating"
                        :key="`o${rev.id}-${i}`"
                        :icon="starOutlineIcon"
                    />
                  </div>

                  <div v-if="rev.userId === userStore.id" class="own-actions">
                    <ion-button fill="clear" size="small" @click="openEditModal(rev)">
                      <ion-icon :icon="editIcon" />
                    </ion-button>
                    <ion-button fill="clear" size="small" color="danger" @click="confirmDeleteRating(rev.id)">
                      <ion-icon :icon="deleteIcon" />
                    </ion-button>
                  </div>
                </div>
                <p class="comment">{{ rev.comment }}</p>
              </div>
            </div>
          </section>

          <div class="footer">
            <div class="left">
              <p class="isbn-line">ISBN {{ book.isbn }}</p>
            </div>
            <div class="right">
              <div class="status-line">
                <span class="status-text">
                  {{ book.available ? 'ausleihbar' : 'Buch bereits ausgeliehen' }}
                </span>
                <span
                    class="status-indicator"
                    :class="book.available ? 'green' : 'red'"
                ></span>
              </div>
              <ion-button
                  v-if="userStore.role === 'LIBRARIAN'"
                  size="small"
                  @click="navigateTo({ name: 'BookingForm', params: { bookId: book.id } })"
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

      <ion-modal :is-open="ratingModalOpen" @did-dismiss="closeModal">
        <ion-page>
          <ion-header>
            <ion-toolbar>
              <ion-buttons slot="start">
                <ion-button @click="closeModal">Abbrechen</ion-button>
              </ion-buttons>
              <ion-title>
                {{ editingRatingId ? 'Bewertung bearbeiten' : 'Neue Bewertung' }}
              </ion-title>
            </ion-toolbar>
          </ion-header>
          <!-- TODO abstand beim Bearbeitungs modal schöner machen-->
          <ion-content class="ion-padding">
            <div class="form-wrapper">
              <form @submit.prevent="saveRating">
                <ion-item lines="none">
                  <ion-label>Sterne</ion-label>
                  <div class="star-select">
                    <ion-icon
                        v-for="n in 5"
                        :key="n"
                        :icon="n <= formRating ? starIcon : starOutlineIcon"
                        class="star-select-icon"
                        @click="formRating = n"
                    />
                  </div>
                </ion-item>
                <ion-item>
                  <!-- ignore the error on rows, its the only way to set the height of the textarea -->
                  <ion-textarea v-model="formComment" label="Kommentar"
                                label-placement="floating"
                                fill="outline"
                                class="comment-textarea"
                                rows="8"/>
                </ion-item>
                <ion-button expand="block" type="submit">
                  {{ editingRatingId ? 'Aktualisieren' : 'Speichern' }}
                </ion-button>
              </form>
            </div>
          </ion-content>
        </ion-page>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonPage, IonHeader, IonToolbar, IonTitle,
  IonButtons, IonBackButton,
  IonContent, IonIcon, IonProgressBar, IonButton,
  IonModal, IonItem, IonLabel, IonTextarea
} from '@ionic/vue'
import { onMounted, ref, computed } from 'vue'
import { useBookStore } from '@/stores/bookStore'
import { useRatingStore } from '@/stores/ratingStore'
import type { Rating } from '@/models/rating'
import type { Book } from '@/models/book'
import { useNavigation } from '@/services/navigationService'
import router from '@/router'
import { useUserStore } from '@/stores/userStore'

import {
  chevronDownOutline, chevronUpOutline,
  star, starHalf, starOutline,
  personCircleOutline, pencil, trash
} from 'ionicons/icons'
import ImageCoverComponent from "@/components/ImageCoverComponent.vue";

const { navigateTo, getIdFromUrl } = useNavigation()
const userStore = useUserStore()

// State
const bookStore = useBookStore()
const ratingStore = useRatingStore()
const book = ref<Book| null>()
const ratings = ref<Rating[]>([])
const expanded = ref(false)

// Modal/form state
const ratingModalOpen = ref(false)
const editingRatingId = ref<number | null>(null)
const formRating = ref(5)
const formComment = ref('')

// Icons
const chevronDown = chevronDownOutline
const chevronUp = chevronUpOutline
const starIcon = star
const starHalfIcon = starHalf
const starOutlineIcon = starOutline
const avatarIcon = personCircleOutline
const editIcon = pencil
const deleteIcon = trash

const id = getIdFromUrl('id')
async function refreshRatings() {
  if (!book.value) return
  ratings.value = await bookStore.fetchRatings(book.value.id)
}

onMounted(async () => {
  if (id) {
    book.value = await bookStore.fetchDetails(id)
    if(book.value) {
      await refreshRatings()
    }
  }
})

const avgRating = computed(() => {
  if (!ratings.value.length) return 0
  return ratings.value.reduce((sum, r) => sum + r.rating, 0) / ratings.value.length
})
const fullStars = computed(() => Math.floor(avgRating.value))
const halfStar = computed(() => avgRating.value % 1 >= 0.5)
const ratingPercent = computed(() => {
  const counts: Record<number, number> = { 5:0,4:0,3:0,2:0,1:0 }
  ratings.value.forEach(r => counts[r.rating] += 1)
  const total = ratings.value.length || 1
  return Object.fromEntries(
      Object.entries(counts).map(([k,v]) => [k, v / total])
  ) as Record<number,number>
})

function openCreateModal() {
  editingRatingId.value = null
  formRating.value = 5
  formComment.value = ''
  ratingModalOpen.value = true
}
function openEditModal(r: Rating) {
  editingRatingId.value = r.id
  formRating.value = r.rating
  formComment.value = r.comment
  ratingModalOpen.value = true
}
function closeModal() {
  ratingModalOpen.value = false
}

async function saveRating() {
  if (!book.value) return
  if (!userStore.id) {
    console.error("no user id");
    return;
  }
    let response;
    if (editingRatingId.value) {
      response = await ratingStore.updateRating(editingRatingId.value, {
        rating: formRating.value,
        comment: formComment.value
      });
    } else {
      response = await ratingStore.createRating({
        bookId: book.value.id,
        userId: userStore.id,
        rating: formRating.value,
        comment: formComment.value
      });
    }

    if (response === null) {
      return;
    }

    await refreshRatings();
    closeModal();
}

async function confirmDeleteRating(ratingId: number) {
  if (confirm('Bewertung wirklich löschen?')) {
    await ratingStore.deleteRating(ratingId)
    await refreshRatings()
  }
}

async function confirmDelete(bookId: number, libraryId: number) {
  if (confirm('Buch wirklich löschen?')) {
    await bookStore.deleteBook(bookId)
    router.push(`/libraries/${libraryId}/books`)
  }
}
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
}

.info,
.desc {
  margin-bottom: 16px;
}

.desc h3 {
  margin: 0 0 8px;
  font-size: 1rem;
}

.reviews {
  margin: 24px 0;
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
}

.bar-pct {
  width: 32px;
  text-align: right;
  font-size: 0.8rem;
}

.toggle-wrapper {
  display: flex;
  align-items: center;
  justify-content: end;
  gap: 8px;
  margin: 8px 0 16px;
}

.toggle-btn {
  margin: 0;
}

.add-rating-btn {
  --padding-start: 8px;
  --padding-end:   8px;
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
  margin-top: 24px;
}

.left {
  flex: 1;
}

.isbn-line {
  margin: 0;
  font-size: 0.8rem;
  color: #555;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 6px;
}

.status-line {
  display: flex;
  align-items: center;
  gap: 4px;
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

.no-book {
  text-align: center;
  margin-top: 40px;
  color: #888;
}

.own-actions {
  margin-left: auto;
  display: flex;
  gap: 0;       /* shrink gap between the two buttons */
}

.own-actions ion-button {
  /* optional: tighten the button padding so they take up even less space */
  --padding-start: 2px;
  --padding-end:   2px;
}

.star-select {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-left: auto;
}

.star-select-icon {
  font-size: 1.6rem;
  cursor: pointer;
  transition: transform 0.1s ease;
}

.star-select-icon:hover {
  transform: scale(1.2);
}

.form-wrapper {
  background: #fff;
  border-radius: 8px;
  padding: 16px;
  /* optional drop-shadow for emphasis */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  /* if you want it centered or constrained in width: */
  max-width: 600px;
  margin: 0 auto;
}
</style>
