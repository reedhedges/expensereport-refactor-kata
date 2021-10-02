package com.nelkinda.training;

abstract class Expense {
    private final ExpenseType type;
    protected final int amount;
    private final String name;

    Expense(ExpenseType type, String name, int amount) {
        this.type = type;
        this.name = name;
        this.amount = amount;
    }

    public static Expense createCarRentalExpense(int amount) {
        return new UnlimitedExpense(ExpenseType.OTHER, "Car Rental", amount);
    }

    public static Expense createBreakfastExpense(int amount) {
        return new LimitedExpense(ExpenseType.MEAL, "Breakfast", amount, 1000);
    }

    public static Expense createDinnerExpense(int amount) {
        return new LimitedExpense(ExpenseType.MEAL, "Dinner", amount, 5000);
    }

    public ReportLineData asData() {
        return new ReportLineData(name, amount, this.isOverExpenses());
    }

    public boolean isMeal() {
        return type == ExpenseType.MEAL;
    }

    abstract public boolean isOverExpenses();

    public String name() {
        return name;
    }

    public int amount() {
        return amount;
    }

    public enum ExpenseType {
        MEAL, OTHER
    }
}
