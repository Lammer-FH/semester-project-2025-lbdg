<template>
  <ion-modal :is-open="isOpen" @did-dismiss="handleClose">
    <ion-page>
      <ion-header>
        <ion-toolbar>
          <ion-buttons slot="start">
            <ion-button @click="handleClose">Abbrechen</ion-button>
          </ion-buttons>
          <ion-title>{{ modalTitle }}</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-content class="ion-padding">
        <form @submit.prevent="onSave">
          <!-- Stars picker -->
          <ion-item lines="none">
            <ion-label>Sterne</ion-label>
            <div class="star-select">
              <ion-icon
                  v-for="n in 5"
                  :key="n"
                  :icon="n <= rating ? starIcon : starOutlineIcon"
                  class="star-select-icon"
                  @click="rating = n"
              />
            </div>
          </ion-item>

          <!-- Comment -->
          <ion-item>
            <ion-label position="stacked">Kommentar</ion-label>
            <ion-textarea
                v-model="comment"
                rows="4"
                placeholder="Dein Feedback…"
            />
          </ion-item>

          <!-- Save -->
          <ion-button expand="block" type="submit">
            Speichern
          </ion-button>
        </form>
      </ion-content>
    </ion-page>
  </ion-modal>
</template>

<script setup lang="ts">
import {
  IonModal,
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton,
  IonContent,
  IonItem,
  IonLabel,
  IonTextarea,
  IonIcon
} from '@ionic/vue'
import { ref, watch } from 'vue'
import { star, starOutline } from 'ionicons/icons'

/**
 * Props:
 * - isOpen: show/hide the modal
 * - modalTitle: header title (e.g. "Neue Bewertung" or "Bewertung bearbeiten")
 * - initialRating, initialComment: to seed the form when editing
 */
const props = defineProps<{
  isOpen: boolean
  modalTitle: string
  initialRating: number
  initialComment: string
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'save', payload: { rating: number; comment: string }): void
}>()

// local form state
const rating = ref(props.initialRating)
const comment = ref(props.initialComment)

// whenever props change (e.g. opening in edit mode), reset form
watch(
    () => props.initialRating,
    v => (rating.value = v)
)
watch(
    () => props.initialComment,
    v => (comment.value = v)
)

const starIcon = star
const starOutlineIcon = starOutline

function handleClose() {
  emit('close')
}

function onSave() {
  emit('save', { rating: rating.value, comment: comment.value })
}
</script>

<style scoped>
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
</style>
