package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Date;

@UseReporter(IdeaReporter.class)
class ExpenseReportCharacter {
    
    @Test
    void noExpenses() {
        ByteArrayOutputStream output = printExpenses(new Expenses());
        Approvals.verify(output);
    }

    @Test
    public void manyExpenses() {
        ByteArrayOutputStream output = printExpenses(new Expenses(
            Expense.createCarRentalExpense(7),
            Expense.createBreakfastExpense(0),
            Expense.createBreakfastExpense(1),
            Expense.createBreakfastExpense(1000),
            Expense.createBreakfastExpense(1001),
            Expense.createDinnerExpense(5000),
            Expense.createDinnerExpense(5001),
            Expense.createLunchExpense(2000),
            Expense.createLunchExpense(2001)
        ));
        Approvals.verify(output);
    }

    private ByteArrayOutputStream printExpenses(Expenses expenses) {
        ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport report = new ExpenseReport() {
            @Override
            protected Date now() {
                return new Date(0);
            }
        };
        report.printReport(expenses);
        return output;
    }
}