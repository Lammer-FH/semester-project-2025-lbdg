import axios from 'axios';
import {User} from "@/models/user";
import {notify} from "@/services/notificationService";

const API_URL = 'http://localhost:8080/library-system/v1/users';

export const userService = {

    async getUsers(): Promise<User[]> {
        try {
            const response = await axios.get<User[]>(API_URL);
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: : Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden.`, 'danger');
            }
            return [];
        }
    },

    async getStudents(): Promise<User[]> {
        try {
            const response = await axios.get<User[]>(`${API_URL}/students`)
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: : Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden.`, 'danger');
            }
            return [];
        }
    },
};