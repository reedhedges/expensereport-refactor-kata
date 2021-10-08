package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
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
}