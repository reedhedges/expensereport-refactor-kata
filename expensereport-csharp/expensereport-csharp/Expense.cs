using System;

namespace expensereport_csharp
{
    public abstract class Expense
    {
        protected Expense(int amount, string name, int expenseLimit = -1)
        {
            if (amount < 0) throw new ArgumentOutOfRangeException(nameof(amount));

            _amount = amount;
            _name = string.IsNullOrWhiteSpace(name) ? throw new ArgumentNullException(nameof(name)) : name;
            _expenseLimit = expenseLimit;
        }

        public int _amount;
        private readonly int _expenseLimit;
        private readonly string _name;

        public string GetExpenseName() => this._name;

        public virtual int GetMealExpense() => _amount;

        public bool IsOverexpensed() => _expenseLimit >= 0 && this._amount > this._expenseLimit;
    }
}