import { defineStore } from 'pinia'
import type { BookDTO } from '@/DTOs/bookDTO'

export const useBookStore = defineStore('book', {
    state: () => ({ current: null as BookDTO | null }),
    actions: {
        select(book: BookDTO) { this.current = book }
    }
})