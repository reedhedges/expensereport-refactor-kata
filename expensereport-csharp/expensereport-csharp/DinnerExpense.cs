namespace expensereport_csharp
{
    public class DinnerExpense : Expense
    {
        public DinnerExpense(int amount) : base(amount, "Dinner", 5000)
        {
        }
    }
}