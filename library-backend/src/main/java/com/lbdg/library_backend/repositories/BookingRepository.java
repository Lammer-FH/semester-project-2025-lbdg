package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.BookingEntity;
import com.lbdg.library_backend.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @Query("SELECT COUNT(b) = 0 FROM BookingEntity b WHERE :currentDate BETWEEN b.startDate AND b.endDate AND b.bookEntity.id = :bookId")
    boolean isBookCurrentlyAvailable(LocalDate currentDate, Long bookId);
    List<BookingEntity> findByBookEntityId(Long bookId);
}
