package com.nelkinda.training;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Expenses {

    private final List<Expense> expenses;

    public Expenses(Expense... expenses) {
        this.expenses = Arrays.asList(expenses);
    }

    public ReportData asData() {
        List<ReportLineData> lines = this.reportLines();
        int mealExpenses = this.mealExpenses();
        int total = this.total();
        return new ReportData(lines, mealExpenses, total);
    }

    int mealExpenses() {
        return this.expenses.stream()
            .filter(Expense::isMeal)
            .mapToInt(expense -> expense.amount)
            .sum();
    }

    int total() {
        return this.expenses.stream()
            .mapToInt(expense -> expense.amount)
            .sum();
    }

    List<ReportLineData> reportLines() {
        return this.expenses.stream()
            .map(Expense::asData)
            .collect(Collectors.toList());
    }

}
