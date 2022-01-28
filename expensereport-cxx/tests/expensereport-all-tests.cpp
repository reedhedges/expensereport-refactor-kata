#define APPROVAL_TESTS_HIDE_DEPRECATED_CODE 1

// defines main function:
#define APPROVALS_CATCH

#include "ApprovalTests.hpp"
#include "catch.hpp"

using namespace ApprovalTests;

#include "ExpenseReport.h"

#include <iostream>
#include <sstream>

TEST_CASE("Empty")
{
    // ApprovalTests will use the contents of the file expensereport-all-tests.Empty.approved.txt as the approved text compared against the argument to verify()
 
    // save std::cout output buffer:
    std::streambuf *orig_cout_buf = std::cout.rdbuf();

    // replace std::cout output buffer with our own stringstream:
    std::ostringstream outsstream;
    std::cout.rdbuf(outsstream.rdbuf());
    
    // Verify output of empty list:
    std::list<ExpenseReportKata::Expense> emptylist;
    ExpenseReportKata::printReport(emptylist, std::chrono::time_point<std::chrono::system_clock>()); // use constant time value
    Approvals::verify(outsstream.str());

    // reset std::cout output buffer:
    std::cout.rdbuf(orig_cout_buf);
}





