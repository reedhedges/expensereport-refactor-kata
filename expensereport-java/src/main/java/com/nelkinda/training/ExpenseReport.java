package com.nelkinda.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public static Expense createExpense(ExpenseType type, int amount) {
        Expense expense = new Expense();
        expense.type = type;
        expense.amount = amount;
        return expense;
    }

    boolean isMeal() {
        return type == ExpenseType.DINNER || type == ExpenseType.BREAKFAST;
    }

    public boolean isMealOverExpenses() {
        return type == ExpenseType.DINNER && amount > 5000
            || type == ExpenseType.BREAKFAST && amount > 1000;
    }

    String expenseName() {
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
            String mealOverExpensesMarker = expense.isMealOverExpenses() ? "X" : " ";
            ReportLineData line = new ReportLineData(expenseName, expense.amount, expense.isMealOverExpenses());
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
            System.out.println(
                line.getExpenseName() + "\t" +
                    line.getAmount() + "\t" +
                    (line.isMealOverExpenses() ? "X" : " ")
            );
        }
        System.out.println("Meal expenses: " + reportData.getMealExpenses());
        System.out.println("Total expenses: " + reportData.getTotal());
    }

    protected Date now() {
        return new Date();
    }
}
