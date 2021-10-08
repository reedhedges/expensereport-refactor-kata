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
        return this.expenses.stream()
                .filter(expense -> expense.isMeal())
                .mapToInt(expense -> expense.getAmount())
                .sum();
    }

    int calculateTotal() {
        return this.expenses.stream()
                .mapToInt(Expense::getAmount)
                .sum();
    }
}