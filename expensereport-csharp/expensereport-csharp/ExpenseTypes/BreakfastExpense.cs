namespace expensereport_csharp.ExpenseTypes
{
    public class BreakfastExpense : Expense
    {
        public BreakfastExpense(int amount) : base(amount, "Breakfast", 1000)
        {
        }
    }
}