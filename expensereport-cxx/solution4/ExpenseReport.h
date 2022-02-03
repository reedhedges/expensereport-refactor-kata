#pragma once

#include <chrono>
#include <iostream>
#include <iterator>
#include <optional>
#include <string_view>
#include <array>
#include <climits>

#define KATA_SOLUTION_4
#define KATA_SOLUTION_CONSTEXPR 

namespace ExpenseReportKata {

enum Type
{
    BREAKFAST, 
    LUNCH, 
    DINNER, 
    CAR_RENTAL
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

// note these could also be member functions of Expense:

constexpr unsigned int limit(const Expense& expense) {
    switch (expense.type) {
        case BREAKFAST:     return 1000;
        case LUNCH:         return 2000;
        case DINNER:        return 5000;
        default:            return UINT_MAX;
    }
}

constexpr bool aboveLimit(const Expense& expense) {
    return expense.amount > limit(expense);
}

constexpr bool category(const Expense& expense) {
    switch (expense.type) {
        case BREAKFAST:
        case LUNCH:
        case DINNER:
            return MEAL;
        default:    
            return NONMEAL;
    }
}

constexpr std::string_view name(const Expense& expense) {
    switch (expense.type) {
        case BREAKFAST:     return "Breakfast";
        case LUNCH:         return "Lunch";
        case DINNER:        return "Dinner";
        case CAR_RENTAL:    return "Car Rental";
    }
    return "Unknown expense type";
}

unsigned int amount(const Expense& expense) {
    return expense.amount;
}

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

// expenses must be an iterable container of Expense objects.
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
        if (category(e) == MEAL) { // could also make generic map of category->categoryTotal.
            mealExpenses += amount(e);
        }
        total += amount(e);
        std::cout << name(e) << '\t' << amount(e) << '\t' << (aboveLimit(e) ? 'X' : ' ') << '\n';
    }

    std::cout << "Meal expenses: " << mealExpenses << '\n';
    std::cout << "Total expenses: " << total << '\n';
}

template <typename T>
void printReport(const T& expenses, std::optional<report_time_t> time_opt = std::nullopt)
{
    printReport(cbegin(expenses), cend(expenses), time_opt);
}



// todo allow other containers other than array: (e.g. implement with iterators, then add wrappers for array or non-array containers)

template<size_t N>
constexpr unsigned long getTotal(const std::array<Expense, N>& expenses)
{
    return std::accumulate(expenses.cbegin(), expenses.cend(), 0UL, [](const auto sum, const Expense& next)
    {
        return sum + next.amount;
    });
}

template<size_t N>
constexpr unsigned long getCategoryTotal(const std::array<Expense, N>& expenses, Category cat)
{
    return std::accumulate(expenses.cbegin(), expenses.cend(), 0UL, [cat](const auto sum, const Expense& next)
    {
        if(category(next) == cat) return sum + next.amount;
        return sum;
    }
    );
}

template<size_t N>
constexpr unsigned long getMealsTotal(const std::array<Expense, N>& expenses)
{
    return getCategoryTotal(expenses, MEAL);
}

template<size_t N>
constexpr size_t getNumAboveLimit(const std::array<Expense, N>& expenses)
{
    return std::count_if(expenses.cbegin(), expenses.cend(), [](const Expense& next)
    {
        return aboveLimit(next);
    });
}

} // namespace ExpenseReportKata