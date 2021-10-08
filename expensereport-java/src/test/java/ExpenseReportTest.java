import com.nelkinda.training.ExpenseReport;
import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Date;

public class ExpenseReportTest {
    @Test
    public void printEmptyReport() {
        ByteArrayOutputStream outputStream = new ApprovalUtilities().writeSystemOutToStringBuffer();
        ExpenseReport expenseReport = new ExpenseReport();

        expenseReport.printReport(Collections.emptyList(), new Date(0));

        Approvals.verify(outputStream.toString());
    }
}
