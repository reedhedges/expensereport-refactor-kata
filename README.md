# ExpenseReport
The ExpenseReport legacy code refactoring kata in various languages.

This is an example of a piece of legacy code with lots of code smells.
The goal is to support the following new feature as best as you can:
* Add Lunch with an expense limit of 2000.

## Process
1. 📚 Read the code to understand what it does and how it works.
2. 🦨 Read the code and check for design smells.
3. 🧑‍🔬 Analyze what you would have to change to implement the new requirement without refactoring the code.
4. 🧪 Write a characterization test. Take note of all design smells that you missed that made your life writing a test miserable.
5. 🔧 Refactor the code.
6. 🔧 Refactor the test.
7. 👼 Test-drive the new feature.

## Supported Languages
The ExpenseReport example currently exists in the following languages:
- [C](expensereport-c/)
- [C#](expensereport-csharp/)
- [C++](expensereport-cxx/)
- [Dart](expensereport-dart/)
- [Go](expensereport-go/)
- [Java](expensereport-java/)
- [JavaScript](expensereport-javascript/)
- [Kotlin](expensereport-kotlin/)
- [PHP](expensereport-php/)
- [Python](expensereport-python/)
- [Rust](expensereport-rust/)
- [Scala](expensereport-scala/)
- [Swift](expensereport-swift/)
- [TypeScript](expensereport-typescript/)

## Solutions
To see solutions, switch to the branch solutions.

**Warning** The solutions branch will be rebased!

Currently, soltuions exist in the following languages:
- [Java](expensereport-java/)
- [Kotlin](expensereport-kotlin/)
- [Rust](expensereport-rust/)

### Solution Rationale
- Make adding the new requirement (lunch with an expense limit 2000) as easy and simple and error-free as possible. That means solving the OCP-violation of `printReport()`.
- Small ("atomic") functions. Extract until you can no longer reasonably extract further methods.

## Credits
I first encountered the ExpenseReport example during a bootcamp at Equal Experts.
I also have seen the ExpenseReport example being used by Robert "Uncle Bob" C. Martin.
I have tried to research its origins but so far I have failed.
If you know who has first come up with this example, please get in touch with me.
