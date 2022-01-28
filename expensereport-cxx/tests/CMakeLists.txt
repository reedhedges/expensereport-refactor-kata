
function(add_approval_catch2_test name)
  # TODO develop a version of this function with more customizability (libraries, compiler flags, command line arguments when running test, etc.)
  add_executable(${name} ${name}.cpp)
  target_compile_options(${name} PRIVATE -Wall -Wextra)
  target_compile_definitions(${name} PRIVATE -DAPPROVAL_TESTS_HIDE_DEPRECATED_CODE=1)
  #target_precompile_headers(${name} PRIVATE "catch.hpp" "ApprovalTests.hpp") # TODO make this work
  target_include_directories(${name} PRIVATE ${CMAKE_SOURCE_DIR})
  target_link_libraries(${name} PRIVATE libExpenseReportKata)
  add_test(NAME ${name} COMMAND ${name})
endfunction()

add_approval_catch2_test(expensereport-all-tests)
