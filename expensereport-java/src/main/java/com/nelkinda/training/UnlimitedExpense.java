package com.nelkinda.training;

public class UnlimitedExpense extends Expense {
    UnlimitedExpense(ExpenseType expenseType, String name, int amount) {
        super(expenseType, name, amount);
    }

    @Override
    public boolean isOverExpenses() {
        return false;
    }
}
