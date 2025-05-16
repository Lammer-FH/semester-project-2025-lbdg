package com.lbdg.library_backend.Services;

import com.lbdg.library_backend.DTOs.BookDTO;
import com.lbdg.library_backend.Entities.BookEntity;
import com.lbdg.library_backend.Repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    public void createBook(BookDTO book){
        BookEntity bookEntity = BookEntity.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .image(book.getImageUrl() != null ? book.getImageUrl().getBytes() : null)
                .publisher(book.getPublisher())
                .shortDescription(book.getShortDescription())
                .publishedYear(book.getPublishedYear())
                .libraryId(book.getLibraryId())
                .build();

        bookRepository.save(bookEntity);
    }

    public Optional<List<BookDTO>> getBooks(){
        List<BookDTO> books = new ArrayList<>();

        books.add(BookDTO.builder()
                .libraryId(1)
                .author("George Orwell")
                .title("1984")
                .isbn("9780451524935")
                .imageUrl(null)
                .publisher("Signet Classics")
                .shortDescription("A dystopian novel about totalitarian surveillance and control.")
                .publishedYear(1949)
                .build());

        // TODO get it from database -> now mocked
       return Optional.of(books);
    }
}
