package com.nelkinda.training;

import java.util.Date;
import java.util.List;

interface ExpenseType {

    String getDescription();
}

enum MealType implements ExpenseType {
    DINNER("Dinner"),
    BREAKFAST("Breakfast");

    private String description;

    MealType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

enum OtherExpensesType implements ExpenseType {
    CAR_RENTAL("Car Rental");

    private String description;

    OtherExpensesType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return null;
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
            if (expense.type instanceof MealType) {
                mealExpenses += expense.amount;
            }

            String mealOverExpensesMarker = getMealOverExpensesMarker(expense);

            System.out.println(expense.type.getDescription() + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

            total += expense.amount;
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);

    }

    private String getMealOverExpensesMarker(Expense expense) {
        return expense.type == ExpenseType.DINNER && expense.amount > 5000 ||
               expense.type == ExpenseType.BREAKFAST && expense.amount > 1000
                ? "X" : " ";
    }

    protected Date currentDate() {
        return new Date();
    }
}
