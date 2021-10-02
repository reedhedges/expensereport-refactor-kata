package com.nelkinda.training;

public class ReportLineData {
    private final String expenseName;
    private final int amount;
    private final boolean isMealOverExpenses;

    ReportLineData(String expenseName, int amount, boolean isMealOverExpenses) {
        this.expenseName = expenseName;
        this.amount = amount;
        this.isMealOverExpenses = isMealOverExpenses;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isMealOverExpenses() {
        return isMealOverExpenses;
    }
}
