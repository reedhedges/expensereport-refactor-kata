using ApprovalTests;
using ApprovalTests.Reporters;
using expensereport_csharp;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Text;

namespace Tests
{

    class ExpenseReportFake : ExpenseReport
    {
        public StringBuilder Output { get; } = new StringBuilder();

        protected override void WriteOutput(string message) =>
            Output.AppendLine(message);

        protected override DateTime GetCurrentDate() => DateTime.MinValue;
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

        [Test]
        public void ExpenseReport_PrintReport_EmptyList()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {});

            Approvals.Verify(report.Output.ToString());
        }

/*
        [Test]
        public void ExpenseReport_PrintReport_NullList()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(null);

            Approvals.Verify(report.Output.ToString());
        }
        */

        [Test]
        public void ExpenseReport_PrintReport_OverExpenseDinner()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new Expense { amount = 5001, type = ExpenseType.DINNER }
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void ExpenseReport_PrintReport_OverExpenseBreakfast()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new Expense { amount = 1001, type = ExpenseType.BREAKFAST }
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void ExpenseReport_PrintReport_DoNotOverExpenseCarRental()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new Expense { amount = int.MaxValue, type = ExpenseType.CAR_RENTAL }
                });

            Approvals.Verify(report.Output.ToString());
        }
    }


}
