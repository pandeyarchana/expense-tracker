package com.archana.expensetracker;

import com.archana.expensetracker.model.SplitType;
import com.archana.expensetracker.splitStrategy.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class SplitStrategyFactoryTest {

    @Autowired
    SplitStrategyFactory splitStrategyFactory;

    @Test
    void testGetEqualStrategy() {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(SplitType.EQUAL);
        assertInstanceOf(EqualSplitStrategy.class, strategy);
    }

    @Test
    void testGetExactStrategy() {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(SplitType.EXACT);
        assertInstanceOf(ExactSplitStrategy.class, strategy);
    }

    @Test
    void testGetPercentageStrategy() {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(SplitType.PERCENTAGE);
        assertInstanceOf(PercentageSplitStrategy.class, strategy);
    }
}
