
* Switched to CMake for build system.
* Added basic unit test infrastructure with Catch2, ctest, ApprovalTests-Cpp (in [tests/](tests/)). (TODO get precompiled headers for Catch2 and ApprovalTestsCpp to work, build is a little slow.  Then, separate out the test cases to separate test programs, and probably also give each solution subdirectory its own set of tests rather than ifdef messiness.
* Used approval testing to ensure the report output matched the original version exactly.
* Attempted to avoid adding excessive additional memory storage (especially the names as strings).
* Attempted to avoid adding a lot of additional code or complexity
* No reduced time performance in printReport().
* Made some small minor optimizations and code cleanups and modernizations.

I worked on three refactoring solutions, in [solution1/](solution1/), [solution2/](solution2/) and [solution3/](solution3).  The original code (but updated a bit to be able to test with unit tests) is in [orig/](orig/).   You can choose solution by defining `KATA_SOLUTION` when running cmake.  (i.e.  `cmake -DKATA_SOLUTION=solution1` or select in cmake gui)

Solution1 uses virtual classes to represent expenses, and so even though the classes only store the expense amount (and type-specific data is provided as constant return values from functions), each expense instance uses more memory (24 bytes) than the simple struct in the original (8 bytes), and the function overrides in the derived types also can't be inlined.  The type names (std::string objects), however, do not need to be allocated or stored for each instance.  Expense instances must be allocated on the heap with pointers stored in the list.  Time performance to print a report is about the same. 

Code complexity and maintenance burden is increased a bit by using smart pointer memory management and virtual class inheritance, with potentially tricky boilerplate needed for each derived class definition.  (e.g. errors can occur if each method declaration is not exactly correct.), but this is also a conventional object-oriented pattern in C++.

Solution1 also changes the API significantly: Instead of creating an Expense instance and initializing the type and amount, users must create a derived type, and for the list, create and store a `unique_ptr` correctly. Re-instating the Type enum and adding a factory function that accepted the type enum value and amount would allow for a superficially similar API that could more easily be automatically changed. (And a type accessor could be added to return the enum value if necessary, in place of the type property.) 

Solution2 instead stores, in each Expense instance, a pointer to a static expense type struct instance which provides type-specific information. This retains many of the advantages of Solution1 (type-specific information, especially the name string, is not duplicated in each Expense instance) but with slightly smaller Expense structs (unsigned int and pointer, 12 bytes), which can more easily be stored directly in containers, and avoids the complexity of virtual class inheritance.  This version has most data and functions declared with constexpr and parts can be evaluated constantly (see "Constexpr" tests).   (I made it generic for any iterable container of `Expense` objects, not just `std::list`, though  at the expense of defining `printReport()` in the header file as a template function. This could also be done for solution1 I think.) 

Solution3 is a variation on Solution2. 

Solution4 is a minimal change in which expense type specific logic is just moved to a class (struct), where the expense type is checked and type specific information is initialized in the constructor, which should generally be inlined by the compiler (resulting in similar resulting compiled code as the original).  

In addition, in all solutions, amount values have been changed to unsigned int.  In a real application, this API change could have a significant impact and possibly cause undetected problems, so I would be much more cautious about this change.

I added the concept of categories to distinguish meal and non-meal.  This could be further abstracted to generic set of categories with totals for all or any categories calculated. This change would make adding categories easier (just like representing the expense Types made adding a new Type better.)

My changes have been, in part, inspired by Gregor Riegler's solution to this Kata: <https://www.youtube.com/playlist?list=PLITEvpe_3xfdicT0Yobk_xtSTfrCElQes>

Reed Hedges

