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
    @Query("SELECT COUNT(b) FROM BookingEntity b WHERE  b.id <> :bookingId AND b.bookEntity.id = :bookId AND b.endDate >= :startDate AND b.startDate <= :endDate")
    int getBookingOverlaps(Long bookingId, Long bookId, LocalDate startDate, LocalDate endDate);
}
