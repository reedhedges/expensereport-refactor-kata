 using System;
using System.Collections.Generic;
using System.Text;

namespace expensereport_csharp
{
    public enum ExpenseType
    {
        DINNER, BREAKFAST, CAR_RENTAL
    }


    public abstract class Expense
    {
        protected Expense(ExpenseType type, int amount)
        {
            this.type = type;
            this.amount = amount;
        }

        public ExpenseType type;
        public int amount;

        public abstract string getExpenseName();

        public virtual int getMealExpense() {
            return amount;
        }
    }

    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base (ExpenseType.DINNER, amount)
        {

        }

        public override string getExpenseName() {
            return "Dinner";
        }

    }

    public class BreakfastExpense : Expense
    {
        public BreakfastExpense(int amount) : base(ExpenseType.BREAKFAST, amount)
        {
        }

        public override string getExpenseName() {
            return "Breakfast";
        }
    }

    public class CarRentalExpense : Expense
    {
        public CarRentalExpense(int amount) : base(ExpenseType.CAR_RENTAL, amount)
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

                String mealOverExpensesMarker =
                    expense.type == ExpenseType.DINNER && expense.amount > 5000 ||
                    expense.type == ExpenseType.BREAKFAST && expense.amount > 1000
                        ? "X"
                        : " ";

                WriteOutput(expense.getExpenseName() + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

                total += expense.amount;
            }

            WriteOutput(mealExpenses.ToString());
            WriteOutput("Total expenses: " + total);
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}