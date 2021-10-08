package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER("Dinner", 5000, true),
    BREAKFAST("Breakfast", 1000, true),
    CAR_RENTAL("Car Rental", Integer.MAX_VALUE, false);

    private final String expenseName;
    private final int amountLimit;
    private final boolean isMeal;

    ExpenseType(String expenseName, int amountLimit, boolean isMeal) {
        this.expenseName = expenseName;
        this.amountLimit = amountLimit;
        this.isMeal = isMeal;
    }

    public int getAmountLimit() {
        return amountLimit;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public boolean isMeal() {
        return isMeal;
    }
}

class Expense {
    ExpenseType type;
    int amount;

    String getExpenseName() {
        return type.getExpenseName();
    }

    boolean isOverLimit() {
        return amount > type.getAmountLimit();
    }

    boolean isMeal() {
        return type.isMeal();
    }

    int getAmount() {
        return amount;
    }
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        printReport(expenses, new Date());
    }

    public void printReport(List<Expense> expenses, Date date) {
        int total = 0;
        int mealExpenses =  0;

        System.out.println("Expenses " + date);

        for (Expense expense : expenses) {
            if (expense.isMeal()) {
                mealExpenses += expense.getAmount();
            }

            String mealOverExpensesMarker = expense.isOverLimit() ? "X" : " ";

            System.out.println(expense.getExpenseName() + "\t" + expense.getAmount() + "\t" + mealOverExpensesMarker);

            total += expense.getAmount();
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);
    }

}
