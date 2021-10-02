package com.nelkinda.training;

import java.util.Date;

public class ExpenseReport {
    public void printReport(Expenses expenses) {
        ReportData reportData = expenses.asData();

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
