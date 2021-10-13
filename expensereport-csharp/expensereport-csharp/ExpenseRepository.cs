using expensereport_csharp.ExpenseTypes;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace expensereport_csharp
{
    public class ExpenseRepository : IEnumerable<Expense>
    {
        private readonly IReadOnlyList<Expense> _expenses;

        public ExpenseRepository(IReadOnlyList<Expense> expenses) => _expenses = expenses;

        public IEnumerator<Expense> GetEnumerator() => _expenses.GetEnumerator();
        IEnumerator IEnumerable.GetEnumerator() => _expenses.GetEnumerator();

        public int SumAmount() => _expenses.Sum(e => e.Amount);
        public int SumMealExpense() => _expenses.Sum(e => e.GetMealExpense());

    }
}