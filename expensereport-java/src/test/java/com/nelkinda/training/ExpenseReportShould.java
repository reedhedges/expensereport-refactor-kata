package com.nelkinda.training;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;

@UseReporter(IdeaReporter.class)
class ExpenseReportShould {
    @Test
    void name() {
        ByteArrayOutputStream outputStream = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport report = new ExpenseReport();
        report.printReport(List.of());
        Approvals.verify(outputStream);
    }
}