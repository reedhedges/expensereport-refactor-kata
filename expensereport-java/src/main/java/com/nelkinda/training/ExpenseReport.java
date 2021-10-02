package com.nelkinda.training;

import java.util.ArrayList;
import java.util.Collection;
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

    public Expenses() {
    }

    public Expenses(Collection<? extends Expense> c) {
        super(c);
    }

    int mealExpenses() {
        int mealExpenses = 0;
        for (Expense expense : this) {
            if (expense.isMeal()) {
                mealExpenses += expense.amount;
            }
        }
        return mealExpenses;
    }

    int total() {
        int total = 0;
        for (Expense expense : this) {
            total += expense.amount;
        }
        return total;
    }

    List<ReportLineData> reportLines() {
        List<ReportLineData> lines = new ArrayList<>();
        for (Expense expense : this) {
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
        List<ReportLineData> lines = expenses.reportLines();
        int mealExpenses = expenses.mealExpenses();
        int total = expenses.total();
        ReportData reportData = new ReportData(lines, mealExpenses, total);

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
