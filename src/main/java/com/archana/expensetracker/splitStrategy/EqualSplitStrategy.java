package com.archana.expensetracker.splitStrategy;

import com.archana.expensetracker.dto.SplitRequest;
import com.archana.expensetracker.model.Split;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.repository.UserRepository;
import com.archana.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EqualSplitStrategy implements SplitStrategy {

    @Autowired
    private UserService userService;

    @Override
    public List<Split> calculateSplits(SplitRequest request) {
        List<Long> participants = request.getParticipants();
        double totalAmount = request.getTotalAmount();

        int count = participants.size();
        if (count == 0) {
            throw new IllegalArgumentException("No participants provided for equal split");
        }

        double equalAmount = totalAmount / count;
        List<Split> splits = new ArrayList<>();

        for (Long userId : participants) {
            User user = userService.getUser(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
            splits.add(new Split(equalAmount, user, null));
        }

        return splits;
    }
}
