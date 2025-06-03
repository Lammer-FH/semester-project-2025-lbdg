package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
