package com.archana.expensetracker;

import com.archana.expensetracker.model.Group;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.service.GroupService;
import com.archana.expensetracker.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GroupServiceTest {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Test
    void testCreateGroupWithUsers() {
        User user1 = userService.createUser(new User("A", "a@mail.com"));
        User user2 = userService.createUser(new User("B", "b@mail.com"));

        Group group = groupService.createGroup(new Group("Trip", List.of(user1, user2)));

        assertEquals("Trip", group.getName());
        assertEquals(2, group.getUsers().size());
    }
}
