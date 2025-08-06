package com.archana.expensetracker.dto;

import com.archana.expensetracker.model.SplitType;

import java.util.List;

public class ExpenseRequest {
    private String description;
    private Double amount;
    private Long paidBy;
    private Long groupId;
    private SplitType splitType;

    private List<Long> participants;
    private List<Double> details;

    public ExpenseRequest(String description, Double amount, Long paidBy, Long groupId, SplitType splitType, List<Long> participants, List<Double> details) {
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.groupId = groupId;
        this.splitType = splitType;
        this.participants = participants;
        this.details = details;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getPaidBy() {
        return paidBy;
    }

    public Long getGroupId() {
        return groupId;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public List<Double> getDetails() {
        return details;
    }
}


