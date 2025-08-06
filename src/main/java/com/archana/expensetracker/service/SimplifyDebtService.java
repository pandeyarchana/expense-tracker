package com.archana.expensetracker.service;

import com.archana.expensetracker.dto.SimplifiedTransaction;
import com.archana.expensetracker.model.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimplifyDebtService {

    @Operation(summary = "simplify debt logic")
    public List<SimplifiedTransaction> simplify(Map<User, Double> balances) {
        PriorityQueue<UserBalance> debtors = new PriorityQueue<>(Comparator.comparingDouble(b -> b.amount));
        PriorityQueue<UserBalance> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.amount, a.amount));

        // Separate creditors and debtors
        for (Map.Entry<User, Double> entry : balances.entrySet()) {
            double amount = entry.getValue();
            if (amount < -0.01) {
                debtors.add(new UserBalance(entry.getKey(), amount));
            } else if (amount > 0.01) {
                creditors.add(new UserBalance(entry.getKey(), amount));
            }
        }

        List<SimplifiedTransaction> result = new ArrayList<>();

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            UserBalance debtor = debtors.poll();
            UserBalance creditor = creditors.poll();

            double settlement = Math.min(-debtor.amount, creditor.amount);

            result.add(new SimplifiedTransaction(
                    debtor.user.getName(),
                    creditor.user.getName(),
                    Math.round(settlement * 100.0) / 100.0
            ));

            debtor.amount += settlement;
            creditor.amount -= settlement;

            if (debtor.amount < -0.01) debtors.add(debtor);
            if (creditor.amount > 0.01) creditors.add(creditor);
        }

        return result;
    }

    private static class UserBalance {
        User user;
        double amount;

        public UserBalance(User user, double amount) {
            this.user = user;
            this.amount = amount;
        }
    }
}
