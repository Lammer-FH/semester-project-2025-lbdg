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
         * Fetch all ratings, optionally for a specific book
         */
        async fetchAll(bookId?: number): Promise<Rating[]> {
            const ratings = await ratingService.getRatings(bookId)
            this.list = ratings
            return ratings
        },

        /**
         * Fetch one rating
         */
        async fetchOne(id: number): Promise<Rating | null> {
            const rating = await ratingService.getRating(id)
            this.current = rating
            return rating
        },

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
