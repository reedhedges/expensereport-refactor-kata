package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Date;

public class ExpenseReportTest {

    private final ByteArrayOutputStream outputStream = new ApprovalUtilities().writeSystemOutToStringBuffer();
    private final ExpenseReport expenseReport = new ExpenseReport();

    @Test
    public void printEmptyReport() {
        expenseReport.printReport(Collections.emptyList(), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printDinner() {
        expenseReport.printReport(Collections.singletonList(createDinnerExpense()), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    private Expense createDinnerExpense() {
        Expense expense = new Expense();
        expense.type = ExpenseType.DINNER;
        expense.amount = 10;
        return expense;
    }
}
