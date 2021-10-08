package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

class ExpenseReportTest {

    private ApprovalUtilities au = new ApprovalUtilities();
    private ByteArrayOutputStream result;
    private ExpenseReport report;

    @BeforeEach
    void setup(){
         result = au.writeSystemOutToStringBuffer();
        report = new ExpenseReport() {
            @Override
            protected Date currentDate() {
                return new Date(0);
            }
        };

    }

    @Test
    void testPrintReport_EmptyList() {

        // Act
        report.printReport(List.of());

        // Assert
        Approvals.verify(result);

    }

    @Test
    void testPrintReport() {

        // Act
        report.printReport(List.of(getExpense(100, MealType.DINNER),
                getExpense(5001, MealType.DINNER),
                getExpense(200, MealType.BREAKFAST),
                getExpense(1001, MealType.BREAKFAST),
                getExpense(300, OtherExpensesType.CAR_RENTAL),
                getExpense(50, MealType.LUNCH),
                getExpense(2001, MealType.LUNCH)));

        // Assert
        Approvals.verify(result);

    }

    private Expense getExpense(int i, ExpenseType dinner) {
        Expense t = new Expense();
        t.amount = i;
        t.type = dinner;
        return t;
    }
}

