#pragma once

#include <chrono>
#include <iostream>
#include <iterator>
#include <optional>
#include <string_view>
#include <variant>
#include <climits>
#include <list>

#define KATA_SOLUTION_5
#define KATA_SOLUTION_CONSTEXPR 

namespace ExpenseReportKata {

enum Category 
{
    MEAL, NONMEAL
};

struct ExpenseBase
{
    unsigned int m_amount;
    constexpr unsigned int amount() const { return m_amount; }
    constexpr unsigned int limit() const { return UINT_MAX; }
    constexpr Category category() const { return NONMEAL; }
};

struct BreakfastExpense : ExpenseBase {
    std::string_view name() const { return "Breakfast"; }
    constexpr unsigned int limit() const { return 1000; }
    constexpr Category category() const { return MEAL; }
};
struct LunchExpense : ExpenseBase {
    std::string_view name() const { return "Lunch"; }
    constexpr unsigned int limit() const { return 2000; }
    constexpr Category category() const { return MEAL; }
};
struct DinnerExpense : ExpenseBase {
    std::string_view name() const { return "Dinner"; }
    constexpr unsigned int limit() const { return 5000; }
    constexpr Category category() const { return MEAL; }
};
struct CarRentalExpense : ExpenseBase {
    std::string_view name() const { return "Car Rental"; }
};

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

using Expense = std::variant<BreakfastExpense, LunchExpense, DinnerExpense, CarRentalExpense>;

using expense_list_t = std::list<Expense>;

// expenses must be an iterable container of Expense type objects
template<typename It>
void printReport(const It begin, const It end, std::optional<report_time_t> time_opt = std::nullopt)
{
    unsigned int total = 0;
    unsigned int mealExpenses = 0;

    const auto now = std::chrono::system_clock::to_time_t(time_opt ? *time_opt : std::chrono::system_clock::now());
    std::cout << "Expenses " << ctime(&now) << '\n';

    for (auto i = begin; i != end; ++i)
    {
        const Expense& ev = *i;
        bool visited = false;
        std::visit([&](auto& e){
            assert(!visited); // for std::variant, I think std::visit should only end up choosing one type, but just verifying for now
            visited = true;
            if (e.category() == MEAL) { // could also make generic map of category->categoryTotal.
                mealExpenses += e.amount();
            }
            total += e.amount();
            std::cout << e.name() << '\t' << e.amount() << '\t' << (e.amount() > e.limit() ? 'X' : ' ') << '\n';
        },
        ev);
        
    }

    std::cout << "Meal expenses: " << mealExpenses << '\n';
    std::cout << "Total expenses: " << total << '\n';
}

template <typename T>
void printReport(const T& expenses, std::optional<report_time_t> time_opt = std::nullopt)
{
    printReport(cbegin(expenses), cend(expenses), time_opt);
}

// simplify std::visit calls:
constexpr unsigned int amount(const Expense& ev) {
    
    return std::visit([](auto& e) -> unsigned int { return e.amount(); }, ev);
}
constexpr std::string_view name(const Expense& ev) {
    return std::visit([](auto& e) -> std::string_view { return e.name(); }, ev);
}
constexpr unsigned int limit(const Expense& ev) {
    return std::visit([](auto& e) -> unsigned int { return e.limit(); }, ev);
}
constexpr Category category(const Expense& ev) {
    return std::visit([](auto& e) -> Category { return e.category(); }, ev);
}

// todo allow other containers other than array: (e.g. implement with iterators, then add wrappers for array or non-array containers)

template<size_t N>
constexpr unsigned long getTotal(const std::array<Expense, N>& expenses)
{
    return std::accumulate(expenses.cbegin(), expenses.cend(), 0UL, [](const auto sum, const Expense& next) -> unsigned int
    {
        return sum + amount(next);
    });
}

template<size_t N>
constexpr unsigned long getCategoryTotal(const std::array<Expense, N>& expenses, Category cat)
{
    return std::accumulate(expenses.cbegin(), expenses.cend(), 0UL, [cat](const auto sum, const Expense& next) -> unsigned int
    {
        if(category(next) == cat) return sum + amount(next);
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
    return std::count_if(expenses.cbegin(), expenses.cend(), [](auto& next) -> bool
    {
        return (amount(next) > limit(next));
    });
}

} // namespace ExpenseReportKata