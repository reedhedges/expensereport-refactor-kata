package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER("Dinner"),
    BREAKFAST("Breakfast"),
    CAR_RENTAL("Car Rental");

    public String description;

    ExpenseType(String description) {
        this.description = description;
    }
}

class Expense {
    ExpenseType type;
    int amount;
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        int total = 0;
        int mealExpenses = 0;

        System.out.println("Expenses " + currentDate());

        for (Expense expense : expenses) {
            if (expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST) {
                mealExpenses += expense.amount;
            }

            String mealOverExpensesMarker = expense.type == ExpenseType.DINNER && expense.amount > 5000 || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000 ? "X" : " ";

            System.out.println(expense.type.description + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

            total += expense.amount;
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);

    }

    protected Date currentDate() {
        return new Date();
    }
}
