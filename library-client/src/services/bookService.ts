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
};