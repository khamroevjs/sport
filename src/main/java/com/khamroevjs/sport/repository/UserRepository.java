package com.khamroevjs.sport.repository;

import com.khamroevjs.sport.model.User;
import com.khamroevjs.sport.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    int countAllByRoleIs(UserRole role);
}
