package com.example.java_test.repository;

import com.example.java_test.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // Custom query methods can be defined here --> single responsibility principle
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
}