import axios from 'axios';
import {LibraryDTO} from "@/DTOs/libraryDTO";
import {BookDTO} from "@/DTOs/bookDTO";

const API_URL = 'http://localhost:8080/library-system/v1/libraries';

export const libraryService = {
    async getLibraries(): Promise<LibraryDTO[]> {
        const response = await axios.get<LibraryDTO[]>(API_URL);
        return response.data;
    },

    async getBooksOfLibrary(libraryId: number): Promise<BookDTO[]> {
        const response = await axios.get<BookDTO[]>(`${API_URL}/${libraryId}/books`);
        return response.data;
    },
};