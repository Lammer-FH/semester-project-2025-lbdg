package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.responseDTOs.BookListResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.LibraryEntity;
import com.lbdg.library_backend.mappers.BookMapper;
import com.lbdg.library_backend.mappers.LibraryMapper;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.BookingRepository;
import com.lbdg.library_backend.repositories.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    @Autowired
    private final LibraryRepository libraryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookingRepository bookingRepository;

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
        if (!libraryRepository.existsById(libraryId)) {
            throw new EntityNotFoundException("Library with ID " + libraryId + " not found");
        }
        List<BookEntity> bookEntities = bookRepository.findByLibraryEntityId(libraryId);
        LocalDate currentDate = LocalDate.now();

        Boolean isAvailable;
        for (BookEntity bookEntity : bookEntities) {
            Optional<Long> activeBookingId =  bookingRepository.findActiveBookingId(currentDate, bookEntity.getId());
            isAvailable = activeBookingId.isEmpty();
            books.add(BookMapper.toBookListResponseDTO(bookEntity, isAvailable));
        }

        return books;
    }
}
