using System;
using System.Collections.Generic;
using System.Linq;

namespace expensereport_csharp
{
    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            int total = 0;

            WriteOutput($"Expenses {GetCurrentDate()}");

            foreach (Expense expense in expenses)
            {
                var mealOverExpensesMarker = expense.IsOverexpensed() ? "X" : " ";

                total += expense._amount;

                WriteOutput($"{expense.GetExpenseName()}\t{ expense._amount }\t{ mealOverExpensesMarker}");
            }

            WriteOutput($"Meal expenses:{expenses.Sum(e => e.GetMealExpense())}");
            WriteOutput($"Total expenses: {total}");
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}