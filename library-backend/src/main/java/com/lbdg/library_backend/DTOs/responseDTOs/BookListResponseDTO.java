package com.lbdg.library_backend.DTOs.responseDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BookListResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("author")
    private String author;
    @JsonProperty("title")
    private String title;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("image")
    private String image;
    @JsonProperty("available")
    private boolean available;
}
