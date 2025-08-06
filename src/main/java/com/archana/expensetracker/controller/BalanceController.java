package com.archana.expensetracker.controller;

import com.archana.expensetracker.dto.Balance;
import com.archana.expensetracker.dto.SimplifiedTransaction;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.service.BalanceService;
import com.archana.expensetracker.service.SimplifyDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private SimplifyDebtService simplifyDebtService;

    @GetMapping("/group/{groupId}")
    public List<Balance> getBalances(@PathVariable Long groupId) {
        return balanceService.calculateBalances(groupId);
    }

    @GetMapping("/user/{userId}")
    public Map<Long, Double> getUserBalance(@PathVariable Long userId) {
        return balanceService.getUserBalance(userId);
    }

    @GetMapping("/settle")
    public List<SimplifiedTransaction> getSimplifiedDebts() {
        Map<User, Double> balances = balanceService.getAllUserBalances();
        return simplifyDebtService.simplify(balances);
    }
}
