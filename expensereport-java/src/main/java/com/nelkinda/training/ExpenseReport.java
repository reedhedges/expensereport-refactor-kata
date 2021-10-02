package com.nelkinda.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

class Expense {
    ExpenseType type;
    int amount;

    boolean isMeal() {
        return type == ExpenseType.DINNER || type == ExpenseType.BREAKFAST;
    }

    String mealOverExpensesMarker() {
        return type == ExpenseType.DINNER && amount > 5000
            || type == ExpenseType.BREAKFAST && amount > 1000
            ? "X"
            : " ";
    }

    String expenseName() {
        return switch (type) {
            case DINNER -> "Dinner";
            case BREAKFAST -> "Breakfast";
            case CAR_RENTAL -> "Car Rental";
        };
    }
}

class Expenses extends ArrayList<Expense> {

    private final List<Expense> expenses;
    
    public Expenses(Expense... expenses) {
        this.expenses = Arrays.asList(expenses);
    }

    ReportData reportData() {
        List<ReportLineData> lines = reportLines();
        int mealExpenses = mealExpenses();
        int total = total();
        return new ReportData(lines, mealExpenses, total);
    }

    int mealExpenses() {
        int mealExpenses = 0;
        for (Expense expense : this.expenses) {
            if (expense.isMeal()) {
                mealExpenses += expense.amount;
            }
        }
        return mealExpenses;
    }

    int total() {
        int total = 0;
        for (Expense expense : this.expenses) {
            total += expense.amount;
        }
        return total;
    }

    List<ReportLineData> reportLines() {
        List<ReportLineData> lines = new ArrayList<>();
        for (Expense expense : this.expenses) {
            String expenseName = expense.expenseName();
            String mealOverExpensesMarker = expense.mealOverExpensesMarker();
            ReportLineData line = new ReportLineData(expenseName, expense.amount, mealOverExpensesMarker);
            lines.add(line);
        }
        return lines;
    }
}

public class ExpenseReport {
    public void printReport(Expenses expenses) {
        ReportData reportData = expenses.reportData();

        print(reportData);
    }

    private void print(ReportData reportData) {
        System.out.println("Expenses " + now());
        for (ReportLineData line : reportData.getLines()) {
            System.out.println(line.getExpenseName() + "\t" + line.getAmount() + "\t" + line.getMealOverExpensesMarker());
        }
        System.out.println("Meal expenses: " + reportData.getMealExpenses());
        System.out.println("Total expenses: " + reportData.getTotal());
    }

    protected Date now() {
        return new Date();
    }
}
