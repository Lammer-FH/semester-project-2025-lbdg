package com.lbdg.library_backend.DTOs.responseDTOs;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BookResponseDTO {
    private Integer libraryId;
    private String author;
    private String title;
    private String isbn;
    private String imageUrl;
    private String publisher;
    private String shortDescription;
    private Integer publishedYear;
}
