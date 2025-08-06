package com.archana.expensetracker.service;

import com.archana.expensetracker.model.Expense;
import com.archana.expensetracker.model.Group;
import com.archana.expensetracker.model.Split;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseValidationService {

    @Autowired
    private final UserRepository userRepository;

    public ExpenseValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "validate users in an expense split")
    public void validateUsersAreSplits(Expense expense) {
        for (Split split : expense.getSplits()) {
            Long userId = split.getUser().getId();
            if (!userRepository.existsById(userId)) {
                throw new IllegalArgumentException("Invalid user ID in split: " + userId);
            }
        }
    }

    @Operation(summary = "validate users in a group")
    public void validateUsersBelongToGroup(Group group, List<Split> splits) {
        List<User> groupMembers = group.getUsers();

        for (Split split : splits) {
            User user = split.getUser();
            if (!groupMembers.contains(user)) {
                throw new IllegalArgumentException(
                        "User " + user.getId() + " is not a member of group " + group.getUsers()
                );
            }
        }
    }
}
