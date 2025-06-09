package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.requestDTOs.RatingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.RatingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.RatingEditResponseDTO;
import com.lbdg.library_backend.services.RatingService;
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
        RatingEditResponseDTO ratingEditResponseDTO = ratingService.getEditableRatingDetails(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingEditResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editRating(@PathVariable Long id, @Valid @RequestBody RatingEditRequestDTO ratingEditRequestDTO)
    {
        ratingService.editRating(id, ratingEditRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating (@PathVariable Long id)
    {
        ratingService.deleteRating(id);
        return ResponseEntity.status(200).build();
    }

    @PostMapping()
    public ResponseEntity<Long> createRating (@Valid @RequestBody RatingCreateRequestDTO ratingCreateRequestDTO)
    {
        Long ratingId = ratingService.createRating(ratingCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingId);
    }
}
