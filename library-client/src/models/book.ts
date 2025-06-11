export interface Book {
    id: number
    libraryId: number
    author: string
    title: string
    isbn: string
    image: string
    publisher: string
    shortDescription: string
    publishedYear: number
    available: boolean
    bookingId: number
}