using System;
using System.Collections.Generic;
using System.Linq;

namespace expensereport_csharp
{
    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            var expenseRepo = new ExpenseRepository(expenses);
            PrintHeader();

            PrintExpenseDetails(expenses);

            WriteOutput($"Meal expenses:{expenses.Sum(e => e.GetMealExpense())}");
            WriteOutput($"Total expenses: {expenseRepo.SumAmount()}");
        }

        private void PrintHeader() => WriteOutput($"Expenses {GetCurrentDate()}");

        private void PrintExpenseDetails(List<Expense> expenses)
            =>
            expenses.ForEach(expense => WriteOutput(ExpenseFormatter.GetExpenseDetail(expense)));

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }

    public static class ExpenseFormatter
    {
        public static string GetExpenseDetail(Expense expense) 
            => 
            $"{expense.GetExpenseName()}\t{ expense.Amount }\t{ (expense.IsOverexpensed() ? "X" : " ")}";
    }

    public class ExpenseRepository
    {
        private readonly IReadOnlyList<Expense> _expenses;

        public ExpenseRepository(IReadOnlyList<Expense> expenses) => _expenses = expenses;

        public int SumAmount() => _expenses.Sum(e => e.Amount);

    }
}