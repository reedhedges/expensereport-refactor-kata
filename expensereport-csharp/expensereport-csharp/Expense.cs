using System;

namespace expensereport_csharp
{
    public abstract class Expense
    {
        protected Expense(int amount, string name, int expenseLimit = -1)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount));

            Amount = amount;
            _name = string.IsNullOrWhiteSpace(name) ? throw new ArgumentNullException(nameof(name)) : name;
            _expenseLimit = expenseLimit;
        }

        private readonly int _expenseLimit;
        private readonly string _name;

        public int Amount { get; init; }

        public string GetExpenseName() => _name;

        public virtual int GetMealExpense() => Amount;

        public bool IsOverexpensed() => _expenseLimit >= 0 && Amount > _expenseLimit;
    }
}