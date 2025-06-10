import axios from 'axios'
import type { Rating } from '@/models/rating'

const API_URL = 'http://localhost:8080/library-system/v1/ratings'

export const ratingService = {
    // Fetch all ratings (optionally filter by bookId)
    async getRatings(bookId?: number): Promise<Rating[]> {
        const url = bookId != null
            ? `${API_URL}?bookId=${bookId}`
            : API_URL
        const response = await axios.get<Rating[]>(url)
        return response.data
    },

    // Fetch one rating (for edit form)
    async getRating(id: number): Promise<Rating> {
        const response = await axios.get<Rating>(`${API_URL}/${id}`)
        return response.data
    },

    // Create a new rating
    async createRating(r: Omit<Rating, 'id'|'userFullName'>): Promise<Rating> {
        // backend will fill id and resolve userFullName from userId
        const response = await axios.post<Rating>(API_URL, r)
        return response.data
    },

    // Update an existing rating
    async updateRating(id: number, r: Partial<Omit<Rating, 'id'|'userId'|'userFullName'>>): Promise<Rating> {
        const response = await axios.put<Rating>(`${API_URL}/${id}`, r)
        return response.data
    },

    // Delete a rating
    async deleteRating(id: number): Promise<void> {
        await axios.delete(`${API_URL}/${id}`)
    }
}