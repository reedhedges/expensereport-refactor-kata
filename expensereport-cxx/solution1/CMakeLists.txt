add_library(libExpenseReportKata STATIC ExpenseReport.cpp)
target_compile_options(libExpenseReportKata PRIVATE -Wall -Wextra -Wpedantic PUBLIC -std=c++20)
