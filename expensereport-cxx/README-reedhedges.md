
* Switched to CMake for build system.
* Added basic unit test infrastructure with Catch2, ctest, ApprovalTests-Cpp (in [tests/](tests/)).
* Used approval testing to ensure the report output matched the original version exactly.
* Attempted to avoid adding excessive additional memory storage (especially the names as strings).
* Attempted to avoid adding a lot of additional code or complexity
* No reduced time performance in printReport().
* Made some small minor optimizations and code cleanups and modernizations.

I worked on three refactoring solutions, in [solution1/](solution1/), [solution2/](solution2/) and [solution3/](solution3).  The original code (but updated a bit to be able to test with unit tests) is in [orig/](orig/).   You can choose solution by defining `KATA_SOLUTION` when running cmake.  (i.e.  `cmake -DKATA_SOLUTION=solution1` or select in cmake gui)

Solution1 uses virtual classes to represent expenses, and so uses more memory (24 bytes) than the simple struct in the original (8 bytes), but encapsulates information specific to the derived expense types, including a string name, as constant function return values which are therefore not stored in each instance.  Data is allocated on the heap with pointers stored in the list.  Time performance to print a report is about the same. Code complexity and maintainence burden is increased a bit by using smart pointer memory management and virtual class inheritance, with potentially tricky boilerplate needed for each derived class definition.  (e.g. errors can occur if each method declaration is not exactly correct.)

Solution1 does change the API significantly: Instead of creating an Expense instance and initializing the type and amount, users must create a derived type, and for the list, create and store a `unique_ptr` correctly. Re-instating the Type enum and adding a factory function that accepted the type enum value and amount would allow for a superficially similar API that could more easily be automatically changed. (And a type accessor could be added to return the enum value if neccesary, in place of the type property.) 

Solution2 instead uses a class for each Expense as in the original, but with a pointer to a static expense type struct instance which provides type-specific information, rather than just an enum value for the type. This retains many of the advantages of Solution1 but with slightly smaller Expense structs (unsigned int and pointer, 12 bytes), which can more easily be stored directly in containers, and avoids the complexty of virtual class inheritance.  (This version can also potentially be constexpr enabled.) 

Solution3 is a minimal change in which expense type specific logic is extracted to static functions in parallel namespaces which can easily be modified.

In addition, in all solutions, amounts are changed to unsigned int.  In a real application, this could have a significant impact and possibly undetected problems, so I would be much more cautious about this change.

My changes have been, in part, inspired by Gregor Riegler's solution to this Kata: <https://www.youtube.com/playlist?list=PLITEvpe_3xfdicT0Yobk_xtSTfrCElQes>

Reed Hedges

