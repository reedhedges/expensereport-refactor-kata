package com.nelkinda.training;

public class UnlimitedExpense extends Expense {
    UnlimitedExpense(String name, int amount) {
        super(null, name, amount);
    }

    @Override
    public boolean isOverExpenses() {
        return false;
    }
}
