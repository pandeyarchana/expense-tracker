package com.archana.expensetracker.service;

import com.archana.expensetracker.model.User;
import com.archana.expensetracker.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "create user")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Operation(summary = "get user")
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Operation(summary = "get all users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
