
---
marp: true
author: Team LBDG
version: 1.0.0
theme: a4-document
paginate: true
header: ' '
footer: 'Advanced Web Frameworks 2025'
---

# Project Specification – Template

## Setup
* run docker-compose.yml to create db
* start backend: run LibraryBackendApplication
* start frontend- "ionic start"

## Resource Description


| Resource  | Description  | Relationships  |
|-----------|--------------|----------------|
|books|A book|One library, multiple bookings, multiple ratings|
|bookings|A booking|1 book, 1 user|
|libraries|A library|multiple books|
|ratings|A user-written rating|1 user, 1 book|

---

## User Stories

- 🟦 **#1 – Static page (no backend)**
- 🟩 **#2–3 – GET endpoints with at least one relationship between two resources**
- 🟥 **#4–6 – CRUD with GET, POST, PUT, or DELETE on individual or combined resources**

---

### 🟦 User Story #1 – Static

**Story:**  
As a visitor, I want to see general information about the library system (such as opening hours, locations, and rules) so that I know how to use the services.
**Affected Resources:**  
*(none)*

**Planned Implementation (Frontend Component):**  
Static webpage

---

### 🟩 User Story #2 - Display Available Books

**Story:**  
As a user, I want to see a list of all available books in a library so that I can decide which one to borrow.

**Affected Resources:**  
`book`, `library`

**Planned Implementation (e.g., list view, detail view):**  
GET `/libraries/{id}/books`
Frontend: List view of books for a selected library, including availability status.

---

### 🟩 User Story #3 - View book ratings

**Story:**  
As a user, I want to view the ratings and reviews of a book so that I can choose a highly rated one.

**Affected Resources:**  
`book`, `rating`

**Planned Implementation:**  
GET `/books/{id}/ratings`
Frontend: Detail view of a book with aggregated ratings and user comments.

---

### 🟥 User Story #4 - Manage books

**Story:**  
As a librarian, I want to create, edit and delete a book

**Affected Resources:**  
`book`

**Planned Implementation:**  
POST `/books` | GET, PUT, DELETE `/books/{id}`
Frontend: Book form, where a librarian can create, edit or delete a book.

---

### 🟥 User Story #5 – Manage Bookings

**Story:**  
As a librarian, I want to create, edit and delete a booking so that I can manage the bookings of a specific book. Accessed at the book details page.

**Affected Resources:**  
`book`, `booking`, `user`

**Planned Implementation:**  
POST `/books/{id}/bookings` | GET, PUT, DELETE `/bookings/{id}`
Frontend: Bookings form, where a librarian can create, edit or delete a booking. Accessed at the book details page.

---

### 🟥 User Story #6 - Manage and submit Ratings

**Story:**  
As a user, I want to get, create, edit, and delete a rating for a book so that I can share my opinion and help others.

**Affected Resources:**  
`rating`, `book`, `user`

**Planned Implementation:**  
POST `/ratings` | GET, PUT, DELETE `/ratings/{id}`
Frontend: Ratings form, where a student can create, edit or delete a rating. Accessed at the book details page in the ratings section.
