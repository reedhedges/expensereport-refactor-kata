package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
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

    @Test
    public void printDinnerOverExpense() {
        expenseReport.printReport(Collections.singletonList(createExpense(ExpenseType.DINNER, 5100)), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printBreakfastOverExpense() {
        expenseReport.printReport(Collections.singletonList(createExpense(ExpenseType.BREAKFAST, 2000)), new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printDinnerAndBreakfast() {
        expenseReport.printReport(Arrays.asList(createExpense(ExpenseType.DINNER, 5100),
                createExpense(ExpenseType.BREAKFAST, 20)),
                new Date(0));

        Approvals.verify(outputStream.toString());
    }

    @Test
    public void printDinnerAndBreakfastAndCarRental() {
        expenseReport.printReport(Arrays.asList(createExpense(ExpenseType.DINNER, 5100),
                createExpense(ExpenseType.BREAKFAST, 20),
                createExpense(ExpenseType.DINNER, 50),
                createExpense(ExpenseType.CAR_RENTAL, 33234))
                , new Date(0));

        Approvals.verify(outputStream.toString());
    }

    private Expense createExpense(ExpenseType expenseType, int amount) {
        Expense expense = new Expense();
        expense.type = expenseType;
        expense.amount = amount;
        return expense;
    }

}
