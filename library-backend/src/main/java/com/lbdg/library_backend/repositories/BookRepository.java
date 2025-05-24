package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByLibraryEntityId(Long libraryEntityId);
}
