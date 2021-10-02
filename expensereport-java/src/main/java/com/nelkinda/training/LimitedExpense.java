package com.nelkinda.training;

public class LimitedExpense extends Expense {
    
    private final int limit;
    
    LimitedExpense(ExpenseType type, String name, int amount, int limit) {
        super(type, name, amount);
        this.limit = limit;
    }

    public boolean isOverExpenses() {
        return amount > limit;
    }
}
