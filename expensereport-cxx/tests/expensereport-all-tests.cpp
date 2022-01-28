#define APPROVAL_TESTS_HIDE_DEPRECATED_CODE 1

// defines main function:
#define APPROVALS_CATCH

#include "ApprovalTests.hpp"
#include "catch.hpp"

using namespace ApprovalTests;

#include "ExpenseReport.h"

TEST_CASE("ExpenseReport")
{
    // ApprovalTests will use the contents of the file expensereport-all-tests.ExpenseReport.approved.txt as the approved text compared against the argument to verify()
    Approvals::verify("Dummy");
}





