import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        id: null as null | number,
        name: null as null | string,
        role: null as null | string,
    }),
    actions: {
        setUser(id: number, name: string, role: string) {
            this.id = id
            this.name = name
            this.role = role
        },
        clearUser() {
            this.id = null
            this.name = null
            this.role = null
        }
    },
})