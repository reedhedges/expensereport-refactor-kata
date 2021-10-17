package com.nelkinda.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

enum ExpenseType {
    DINNER("Dinner", true, 5000),
    BREAKFAST("Breakfast", true, 1000),
    CAR_RENTAL("Car Rental", false, Integer.MAX_VALUE),
    LUNCH("Lunch", true, 2000);

    private final String name;
    private final boolean meal;
    private final int limit;

    ExpenseType(String name, boolean meal, int limit) {
        this.name = name;
        this.meal = meal;
        this.limit = limit;
    }

    String getName() {
        return name;
    }

    boolean isMeal() {
        return meal;
    }

    int getLimit() {
        return limit;
    }
}

class Expense {
    ExpenseType type;
    int amount;

    String getName() {
        return type.getName();
    }

    boolean isMeal() {
        return type.isMeal();
    }

    boolean isOverLimit() {
        return amount > type.getLimit();
    }

    int getAmount() {
        return amount;
    }
}

class Expenses implements Iterable<Expense> {

    private final List<Expense> expenseList = new ArrayList<>();

    public Expenses() {
    }

    public Expenses(Expense... expenses) {
        expenseList.addAll(Arrays.asList(expenses));
    }

    int getMealExpenses() {
        return expenseList.stream()
            .filter(Expense::isMeal)
            .mapToInt(Expense::getAmount)
            .sum();
    }

    int getTotal() {
        return expenseList.stream()
            .mapToInt(Expense::getAmount)
            .sum();
    }

    @Override
    public Iterator iterator() {
        return expenseList.iterator();
    }
}

public class ExpenseReport {
    public void printReport(Expenses expenses) {
        printReport(expenses, new Date());
    }

    public void printReport(Expenses expenses, Date date) {
        System.out.println("Expenses " + date);

        for (Expense expense : expenses) {
            printSingleExpense(expense);
        }

        System.out.println("Meal expenses: " + expenses.getMealExpenses());
        System.out.println("Total expenses: " + expenses.getTotal());
    }

    private void printSingleExpense(Expense expense) {
        String mealOverExpensesMarker = expense.isOverLimit() ? "X" : " ";
        System.out.println(expense.getName() + "\t" + expense.getAmount() + "\t" + mealOverExpensesMarker);
    }

}
