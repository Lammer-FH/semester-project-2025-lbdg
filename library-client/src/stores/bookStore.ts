import { defineStore } from 'pinia'
import type { Book } from '@/models/book'

export const useBookStore = defineStore('book', {
    state: () => ({ current: null as Book | null }),
    actions: {
        select(book: Book) { this.current = book }
    }
})