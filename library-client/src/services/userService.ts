import axios from 'axios';
import {User} from "@/models/user";

const API_URL = 'http://localhost:8080/library-system/v1/users';

export const userService = {

    async getUsers(): Promise<User[]> {
        const response = await axios.get<User[]>(API_URL);
        return response.data;
    },
    async getStudents(): Promise<User[]> {
        const response = await axios.get<User[]>(`${API_URL}/students`)
        return response.data;
    },
};