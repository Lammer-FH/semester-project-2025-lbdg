package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.responseDTOs.BookDetailsResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}/ratings")
    public List<RatingResponseDTO> getRatingsOfBook(@PathVariable Long id)
    {
        return bookService.getRatingsOfBook(id);
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
