package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Map;
import java.util.function.IntFunction;

@UseReporter(IdeaReporter.class)
class ExpenseReportCharacter {
    
    private Map<String, IntFunction<Expense>> factories = Map.of(
        "DINNER", Expense::createDinnerExpense,
        "BREAKFAST", Expense::createBreakfastExpense,
        "CAR_RENTAL", Expense::createCarRentalExpense
    );

    @Test
    void noExpenses() {
        ByteArrayOutputStream output = printExpenses(new Expenses());
        Approvals.verify(output);
    }

    @Test
    void singleExpenses() {
        CombinationApprovals.verifyAllCombinations(
            this::printSingleExpense,
            new String[]{"DINNER", "BREAKFAST", "CAR_RENTAL"},
            new Integer[]{-1, 0, 500, 999, 1000, 1001, 4999, 5000, 5001, 10000}
        );
    }

    @Test
    public void manyExpenses() {
        ByteArrayOutputStream output = printExpenses(new Expenses(
            Expense.createCarRentalExpense(1000),
            Expense.createBreakfastExpense(3000)
        ));
        Approvals.verify(output);
    }

    private ByteArrayOutputStream printSingleExpense(String factory, int amount) {
        IntFunction<Expense> expenseFactory = factories.get(factory);
        Expense expense = expenseFactory.apply(amount);
        return printExpenses(new Expenses(expense));
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