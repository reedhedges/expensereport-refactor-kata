#define APPROVAL_TESTS_HIDE_DEPRECATED_CODE 1

// defines main function:
#define APPROVALS_CATCH

#include "ApprovalTests.hpp"
#include "catch.hpp"

using namespace ApprovalTests;

#include "ExpenseReport.h"
#include <vector>
#include <list>
#include <iostream>
#include <sstream>
#include <string_view>
#include <memory>

// During this object's lifetime, all output to std::cout will instead be stored in a stringstream within this object. Use str() to get this string.
// Upon destruction, std::cout's output buffer is restored to its previous state (i.e. probably program stdout)
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

using namespace ExpenseReportKata;


#ifdef KATA_SOLUTION_1
TEST_CASE("Empty")
{
    // ApprovalTests will use the contents of the file expensereport-all-tests.Empty.approved.txt as the approved text compared against the argument to verify()
    CaptureCout captured;
    // Verify output of empty list:
    ExpenseReportKata::expense_list_t emptylist;
    printReport(emptylist, system_clock_zero); // use constant time value
    Approvals::verify(captured.str());
}
#else
TEST_CASE("Empty")
{
    CaptureCout cap;
    std::list<Expense> emptylist;
    //printReport(emptylist, system_clock_zero);
    printReport(emptylist.begin(), emptylist.end(), system_clock_zero);
    Approvals::verify(cap.str());
}

TEST_CASE("EmptyVector")
{
    CaptureCout cap;
    std::vector<Expense> emptylist;
    //printReport(emptylist, system_clock_zero);
    printReport(emptylist.begin(), emptylist.end(), system_clock_zero);
    Approvals::verify(cap.str());
}
#endif

#ifdef KATA_SOLUTION_1
TEST_CASE("Several_AntiRegression")
{    
    // This report output was approved from the original version, before refactoring.
    // This test will break if formatting of report changes, or any limits of the types changes.
    ExpenseReportKata::expense_list_t e;
    e.push_back(std::make_unique<BreakfastExpense>(1000));
    e.push_back(std::make_unique<BreakfastExpense>(1001));
    e.push_back(std::make_unique<DinnerExpense>(5000));
    e.push_back(std::make_unique<DinnerExpense>(5001));
    e.push_back(std::make_unique<BreakfastExpense>(0));
    e.push_back(std::make_unique<DinnerExpense>(0));
    e.push_back(std::make_unique<CarRentalExpense>(1000));
    e.push_back(std::make_unique<CarRentalExpense>(999999));
    e.push_back(std::make_unique<CarRentalExpense>(0));
    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}
#else
TEST_CASE("Several_AntiRegression")
{    
    // This report output was approved from the original version, before refactoring.
    // This test will break if formatting of report changes, or any limits of the types changes.
    std::list<Expense> e;
    e.emplace_back(BREAKFAST, 1000);
    e.emplace_back(BREAKFAST, 1001);
    e.emplace_back(DINNER, 5000);
    e.emplace_back(DINNER, 5001);
    e.emplace_back(BREAKFAST, 0);
    e.emplace_back(DINNER, 0);
    e.emplace_back(CAR_RENTAL, 1000);
    e.emplace_back(CAR_RENTAL, 999999);
    e.emplace_back(CAR_RENTAL, 0);
    CaptureCout capt;
//    ExpenseReportKata::printReport(e, system_clock_zero);
    ExpenseReportKata::printReport(e.cbegin(), e.cend(), system_clock_zero);
    Approvals::verify(capt.str());
}
#endif

TEST_CASE("ExpenseSize")
{
    if(sizeof(ExpenseReportKata::Expense) > 8) {
        WARN("sizeof(Expense) > 8: " << sizeof(Expense));
    }
}

#ifdef KATA_SOLUTION_1
TEST_CASE("DerivedExpenseSize")
{
    if(sizeof(BreakfastExpense) > 8) {
        WARN("sizeof(BreakfastExpense) > 8: " << sizeof(BreakfastExpense));
    }
}
#endif


#ifdef KATA_SOLUTION_1

TEST_CASE("Several_WithFixedAmounts") 
{
    // This test will break if formatting of report changes, or any limits of the types changes.
    ExpenseReportKata::expense_list_t e;
    e.push_back(std::make_unique<BreakfastExpense>(1000));
    e.push_back(std::make_unique<BreakfastExpense>(1001));
    e.push_back(std::make_unique<DinnerExpense>(5000));
    e.push_back(std::make_unique<DinnerExpense>(5001));
    e.push_back(std::make_unique<LunchExpense>(2000));
    e.push_back(std::make_unique<LunchExpense>(2001));

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

TEST_CASE("Several_WithRelAmounts") 
{
    // This test will break if report changes, but not if limit values of types change, since we use values based on whatever the type's limit value is

    BreakfastExpense bkfst(0);
    DinnerExpense dinner(0);
    LunchExpense lunch(0);

    ExpenseReportKata::expense_list_t e;
    e.push_back(std::make_unique<BreakfastExpense>(bkfst.limit()));
    e.push_back(std::make_unique<BreakfastExpense>(bkfst.limit() + 1));
    e.push_back(std::make_unique<DinnerExpense>(dinner.limit()));
    e.push_back(std::make_unique<DinnerExpense>(dinner.limit() + 1));
    e.push_back(std::make_unique<LunchExpense>(lunch.limit()));
    e.push_back(std::make_unique<LunchExpense>(lunch.limit() + 1));

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

#else

TEST_CASE("Several_WithFixedAmounts") 
{
    // This test will break if formatting of report changes, or any limits of the types changes.
    std::list<Expense> e;
    e.emplace_back(BREAKFAST, 1000);
    e.emplace_back(BREAKFAST, 1001);
    e.emplace_back(DINNER, 5000);
    e.emplace_back(DINNER, 5001);
    e.emplace_back(LUNCH, 2000);
    e.emplace_back(LUNCH, 2001);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

TEST_CASE("Several_WithFixedAmounts_Vector") 
{
    // This test will break if formatting of report changes, or any limits of the types changes.
    std::vector<Expense> e;
    e.emplace_back(BREAKFAST, 1000);
    e.emplace_back(BREAKFAST, 1001);
    e.emplace_back(DINNER, 5000);
    e.emplace_back(DINNER, 5001);
    e.emplace_back(LUNCH, 2000);
    e.emplace_back(LUNCH, 2001);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

TEST_CASE("Several_WithRelAmounts") 
{
    // This test will break if report changes, but not if limit values of types change, since we use values based on whatever the type's limit value is

    const ExpenseReportKata::Type& bkfst = getType(BREAKFAST);
    const ExpenseReportKata::Type& dinner = getType(DINNER);
    const ExpenseReportKata::Type& lunch = getType(LUNCH);

    CHECK(bkfst.category == MEAL);
    CHECK(dinner.category == MEAL);
    CHECK(lunch.category == MEAL);

    std::list<Expense> e;
    e.emplace_back(BREAKFAST, bkfst.limit);
    e.emplace_back(BREAKFAST, bkfst.limit + 1);
    e.emplace_back(DINNER, dinner.limit);
    e.emplace_back(DINNER, dinner.limit + 1);
    e.emplace_back(LUNCH, lunch.limit);
    e.emplace_back(LUNCH, lunch.limit + 1);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

#endif


#ifdef KATA_SOLUTION_2
#include <array>
TEST_CASE("Constexpr")
{
    constexpr std::array<Expense, 3> expenses {
        Expense {BREAKFAST, 800},
        Expense {LUNCH, 1200},
        Expense {DINNER, 5200} // should be above limit
    };
    static_assert(getTotal(expenses) == 800+1200+5200);
    STATIC_REQUIRE(getTotal(expenses) == 800+1200+5200);
    STATIC_REQUIRE(getTotal(expenses) == getMealsTotal(expenses)); // all are meals
    STATIC_REQUIRE(getNumAboveLimit(expenses) == 1);
}
#endif