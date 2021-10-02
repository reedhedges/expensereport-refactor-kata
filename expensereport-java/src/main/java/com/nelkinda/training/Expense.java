package com.nelkinda.training;

class Expense {
    ExpenseType type;

    int amount;

    public static Expense createCarRentalExpense(int amount) {
        return createExpense(ExpenseType.CAR_RENTAL, amount);
    }

    public static Expense createBreakfastExpense(int amount) {
        return createExpense(ExpenseType.BREAKFAST, amount);
    }

    public static Expense createDinnerExpense(int amount) {
        return createExpense(ExpenseType.DINNER, amount);
    }

    private static Expense createExpense(ExpenseType type, int amount) {
        Expense expense = new Expense();
        expense.type = type;
        expense.amount = amount;
        return expense;
    }

    public ReportLineData asData() {
        return new ReportLineData(this.name(), this.amount, this.isMealOverExpenses());
    }

    public boolean isMeal() {
        return type == ExpenseType.DINNER || type == ExpenseType.BREAKFAST;
    }

    public boolean isMealOverExpenses() {
        return type == ExpenseType.DINNER && amount > 5000
            || type == ExpenseType.BREAKFAST && amount > 1000;
    }

    public String name() {
        return switch (type) {
            case DINNER -> "Dinner";
            case BREAKFAST -> "Breakfast";
            case CAR_RENTAL -> "Car Rental";
        };
    }

    private enum ExpenseType {
        DINNER, BREAKFAST, CAR_RENTAL
    }
}
