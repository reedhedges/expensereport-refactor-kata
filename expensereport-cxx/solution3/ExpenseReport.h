#pragma once

#include <chrono>
#include <iostream>
#include <iterator>
#include <optional>
#include <array>
#include <string_view>
#include <climits>

#define KATA_SOLUTION_3

namespace ExpenseReportKata {

enum Type
{
    BREAKFAST = 0, 
    LUNCH = 1, 
    DINNER = 2, 
    CAR_RENTAL = 3,
    MAX_TYPE_INDEX = 3
};

struct Expense
{
    Type type;
    unsigned int amount;
};

enum Category 
{
    MEAL, NONMEAL
};

namespace priv {
    // these could also be private members of Expense.
    // note order of these arrays must match order in Type enum
    constexpr static std::array<unsigned int, MAX_TYPE_INDEX+1> g_limits = { 
        1000,
        2000,
        5000,
        UINT_MAX
    };
    constexpr static std::array<std::string_view, MAX_TYPE_INDEX+1> g_names = {
        std::string_view{"Breakfast"},
        std::string_view{"Lunch"},
        std::string_view{"Dinner"},
        std::string_view{"Car Rental"}
    };
    constexpr static std::array<Category, MAX_TYPE_INDEX+1> g_categories = {
        MEAL,
        MEAL,
        MEAL,
        NONMEAL
    };
}

// note these could also be member functions of Expense:

unsigned int limit(const Expense& expense) {
    return priv::g_limits[expense.type]; 
}

bool aboveLimit(const Expense& expense) {
    return expense.amount > limit(expense);
}

bool category(const Expense& expense) {
    return priv::g_categories[expense.type];
}

std::string_view name(const Expense& expense) {
    return priv::g_names[expense.type];
}

unsigned int amount(const Expense& expense) {
    return expense.amount;
}

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

template<typename It>
void printReport(const It begin, const It end, std::optional<report_time_t> time_opt = std::nullopt)
{
    unsigned int total = 0;
    unsigned int mealExpenses = 0;

    const auto now = std::chrono::system_clock::to_time_t(time_opt ? *time_opt : std::chrono::system_clock::now());
    std::cout << "Expenses " << ctime(&now) << '\n';

    for (auto i = begin; i != end; ++i)
    {
        const auto& e = *i;
        const auto amt = amount(e);
        if (category(e) == MEAL) { // could also make generic map of category->categoryTotal.
            mealExpenses += amt;
        }
        total += amt;
        std::cout << name(e) << '\t' << amt << '\t' << (aboveLimit(e) ? 'X' : ' ') << '\n';
    }

    std::cout << "Meal expenses: " << mealExpenses << '\n';
    std::cout << "Total expenses: " << total << '\n';
}

template <typename T>
void printReport(const T& expenses, std::optional<report_time_t> time_opt = std::nullopt)
{
    printReport(cbegin(expenses), cend(expenses), time_opt);
}

} // namespace ExpenseReportKata