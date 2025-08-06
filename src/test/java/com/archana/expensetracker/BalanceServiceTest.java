package com.archana.expensetracker;

import com.archana.expensetracker.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest
public class BalanceServiceTest {

    @Autowired
    private BalanceService balanceService;

    @Test
    @Transactional
    void testGetBalanceForUser() {
        Map<Long, Double> balanceMap = balanceService.getUserBalance(2L);
        balanceMap.forEach((user, balance) -> {
            System.out.println("User " + user+ " owes: " + balance);
        });
    }
}