package com.lbdg.library_backend.DTOs.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class RatingResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("userFullName")
    private String userFullName;
    @JsonProperty("rating")
    private int rating;
    @JsonProperty("comment")
    private String comment;
}
