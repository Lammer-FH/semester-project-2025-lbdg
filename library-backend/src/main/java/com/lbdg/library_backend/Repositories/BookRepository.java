package com.lbdg.library_backend.Repositories;

import com.lbdg.library_backend.Entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
