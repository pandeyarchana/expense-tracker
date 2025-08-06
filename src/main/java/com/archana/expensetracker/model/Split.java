package com.archana.expensetracker.model;

import jakarta.persistence.*;

@Entity
public class Split {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private double amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Expense expense;

    public Split(double amount, User user, Expense expense) {
        this.amount = amount;
        this.user = user;
        this.expense = expense;
    }

    public Split() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
