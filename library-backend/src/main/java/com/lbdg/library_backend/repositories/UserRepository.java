package com.lbdg.library_backend.repositories;

import com.lbdg.library_backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
