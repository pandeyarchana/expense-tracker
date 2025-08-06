package com.archana.expensetracker.splitStrategy;

import com.archana.expensetracker.dto.SplitRequest;
import com.archana.expensetracker.model.Split;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExactSplitStrategy implements SplitStrategy {

    @Autowired
    private UserService userService;

    @Override
    public List<Split> calculateSplits(SplitRequest request) {
        List<Long> participants = request.getParticipants();
        List<Double> amounts = request.getDetails();
        double totalAmount = request.getTotalAmount();

        if (participants.size() != amounts.size()) {
            throw new IllegalArgumentException("Mismatch between number of participants and amounts");
        }

        double sum = amounts.stream().mapToDouble(Double::doubleValue).sum();
        if (Double.compare(sum, totalAmount) != 0) {
            throw new IllegalArgumentException("Exact amounts must sum to total amount");
        }

        List<Split> splits = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            Long userId = participants.get(i);
            Double amount = amounts.get(i);
            User user = userService.getUser(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
            splits.add(new Split(amount, user, null));
        }

        return splits;
    }
}