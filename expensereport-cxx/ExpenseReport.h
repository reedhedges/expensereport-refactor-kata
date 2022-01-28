#pragma once

#include <chrono>
#include <iostream>
#include <iterator>
#include <list>

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

void printReport(std::list<Expense> expenses);

}