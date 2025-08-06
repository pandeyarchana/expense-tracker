package com.archana.expensetracker.dto;


import com.archana.expensetracker.model.SplitType;

import java.util.List;

public class SplitRequest {

    private double totalAmount;

    private List<Long> participants;

    private SplitType splitType; // EQUAL, EXACT, PERCENTAGE

    /**
     * If splitType == PERCENTAGE → percentages
     * If splitType == EXACT → exact amounts
     * If splitType == EQUAL → can be null or empty
     */
    private List<Double> details;

    public SplitRequest(double totalAmount, List<Long> participants, SplitType splitType, List<Double> details) {
        this.totalAmount = totalAmount;
        this.participants = participants;
        this.splitType = splitType;
        this.details = details;
    }

    public SplitRequest() {
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }

    public List<Double> getDetails() {
        return details;
    }

    public void setDetails(List<Double> details) {
        this.details = details;
    }
}