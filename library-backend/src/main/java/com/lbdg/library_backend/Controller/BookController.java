package com.lbdg.library_backend.Controller;

import com.lbdg.library_backend.DTOs.BookDTO;
import com.lbdg.library_backend.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/library-system/")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(path="books")
    public void createBook(@RequestBody BookDTO book)
    {
        bookService.createBook(book);
    }

    @GetMapping(path="books")
    public List<BookDTO> getBooks()
    {
        return bookService.getBooks().orElse(null);
    }
}
