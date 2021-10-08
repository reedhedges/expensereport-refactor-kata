using expensereport_csharp;
using NUnit.Framework;
using System.Collections.Generic;

namespace Tests
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void ExpenseReport_PrintReport_OutPutNItems()
        {
            var report = new ExpenseReport();

            report.PrintReport(new List<Expense>()
                {
                    new Expense { amount = 1, type = ExpenseType.BREAKFAST },
                    new Expense { amount = 1, type = ExpenseType.CAR_RENTAL },
                    new Expense { amount = 1, type = ExpenseType.DINNER }
                });

            
            Assert.Pass();
        }
    }
}