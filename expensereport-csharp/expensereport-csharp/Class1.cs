 using System;
using System.Collections.Generic;
using System.Text;

namespace expensereport_csharp
{
    public abstract class Expense
    {
        protected Expense( int amount)
        {
            this.amount = amount;
        }

        public int amount;

        public abstract string getExpenseName();

        public virtual int getMealExpense() {
            return amount;
        }

        public virtual bool isOverexpensed()
        {
            return false;
        }

    }

    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base (amount)
        {

        }

        public override string getExpenseName() {
            return "Dinner";
        }

        public override bool isOverexpensed() => this.amount > 5000;

    }

    public class BreakfastExpense : Expense
    {
        public BreakfastExpense(int amount) : base(amount)
        {
        }

        public override string getExpenseName() {
            return "Breakfast";
        }

        public override bool isOverexpensed() => this.amount > 1000;
    }

    public class LunchExpense : Expense
    {
        public LunchExpense(int amount) : base(amount)
        {
        }

        public override string getExpenseName() {
            return "Lunch";
        }

        public override bool isOverexpensed() => this.amount > 2000;
    }

    public class CarRentalExpense : Expense
    {
        public CarRentalExpense(int amount) : base(amount)
        {
        }

        public override string getExpenseName() {
            return "Car Rental";
        }

        public override int getMealExpense() {
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