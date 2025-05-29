package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.entities.LibraryEntity;


public class LibraryMapper {

    public static LibraryResponseDTO toLibraryResponseDTO(LibraryEntity libraryEntity) {
        return LibraryResponseDTO.builder()
                .id(libraryEntity.getId())
                .name(libraryEntity.getName())
                .build();
    }
}
