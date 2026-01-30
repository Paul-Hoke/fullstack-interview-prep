package com.paul.fullstack_interview_prep.examples;

/**
 * Q7: What is the difference between final, finally, and finalize?
 *
 * final: Keyword to declare constants, prevent inheritance/overriding
 * finally: Block that always executes after try-catch
 * finalize: Method called by GC before object destruction (deprecated)
 */
public class Q07_FinalFinallyFinalize {

  public static void main(String[] args) {
    System.out.println("=== final vs finally vs finalize Demo ===\n");

    // FINAL keyword
    System.out.println("--- final Keyword ---");
    demonstrateFinal();

    // FINALLY block
    System.out.println("\n--- finally Block ---");
    demonstrateFinally();

    // FINALIZE method
    System.out.println("\n--- finalize Method (Deprecated) ---");
    demonstrateFinalize();
  }

  static void demonstrateFinal() {
    // final variable - cannot be reassigned
    final int MAX_VALUE = 100;
    System.out.println("final variable MAX_VALUE = " + MAX_VALUE);
    // MAX_VALUE = 200; // Compilation error

    // final reference - reference cannot change, but object can be modified
    final StringBuilder sb = new StringBuilder("Hello");
    sb.append(" World"); // OK - modifying object
    System.out.println("final reference, modified content: " + sb);
    // sb = new StringBuilder("New"); // Compilation error

    // final class - cannot be extended
    System.out.println("String is final - cannot be subclassed");

    // final method - cannot be overridden
    System.out.println("final methods cannot be overridden in subclasses");
  }

  static void demonstrateFinally() {
    // finally always executes
    try {
      System.out.println("In try block");
      int result = 10 / 2;
      System.out.println("Result: " + result);
    } catch (ArithmeticException e) {
      System.out.println("In catch block");
    } finally {
      System.out.println("finally block ALWAYS executes (cleanup resources here)");
    }

    // finally executes even with exception
    try {
      System.out.println("\nIn try block (with exception)");
      int result = 10 / 0;
    } catch (ArithmeticException e) {
      System.out.println("In catch block: " + e.getMessage());
    } finally {
      System.out.println("finally block executes even after exception");
    }
  }

  static void demonstrateFinalize() {
    // finalize is deprecated since Java 9
    System.out.println("finalize() is deprecated - use try-with-resources instead");
    System.out.println("Was called by GC before destroying object");
    System.out.println("Not guaranteed to run, don't rely on it for cleanup");

    // Modern alternative: try-with-resources
    System.out.println("\nModern approach: implement AutoCloseable and use try-with-resources");
  }

  // Example of final class
  static final class ImmutableClass {
    private final String value;

    ImmutableClass(String value) {
      this.value = value;
    }
  }

  // Example of final method
  static class Parent {
    final void cannotOverride() {
      System.out.println("This method cannot be overridden");
    }
  }
}
