package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
