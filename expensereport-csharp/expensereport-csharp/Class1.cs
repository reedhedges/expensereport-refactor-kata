using System;
using System.Collections.Generic;
using System.Text;

namespace expensereport_csharp
{
    public abstract class Expense
    {
        protected Expense(int amount, string name, int expenseLimit = -1)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount));

            this.amount = amount;
            this.name = String.IsNullOrWhiteSpace(name) ? throw new ArgumentNullException(nameof(name)) : name;
            this.expenseLimit = expenseLimit;
        }

        public int amount;
        private readonly int expenseLimit;
        private readonly string name;

        public string getExpenseName() => this.name;

        public virtual int getMealExpense() => amount;

        public bool isOverexpensed() => expenseLimit >= 0 ? this.amount > this.expenseLimit : false;
    }

    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base(amount, "Dinner", 5000)
        {

        }
    }

    public class BreakfastExpense : Expense
    {
        public BreakfastExpense(int amount) : base(amount,"Breakfast", 1000)
        {
        }
    }

    public class LunchExpense : Expense
    {
        public LunchExpense(int amount) : base(amount, "Lunch", 2000)
        {
        }
    }

    public class CarRentalExpense : Expense
    {
        public CarRentalExpense(int amount) : base(amount, "Car Rental")
        {
        }

        public override int getMealExpense() => 0;
    }

    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            int total = 0;
            int mealExpenses = 0;

            WriteOutput($"Expenses {GetCurrentDate()}");

            foreach (Expense expense in expenses)
            {
                var mealOverExpensesMarker = expense.isOverexpensed() ? "X" : " ";

                mealExpenses += expense.getMealExpense();
                total += expense.amount;

                WriteOutput($"{expense.getExpenseName()}\t{ expense.amount }\t{ mealOverExpensesMarker}");
            }

            WriteOutput($"Meal expenses:{mealExpenses}");
            WriteOutput($"Total expenses: {total}");
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}