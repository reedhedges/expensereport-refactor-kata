package com.nelkinda.training;

import java.util.List;

public class Expenses {
    private List<Expense> expenses;

    public Expenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    void printExpenseInformation() {
        for (Expense expense : this.expenses) {
            System.out.println(expense.toString());
        }
    }

    int calculateMealExpenses() {
        int mealExpenses = 0;
        for (Expense expense : this.expenses) {
            int mealExpenses1 = mealExpenses;
            if (expense.isMeal()) {
                mealExpenses1 += expense.getAmount();
            }
            mealExpenses = mealExpenses1;
        }
        return mealExpenses;
    }

    int calculateTotal() {
        int total = 0;
        for (Expense expense : this.expenses) {
            total += expense.getAmount();
        }
        return total;
    }
}