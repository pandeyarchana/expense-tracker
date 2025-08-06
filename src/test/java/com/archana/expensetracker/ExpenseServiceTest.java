package com.archana.expensetracker;

import com.archana.expensetracker.dto.ExpenseRequest;
import com.archana.expensetracker.dto.SplitRequest;
import com.archana.expensetracker.model.Expense;
import com.archana.expensetracker.model.Group;
import com.archana.expensetracker.model.SplitType;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.service.ExpenseService;
import com.archana.expensetracker.service.GroupService;
import com.archana.expensetracker.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Test
    void testAddPeerToPeerExpense() {
        User u1 = userService.createUser(new User("U1", "u1@mail.com"));
        User u2 = userService.createUser(new User( "U2", "u2@mail.com"));

        ExpenseRequest req = new ExpenseRequest("Lunch", 3000.0, u1.getId(), null,
                SplitType.EXACT, List.of(u1.getId(), u2.getId()),
                List.of(1500.0, 1500.0));


        Expense expense = expenseService.createExpense(req);
        assertNull(expense.getGroup());
        assertEquals(2, expense.getSplits().size());
    }

    @Test
    @Transactional
    void testAddEqualSplitExpenseInGroup() {
        User u1 = userService.createUser(new User("U1", "u1@mail.com"));
        User u2 = userService.createUser(new User("U2", "u2@mail.com"));
        Group group = groupService.createGroup(new Group("Trip", List.of(u1, u2)));

        ExpenseRequest req = new ExpenseRequest("Hotel", 1000.0, u1.getId(), group.getId(),
                SplitType.EQUAL, List.of(u1.getId(), u2.getId()),
               null);


        Expense expense = expenseService.createExpense(req);
        assertEquals(2, expense.getSplits().size());
    }

}
