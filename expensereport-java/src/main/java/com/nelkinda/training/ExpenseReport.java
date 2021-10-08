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
    private final ExpenseType type;
    private final int amount;

    public Expense(ExpenseType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

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

    String getExceedingLimitMark() {
        return isOverLimit() ? "X" : " ";
    }

    @Override
    public String toString() {
        return getExpenseName() + "\t" + getAmount() + "\t" + getExceedingLimitMark();
    }
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        printReport(expenses, new Date());
    }

    public void printReport(List<Expense> expenses, Date date) {
        System.out.println("Expenses " + date);
        printExpenseInformation(expenses);
        System.out.println("Meal expenses: " + calculateMealExpenses(expenses));
        System.out.println("Total expenses: " + calculateTotal(expenses));
    }

    private void printExpenseInformation(List<Expense> expenses) {
        for (Expense expense : expenses) {
            System.out.println(expense.toString());
        }
    }

    private int calculateMealExpenses(List<Expense> expenses) {
        int mealExpenses = 0;
        for (Expense expense : expenses) {
            int mealExpenses1 = mealExpenses;
            if (expense.isMeal()) {
                mealExpenses1 += expense.getAmount();
            }
            mealExpenses = mealExpenses1;
        }
        return mealExpenses;
    }

    private int calculateTotal(List<Expense> expenses) {
        int total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

}
