package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.responseDTOs.BookResponseDTO;
import com.lbdg.library_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/library-system/")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(path="books")
    public void createBook(@RequestBody BookResponseDTO book)
    {
        bookService.createBook(book);
    }

    @GetMapping(path="books")
    public List<BookResponseDTO> getBooks()
    {
        return bookService.getBooks().orElse(null);
    }
}
