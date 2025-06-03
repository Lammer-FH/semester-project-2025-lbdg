package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.responseDTOs.BookDetailsResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookListResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;

import java.util.Base64;


public class BookMapper {

    public static BookListResponseDTO toBookListResponseDTO(BookEntity bookEntity, boolean isAvailable) {
        return BookListResponseDTO.builder()
                .id(bookEntity.getId())
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .image(bookEntity.getImage() != null ? Base64.getEncoder().encodeToString(bookEntity.getImage()) : null)
                .isbn(bookEntity.getIsbn())
                .available(isAvailable)
                .build();
    }

    public static BookDetailsResponseDTO toBookDetailsResponseDTO(BookEntity bookEntity, boolean isAvailable) {
        return BookDetailsResponseDTO.builder()
                .id(bookEntity.getId())
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .image(bookEntity.getImage() != null ? Base64.getEncoder().encodeToString(bookEntity.getImage()) : null)
                .publisher(bookEntity.getPublisher())
                .isbn(bookEntity.getIsbn())
                .publishedYear(bookEntity.getPublishedYear())
                .shortDescription(bookEntity.getShortDescription())
                .available(isAvailable)
                .build();
    }
}
