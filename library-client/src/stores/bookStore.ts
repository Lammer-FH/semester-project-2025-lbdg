import {defineStore} from 'pinia'
import {bookService} from '@/services/bookService'
import type {Book} from '@/models/book'
import type {Rating} from '@/models/rating'

export const useBookStore = defineStore('book', {
    state: () => ({
        current: null as Book | null,
        editDetails: null as Book | null,
        ratings: [] as Rating[],
    }),
    actions: {
        /**
         * Load book details for viewing
         */
        async fetchDetails(id: number): Promise<Book | null> {
            const book = await bookService.getBookDetails(id)
            this.current = book
            return book
        },

        /**
         * Load book data for editing
         */
        async fetchEditDetails(id: number): Promise<Book | null> {
            const book = await bookService.getBookEditDetails(id)
            this.editDetails = book
            return book
        },

        /**
         * Create a new book; returns new book ID
         */
        async createBook(payload: Pick<Book, any>): Promise<number> {
            return await bookService.createBook(payload)
        },

        /**
         * Update an existing book; returns success flag
         */
        async updateBook(id: number, payload: Pick<Book, any>): Promise<boolean> {
            return await bookService.editBook(id, payload)
        },

        /**
         * Delete a book; returns success flag
         */
        async deleteBook(id: number): Promise<boolean> {
            return await bookService.deleteBook(id)
        },

        /**
         * Load ratings for a book
         */
        async fetchRatings(bookId: number): Promise<Rating[]> {
            const list = await bookService.getRatingsForBook(bookId)
            this.ratings = list
            return list
        }
    }
})
