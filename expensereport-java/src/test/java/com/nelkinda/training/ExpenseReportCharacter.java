package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
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
    void singleExpenses() {
        CombinationApprovals.verifyAllCombinations(
            this::printSingleExpense,
            Expense.ExpenseType.values(),
            new Integer[]{-1, 0, 500, 999, 1000, 1001, 4999, 5000, 5001, 10000}
        );
    }

    @Test
    public void manyExpenses() {
        ByteArrayOutputStream output = printExpenses(new Expenses(
            createExpense(Expense.ExpenseType.CAR_RENTAL, 1000),
            createExpense(Expense.ExpenseType.BREAKFAST, 3000)
        ));
        Approvals.verify(output);
    }

    private ByteArrayOutputStream printSingleExpense(Expense.ExpenseType carRental, int amount) {
        Expense expense = createExpense(carRental, amount);
        return printExpenses(new Expenses(expense));
    }

    private Expense createExpense(Expense.ExpenseType type, int amount) {
        Expense expense = new Expense();
        expense.type = type;
        expense.amount = amount;
        return expense;
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