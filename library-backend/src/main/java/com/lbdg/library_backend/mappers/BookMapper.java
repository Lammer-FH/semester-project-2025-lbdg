package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.requestDTOs.BookCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookDetailsResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookListResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookEditResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.LibraryEntity;

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

    public static BookEntity toBookEntity(BookCreateRequestDTO bookCreateRequestDTO, LibraryEntity library) {
        return BookEntity.builder()
                .libraryEntity(library)
                .author(bookCreateRequestDTO.getAuthor())
                .title(bookCreateRequestDTO.getTitle())
                .isbn(bookCreateRequestDTO.getIsbn())
                .image(bookCreateRequestDTO.getImage() != null ? Base64.getDecoder().decode(bookCreateRequestDTO.getImage()) : null)
                .publisher(bookCreateRequestDTO.getPublisher())
                .shortDescription(bookCreateRequestDTO.getShortDescription())
                .publishedYear(bookCreateRequestDTO.getPublishedYear())
                .build();

    }

    public static void updateBookEntityFromBookEditRequestDTO(BookEntity bookEntity, BookEditRequestDTO bookEditRequestDTO) {
        bookEntity.setTitle(bookEditRequestDTO.getTitle());
        bookEntity.setAuthor(bookEditRequestDTO.getAuthor());
        bookEntity.setIsbn(bookEditRequestDTO.getIsbn());
        bookEntity.setPublisher(bookEditRequestDTO.getPublisher());
        bookEntity.setShortDescription(bookEditRequestDTO.getShortDescription());
        bookEntity.setPublishedYear(bookEditRequestDTO.getPublishedYear());
        bookEntity.setImage(bookEditRequestDTO.getImage() != null ? Base64.getDecoder().decode(bookEditRequestDTO.getImage()) : null);
    }

    public static BookEditResponseDTO toBookEditResponseDTO(BookEntity bookEntity) {
        return BookEditResponseDTO.builder()
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .isbn(bookEntity.getIsbn())
                .image(bookEntity.getImage() != null ? Base64.getEncoder().encodeToString(bookEntity.getImage()) : null)
                .publisher(bookEntity.getPublisher())
                .shortDescription(bookEntity.getShortDescription())
                .publishedYear(bookEntity.getPublishedYear())
                .build();
    }
}
