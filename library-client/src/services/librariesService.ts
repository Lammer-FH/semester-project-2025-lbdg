import axios from 'axios';
import {Library} from "@/models/library";
import {Book} from "@/models/book";

const API_URL = 'http://localhost:8080/library-system/v1/libraries';

export const libraryService = {
    async getLibraries(): Promise<Library[]> {
        const response = await axios.get<Library[]>(API_URL);
        return response.data;
    },

    async getBooksOfLibrary(libraryId: number): Promise<Book[]> {
        const response = await axios.get<Book[]>(`${API_URL}/${libraryId}/books`);
        return response.data;
    },
};