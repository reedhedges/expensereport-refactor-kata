using System;
using System.Collections.Generic;
using System.Text;

namespace expensereport_csharp
{
    public abstract class Expense
    {
        protected Expense(int amount, int expenseLimit = -1)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount));

            this.amount = amount;
            this.expenseLimit = expenseLimit;
        }

        public int amount;
        private readonly int expenseLimit;

        public abstract string getExpenseName();

        public virtual int getMealExpense()
        {
            return amount;
        }

        public bool isOverexpensed() => expenseLimit >= 0 ? this.amount > this.expenseLimit : false;
    }

    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base(amount, 5000)
        {

        }

        public override string getExpenseName()
        {
            return "Dinner";
        }
    }

    public class BreakfastExpense : Expense
    {
        public BreakfastExpense(int amount) : base(amount, 1000)
        {
        }

        public override string getExpenseName()
        {
            return "Breakfast";
        }
    }

    public class LunchExpense : Expense
    {
        public LunchExpense(int amount) : base(amount, 2000)
        {
        }

        public override string getExpenseName()
        {
            return "Lunch";
        }
    }

    public class CarRentalExpense : Expense
    {
        public CarRentalExpense(int amount) : base(amount)
        {
        }

        public override string getExpenseName()
        {
            return "Car Rental";
        }

        public override int getMealExpense()
        {
            return 0;
        }
    }

    public class ExpenseReport
    {
        public void PrintReport(List<Expense> expenses)
        {
            int total = 0;
            int mealExpenses = 0;

            WriteOutput("Expenses " + GetCurrentDate());

            foreach (Expense expense in expenses)
            {
                mealExpenses += expense.getMealExpense();

                String mealOverExpensesMarker = expense.isOverexpensed() ? "X" : " ";

                WriteOutput($"{expense.getExpenseName()}\t{ expense.amount }\t{ mealOverExpensesMarker}");

                total += expense.amount;
            }

            WriteOutput($"Meal expenses:{mealExpenses}");
            WriteOutput($"Total expenses: {total}");
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}