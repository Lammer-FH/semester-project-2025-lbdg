package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.responseDTOs.BookResponseDTO;
import com.lbdg.library_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public void createBook(@RequestBody BookResponseDTO book)
    {
          bookService.createBook(book);
    }

    @GetMapping
    public List<BookResponseDTO> getBooks()
    {
        return bookService.getBooks().orElse(null);
    }
}
