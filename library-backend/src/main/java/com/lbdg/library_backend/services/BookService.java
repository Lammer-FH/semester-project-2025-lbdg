package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.requestDTOs.BookCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookDetailsResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookEditResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.LibraryEntity;
import com.lbdg.library_backend.entities.RatingEntity;
import com.lbdg.library_backend.mappers.BookMapper;
import com.lbdg.library_backend.mappers.RatingMapper;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.BookingRepository;
import com.lbdg.library_backend.repositories.LibraryRepository;
import com.lbdg.library_backend.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final LibraryRepository libraryRepository;

    public BookDetailsResponseDTO getBookDetails(Long bookId)
    {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> {
            log.error("Book not found with id {}", bookId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        });

        LocalDate currentDate = LocalDate.now();
        Optional<Long> activeBookingId =  bookingRepository.findActiveBookingId(currentDate, bookEntity.getId());
        Boolean isAvailable = true;
        Long bookingId = null;
        if (activeBookingId.isPresent()) {
            isAvailable = false;
            bookingId = activeBookingId.get();
        }
        return BookMapper.toBookDetailsResponseDTO(bookEntity, isAvailable, bookingId);
    }

    public List<RatingResponseDTO> getRatingsOfBook(Long bookId)
    {
        List<RatingResponseDTO> ratings = new ArrayList<>();
        List<RatingEntity> ratingEntities = ratingRepository.findByBookEntityId(bookId);

        for (RatingEntity ratingEntity : ratingEntities) {
            ratings.add(RatingMapper.toRatingResponseDTO(ratingEntity));
        }

        return ratings;
    }

    public Long createBook(BookCreateRequestDTO bookCreateRequestDTO) {
        LibraryEntity libraryEntity = libraryRepository.findById(bookCreateRequestDTO.getLibraryId())
                .orElseThrow(() -> {
                    log.error("Library not found with id {}", bookCreateRequestDTO.getLibraryId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Library not found");
                });

        BookEntity bookEntity = BookMapper.toBookEntity(bookCreateRequestDTO, libraryEntity);
        bookEntity = bookRepository.save(bookEntity);
        return bookEntity.getId();
    }

    public void editBook(Long bookId, BookEditRequestDTO bookEditRequestDTO) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found with id {}", bookId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
                });

        BookMapper.updateBookEntityFromBookEditRequestDTO(bookEntity, bookEditRequestDTO);
        bookRepository.save(bookEntity);
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            log.error("Book not found with id {}", bookId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + bookId + " not found");
        }
        bookRepository.deleteById(bookId);
    }

    public Optional<BookEditResponseDTO> getEditableBookDetails(Long bookId) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookId);

        if(bookEntity.isEmpty()){
            log.error("Book not found with id {}", bookId);
            return Optional.empty();
        }

        return Optional.of(BookMapper.toBookEditResponseDTO(bookEntity.get()));
    }
}
