package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public List<LibraryResponseDTO> getLibraries()
    {
        return libraryService.getLibraries();
    }
}
