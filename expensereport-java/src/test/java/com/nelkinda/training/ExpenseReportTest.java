package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class ExpenseReportTest {
    @Test
    void empty_report() {
        ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport report = new ExpenseReport();
        report.printReport(Collections.emptyList(), new Date(0));
        Approvals.verify(output);
    }

    @Test
    void big_report() {
        ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport report = new ExpenseReport();
        List<Expense> expenses = List.of(
            createExpense(ExpenseType.DINNER, 5000),
            createExpense(ExpenseType.DINNER, 5001),
            createExpense(ExpenseType.BREAKFAST, 1000),
            createExpense(ExpenseType.BREAKFAST, 1001),
            createExpense(ExpenseType.CAR_RENTAL, 50),
            createExpense(ExpenseType.CAR_RENTAL, Integer.MAX_VALUE)
        );
        report.printReport(expenses, new Date(0));
        Approvals.verify(output);
    }

    private Expense createExpense(ExpenseType type, int amount) {
        Expense expense = new Expense();
        expense.type = type;
        expense.amount = amount;
        return expense;
    }
}