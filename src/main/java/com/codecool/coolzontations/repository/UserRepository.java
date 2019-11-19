package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
