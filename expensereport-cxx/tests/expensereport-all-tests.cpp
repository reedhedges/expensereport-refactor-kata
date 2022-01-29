#define APPROVAL_TESTS_HIDE_DEPRECATED_CODE 1

// defines main function:
#define APPROVALS_CATCH

#include "ApprovalTests.hpp"
#include "catch.hpp"

using namespace ApprovalTests;

#include "ExpenseReport.h"

#include <iostream>
#include <sstream>
#include <string_view>

class CaptureCout {
    std::streambuf *orig_cout_buf;
    std::ostringstream outsstream;
public:
    CaptureCout() : orig_cout_buf(std::cout.rdbuf()) {
        std::cout.rdbuf(outsstream.rdbuf());
    }
    ~CaptureCout() {
        std::cout.rdbuf(orig_cout_buf);
    }
    std::string str() { return outsstream.str(); }
    // Copying and moving is disabled for this class
    // TODO implement these?
    CaptureCout(const CaptureCout& other) = delete;
    CaptureCout(CaptureCout&& old) = delete;
    CaptureCout& operator=(const CaptureCout& other) = delete;
    CaptureCout& operator=(CaptureCout&& old) = delete;
};

const auto system_clock_zero = std::chrono::time_point<std::chrono::system_clock>();


TEST_CASE("Empty")
{
    // ApprovalTests will use the contents of the file expensereport-all-tests.Empty.approved.txt as the approved text compared against the argument to verify()
 
    CaptureCout captured;
    
    // Verify output of empty list:
    std::list<ExpenseReportKata::Expense> emptylist;
    ExpenseReportKata::printReport(emptylist, system_clock_zero); // use constant time value
    Approvals::verify(captured.str());

}

TEST_CASE("Several")
{
    std::list<ExpenseReportKata::Expense> e;
    e.push_back({.type = ExpenseReportKata::BREAKFAST,  .amount = 1000});
    e.push_back({.type = ExpenseReportKata::BREAKFAST,  .amount = 1001});
    e.push_back({.type = ExpenseReportKata::DINNER,     .amount = 5000});
    e.push_back({.type = ExpenseReportKata::DINNER,     .amount = 5001});
    e.push_back({.type = ExpenseReportKata::BREAKFAST,  .amount = 0});
    e.push_back({.type = ExpenseReportKata::DINNER,     .amount = 0});
    e.push_back({.type = ExpenseReportKata::CAR_RENTAL,  .amount = 1000});
    e.push_back({.type = ExpenseReportKata::CAR_RENTAL,  .amount = 999999});
    e.push_back({.type = ExpenseReportKata::CAR_RENTAL,  .amount = 0});

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}




