namespace expensereport_csharp
{
    public class CarRentalExpense : Expense
    {
        public CarRentalExpense(int amount) : base(amount, "Car Rental")
        {
        }

        public override int GetMealExpense() => 0;
    }
}