package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseType {
    BREAKFAST("Breakfast", 1000, true),
    LUNCH("Lunch", 2000, true),
    DINNER("Dinner", 5000, true),
    CAR_RENTAL("Car Rental", Integer.MAX_VALUE, false)

    ;

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

    public void printReport(List<Expense> expenseList, Date date) {
        final Expenses expenses = new Expenses(expenseList);
        System.out.println("Expenses " + date);
        expenses.printExpenseInformation();
        System.out.println("Meal expenses: " + expenses.calculateMealExpenses());
        System.out.println("Total expenses: " + expenses.calculateTotal());
    }

}
