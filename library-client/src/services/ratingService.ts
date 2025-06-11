import axios from 'axios'
import type { Rating } from '@/models/rating'
import {notify} from "@/services/notificationService";

const API_URL = 'http://localhost:8080/library-system/v1/ratings'

export const ratingService = {
    // TODO deprecated
    // Fetch all ratings (optionally filter by bookId)
    async getRatings(bookId?: number): Promise<Rating[]> {

        const url = bookId != null
            ? `${API_URL}?bookId=${bookId}`
            : API_URL
        const response = await axios.get<Rating[]>(url)
        return response.data
    },

    // TODO deprecated
    // Fetch one rating (for edit form)
    async getRating(id: number): Promise<Rating | null> {
        const response = await axios.get<Rating>(`${API_URL}/${id}`)
        return response.data
    },

    // Create a new rating
    async createRating(r: Omit<Rating, 'id'|'userFullName'>): Promise<Rating | null> {
        try {
            // backend will fill id and resolve userFullName from userId
            const response = await axios.post<Rating>(API_URL, r)
            await notify(`Das Rating konnte erfolgreich erstellt werden`, 'success');
            return response.data
        }  catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Rating mit der ID ${r.bookId} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten.  Rating konnte nicht erstellt werden.`, 'danger');
            }
            return null
        }
    },

    // Update an existing rating
    async updateRating(id: number, r: Partial<Omit<Rating, 'id'|'userId'|'userFullName'>>): Promise<Rating | null> {
        try {
            const response = await axios.put<Rating>(`${API_URL}/${id}`, r)
            await notify(`Das Rating konnte erfolgreich bearbeitet werden`, 'success');
            return response.data
        }  catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Rating mit der ID ${id} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Rating konnte nicht bearbeitet werden.`, 'danger');
            }
            return null
        }
    },

    // Delete a rating
    async deleteRating(id: number): Promise<void> {
        try {
            await axios.delete(`${API_URL}/${id}`)
            await notify(`Das Rating konnte erfolgreich gelöscht werden`, 'success');
        }  catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Rating mit der ID ${id} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten.  Rating konnte nicht gelöscht werden`, 'danger');
            }
        }
    }
}