package com.lbdg.library_backend.mappers;

import com.lbdg.library_backend.DTOs.requestDTOs.BookingCreateRequestDTO;
import com.lbdg.library_backend.DTOs.requestDTOs.BookingEditRequestDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.BookingEditResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.BookingEntity;
import com.lbdg.library_backend.entities.UserEntity;

public class BookingMapper {
    public static BookingEditResponseDTO toBookingEditResponseDTO(BookingEntity bookingEntity) {
        return BookingEditResponseDTO.builder()
                .userId(bookingEntity.getUserEntity().getId())
                .startDate(bookingEntity.getStartDate())
                .endDate(bookingEntity.getEndDate())
                .build();
    }

    public static void updateBookingEntityFromBookingEditRequestDTO(BookingEntity bookingEntity, BookingEditRequestDTO bookingEditRequestDTO) {
        bookingEntity.setStartDate(bookingEditRequestDTO.getStartDate());
        bookingEntity.setEndDate(bookingEditRequestDTO.getEndDate());
    }

    public static BookingEntity toBookingEntity(BookingCreateRequestDTO bookingCreateRequestDTO, BookEntity bookEntity, UserEntity userEntity) {
        return BookingEntity.builder()
                .bookEntity(bookEntity)
                .userEntity(userEntity)
                .startDate(bookingCreateRequestDTO.getStartDate())
                .endDate(bookingCreateRequestDTO.getEndDate())
                .build();

    }
}
