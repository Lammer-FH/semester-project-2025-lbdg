package com.lbdg.library_backend.services;

import com.lbdg.library_backend.DTOs.responseDTOs.BookListResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.LibraryResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.UserResponseDTO;
import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.LibraryEntity;
import com.lbdg.library_backend.entities.UserEntity;
import com.lbdg.library_backend.mappers.BookMapper;
import com.lbdg.library_backend.mappers.LibraryMapper;
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
            users.add(UserMapper.toRatingResponseDTO(userEntity));
        }

        return users;
    }

}
