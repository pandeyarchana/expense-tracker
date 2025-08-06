package com.archana.expensetracker.dto;

import lombok.Data;


public class SimplifiedTransaction {
    private String from;
    private String to;
    private double amount;

    public SimplifiedTransaction(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }
}
