package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
