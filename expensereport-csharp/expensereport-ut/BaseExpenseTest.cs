using expensereport_csharp.ExpenseTypes;

namespace Tests
{
    class BaseExpenseTest : Expense
    {
        public BaseExpenseTest(int amount, string name) : base(amount, name)
        {

        }
    }
}
