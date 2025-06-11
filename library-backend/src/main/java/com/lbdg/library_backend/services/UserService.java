package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.responseDTOs.UserResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.UserStudentResponseDTO;
import com.lbdg.library_backend.config.Role;
import com.lbdg.library_backend.entities.UserEntity;
import com.lbdg.library_backend.mappers.UserMapper;
import com.lbdg.library_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public List<UserResponseDTO> getUsers(){
        List<UserResponseDTO> users = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();

        for (UserEntity userEntity : userEntities) {
            users.add(UserMapper.toUserResponseDTO(userEntity));
        }

        return users;
    }

    public List<UserStudentResponseDTO> getStudents(){
        List<UserStudentResponseDTO> students = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAllByRole(Role.STUDENT);

        for (UserEntity userEntity : userEntities) {
            students.add(UserMapper.toUserStudentResponseDTO(userEntity));
        }

        return students;
    }
}
