import axios from 'axios';
import {Rating} from "@/models/rating";
import {Book} from "@/models/book";
import {notify} from "@/services/notificationService";

const API_URL = 'http://localhost:8080/library-system/v1/books';

export const bookService = {

    async getBookDetails(id: number): Promise<Book | null> {
        try {
            const response = await axios.get<Book>(`${API_URL}/${id}`);
            return response.data;
        }  catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buch mit der ID ${id} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden.`, 'danger');
            }
            return null;
        }
    },

    async getRatingsForBook(bookId: number): Promise<Rating[]> {
        try {
            const response = await axios.get<Rating[]>(`${API_URL}/${bookId}/ratings`);
            return response.data;
        }  catch (error: any) {
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

    async getBookEditDetails(id: number): Promise<Book | null> {
        try {
            const response = await axios.get<Book>(`${API_URL}/${id}/edit`);
            return response.data;
        }  catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buch mit der ID ${id} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden.`, 'danger');
            }

            return null;
        }
    },

    async createBook(book: Pick<Book, any>): Promise<number> {
        try {
            const response = await axios.post<number>(`${API_URL}`, book);
            await notify(`Das Buch konnte erfolgreich erstellt werden`, 'success');
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buch mit der ID ${book.id} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Buch konnte nicht erstellt werden!`, 'danger');
            }
            return 0;
        }

    },

    async editBook(bookId: number, book: Pick<Book, any>): Promise<boolean> {
        try {
            await axios.put(`${API_URL}/${bookId}`, book);
            await notify(`Das Buch konnte erfolgreich bearbeitet werden`, 'success');
            return true;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buch mit der ID ${book.id} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Buch konnte nicht bearbeitet werden!`, 'danger');
            }
            return false;
        }
    },

    async deleteBook(bookId: number): Promise<boolean> {
        try {
            await axios.delete(`${API_URL}/${bookId}`);
            await notify(`Das Buch konnte erfolgreich gelöscht werden`, 'success');
            return true;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buch mit der ID ${bookId} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Buch konnte nicht gelöscht werden!`, 'danger');
            }
            return false;
        }
    },
};