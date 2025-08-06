package com.archana.expensetracker.splitStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.archana.expensetracker.model.SplitType;

@Component
public class SplitStrategyFactory {

    private final Map<SplitType, SplitStrategy> strategyMap = new EnumMap<>(SplitType.class);

    @Autowired
    public SplitStrategyFactory(List<SplitStrategy> strategies) {
        for (SplitStrategy strategy : strategies) {
            if (strategy instanceof EqualSplitStrategy) {
                strategyMap.put(SplitType.EQUAL, strategy);
            } else if (strategy instanceof ExactSplitStrategy) {
                strategyMap.put(SplitType.EXACT, strategy);
            } else if (strategy instanceof PercentageSplitStrategy) {
                strategyMap.put(SplitType.PERCENTAGE, strategy);
            } else {
                throw new IllegalArgumentException("Unknown strategy: " + strategy.getClass());
            }
        }
    }

    public SplitStrategy getStrategy(SplitType type) {
        return strategyMap.get(type);
    }
}
