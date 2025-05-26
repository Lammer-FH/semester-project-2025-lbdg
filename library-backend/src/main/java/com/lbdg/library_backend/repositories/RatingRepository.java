package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByBookEntityId(Long bookId);
}
