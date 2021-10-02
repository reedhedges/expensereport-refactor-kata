package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

class Expense {
    ExpenseType type;
    int amount;
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        int total = 0;

        System.out.println("Expenses " + now());


        for (Expense expense : expenses) {
            String expenseName = expenseName(expense);

            String mealOverExpensesMarker = mealOverExpensesMarker(expense);

            System.out.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

            total += expense.amount;
        }

        int mealExpenses = mealExpenses(expenses);
        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);
    }

    private int mealExpenses(List<Expense> expenses) {
        int mealExpenses = 0;
        for (Expense expense : expenses) {
            if (isMeal(expense)) {
                mealExpenses += expense.amount;
            }
        }
        return mealExpenses;
    }

    private String mealOverExpensesMarker(Expense expense) {
        return expense.type == ExpenseType.DINNER && expense.amount > 5000
            || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000
            ? "X"
            : " ";
    }

    private String expenseName(Expense expense) {
        return switch (expense.type) {
            case DINNER -> "Dinner";
            case BREAKFAST -> "Breakfast";
            case CAR_RENTAL -> "Car Rental";
        };
    }

    private boolean isMeal(Expense expense) {
        return expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST;
    }

    protected Date now() {
        return new Date();
    }
}
