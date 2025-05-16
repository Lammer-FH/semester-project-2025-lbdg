package com.lbdg.library_backend.DTOs;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Jacksonized
@Value
public class BookDTO {
    private Integer libraryId;
    private String author;
    private String title;
    private String isbn;
    private MultipartFile image;
    private String publisher;
    private String shortDescription;
    private Integer publishedYear;
}
