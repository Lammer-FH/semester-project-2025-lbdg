package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.requestDTOs.RatingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.RatingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingEditResponseDTO;
import com.lbdg.library_backend.services.RatingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{id}/edit")
    public ResponseEntity<RatingEditResponseDTO> getEditableRatingDetails(@PathVariable Long id)
    {
        try {
            RatingEditResponseDTO ratingEditResponseDTO = ratingService.getEditableRatingDetails(id);
            return ResponseEntity.status(200).body(ratingEditResponseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editRating(@PathVariable Long id, @Valid @RequestBody RatingEditRequestDTO ratingEditRequestDTO)
    {
        try {
            ratingService.editRating(id, ratingEditRequestDTO);
            return ResponseEntity.status(200).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating (@PathVariable Long id)
    {
        try {
            ratingService.deleteRating(id);
            return ResponseEntity.status(200).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Long> createRating (@Valid @RequestBody RatingCreateRequestDTO ratingCreateRequestDTO)
    {
        try {
            Long ratingId = ratingService.createRating(ratingCreateRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ratingId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
