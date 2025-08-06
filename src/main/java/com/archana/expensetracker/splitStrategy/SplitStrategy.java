package com.archana.expensetracker.splitStrategy;


import com.archana.expensetracker.dto.SplitRequest;
import com.archana.expensetracker.model.Split;

import java.util.List;

public interface SplitStrategy {
    public List<Split> calculateSplits(SplitRequest request);
}
