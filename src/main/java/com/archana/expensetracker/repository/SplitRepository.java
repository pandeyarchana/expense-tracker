package com.archana.expensetracker.repository;

import com.archana.expensetracker.model.Expense;
import com.archana.expensetracker.model.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {
    List<Split> findByExpenseId(Long expenseId);
}
