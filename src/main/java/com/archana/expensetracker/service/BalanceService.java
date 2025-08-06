package com.archana.expensetracker.service;

import com.archana.expensetracker.dto.Balance;
import com.archana.expensetracker.model.Expense;
import com.archana.expensetracker.model.Split;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.repository.ExpenseRepository;
import com.archana.expensetracker.repository.SplitRepository;
import com.archana.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private UserRepository userRepository;


    //for a given groupId
    public List<Balance> calculateBalances(final Long groupId) {
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);
        Map<Long, Double> netBalances = new HashMap<>();

        for (Expense expense : expenses) {
            Long payerId = expense.getPaidBy().getId();
            Double totalAmount = expense.getAmount();

            List<Split> splits = splitRepository.findByExpenseId(expense.getId());
            for (Split split : splits) {
                Long borrowerId = split.getUser().getId();
                Double share = split.getAmount();

                // The borrower owes money
                netBalances.put(borrowerId, netBalances.getOrDefault(borrowerId, 0.0) - share);

                // The payer is owed money
                netBalances.put(payerId, netBalances.getOrDefault(payerId, 0.0) + share);
            }
        }

        return netBalances.entrySet().stream()
                .map(entry -> new Balance(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Map<Long, Double> getUserBalance(Long userId) {
        Map<Long, Double> balanceMap = new HashMap<>();

        List<Expense> allExpenses = expenseRepository.findAll();

        for (Expense expense : allExpenses) {
            Long paidById = expense.getPaidBy().getId();

            for (Split split : expense.getSplits()) {
                Long owedById = split.getUser().getId();
                double amount = split.getAmount();

                if (Objects.equals(owedById, paidById)) {
                    // Skip if user paid for themselves
                    continue;
                }

                if (Objects.equals(owedById, userId)) {
                    // Current user owes money
                    balanceMap.put(paidById, balanceMap.getOrDefault(paidById, 0.0) - amount);
                } else if (Objects.equals(paidById, userId)) {
                    // Someone else owes money to current user
                    balanceMap.put(owedById, balanceMap.getOrDefault(owedById, 0.0) + amount);
                }
            }
        }

        return balanceMap;
    }

    public Map<User, Double> getAllUserBalances() {
        List<User> users = userRepository.findAll();

        Map<User, Double> paidMap = new HashMap<>();
        Map<User, Double> owedMap = new HashMap<>();

        List<Expense> allExpenses = expenseRepository.findAll();

        // Sum up total paid by each user
        for (Expense expense : allExpenses) {
            User paidBy = expense.getPaidBy();
            paidMap.put(paidBy, paidMap.getOrDefault(paidBy, 0.0) + expense.getAmount());

            // Sum up total owed by each split user
            for (Split split : expense.getSplits()) {
                User user = split.getUser();
                owedMap.put(user, owedMap.getOrDefault(user, 0.0) + split.getAmount());
            }
        }

        // Compute final balance = paid - owed
        Map<User, Double> balanceMap = new LinkedHashMap<>();

        for (User user : users) {
            double paid = paidMap.getOrDefault(user, 0.0);
            double owed = owedMap.getOrDefault(user, 0.0);
            double balance = Math.round((paid - owed) * 100.0) / 100.0;

            balanceMap.put(user, balance);
        }

        return balanceMap;
    }
}
