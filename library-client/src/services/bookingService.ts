import axios from 'axios';
import {Book} from "@/models/book";
import {Booking} from "@/models/booking";
import { notify } from '@/services/notificationService';

const API_URL = 'http://localhost:8080/library-system/v1/bookings';

export const bookingService = {

    async getBookingEditDetails(id: number): Promise<Booking | null> {
        try{
            const response = await axios.get<Booking>(`${API_URL}/${id}/edit`);
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte keine Buchung mit der ID ${id} gefunden werden!`, 'danger');
                    break;
                case 409: // HttpStatus.CONFLICT
                    notify(`${error.status}: Ein Konflikt ist aufgetreten: Buchungs-ID ${id}!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Daten konnten nicht geladen werden`, 'danger');
            }
            return null;
        }
    },

    async createBooking(book: Pick<Book, any>): Promise<number> {
        try {
            const response = await axios.post<number>(`${API_URL}`, book);
            await notify(`Die Buchung konnte erfolgreich erstellt werden`, 'success');
            return response.data;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buchung mit der ID ${book.id} gefunden werden!`, 'danger');
                    break;
                case 409: // HttpStatus.CONFLICT
                    await notify(`${error.status}: Ein Konflikt ist aufgetreten. Eine Überlappung der Zeit wurde festgestellt!`, 'warning');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Buchung konnte nicht erstellt werden`, 'danger');
            }
            return 0;
        }

    },

    async editBooking(bookingId: number, booking: Pick<Booking, any>): Promise<boolean> {
        try {
            await axios.put(`${API_URL}/${bookingId}`, booking);
            await notify(`Die Buchung konnte erfolgreich bearbeitet werden`, 'success');
            return true;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte kein Buchung mit der ID ${bookingId} gefunden werden!`, 'danger');
                    break;
                case 409: // HttpStatus.CONFLICT
                    await notify(`${error.status}: Ein Konflikt ist aufgetreten. Eine Überlappung der Zeit wurde festgestellt!`, 'warning');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Buchung konnte nicht bearbeitet werden`, 'danger');
            }
            return false;
        }
    },

    async deleteBooking(bookingId: number): Promise<boolean> {
        try {
            await axios.delete(`${API_URL}/${bookingId}`);
            await notify(`Die Buchung konnte erfolgreich gelöscht werden`, 'success');
            return true;
        } catch (error: any) {
            const status = error.response?.status;

            switch (status) {
                case 400: // Bad Request
                    await notify(`${error.status}: Ungültige Anfrage – bitte überprüfe deine Eingaben!`, 'danger');
                    break;
                case 404: // HttpStatus.NOT_FOUND
                    await notify(`${error.status}: Es konnte keine Buchung mit der ID ${bookingId} gefunden werden!`, 'danger');
                    break;
                default:
                    await notify(`${status ?? 'unbekannt'}: Ein unerwarteter Fehler ist aufgetreten. Buchung konnte nicht gelöscht werden`, 'danger');
            }
            return false;
        }
    },
};