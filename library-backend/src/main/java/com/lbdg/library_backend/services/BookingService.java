package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.requestDTOs.BookingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookingEditResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.BookingEntity;
import com.lbdg.library_backend.entities.UserEntity;
import com.lbdg.library_backend.mappers.BookingMapper;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.BookingRepository;
import com.lbdg.library_backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BookRepository bookRepository;


    public BookingEditResponseDTO getEditableBookingDetails(Long bookingId) {
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + bookingId + " not found"));

        return BookingMapper.toBookingEditResponseDTO(bookingEntity);
    }

    public void editBooking(Long bookingId, BookingEditRequestDTO bookingEditRequestDTO) {
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + bookingId + " not found"));

        // check for overlap
        if (bookingRepository.getBookingOverlaps(bookingId, bookingEntity.getBookEntity().getId(), bookingEditRequestDTO.getStartDate(), bookingEditRequestDTO.getEndDate()) > 0) {
            throw new IllegalArgumentException("Overlap detected");
        }

        BookingMapper.updateBookingEntityFromBookingEditRequestDTO(bookingEntity, bookingEditRequestDTO);
        bookingRepository.save(bookingEntity);
    }

    public void deleteBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found");
        }
        bookingRepository.deleteById(bookingId);
    }

    public Long createBooking(BookingCreateRequestDTO bookingCreateRequestDTO) {
        Long bookId = bookingCreateRequestDTO.getBookId();
        Long userId = bookingCreateRequestDTO.getUserId();

        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BookingEntity bookingEntity = BookingMapper.toBookingEntity(bookingCreateRequestDTO, bookEntity, userEntity);

        // check for overlap
        if (bookingRepository.getBookingOverlaps(0L, bookingEntity.getBookEntity().getId(), bookingEntity.getStartDate(), bookingEntity.getEndDate()) > 0) {
            throw new IllegalArgumentException("Overlap detected");
        }
        bookingRepository.save(bookingEntity);
        return bookingEntity.getId();
    }
}
