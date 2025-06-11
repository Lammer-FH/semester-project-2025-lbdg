package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.requestDTOs.RatingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.RatingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingEditResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.RatingEntity;
import com.lbdg.library_backend.entities.UserEntity;


public class RatingMapper {

    public static RatingResponseDTO toRatingResponseDTO(RatingEntity ratingEntity) {

        return RatingResponseDTO.builder()
                .id(ratingEntity.getId())
                .userId(ratingEntity.getUserEntity().getId())
                .userFullName(ratingEntity.getUserEntity().getFirstName() + " " + ratingEntity.getUserEntity().getLastName())
                .rating(ratingEntity.getRating())
                .comment(ratingEntity.getComment())
                .build();
    }

    public static RatingEditResponseDTO toRatingEditResponseDTO(RatingEntity ratingEntity) {
        return RatingEditResponseDTO.builder()
                .userFullName(ratingEntity.getUserEntity().getFirstName() + " " + ratingEntity.getUserEntity().getLastName())
                .rating(ratingEntity.getRating())
                .comment(ratingEntity.getComment())
                .build();
    }

    public static void updateRatingEntityFromRatingEditRequestDTO(RatingEntity ratingEntity, RatingEditRequestDTO ratingEditRequestDTO) {
        ratingEntity.setRating(ratingEditRequestDTO.getRating());
        ratingEntity.setComment(ratingEditRequestDTO.getComment());
    }

    public static RatingEntity toRatingEntity(RatingCreateRequestDTO ratingCreateRequestDTO, BookEntity bookEntity, UserEntity userEntity) {
        return RatingEntity.builder()
                .bookEntity(bookEntity)
                .userEntity(userEntity)
                .rating(ratingCreateRequestDTO.getRating())
                .comment(ratingCreateRequestDTO.getComment())
                .build();
    }
}

