package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.responseDTOs.BookResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;

import java.util.Base64;


public class BookMapper {

    public static BookResponseDTO toBookResponseDTO(BookEntity bookEntity) {
        return BookResponseDTO.builder()
                .id(bookEntity.getId())
                .libraryId(bookEntity.getLibraryEntity().getId())
                .title(bookEntity.getTitle())
                .image(bookEntity.getImage() != null ? Base64.getEncoder().encodeToString(bookEntity.getImage()) : null)
                .publisher(bookEntity.getPublisher())
                .isbn(bookEntity.getIsbn())
                .publishedYear(bookEntity.getPublishedYear())
                .shortDescription(bookEntity.getShortDescription())
                .build();
    }
}
