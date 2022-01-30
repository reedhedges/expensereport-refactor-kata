#pragma once

#include <chrono>
#include <list>
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
    std::string_view name;
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

static constexpr const Type& getType(TypeIndex t) {
    return g_types[t];
}

class Expense
{
private:
    const Type& type_;   
    unsigned int amount_;

public:
    constexpr Expense(TypeIndex t, unsigned int amt) : type_(g_types[t]), amount_(amt) {}
    constexpr unsigned int amount() const { return amount_; }
    constexpr bool aboveLimit() const { return amount_ > type_.limit; }
    constexpr std::string_view name() const { return type_.name; }
    constexpr Category category() const { return type_.category; }
};

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

bool printReport(const std::list<Expense>& expenses, std::optional<report_time_t> time_opt = std::nullopt);


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