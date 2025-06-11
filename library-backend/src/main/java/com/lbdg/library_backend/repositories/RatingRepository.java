package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    Optional<RatingEntity> findById(Long ratingId);
    List<RatingEntity> findByBookEntityId(Long bookId);
}
