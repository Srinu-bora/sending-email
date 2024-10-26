package com.example.emailapp.repository;

import com.example.emailapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods here if needed
    boolean existsByEmail(String email);
}
