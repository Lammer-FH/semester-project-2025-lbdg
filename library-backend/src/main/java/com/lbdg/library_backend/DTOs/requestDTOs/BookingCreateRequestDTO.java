package com.lbdg.library_backend.DTOs.requestDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
@Value
public class BookingCreateRequestDTO {
    @JsonProperty("bookId")
    private Long bookId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("startDate")
    private LocalDate startDate;
    @JsonProperty("endDate")
    private LocalDate endDate;
}
