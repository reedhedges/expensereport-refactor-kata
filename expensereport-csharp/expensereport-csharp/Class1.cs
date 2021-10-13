using System;
using System.Collections.Generic;
using System.Linq;

namespace expensereport_csharp
{
    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            WriteOutput($"Expenses {GetCurrentDate()}");

            foreach (Expense expense in expenses)
            {
                WriteOutput($"{expense.GetExpenseName()}\t{ expense.Amount }\t{ (expense.IsOverexpensed() ? "X" : " ")}");
            }

            WriteOutput($"Meal expenses:{expenses.Sum(e => e.GetMealExpense())}");
            WriteOutput($"Total expenses: {expenses.Sum(e => e.Amount)}");
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}