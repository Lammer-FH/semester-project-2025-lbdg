package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.entities.LibraryEntity;
import com.lbdg.library_backend.mappers.LibraryMapper;
import com.lbdg.library_backend.repositories.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    @Autowired
    private final LibraryRepository libraryRepository;

    public List<LibraryResponseDTO> getLibraries(){
        List<LibraryResponseDTO> libraries = new ArrayList<>();
        List <LibraryEntity> libraryEntities = libraryRepository.findAll();

        for (LibraryEntity libraryEntity : libraryEntities) {
            libraries.add(LibraryMapper.toLibraryResponseDTO(libraryEntity));
        }

        return libraries;
    }
}
