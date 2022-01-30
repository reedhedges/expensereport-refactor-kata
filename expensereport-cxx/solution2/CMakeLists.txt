add_library(libExpenseReportKata STATIC ExpenseReport.cpp)
target_compile_options(libExpenseReportKata PRIVATE -Wall -Wextra PUBLIC -std=c++20)
