set(CXX_STANDARD 20)
add_library(libExpenseReportKata STATIC ExpenseReport.cpp)
target_compile_options(libExpenseReportKata PRIVATE -Wall -Wextra)
