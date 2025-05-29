package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.responseDTOs.BookListResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.LibraryEntity;
import com.lbdg.library_backend.mappers.BookMapper;
import com.lbdg.library_backend.mappers.LibraryMapper;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    @Autowired
    private final LibraryRepository libraryRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<LibraryResponseDTO> getLibraries(){
        List<LibraryResponseDTO> libraries = new ArrayList<>();
        List<LibraryEntity> libraryEntities = libraryRepository.findAll();

        for (LibraryEntity libraryEntity : libraryEntities) {
            libraries.add(LibraryMapper.toLibraryResponseDTO(libraryEntity));
        }

        return libraries;
    }

    public List<BookListResponseDTO> getBooksOfLibrary(Long libraryId) {
        List<BookListResponseDTO> books = new ArrayList<>();
        List<BookEntity> bookEntities = bookRepository.findByLibraryEntityId(libraryId);

        for (BookEntity bookEntity : bookEntities) {
            books.add(BookMapper.toBookListResponseDTO(bookEntity));
        }

        return books;
    }
}
