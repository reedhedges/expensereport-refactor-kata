package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER("Dinner", true, 5000),
    BREAKFAST("Breakfast", true, 1000),
    CAR_RENTAL("Car Rental", false, Integer.MAX_VALUE),
    LUNCH("Lunch", true, 2000);

    private final String name;
    private final boolean meal;
    private final int limit;

    ExpenseType(String name, boolean meal, int limit) {
        this.name = name;
        this.meal = meal;
        this.limit = limit;
    }

    String getName() {
        return name;
    }

    boolean isMeal() {
        return meal;
    }

    int getLimit() {
        return limit;
    }
}

class Expense {
    ExpenseType type;
    int amount;

    String getName() {
        return type.getName();
    }

    boolean isMeal() {
        return type.isMeal();
    }

    boolean isOverLimit() {
        return amount > type.getLimit();
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
        int mealExpenses = 0;

        System.out.println("Expenses " + date);

        for (Expense expense : expenses) {
            if (expense.isMeal()) {
                mealExpenses += expense.getAmount();
            }
            String expenseName = expense.getName();
            String mealOverExpensesMarker = expense.isOverLimit() ? "X" : " ";
            System.out.println(expenseName + "\t" + expense.getAmount() + "\t" + mealOverExpensesMarker);
            total += expense.getAmount();
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);
    }

}
