import { defineStore } from 'pinia'
import {User} from "@/models/user";
import {userService} from "@/services/userService";

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
        },
        async getUsers(): Promise<User[]> {
            return await userService.getUsers();
        },
        async getStudents(): Promise<User[]> {
            return await userService.getStudents();
        }
    },
})