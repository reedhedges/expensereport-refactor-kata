package com.nelkinda.training;

public class ReportLineData {
    private final String expenseName;
    private final int amount;
    private final String mealOverExpensesMarker;

    public ReportLineData(String expenseName, int amount, String mealOverExpensesMarker) {
        this.expenseName = expenseName;
        this.amount = amount;
        this.mealOverExpensesMarker = mealOverExpensesMarker;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public int getAmount() {
        return amount;
    }

    public String getMealOverExpensesMarker() {
        return mealOverExpensesMarker;
    }
}
