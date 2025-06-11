import axios from 'axios';
import {Library} from "@/models/library";
import {Book} from "@/models/book";
import {notify} from "@/services/notificationService";

const API_URL = 'http://localhost:8080/library-system/v1/libraries';

export const libraryService = {
    async getLibraries(): Promise<Library[]> {
        try {
            const response = await axios.get<Library[]>(API_URL);
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden.`, 'danger');
            }
            return [];
        }
    },

    async getBooksOfLibrary(libraryId: number): Promise<Book[]> {
        try {
            const response = await axios.get<Book[]>(`${API_URL}/${libraryId}/books`);
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden.`, 'danger');
            }
            return [];
        }
    },
};