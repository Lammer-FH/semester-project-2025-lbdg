package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.requestDTOs.BookingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookingEditResponseDTO;
import com.lbdg.library_backend.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}/edit")
    public ResponseEntity<BookingEditResponseDTO> getEditableBookingDetails(@PathVariable Long id)
    {
        BookingEditResponseDTO bookingEditResponseDTO = bookingService.getEditableBookingDetails(id);
        return ResponseEntity.status(200).body(bookingEditResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editBooking(@PathVariable Long id, @RequestBody BookingEditRequestDTO bookingEditRequestDTO)
    {
        bookingService.editBooking(id, bookingEditRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking (@PathVariable Long id)
    {
        bookingService.deleteBooking(id);
        return ResponseEntity.status(200).build();
    }

    @PostMapping()
    public ResponseEntity<Long> createBooking (@RequestBody BookingCreateRequestDTO bookingCreateRequestDTO)
    {
        Long bookingId = bookingService.createBooking(bookingCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingId);
    }
}
