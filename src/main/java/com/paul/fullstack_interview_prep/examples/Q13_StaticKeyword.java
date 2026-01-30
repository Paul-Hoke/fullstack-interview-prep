package com.paul.fullstack_interview_prep.examples;

/**
 * Q13: What is the static keyword in Java?
 *
 * Static members belong to the class, not instances:
 * - static variable: Shared across all instances
 * - static method: Can be called without creating an object
 * - static block: Executed once when class is loaded
 * - static nested class: Nested class that doesn't need outer instance
 */
public class Q13_StaticKeyword {

  public static void main(String[] args) {
    System.out.println("=== Static Keyword Demo ===\n");

    // STATIC VARIABLE
    System.out.println("--- Static Variable ---");
    demonstrateStaticVariable();

    // STATIC METHOD
    System.out.println("\n--- Static Method ---");
    demonstrateStaticMethod();

    // STATIC BLOCK
    System.out.println("\n--- Static Block ---");
    demonstrateStaticBlock();

    // STATIC NESTED CLASS
    System.out.println("\n--- Static Nested Class ---");
    demonstrateStaticNestedClass();
  }

  static void demonstrateStaticVariable() {
    Counter c1 = new Counter();
    Counter c2 = new Counter();
    Counter c3 = new Counter();

    System.out.println("Created 3 Counter objects");
    System.out.println("Static count (shared): " + Counter.count);
    System.out.println("c1.instanceId: " + c1.instanceId);
    System.out.println("c2.instanceId: " + c2.instanceId);
    System.out.println("c3.instanceId: " + c3.instanceId);
  }

  static void demonstrateStaticMethod() {
    // Called without creating instance
    int result = MathUtils.add(5, 3);
    System.out.println("MathUtils.add(5, 3) = " + result);

    // Common static method examples
    System.out.println("Math.max(10, 20) = " + Math.max(10, 20));
    System.out.println("Integer.parseInt(\"42\") = " + Integer.parseInt("42"));

    System.out.println("\nNote: Static methods cannot access instance members directly");
  }

  static void demonstrateStaticBlock() {
    // Static block runs when class is first loaded
    System.out.println("Accessing DatabaseConfig for first time...");
    System.out.println("DB URL: " + DatabaseConfig.DB_URL);
    System.out.println("\nNote: Static block already executed during class loading");
  }

  static void demonstrateStaticNestedClass() {
    // Static nested class can be instantiated without outer class
    OuterClass.StaticNested nested = new OuterClass.StaticNested();
    nested.display();

    // Non-static inner class needs outer instance
    OuterClass outer = new OuterClass();
    OuterClass.InnerClass inner = outer.new InnerClass();
    inner.display();
  }

  // Static variable example
  static class Counter {
    static int count = 0;      // Shared across all instances
    int instanceId;            // Unique to each instance

    Counter() {
      count++;
      instanceId = count;
    }
  }

  // Static method example
  static class MathUtils {
    static int add(int a, int b) {
      return a + b;
    }

    // Static methods cannot use 'this' or access instance members
    // void instanceMethod() { } // Cannot be called from static context
  }

  // Static block example
  static class DatabaseConfig {
    static String DB_URL;
    static String DB_USER;

    // Static block - runs once when class is loaded
    static {
      System.out.println("Static block executing - loading config...");
      DB_URL = "jdbc:mysql://localhost:3306/mydb";
      DB_USER = "admin";
    }
  }

  // Static nested class vs Inner class
  static class OuterClass {
    private static String staticField = "Static Outer Field";
    private String instanceField = "Instance Outer Field";

    // Static nested class - no reference to outer instance
    static class StaticNested {
      void display() {
        System.out.println("Static nested class can access: " + staticField);
        // Cannot access instanceField - no outer instance
      }
    }

    // Inner class (non-static) - has reference to outer instance
    class InnerClass {
      void display() {
        System.out.println("Inner class can access: " + staticField);
        System.out.println("Inner class can access: " + instanceField);
      }
    }
  }
}
