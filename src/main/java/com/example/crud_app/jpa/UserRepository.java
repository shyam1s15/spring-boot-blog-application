package com.example.crud_app.jpa;

import com.example.crud_app.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Integer> {
    List<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
