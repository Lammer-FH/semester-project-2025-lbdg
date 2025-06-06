package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.requestDTOs.BookCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookDetailsResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookEditResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsResponseDTO> getBookDetails(@PathVariable Long id)
    {
        return bookService.getBookDetails(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<BookEditResponseDTO> getEditableBookDetails(@PathVariable Long id)
    {
        return bookService.getEditableBookDetails(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping("/{id}/ratings")
    public List<RatingResponseDTO> getRatingsOfBook(@PathVariable Long id)
    {
        return bookService.getRatingsOfBook(id);
    }

    @PostMapping()
    public ResponseEntity<Long> createBook (@RequestBody BookCreateRequestDTO bookCreateRequestDTO)
    {
        Long bookId = bookService.createBook(bookCreateRequestDTO);
        return ResponseEntity.status(201).body(bookId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editBook (@PathVariable Long id, @RequestBody BookEditRequestDTO bookEditRequestDTO)
    {
        bookService.editBook(id, bookEditRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook (@PathVariable Long id)
    {
        bookService.deleteBook(id);
        return ResponseEntity.status(200).build();
    }

    /*@PostMapping
    public void createBook(@RequestBody BookResponseDTO book)
    {
          bookService.createBook(book);
    }

    @GetMapping
    public List<BookResponseDTO> getBooks()
    {
        return bookService.getBooks().orElse(null);
    }*/
}
