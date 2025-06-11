package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findById(Long bookId);
    List<BookEntity> findByLibraryEntityId(Long libraryEntityId);
}
