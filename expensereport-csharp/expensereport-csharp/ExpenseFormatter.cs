using expensereport_csharp.ExpenseTypes;

namespace expensereport_csharp
{
    public static class ExpenseFormatter
    {
        public static string GetExpenseDetail(Expense expense) 
            => 
            $"{expense.GetExpenseName()}\t{ expense.Amount }\t{ (expense.IsOverexpensed() ? "X" : " ")}";
    }
}