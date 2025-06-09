import { defineStore } from 'pinia'
import { libraryService } from '@/services/libraryService'
import type { Library } from '@/models/library'
import type { Book } from '@/models/book'

export const useLibraryStore = defineStore('library', {
    state: () => ({
        list: [] as Library[],
        books: [] as Book[],
    }),
    actions: {
        /**
         * Fetch all libraries
         */
        async fetchAll(): Promise<Library[]> {
            const libs = await libraryService.getLibraries()
            this.list = libs
            return libs
        },

        /**
         * Fetch all books of a specific library
         */
        async fetchBooks(libraryId: number): Promise<Book[]> {
            const books = await libraryService.getBooksOfLibrary(libraryId)
            this.books = books
            return books
        }
    }
})