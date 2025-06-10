package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.config.Role;
import com.lbdg.library_backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByRole(Role role);
}
