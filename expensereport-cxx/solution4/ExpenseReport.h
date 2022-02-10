#pragma once

#include <chrono>
#include <iostream>
#include <iterator>
#include <optional>
#include <string_view>
#include <array>
#include <climits>
#include <list>

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

struct ExpenseTypeInfo {
    unsigned int limit;
    Category category;
    std::string_view type_name;
    constexpr ExpenseTypeInfo(Type type) {
        switch(type) {
            case BREAKFAST:
                limit=1000; category=MEAL; type_name="Breakfast";
                break;
            case LUNCH:
                limit=2000; category=MEAL; type_name="Lunch";
                break;
            case DINNER:
                limit=5000; category=MEAL; type_name="Dinner";
                break;
            case CAR_RENTAL:
                limit=UINT_MAX; category=NONMEAL; type_name="Car Rental";
        }
    }
};

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

using expense_list_t = std::list<Expense>;

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
        const ExpenseTypeInfo t(e.type); // should be inlined by optimizer
        if (t.category == MEAL) { // could also make generic map of category->categoryTotal.
            mealExpenses += e.amount;
        }
        total += e.amount;
        std::cout << t.type_name << '\t' << e.amount << '\t' << (e.amount > t.limit ? 'X' : ' ') << '\n';
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