
This is a refactoring "kata" or exersise.  So far I have worked only on the C++ version
in expensereport-cxx.  The goal is to refactor while adding a new expense report type
for Lunch.

In addition, I:
* Switched to CMake for build system.
* Added basic unit test infrastructure with Catch2, ctest, ApprovalTests-Cpp (in tests/).
* Used approval testing to ensure the report output matched the original version exactly.
* Added no additional time or space impact, the refactored version adds no additional memory usage and is about the same time performance.
* Made some small minor optimizations and code cleanups and modernizations.

Reed Hedges

