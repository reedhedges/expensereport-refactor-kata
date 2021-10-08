package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER("Dinner"),
    BREAKFAST("Breakfast"),
    CAR_RENTAL("Car Rental");

    private final String expenseName;

    ExpenseType(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseName() {
        return expenseName;
    }
}

class Expense {
    ExpenseType type;
    int amount;

    String getExpenseName() {
        return type.getExpenseName();
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
            if (expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST) {
                mealExpenses += expense.amount;
            }

            String mealOverExpensesMarker = expense.type == ExpenseType.DINNER && expense.amount > 5000
                    || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000 ? "X" : " ";

            System.out.println(expense.getExpenseName() + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

            total += expense.amount;
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);
    }

}
