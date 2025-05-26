import axios from 'axios';
import {RatingDTO} from "@/DTOs/ratingDTO";

const API_URL = 'http://localhost:8080/library-system/v1/books';

export const bookService = {

    async getBooksOfLibrary(bookId: number): Promise<RatingDTO[]> {
        const response = await axios.get<RatingDTO[]>(`${API_URL}/${bookId}/ratings`);
        return response.data;
    },
};