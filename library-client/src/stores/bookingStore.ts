import {defineStore} from 'pinia'
import {bookingService} from '@/services/bookingService'
import type {Booking} from '@/models/booking'

export const useBookingStore = defineStore('booking', {
    state: () => ({
        current: null as Booking | null,
        editDetails: null as Booking | null,
    }),
    actions: {
        /**
         * Load booking details for viewing
         */
        async fetchDetails(id: number): Promise<Booking> {
            const booking = await bookingService.getBookingDetails(id)
            this.current = booking
            return booking
        },

        /**
         * Load booking data for editing
         */
        async fetchEditDetails(id: number): Promise<Booking> {
            const booking = await bookingService.getBookingEditDetails(id)
            this.editDetails = booking
            return booking
        },

        /**
         * Create a new booking; returns new booking ID
         */
        async createBooking(payload: Pick<Booking, any>): Promise<number> {
            return await bookingService.createBooking(payload)
        },

        /**
         * Update an existing booking; returns success flag
         */
        async updateBooking(id: number, payload: Pick<Booking, any>): Promise<boolean> {
            return await bookingService.editBooking(id, payload)
        },

        /**
         * Delete an existing booking by ID
         */
        async deleteBooking(id: number): Promise<boolean> {
            return await bookingService.deleteBooking(id)
        }
    }
})
