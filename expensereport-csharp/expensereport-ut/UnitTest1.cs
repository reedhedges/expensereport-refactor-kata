using ApprovalTests;
using ApprovalTests.Reporters;
using expensereport_csharp;
using NUnit.Framework;
using System.Collections.Generic;
using System.Text;

namespace Tests
{

    class ExpenseReportFake : ExpenseReport
    {
        public StringBuilder Output { get; } = new StringBuilder();

        protected override void WriteOutput(string message) =>
            Output.AppendLine(message);
    }

    [UseReporter(typeof(DiffReporter))]

    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void ExpenseReport_PrintReport_OutPutNItems()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new Expense { amount = 1, type = ExpenseType.BREAKFAST },
                    new Expense { amount = 1, type = ExpenseType.CAR_RENTAL },
                    new Expense { amount = 1, type = ExpenseType.DINNER }
                });

            Approvals.Verify(report.Output.ToString());
        }
    }
}