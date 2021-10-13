using System;
using System.Collections.Generic;

namespace expensereport_csharp
{
    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            var expenseRepo = new ExpenseRepository(expenses);

            PrintHeader();
            PrintExpenseDetails(expenseRepo);
            PrintTotals(expenseRepo);
        }

        private void PrintTotals(ExpenseRepository expenseRepo)
        {
            WriteOutput($"Meal expenses:{expenseRepo.SumMealExpense()}");
            WriteOutput($"Total expenses: {expenseRepo.SumAmount()}");
        }

        private void PrintHeader() => WriteOutput($"Expenses {GetCurrentDate()}");

        private void PrintExpenseDetails(IEnumerable<Expense> expenses)
        {
            foreach (var exp in expenses)
            {
               WriteOutput(ExpenseFormatter.GetExpenseDetail(exp));
            }
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}