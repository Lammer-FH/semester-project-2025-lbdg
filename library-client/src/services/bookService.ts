import axios from 'axios';
import {BookDTO} from "@/DTOs/bookDTO";

const API_URL = 'http://localhost:8080/library-system/v1/books';

export const bookService = {

    async getBooksOfLibrary(bookId: number): Promise<BookDTO[]> {
        const response = await axios.get<BookDTO[]>(`${API_URL}/${bookId}/ratings`);
        return response.data;
    },
};