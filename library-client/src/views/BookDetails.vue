<template>
  <ion-page>
    <ion-content class="detail-page">
      <BookDetailSection
          v-if="book"
          :book="book"
          :ratings="ratings"
          :expanded="expanded"
          @toggle="expanded = $event"
          @add-rating="openCreateModal"
          @edit-rating="openEditModal"
          @delete-rating="confirmDeleteRating"
          @edit-book="navigateTo({ name: 'BookForm', params: { id: book.id, libraryId: book.libraryId } })"
          @delete-book="confirmDelete(book.id, book.libraryId)"
      />

      <RatingModal
          :is-open="ratingModalOpen"
          modal-title="Rating editieren"
          :initial-rating="formRating"
          :initial-comment="formComment"
          @save="saveRating"
          @close="closeModal"
      />

      
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonPage
} from '@ionic/vue'
import { onMounted, ref } from 'vue'
import { bookService } from '@/services/bookService'
import { ratingService } from '@/services/ratingService'
import type { Rating } from '@/models/rating'
import type { Book } from '@/models/book'
import { useNavigation } from '@/services/navigationService'
import router from '@/router'
import { useUserStore } from '@/stores/userStore'
import RatingModal from "@/components/organisms/RatingModal.vue";
import BookDetailSection from "@/components/organisms/BookDetailSection.vue";

const { navigateTo, getIdFromUrl } = useNavigation()
const userStore = useUserStore()

// State
const book = ref<Book>()
const ratings = ref<Rating[]>([])
const expanded = ref(false)

// Modal/form state
const ratingModalOpen = ref(false)
const editingRatingId = ref<number | null>(null)
const formRating = ref(5)
const formComment = ref('')

const id = getIdFromUrl('id')
async function refreshRatings() {
  if (!book.value) return
  ratings.value = await bookService.getRatingsForBook(book.value.id)
}

onMounted(async () => {
  if (id) {
    book.value = await bookService.getBookDetails(id)
    await refreshRatings()
  }
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
  try {
    if (editingRatingId.value) {
      await ratingService.updateRating(editingRatingId.value, {
        rating: formRating.value,
        comment: formComment.value
      })
    } else {
      await ratingService.createRating({
        bookId: book.value.id,
        userId: userStore.id,
        rating: formRating.value,
        comment: formComment.value
      })
    }
    await refreshRatings()
    closeModal()
  } catch (err) {
    console.error('Fehler beim Speichern der Bewertung:', err)
  }
}

async function confirmDeleteRating(ratingId: number) {
  if (confirm('Bewertung wirklich löschen?')) {
    await ratingService.deleteRating(ratingId)
    await refreshRatings()
  }
}

async function confirmDelete(bookId: number, libraryId: number) {
  if (confirm('Buch wirklich löschen?')) {
    await bookService.deleteBook(bookId)
    router.push(`/libraries/${libraryId}/books`)
  }
}
</script>

<style scoped>
.detail-page { padding:16px; background:#f4b980; }

.detail-page {
  padding: 16px;
  background: #f4b980;
}

.desc h3 {
  margin: 0 0 8px;
  font-size: 1rem;
}

.user-avatar ion-icon {
  font-size: 24px;
  color: #666;
}

.user-stars ion-icon {
  color: #f5a623;
  font-size: 1rem;
}
</style>
