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
// Upon destruction, std::cout's output buffer is restored to its previous state (i.e. probably program stdout).
// Alternatively we could use one catch2 TEST_CASE that sets up the capturing, then multiple SECTIONs for each test.
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


// The different solutions have different ways of constructing objects and adding them to a list or other container:

#ifdef KATA_SOLUTION_1

// solution 1 requires std::unique_ptr<Expense> objects and push_back:
#define ADD_BREAKFAST(list, amt)     list.push_back(std::make_unique<BreakfastExpense>(amt))
#define ADD_LUNCH(list, amt)     list.push_back(std::make_unique<LunchExpense>(amt))
#define ADD_DINNER(list, amt)     list.push_back(std::make_unique<DinnerExpense>(amt))
#define ADD_CAR_RENTAL(list, amt)     list.push_back(std::make_unique<CarRentalExpense>(amt))

#else
#ifdef KATA_SOLUTION_5

// solution 5 just has plain subtypes, we can emplace_back:
#define ADD_BREAKFAST(list, amt)     list.emplace_back(BreakfastExpense{amt})
#define ADD_LUNCH(list, amt)     list.emplace_back(LunchExpense{amt})
#define ADD_DINNER(list, amt)     list.emplace_back(DinnerExpense{amt})
#define ADD_CAR_RENTAL(list, amt)     list.emplace_back(CarRentalExpense{amt})

#else

// the other solutions use a type enum, we can also use emplace_back:
#define ADD_BREAKFAST(list, amt)     list.emplace_back(BREAKFAST, amt)
#define ADD_LUNCH(list, amt)     list.emplace_back(LUNCH, amt)
#define ADD_DINNER(list, amt)     list.emplace_back(DINNER, amt)
#define ADD_CAR_RENTAL(list, amt)     list.emplace_back(CAR_RENTAL, amt)

#endif
#endif


TEST_CASE("Empty")
{

    // ApprovalTests will use the contents of the file expensereport-all-tests.Empty.approved.txt as the approved text compared against the argument to verify()
    CaptureCout captured;
    // Verify output of empty list:

#ifdef KATA_SOLUTION_1
    ExpenseReportKata::expense_list_t emptylist;
    printReport(emptylist, system_clock_zero); // use constant time value
#else
    std::list<Expense> emptylist;
    printReport(emptylist.begin(), emptylist.end(), system_clock_zero);
#endif
    Approvals::verify(captured.str());
}


TEST_CASE("EmptyVector")
{
    CaptureCout cap;
    std::vector<Expense> emptylist;
    //printReport(emptylist, system_clock_zero);
    printReport(emptylist.begin(), emptylist.end(), system_clock_zero);
    Approvals::verify(cap.str());
}



TEST_CASE("Several_AntiRegression")
{    
    // This report output was approved from the original version, before refactoring.
    // This test will break if formatting of report changes, or any limits of the types changes.


    ExpenseReportKata::expense_list_t e;
    ADD_BREAKFAST(e, 1000);
    ADD_BREAKFAST(e, 1001);
    ADD_DINNER(e, 5000);
    ADD_DINNER(e, 5001);
    ADD_BREAKFAST(e, 0);
    ADD_DINNER(e, 0);
    ADD_CAR_RENTAL(e,1000);
    ADD_CAR_RENTAL(e,999999);
    ADD_CAR_RENTAL(e,0);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}


TEST_CASE("ExpenseSize")
{
    if(sizeof(ExpenseReportKata::Expense) > 8) {
        WARN("sizeof(Expense) > 8: " << sizeof(Expense));
    }
}

#if defined(KATA_SOLUTION_1) || defined(KATA_SOLUTION_5)
TEST_CASE("DerivedExpenseSize")
{
    if(sizeof(BreakfastExpense) > 8) {
        WARN("sizeof(BreakfastExpense) > 8: " << sizeof(BreakfastExpense));
    }
}
#endif



TEST_CASE("Several_WithFixedAmounts") 
{
    // This test will break if formatting of report changes, or any limits of the types changes.

    ExpenseReportKata::expense_list_t e;
    ADD_BREAKFAST(e, 1000);
    ADD_BREAKFAST(e, 1001);
    ADD_DINNER(e, 5000);
    ADD_DINNER(e, 5001);
    ADD_LUNCH(e, 2000);
    ADD_LUNCH(e, 2001);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

#ifdef KATA_SOLUTION_1

#define BREAKFAST_LIMIT() ( BreakfastExpense(0).limit() )
#define LUNCH_LIMIT() ( LunchExpense(0).limit() )
#define DINNER_LIMIT() ( DinnerExpense(0).limit() )

#else
#ifdef KATA_SOLUTION_2

#define BREAKFAST_LIMIT() ( getType(BREAKFAST).limit )
#define LUNCH_LIMIT() ( getType(LUNCH).limit )
#define DINNER_LIMIT() ( getType(DINNER).limit )

#else
#ifdef KATA_SOLUTION_5

#define BREAKFAST_LIMIT() (BreakfastExpense().limit())
#define LUNCH_LIMIT() (LunchExpense().limit())
#define DINNER_LIMIT() (DinnerExpense().limit())

#else

#define BREAKFAST_LIMIT() limit({.type=BREAKFAST, .amount=0});
#define LUNCH_LIMIT() limit({.type=LUNCH, .amount=0});
#define DINNER_LIMIT() limit({.type=DINNER, .amount=0});

#endif
#endif
#endif


TEST_CASE("Several_WithRelAmounts") 
{
    // This test will break if report changes, but not if limit values of types change, since we use values based on whatever the type's limit value is

    auto bkfst = BREAKFAST_LIMIT();
    auto dinner = DINNER_LIMIT();
    auto lunch = LUNCH_LIMIT();

    ExpenseReportKata::expense_list_t e;
    ADD_BREAKFAST(e, bkfst);
    ADD_BREAKFAST(e, bkfst + 1);
    ADD_DINNER(e, dinner);
    ADD_DINNER(e, dinner + 1);
    ADD_LUNCH(e, lunch);
    ADD_LUNCH(e, lunch + 1);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}

TEST_CASE("Several_WithFixedAmounts_Vector") 
{
    // This test will break if formatting of report changes, or any limits of the types changes.
    std::vector<Expense> e;
    ADD_BREAKFAST(e, 1000);
    ADD_BREAKFAST(e, 1001);
    ADD_DINNER(e, 5000);
    ADD_DINNER(e, 5001);
    ADD_LUNCH(e, 2000);
    ADD_LUNCH(e, 2001);

    CaptureCout capt;
    ExpenseReportKata::printReport(e, system_clock_zero);
    Approvals::verify(capt.str());
}


#ifdef KATA_SOLUTION_CONSTEXPR
#include <array>
TEST_CASE("Constexpr")
{

#ifdef KATA_SOLUTION_5
    constexpr std::array<Expense, 3> expenses {
        BreakfastExpense {800},
        LunchExpense {1200},
        DinnerExpense {5200} // should be above limit
    };
#else
    constexpr std::array<Expense, 3> expenses {
        Expense {BREAKFAST, 800},
        Expense {LUNCH, 1200},
        Expense {DINNER, 5200} // should be above limit
    };
#endif
    static_assert(getTotal(expenses) == 800+1200+5200);
    STATIC_REQUIRE(getTotal(expenses) == 800+1200+5200);
    STATIC_REQUIRE(getTotal(expenses) == getMealsTotal(expenses)); // all are meals
    STATIC_REQUIRE(getNumAboveLimit(expenses) == 1);

}
#endif
