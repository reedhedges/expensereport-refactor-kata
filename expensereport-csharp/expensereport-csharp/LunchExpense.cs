namespace expensereport_csharp
{
    public class LunchExpense : Expense
    {
        public LunchExpense(int amount) : base(amount, "Lunch", 2000)
        {
        }
    }
}