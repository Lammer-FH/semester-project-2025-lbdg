package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.requestDTOs.BookCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookDetailsResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookEditResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            BookDetailsResponseDTO bookDetailsResponseDTO = bookService.getBookDetails(id);
            return ResponseEntity.status(200).body(bookDetailsResponseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<BookEditResponseDTO> getEditableBookDetails(@PathVariable Long id)
    {
        try {
            BookEditResponseDTO bookEditResponseDTO = bookService.getEditableBookDetails(id);
            return ResponseEntity.status(200).body(bookEditResponseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsOfBook(@PathVariable Long id)
    {
        try {
            List<RatingResponseDTO> ratingResponseDTOList = bookService.getRatingsOfBook(id);
            return ResponseEntity.status(200).body(ratingResponseDTOList);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Long> createBook (@RequestBody BookCreateRequestDTO bookCreateRequestDTO)
    {
        try {
            Long bookId = bookService.createBook(bookCreateRequestDTO);
            return ResponseEntity.status(201).body(bookId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editBook (@PathVariable Long id, @RequestBody BookEditRequestDTO bookEditRequestDTO)
    {
        try {
            bookService.editBook(id, bookEditRequestDTO);
            return ResponseEntity.status(200).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook (@PathVariable Long id)
    {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(200).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
