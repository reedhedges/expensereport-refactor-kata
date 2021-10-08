package com.nelkinda.training;

import java.util.Date;
import java.util.List;

interface ExpenseType {

    String getDescription();
    String getMealOverExpensesMarker(int amount);
    default boolean isMealType(){
        return false;
    }
}

enum MealType implements ExpenseType {
    DINNER("Dinner", 5000),
    BREAKFAST("Breakfast", 1000),
    LUNCH("Lunch", 2000);

    private final String description;
    private final int maxAmount;

    MealType(String description, int maxAmount) {
        this.description = description;
        this.maxAmount = maxAmount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getMealOverExpensesMarker(int amount) {
        return amount > getMaxAmount() ? "X" : " ";
    }

    @Override
    public boolean isMealType() {
        return true;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}

enum OtherExpensesType implements ExpenseType {
    CAR_RENTAL("Car Rental");

    private final String description;

    OtherExpensesType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getMealOverExpensesMarker(int maxAmount) {
        return " ";
    }

}
class Expense {
    ExpenseType type;
    int amount;

    public String toString() {
        return type.getDescription() + "\t" + amount + "\t" + type.getMealOverExpensesMarker(amount);
    }
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        int total = 0;
        int mealExpenses = 0;

        System.out.println("Expenses " + currentDate());

        for (Expense expense : expenses) {
            mealExpenses = getMealExpenses(mealExpenses, expense);
            System.out.println(expense);

            total += expense.amount;
        }

        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);

    }

    private int getMealExpenses(int mealExpenses, Expense expense) {
        if (expense.type.isMealType()) {
            mealExpenses += expense.amount;
        }
        return mealExpenses;
    }

    protected Date currentDate() {
        return new Date();
    }
}
