using ApprovalTests;
using ApprovalTests.Reporters;
using expensereport_csharp.ExpenseTypes;
using NUnit.Framework;
using System;
using System.Collections.Generic;

namespace Tests
{
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
            => 
            _ = Assert.Throws<ArgumentOutOfRangeException>(() => new BaseExpenseTest(-1, "base"));

        [Test]
        public void Expense_NameNull_Throws() 
            => 
            _ = Assert.Throws<ArgumentNullException>(() => new BaseExpenseTest(0, null));

        [Test]
        public void Expense_NameEmpty_Throws() 
            => 
            _ = Assert.Throws<ArgumentNullException>(() => new BaseExpenseTest(0, ""));

        [Test]
        public void Expense_NameWhiteSpace_Throws() 
            => 
            _ = Assert.Throws<ArgumentNullException>(() => new BaseExpenseTest(0, "  "));
    }
}
