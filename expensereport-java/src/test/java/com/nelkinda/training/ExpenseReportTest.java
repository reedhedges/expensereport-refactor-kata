package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExpenseReportTest {

    @Test
    public void printEmptyReport() {
        ByteArrayOutputStream outputStream = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport expenseReport = new ExpenseReport();

        expenseReport.printReport(Collections.emptyList(), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printDinner() {
        ByteArrayOutputStream outputStream = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport expenseReport = new ExpenseReport();
        Expense expense = new Expense();
        expense.type = ExpenseType.DINNER;
        expense.amount = 10;
        List<Expense> expenses = Collections.singletonList(expense);

        expenseReport.printReport(expenses, new Date(0));

        Approvals.verify(outputStream.toString());
    }
}
