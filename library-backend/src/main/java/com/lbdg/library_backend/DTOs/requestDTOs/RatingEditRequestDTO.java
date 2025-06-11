package com.lbdg.library_backend.DTOs.requestDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class RatingEditRequestDTO {
    @Min(1)
    @Max(5)
    @JsonProperty("rating")
    private int rating;
    @JsonProperty("comment")
    private String comment;
}
