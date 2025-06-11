package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.responseDTOs.BookListResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.services.LibraryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/libraries")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<LibraryResponseDTO>> getLibraries()
    {
        List<LibraryResponseDTO> libraryResponseDTOList = libraryService.getLibraries();
        return ResponseEntity.status(200).body(libraryResponseDTOList);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookListResponseDTO>> getBooksOfLibrary(@PathVariable Long id)
    {
        try {
            List<BookListResponseDTO> bookListResponseDTOList = libraryService.getBooksOfLibrary(id);
            return ResponseEntity.status(200).body(bookListResponseDTOList);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
