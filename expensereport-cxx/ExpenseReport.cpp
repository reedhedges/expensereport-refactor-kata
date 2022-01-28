#include <chrono>
#include <iostream>
#include <iterator>
#include <list>

#include "ExpenseReport.h"

using namespace ExpenseReportKata;

void printReport(std::list<Expense> expenses)
{
    int total = 0;
    int mealExpenses = 0;

    auto now = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
    std::cout << "Expenses " << ctime(&now) << '\n';

    for (std::list<Expense>::iterator expense = expenses.begin(); expense != expenses.end(); ++expense) {
        if (expense->type == BREAKFAST || expense->type == DINNER) {
            mealExpenses += expense->amount;
        }

        std::string expenseName = "";
        switch (expense->type) {
        case DINNER:
            expenseName = "Dinner";
            break;
        case BREAKFAST:
            expenseName = "Breakfast";
            break;
        case CAR_RENTAL:
            expenseName = "Car Rental";
            break;
        }

        std::string mealOverExpensesMarker = (expense->type == DINNER && expense->amount > 5000) || (expense->type == BREAKFAST && expense->amount > 1000) ? "X" : " ";

        std::cout << expenseName << '\t' << expense->amount << '\t' << mealOverExpensesMarker << '\n';

        total += expense->amount;
    }

    std::cout << "Meal expenses: " << mealExpenses << '\n';
    std::cout << "Total expenses: " << total << '\n';
}
