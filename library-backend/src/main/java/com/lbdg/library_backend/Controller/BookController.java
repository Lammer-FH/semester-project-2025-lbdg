package com.lbdg.library_backend.Controller;

import com.lbdg.library_backend.DTOs.BookDTO;
import com.lbdg.library_backend.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library-system/")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(path="books")
    public void createBook(@RequestBody BookDTO book )
    {
        bookService.createBook(book);
    }
}
