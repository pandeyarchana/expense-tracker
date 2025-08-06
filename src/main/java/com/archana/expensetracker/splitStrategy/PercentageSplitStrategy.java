package com.archana.expensetracker.splitStrategy;

import com.archana.expensetracker.dto.SplitRequest;
import com.archana.expensetracker.model.Split;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PercentageSplitStrategy implements SplitStrategy {

    @Autowired
    private UserService userService;

    @Operation(summary = "calculate percentage splits")
    @Override
    public List<Split> calculateSplits(SplitRequest request) {
        List<Long> participants = request.getParticipants();
        List<Double> percentages = request.getDetails();
        double totalAmount = request.getTotalAmount();

        if (participants.size() != percentages.size()) {
            throw new IllegalArgumentException("Mismatch between number of participants and percentages");
        }

        double totalPercent = percentages.stream().mapToDouble(Double::doubleValue).sum();
        if (Double.compare(totalPercent, 100.0) != 0) {
            throw new IllegalArgumentException("Percentages must sum to 100");
        }

        List<Split> splits = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            Long userId = participants.get(i);
            double amount = (percentages.get(i) / 100.0) * totalAmount;
            User user = userService.getUser(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
            splits.add(new Split(amount, user, null));
        }

        return splits;
    }
}
