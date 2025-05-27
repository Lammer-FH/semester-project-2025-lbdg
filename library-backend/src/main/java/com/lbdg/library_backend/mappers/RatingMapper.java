package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.responseDTOs.RatingResponseDTO;
import com.lbdg.library_backend.entities.RatingEntity;


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
}

