package com.nelkinda.training;

class Expense {
    private final ExpenseType type;
    public final int amount;
    private final String name;

    Expense(ExpenseType type, String name, int amount) {
        this.type = type;
        this.name = name;
        this.amount = amount;
    }

    public static Expense createCarRentalExpense(int amount) {
        return createExpense(ExpenseType.CAR_RENTAL, "Car Rental", amount);
    }

    public static Expense createBreakfastExpense(int amount) {
        return createExpense(ExpenseType.BREAKFAST, "Breakfast", amount);
    }

    public static Expense createDinnerExpense(int amount) {
        return createExpense(ExpenseType.DINNER, "Dinner", amount);
    }

    private static Expense createExpense(ExpenseType type, String name, int amount) {
        return new Expense(type, name, amount);
    }

    public ReportLineData asData() {
        return new ReportLineData(this.name(), this.amount, this.isOverExpenses());
    }

    public boolean isMeal() {
        return type == ExpenseType.DINNER || type == ExpenseType.BREAKFAST;
    }

    public boolean isOverExpenses() {
        return type == ExpenseType.DINNER && amount > 5000
            || type == ExpenseType.BREAKFAST && amount > 1000;
    }

    public String name() {
        return name;
    }

    private enum ExpenseType {
        DINNER, BREAKFAST, CAR_RENTAL;
    }
}
