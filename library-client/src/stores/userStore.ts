import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        role: null as null | 'Student' | 'Bibliothekar',
        name: null as null | string,
    }),
    actions: {
        setUser(role: 'Student' | 'Bibliothekar', name: string) {
            this.role = role
            this.name = name
        },
        clearUser() {
            this.role = null
            this.name = null
        }
    },
})