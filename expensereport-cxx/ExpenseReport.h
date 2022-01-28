#pragma once

#include <chrono>
#include <iostream>
#include <iterator>
#include <list>
#include <optional>

namespace ExpenseReportKata {

enum Type
{
    BREAKFAST, DINNER, CAR_RENTAL
};

class Expense
{
    public:
    Type type;
    int amount;
};

using report_time_t = std::chrono::time_point<std::chrono::system_clock>;

void printReport(std::list<Expense> expenses, std::optional<report_time_t> time_opt = std::nullopt);

} // namespace ExpenseReportKata