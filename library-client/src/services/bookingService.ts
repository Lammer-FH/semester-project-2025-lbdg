import axios from 'axios';

const API_URL = 'http://localhost:8080/library-system/v1/bookings';

export const bookingService = {

    async getBookingDetails(id: number): Promise<Book> {
        const response = await axios.get<Booking>(`${API_URL}/${id}`);
        return response.data;
    },


    async getBookingEditDetails(id: number): Promise<Booking> {
        const response = await axios.get<Booking>(`${API_URL}/${id}/edit`);
        return response.data;
    },

    async createBooking(book: Pick<Book, any>): Promise<number> {
        try {
            const response = await axios.post<number>(`${API_URL}`, book);
            return response.data;
        } catch (error) {
            return 0;
        }

    },

    /*async editBooking(bookId: number, book: Pick<Book, any>): Promise<boolean> {
        try {
            await axios.put(`${API_URL}/${bookId}`, book);
            return true;
        } catch (error) {
            return false;
        }
    },*/

    async deleteBooking(bookId: number): Promise<boolean> {
        try {
            await axios.delete(`${API_URL}/${bookingId}`);
            return true;
        } catch (error) {
            return false;
        }
    },
};