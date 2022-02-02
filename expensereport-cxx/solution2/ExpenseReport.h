#pragma once

#include <chrono>
#include <iterator>
#include <optional>
#include <array>
#include <climits>
#include <string>
#include <string_view>
#include <algorithm>
#include <numeric>

#define KATA_SOLUTION_2

namespace ExpenseReportKata {

enum Category {
    MEAL,
    NONMEAL
};

struct Type
{    
    std::string_view name; // note a Type struct is expected to be constructed with a static string literal for name
    unsigned int limit;
    Category category;
};

enum TypeIndex {
    BREAKFAST = 0,
    DINNER = 1,
    LUNCH = 2,
    CAR_RENTAL = 3,
    MAX_TYPE_INDEX = 3
};

namespace Private {
    // note: order in types array must match TypeIndex
    static constexpr std::array<Type, MAX_TYPE_INDEX+1> g_types {
        Type{
            .name = "Breakfast",
            .limit = 1000,
            .category = MEAL
        },
        Type{
            .name = "Dinner",
            .limit = 5000,
            .category = MEAL
        },
        Type{
            .name = "Lunch",
            .limit = 2000,
            .category = MEAL
        },
        Type{
            .name = "Car Rental",
            .limit = UINT_MAX,
            .category = NONMEAL
        }
    };
}

static constexpr const Type& getType(TypeIndex t) {
    return Private::g_types[t];
}

class Expense
{
private:
    const Type& type_;   
    unsigned int amount_;

public:
    constexpr Expense(TypeIndex t, unsigned int amt) : type_(getType(t)), amount_(amt) {}
    // default move and copy constructors and operators should work
    constexpr unsigned int amount() const { return amount_; }
    constexpr bool aboveLimit() const { return amount_ > type_.limit; }
    constexpr std::string_view name() const { return type_.name; }
    constexpr Category category() const { return type_.category; }
};

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

//bool printReport(const std::list<Expense>& expenses, std::optional<report_time_t> time_opt = std::nullopt);
template <typename It>
bool printReport(const It begin, const It end, std::optional<report_time_t> time_opt = std::nullopt)
{
    time_t now = 0;
    if(time_opt) now = std::chrono::system_clock::to_time_t(*time_opt);

    std::cout << "Expenses " << ctime(&now) << '\n';

    unsigned long total = 0;
    unsigned long mealExpenses = 0; // todo this could be abstracted to generic map of category -> total, with category names in static list/map, similar to Types. 

    //for (const auto& expense : expenses) {
    for(auto i = begin; i != end; ++i)
    {
        // todo guard against overflowing total or mealExpenses
        const Expense& expense = *i;
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

template <typename T>
bool printReport(const T& cont, std::optional<report_time_t> time_opt = std::nullopt) {
    return printReport(std::cbegin(cont), std::cend(cont), time_opt);
}

template<size_t N>
constexpr unsigned long getTotal(const std::array<Expense, N>& expenses)
{
   /*  std::for_each(expenses.cbegin(), expenses.cend(), [&r](const auto& e){
        // todo guard against overflowing total or mealExpenses
        if (e.category() == MEAL) {
            r.mealExpenses += e.amount();
        }
        r.total += e.amount();
        if(e.aboveLimit())
            r.numOverLimit += 1;
    }); */
    return std::accumulate(expenses.cbegin(), expenses.cend(), 0UL, [](const auto sum, const Expense& next)
    {
        return sum + next.amount();
    });
}

template<size_t N>
constexpr unsigned long getMealsTotal(const std::array<Expense, N>& expenses)
{
    return std::accumulate(expenses.cbegin(), expenses.cend(), 0UL, [](const auto sum, const Expense& next)
    {
        if(next.category() == MEAL) return sum + next.amount();
        return sum;
    }
    );
}

template<size_t N>
constexpr size_t getNumAboveLimit(const std::array<Expense, N>& expenses)
{
    return std::count_if(expenses.cbegin(), expenses.cend(), [](const Expense& next)
    {
        return next.aboveLimit();
    });
}

} // namespace ExpenseReportKata