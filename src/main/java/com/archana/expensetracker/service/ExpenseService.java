package com.archana.expensetracker.service;

import com.archana.expensetracker.dto.ExpenseRequest;
import com.archana.expensetracker.dto.SplitRequest;
import com.archana.expensetracker.model.*;
import com.archana.expensetracker.repository.ExpenseRepository;
import com.archana.expensetracker.repository.GroupRepository;
import com.archana.expensetracker.repository.UserRepository;
import com.archana.expensetracker.splitStrategy.SplitStrategy;
import com.archana.expensetracker.splitStrategy.SplitStrategyFactory;
import com.archana.expensetracker.model.SplitType;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ExpenseValidationService expenseValidationService;

    private final SplitStrategyFactory splitStrategyFactory;

    @Autowired
    public ExpenseService(SplitStrategyFactory splitStrategyFactory) {
        this.splitStrategyFactory = splitStrategyFactory;
    }

    @Operation(summary = "add a new expense")
    public Expense createExpense(ExpenseRequest request) {
        // Fetch user who paid
        User paidBy = userRepository.findById(request.getPaidBy())
                .orElseThrow(() -> new IllegalArgumentException("Invalid paidByUserId"));

        // Create expense entity
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setPaidBy(paidBy);

        // Optional group
        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid groupId"));
            expense.setGroup(group);
        }

        // Construct SplitRequest from ExpenseRequest
        SplitRequest splitRequest = new SplitRequest();
        splitRequest.setTotalAmount(request.getAmount());
        splitRequest.setParticipants(request.getParticipants());
        splitRequest.setSplitType(request.getSplitType());

        if (request.getSplitType() != SplitType.EQUAL) {
            splitRequest.setDetails(request.getDetails());
        }

        // Use strategy to calculate splits
        SplitStrategy strategy = splitStrategyFactory.getStrategy(request.getSplitType());
        List<Split> splits = strategy.calculateSplits(splitRequest);

        // Set reverse links
        splits.forEach(split -> split.setExpense(expense));
        expense.setSplits(splits);

        // Validate
        if (expense.getGroup() == null) {
            expenseValidationService.validateUsersAreSplits(expense);
        } else {
            expenseValidationService.validateUsersBelongToGroup(expense.getGroup(), splits);
        }

        return expenseRepository.save(expense);
    }

    @Operation(summary = "return expenses for a given groupId")
    public List<Expense> getExpensesByGroupId(Long groupId) {
        return expenseRepository.findByGroupId(groupId);
    }

    @Operation(summary = "return expenses for a given userId")
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByPaidById(userId);
    }



}
