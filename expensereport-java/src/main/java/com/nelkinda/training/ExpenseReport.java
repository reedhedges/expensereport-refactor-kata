package com.nelkinda.training;

import java.util.Date;
import java.util.List;

interface ExpenseType {

    String getDescription();
}

enum MealType implements ExpenseType {
    DINNER("Dinner", 5000),
    BREAKFAST("Breakfast", 1000),
    LUNCH("Lunch", 2000);

    private String description;
    private int maxAmount;

    MealType(String description, int maxAmount) {
        this.description = description;
        this.maxAmount = maxAmount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public int getMaxAmount() {
        return maxAmount;
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
        return description;
    }
}
class Expense {
    ExpenseType type;
    int amount;

    public String getMealOverExpensesMarker(){
        return (type instanceof MealType && amount > ((MealType)type).getMaxAmount() ) ? "X":" ";
    }

    public String toString() {
        return type.getDescription() + "\t" + amount + "\t" + getMealOverExpensesMarker();
    }
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
            System.out.println(expense);

            total += expense.amount;
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);

    }

    protected Date currentDate() {
        return new Date();
    }
}
