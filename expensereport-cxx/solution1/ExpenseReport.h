#pragma once

#include <chrono>
#include <list>
#include <optional>
#include <climits>
#include <string_view>
#include <memory>

#define KATA_SOLUTION_1 1
namespace ExpenseReportKata {

class Expense
{
    unsigned int amount_;
public:
    Expense(unsigned int amount) : amount_(amount) {}
    virtual ~Expense() = default;
    unsigned int amount() const { return amount_; }
    virtual std::string_view name() const { return "Unknown"; }
    virtual unsigned int limit() const { return UINT_MAX; }
    bool aboveLimit() const { return amount() > limit(); }
    enum Category { MEAL, NONMEAL };
    virtual Category category() const { return NONMEAL; }
};

// todo these could be moved to another header file and/or namespace
// todo many of these could be made static?
class BreakfastExpense : public virtual Expense
{
public:
    BreakfastExpense(unsigned int amount) : Expense(amount) {}
    virtual ~BreakfastExpense() = default;
    virtual std::string_view name() const override { return "Breakfast"; }
    virtual unsigned int limit() const override { return 1000; }
    virtual Category category() const override { return MEAL; }
};
class DinnerExpense : public virtual Expense
{
public:
    DinnerExpense(unsigned int amount) : Expense(amount) {}
    virtual ~DinnerExpense() = default;
    virtual std::string_view name() const override  { return "Dinner"; }
    virtual unsigned int limit() const override { return 5000; }
    virtual Category category() const override { return MEAL; }
};
class LunchExpense : public virtual Expense
{    
public:
    LunchExpense(unsigned int amount) : Expense(amount) {}
    virtual ~LunchExpense() = default;
    virtual std::string_view name() const override { return "Lunch"; }
    virtual unsigned int limit() const  override { return 2000; }
    virtual Category category() const override { return MEAL; }
};
class CarRentalExpense : public virtual Expense
{
public:
    CarRentalExpense(unsigned int amount) : Expense(amount) {}
    virtual ~CarRentalExpense() = default;
    virtual std::string_view name() const override { return "Car Rental"; }
};

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;
using expense_list_t = std::list<std::unique_ptr<Expense>>;
void printReport(const expense_list_t& expenses, std::optional<report_time_t> time_opt = std::nullopt);

} // namespace ExpenseReportKata