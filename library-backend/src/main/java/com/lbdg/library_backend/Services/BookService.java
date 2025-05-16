package com.lbdg.library_backend.Services;

import com.lbdg.library_backend.DTOs.BookDTO;
import com.lbdg.library_backend.Entities.BookEntity;
import com.lbdg.library_backend.Repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    public void createBook(BookDTO book){
        try {
            BookEntity bookEntity = BookEntity.builder()
                    .author(book.getAuthor())
                    .title(book.getTitle())
                    .isbn(book.getIsbn())
                    .image(book.getImage() != null ? book.getImage().getBytes() : null)
                    .publisher(book.getPublisher())
                    .shortDescription(book.getShortDescription())
                    .publishedYear(book.getPublishedYear())
                    .libraryId(book.getLibraryId())
                    .build();

            bookRepository.save(bookEntity);
        } catch (IOException e) {
            log.error("Error creating book", e);
            throw new RuntimeException("Fehler beim Lesen des Bild-Uploads", e);
        }
    }
}
