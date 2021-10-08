package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ExpenseReportTest {

    @Test
    void testPrintReport_EmptyList() {

        // Arrange
        ApprovalUtilities au = new ApprovalUtilities();
        ByteArrayOutputStream result = au.writeSystemOutToStringBuffer();
        ExpenseReport report = new ExpenseReport() {
            @Override
            protected Date currentDate() {
                return new Date(0);
            }
        };

        // Act
        report.printReport(List.of());

        // Assert
        Approvals.verify(result);

    }

    @Test
    void testPrintReport() {

        // Arrange
        ApprovalUtilities au = new ApprovalUtilities();
        ByteArrayOutputStream result = au.writeSystemOutToStringBuffer();
        ExpenseReport report = new ExpenseReport() {
            @Override
            protected Date currentDate() {
                return new Date(0);
            }
        };

        // Act
        Expense t = getExpense(100, ExpenseType.DINNER);

        Expense t2 = getExpense(200, ExpenseType.BREAKFAST);

        Expense t3 = getExpense(300, ExpenseType.CAR_RENTAL);

        report.printReport(List.of(t,t2, t3));

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

