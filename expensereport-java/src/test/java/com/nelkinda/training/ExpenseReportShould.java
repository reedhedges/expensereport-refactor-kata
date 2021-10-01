package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

@UseReporter(IdeaReporter.class)
class ExpenseReportCharacter {
    @Test
    void noExpenses() {
        ByteArrayOutputStream output = printExpenses(List.of());
        Approvals.verify(output);
    }

    @Test
    void carRental_500() {
        ByteArrayOutputStream output = printSingleExpense(ExpenseType.CAR_RENTAL, 500);
        Approvals.verify(output);
    }

    private ByteArrayOutputStream printSingleExpense(ExpenseType carRental, int amount) {
        Expense expense = createExpense(carRental, amount);
        return printExpenses(List.of(expense));
    }

    private Expense createExpense(ExpenseType type, int amount) {
        Expense expense = new Expense();
        expense.type = type;
        expense.amount = amount;
        return expense;
    }

    private ByteArrayOutputStream printExpenses(List<Expense> expenses) {
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