using System;
using System.Collections.Generic;

namespace expensereport_csharp
{
    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            int total = 0;
            int mealExpenses = 0;

            WriteOutput($"Expenses {GetCurrentDate()}");

            foreach (Expense expense in expenses)
            {
                var mealOverExpensesMarker = expense.IsOverexpensed() ? "X" : " ";

                mealExpenses += expense.GetMealExpense();
                total += expense._amount;

                WriteOutput($"{expense.GetExpenseName()}\t{ expense._amount }\t{ mealOverExpensesMarker}");
            }

            WriteOutput($"Meal expenses:{mealExpenses}");
            WriteOutput($"Total expenses: {total}");
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}