package com.lbdg.library_backend.controllers;

import com.lbdg.library_backend.DTOs.responseDTOs.UserResponseDTO;
import com.lbdg.library_backend.DTOs.responseDTOs.UserStudentResponseDTO;
import com.lbdg.library_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDTO> getUsers()
    {
        return userService.getUsers();
    }

    @GetMapping("/students")
    public List<UserStudentResponseDTO> getStudents()
    {
        return userService.getStudents();
    }
}
