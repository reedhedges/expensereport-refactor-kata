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
        expenseReport.printReport(Collections.singletonList(createExpense(ExpenseType.DINNER, 10)), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printBreakfast() {
        expenseReport.printReport(Collections.singletonList(createExpense(ExpenseType.BREAKFAST, 10)), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printCarRental() {
        expenseReport.printReport(Collections.singletonList(createExpense(ExpenseType.CAR_RENTAL, 10)), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    private Expense createExpense(ExpenseType expenseType, int amount) {
        Expense expense = new Expense();
        expense.type = expenseType;
        expense.amount = amount;
        return expense;
    }

}
