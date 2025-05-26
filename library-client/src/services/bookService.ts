import axios from 'axios';
import {RatingDTO} from "@/DTOs/ratingDTO";
import {BookDTO} from "@/DTOs/bookDTO";

const API_URL = 'http://localhost:8080/library-system/v1/books';

export const bookService = {

    async getBookDetails(id: number): Promise<BookDTO> {
        const response = await axios.get<BookDTO>(`${API_URL}/${id}`);
        return response.data;
    },

    async getBooksOfLibrary(bookId: number): Promise<RatingDTO[]> {
        const response = await axios.get<RatingDTO[]>(`${API_URL}/${bookId}/ratings`);
        return response.data;
    },
};