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
    }

    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base (ExpenseType.DINNER, amount)
        {

        }

    }

    public class BreakfastExpense : Expense
    {
        public BreakfastExpense(int amount) : base(ExpenseType.BREAKFAST, amount)
        {
        }
    }

    public class CarRentalExpense : Expense
    {
        public CarRentalExpense(int amount) : base(ExpenseType.CAR_RENTAL, amount)
        {
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
                if (expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST)
                {
                    mealExpenses += expense.amount;
                }

                String expenseName = "";
                switch (expense.type)
                {
                    case ExpenseType.DINNER:
                        expenseName = "Dinner";
                        break;
                    case ExpenseType.BREAKFAST:
                        expenseName = "Breakfast";
                        break;
                    case ExpenseType.CAR_RENTAL:
                        expenseName = "Car Rental";
                        break;
                }

                String mealOverExpensesMarker =
                    expense.type == ExpenseType.DINNER && expense.amount > 5000 ||
                    expense.type == ExpenseType.BREAKFAST && expense.amount > 1000
                        ? "X"
                        : " ";

                WriteOutput(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

                total += expense.amount;
            }

            WriteOutput(mealExpenses.ToString());
            WriteOutput("Total expenses: " + total);
        }

        protected virtual void WriteOutput(string message) => Console.WriteLine(message);
        protected virtual string GetCurrentDate() => DateTime.Now.ToString();
    }
}