import axios from 'axios';
import {Rating} from "@/models/rating";
import {Book} from "@/models/book";

const API_URL = 'http://localhost:8080/library-system/v1/books';

export const bookService = {

    async getBookDetails(id: number): Promise<Book> {
        const response = await axios.get<Book>(`${API_URL}/${id}`);
        return response.data;
    },

    async getRatingsForBook(bookId: number): Promise<Rating[]> {
        const response = await axios.get<Rating[]>(`${API_URL}/${bookId}/ratings`);
        return response.data;
    },

    async getBookEditDetails(id: number): Promise<Book> {
        const response = await axios.get<Book>(`${API_URL}/${id}/edit`);
        return response.data;
    },

    async createBook(book: Pick<Book, any>): Promise<number> {
        try {
            const response = await axios.post<number>(`${API_URL}`, book);
            return response.data;
        } catch (error) {
            return 0;
        }

    },

    async editBook(bookId: number, book: Pick<Book, any>): Promise<boolean> {
        try {
            await axios.put(`${API_URL}/${bookId}`, book);
            return true;
        } catch (error) {
            return false;
        }
    },

    async deleteBook(bookId: number): Promise<boolean> {
        try {
            await axios.delete(`${API_URL}/${bookId}`);
            return true;
        } catch (error) {
            return false;
        }
    },
};