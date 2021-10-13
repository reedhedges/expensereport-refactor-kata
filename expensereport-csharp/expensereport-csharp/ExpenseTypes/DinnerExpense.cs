namespace expensereport_csharp.ExpenseTypes
{
    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base(amount, "Dinner", 5000)
        {
        }
    }
}