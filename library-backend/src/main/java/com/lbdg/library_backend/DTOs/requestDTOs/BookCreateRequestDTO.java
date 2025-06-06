package com.lbdg.library_backend.DTOs.requestDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BookCreateRequestDTO {
    @JsonProperty("libraryId")
    private Long libraryId;
    @JsonProperty("author")
    private String author;
    @JsonProperty("title")
    private String title;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("image")
    private String image;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("publishedYear")
    private Integer publishedYear;

}
