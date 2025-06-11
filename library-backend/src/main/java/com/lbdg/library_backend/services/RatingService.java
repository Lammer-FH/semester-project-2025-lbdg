package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.requestDTOs.RatingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.RatingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingEditResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.RatingEntity;
import com.lbdg.library_backend.entities.UserEntity;
import com.lbdg.library_backend.mappers.RatingMapper;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.RatingRepository;
import com.lbdg.library_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {
    @Autowired
    private final RatingRepository ratingRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final UserRepository userRepository;

    public RatingEditResponseDTO getEditableRatingDetails(Long ratingId) {
        RatingEntity ratingEntity = ratingRepository.findById(ratingId)
                .orElseThrow(() -> {
                    log.error("Rating not found with id {}", ratingId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating with ID " + ratingId + " not found");
                });

        return RatingMapper.toRatingEditResponseDTO(ratingEntity);
    }

    public void editRating(Long ratingId, RatingEditRequestDTO ratingEditRequestDTO) {
        RatingEntity ratingEntity = ratingRepository.findById(ratingId)
                .orElseThrow(() -> {
                    log.error("Rating not found with id {}", ratingId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating with ID " + ratingId + " not found");
                });

        RatingMapper.updateRatingEntityFromRatingEditRequestDTO(ratingEntity, ratingEditRequestDTO);
        ratingRepository.save(ratingEntity);
    }

    public void deleteRating(Long ratingId) {
        if (!ratingRepository.existsById(ratingId)) {
            log.error("Rating not found with id {}", ratingId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating with ID " + ratingId + " not found");
        }
        ratingRepository.deleteById(ratingId);
    }

    public Long createRating(RatingCreateRequestDTO ratingCreateRequestDTO) {
        Long bookId = ratingCreateRequestDTO.getBookId();
        Long userId = ratingCreateRequestDTO.getUserId();

        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found with id {}", bookId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
                });
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });
        RatingEntity ratingEntity = RatingMapper.toRatingEntity(ratingCreateRequestDTO, bookEntity, userEntity);

        ratingRepository.save(ratingEntity);
        return ratingEntity.getId();
    }
}
