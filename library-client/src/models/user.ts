export interface User {
    id: number
    userFullName: string
    role: string
}

export const Roles = Object.freeze({
    LIBRARIAN: 'Bibliothekar',
    STUDENT: 'Student'
});