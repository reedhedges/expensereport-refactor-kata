package com.nelkinda.training;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

class Expense {
    ExpenseType type;
    int amount;
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        System.out.println("Expenses " + now());

        List<ExpenseReportLine> lines = reportLines(expenses);
        int mealExpenses = mealExpenses(expenses);
        int total = total(expenses);

        printLines(lines);
        System.out.println("Meal expenses: " + mealExpenses);
        System.out.println("Total expenses: " + total);
    }

    private void printLines(List<ExpenseReportLine> lines) {
        for (ExpenseReportLine line : lines) {
            System.out.println(line.getExpenseName() + "\t" + line.getAmount() + "\t" + line.getMealOverExpensesMarker());
        }
    }

    private List<ExpenseReportLine> reportLines(List<Expense> expenses) {
        List<ExpenseReportLine> lines = new ArrayList<>();
        for (Expense expense : expenses) {
            String expenseName = expenseName(expense);
            String mealOverExpensesMarker = mealOverExpensesMarker(expense);
            ExpenseReportLine line = new ExpenseReportLine(expenseName, expense.amount, mealOverExpensesMarker);
            lines.add(line);
        }
        return lines;
    }

    private int mealExpenses(List<Expense> expenses) {
        int mealExpenses = 0;
        for (Expense expense : expenses) {
            if (isMeal(expense)) {
                mealExpenses += expense.amount;
            }
        }
        return mealExpenses;
    }

    private int total(List<Expense> expenses) {
        int total = 0;
        for (Expense expense : expenses) {
            total += expense.amount;
        }
        return total;
    }

    private String mealOverExpensesMarker(Expense expense) {
        return expense.type == ExpenseType.DINNER && expense.amount > 5000
            || expense.type == ExpenseType.BREAKFAST && expense.amount > 1000
            ? "X"
            : " ";
    }

    private String expenseName(Expense expense) {
        return switch (expense.type) {
            case DINNER -> "Dinner";
            case BREAKFAST -> "Breakfast";
            case CAR_RENTAL -> "Car Rental";
        };
    }

    private boolean isMeal(Expense expense) {
        return expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST;
    }

    protected Date now() {
        return new Date();
    }
}
