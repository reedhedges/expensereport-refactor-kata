using ApprovalTests;
using ApprovalTests.Reporters;
using expensereport_csharp;
using expensereport_csharp.ExpenseTypes;
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

        protected override string GetCurrentDate() => "01.01.0001 00:00:00";
    }

    class BaseExpenseTest : Expense
    {
        public BaseExpenseTest(int amount, string name) : base(amount, name)
        {

        }
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
                    new BreakfastExpense (  1000),
                    new CarRentalExpense (  1),
                    new DinnerExpense (  5000),
                    new LunchExpense (2000)
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void ExpenseReport_PrintReport_EmptyList()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
            { });

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
                    new DinnerExpense ( 5001)
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void ExpenseReport_PrintReport_OverExpenseBreakfast()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new BreakfastExpense (  1001)
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void ExpenseReport_PrintReport_OverExpenseLunch()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new LunchExpense (  2001)
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void ExpenseReport_PrintReport_DoNotOverExpenseCarRental()
        {
            var report = new ExpenseReportFake();

            report.PrintReport(new List<Expense>()
                {
                    new CarRentalExpense (  int.MaxValue)
                });

            Approvals.Verify(report.Output.ToString());
        }

        [Test]
        public void Expense_NegativeAmount_Throws()
        {

            Assert.Throws<ArgumentOutOfRangeException>(() => new BaseExpenseTest (-1, "base"));
        }

        [Test]
        public void Expense_NameNull_Throws()
        {

            Assert.Throws<ArgumentNullException>(() => new BaseExpenseTest (0, null));
        }

        [Test]
        public void Expense_NameEmpty_Throws()
        {

            Assert.Throws<ArgumentNullException>(() => new BaseExpenseTest (0, ""));
        }

        [Test]
        public void Expense_NameWhiteSpace_Throws()
        {

            Assert.Throws<ArgumentNullException>(() => new BaseExpenseTest (0, "  "));
        }
    }
}
