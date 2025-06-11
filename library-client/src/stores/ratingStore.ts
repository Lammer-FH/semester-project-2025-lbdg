import {defineStore} from 'pinia'
import {ratingService} from '@/services/ratingService'
import type {Rating} from '@/models/rating'

export const useRatingStore = defineStore('rating', {
    state: () => ({
        list: [] as Rating[],
        current: null as Rating | null,
    }),
    actions: {

        /**
         * Create a new rating
         */
        async createRating(payload: Omit<Rating, 'id'|'userFullName'>): Promise<Rating | null> {
            return await ratingService.createRating(payload)
        },

        /**
         * Update an existing rating
         */
        async updateRating(id: number, payload: Partial<Omit<Rating, 'id'|'userId'|'userFullName'>>): Promise<Rating | null> {
            return await ratingService.updateRating(id, payload)
        },

        /**
         * Delete a rating
         */
        async deleteRating(id: number): Promise<void> {
            await ratingService.deleteRating(id)
        }
    }
})
