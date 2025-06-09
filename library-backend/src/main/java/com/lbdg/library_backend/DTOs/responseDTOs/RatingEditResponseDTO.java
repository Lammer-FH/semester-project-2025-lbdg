package com.lbdg.library_backend.DTOs.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
@Value
public class RatingEditResponseDTO {
    @JsonProperty("userFullName")
    private String userFullName;
    @JsonProperty("rating")
    private int rating;
    @JsonProperty("comment")
    private String comment;
}
