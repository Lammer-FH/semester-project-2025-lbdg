package com.lbdg.library_backend.mappers;


import com.lbdg.library_backend.DTOs.responseDTOs.UserResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.UserStudentResponseDTO;
import com.lbdg.library_backend.entities.UserEntity;

public class UserMapper {

    public static UserResponseDTO toUserResponseDTO(UserEntity userEntity) {

        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .userFullName(userEntity.getFirstName() + " " + userEntity.getLastName())
                .role(userEntity.getRole().toString())
                .build();
    }

    public static UserStudentResponseDTO toUserStudentResponseDTO(UserEntity userEntity) {

        return UserStudentResponseDTO.builder()
                .id(userEntity.getId())
                .userFullName(userEntity.getFirstName() + " " + userEntity.getLastName())
                .build();
    }
}

