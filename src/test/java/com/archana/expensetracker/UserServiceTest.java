package com.archana.expensetracker;

import com.archana.expensetracker.model.User;
import com.archana.expensetracker.repository.UserRepository;
import com.archana.expensetracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAddAndGetUser() {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        User savedUser = userService.createUser(user);
        assertNotNull(savedUser.getId());
        assertEquals("Alice", savedUser.getName());

        Optional<User> fetched = userService.getUser(savedUser.getId());
        assertTrue(fetched.isPresent());
        assertEquals("alice@example.com", fetched.get().getEmail());
    }
}
