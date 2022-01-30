#include <chrono>
#include <iostream>
#include <iterator>
#include <list>
#include <optional>

#include "ExpenseReport.h"

using namespace ExpenseReportKata;


bool ExpenseReportKata::printReport(const std::list<Expense>& expenses, std::optional<report_time_t> time_opt)
{
    time_t now = 0;
    if(time_opt) now = std::chrono::system_clock::to_time_t(*time_opt);

    std::cout << "Expenses " << ctime(&now) << '\n';

    unsigned long total = 0;
    unsigned long mealExpenses = 0;

    for (const auto& expense : expenses) {
        // todo guard against overflowing total or mealExpenses
        if (expense.category() == MEAL) {
            mealExpenses += expense.amount();
        }
        total += expense.amount();
        std::cout << expense.name() << '\t' << expense.amount() << '\t' << (expense.aboveLimit() ? 'X' : ' ') << '\n';
    }

    std::cout << "Meal expenses: " << mealExpenses << '\n';
    std::cout << "Total expenses: " << total << '\n';

    return true;
}
