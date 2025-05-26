package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.responseDTOs.BookResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.RatingEntity;
import com.lbdg.library_backend.mappers.RatingMapper;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final RatingRepository ratingRepository;

    public List<RatingResponseDTO> getRatingsOfBook(Long bookId)
    {
        List<RatingResponseDTO> ratings = new ArrayList<>();
        List<RatingEntity> ratingEntities = ratingRepository.findByBookEntityId(bookId);

        for (RatingEntity ratingEntity : ratingEntities) {
            ratings.add(RatingMapper.toRatingResponseDTO(ratingEntity));
        }

        return ratings;
    }

    /* public void createBook(BookRequestDTO book){
        return null;
        bookEntity = BookEntity.builder()
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

    public Optional<List<BookResponseDTO>> getBooks(){
        List<BookResponseDTO> books = new ArrayList<>();
        List <BookEntity> bookEntities = bookRepository.findAll();
        bookEntities.forEach(bookEntity -> {
            books.add(BookResponseDTO.builder()
                    .libraryId(bookEntity.getLibraryId())
                    .author(bookEntity.getAuthor())
                    .title(bookEntity.getTitle())
                    .isbn(bookEntity.getIsbn())
                    .imageUrl(null)
                    .publisher(bookEntity.getPublisher())
                    .shortDescription(bookEntity.getShortDescription())
                    .publishedYear(bookEntity.getPublishedYear())
                    .build());
        });

        return Optional.of(books);

    }*/
}
