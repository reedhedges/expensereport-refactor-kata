#include <chrono>
#include <iostream>
#include <iterator>
#include <list>
#include <optional>
#include <cassert>

#include "ExpenseReport.h"

using namespace ExpenseReportKata;

void ExpenseReportKata::printReport(const expense_list_t& expenses, std::optional<report_time_t> time_opt)
{
    const auto now = std::chrono::system_clock::to_time_t(time_opt ? *time_opt : std::chrono::system_clock::now());
    std::cout << "Expenses " << ctime(&now) << '\n';

    unsigned long total = 0;
    unsigned long mealExpenses = 0; // todo this could be a generic map of category -> total, with category names in category subclasses, similar to expense types.
    for (auto i = expenses.cbegin(); i != expenses.cend(); ++i) {
        const Expense& expense = **i; // i is iterator to unique_ptr
        // todo guard against overflow in total and mealExpenses
        if (expense.category() == Expense::MEAL) {
            mealExpenses += expense.amount();
        }
        total += expense.amount();
        std::cout << expense.name() << '\t' << expense.amount() << '\t' << (expense.aboveLimit() ? 'X' : ' ') << '\n';
    }

    std::cout << "Meal expenses: " << mealExpenses << '\n';
    std::cout << "Total expenses: " << total << '\n';
}
