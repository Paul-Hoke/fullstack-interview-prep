package com.paul.fullstack_interview_prep.examples;

/**
 * Q14: What is the difference between stack and heap memory?
 *
 * Stack Memory:
 * - Stores local variables and method call frames
 * - LIFO (Last In First Out) structure
 * - Fast allocation/deallocation
 * - Thread-specific (each thread has its own stack)
 * - Limited size (can cause StackOverflowError)
 * - Stores primitives and object references
 *
 * Heap Memory:
 * - Stores objects and instance variables
 * - Shared across all threads
 * - Managed by Garbage Collector
 * - Larger than stack
 * - Slower allocation than stack
 * - Can cause OutOfMemoryError
 */
public class Q14_StackVsHeap {

  public static void main(String[] args) {
    System.out.println("=== Stack vs Heap Memory Demo ===\n");

    // Demonstrate stack and heap usage
    System.out.println("--- Memory Allocation ---");
    demonstrateMemoryAllocation();

    // Show stack frame behavior
    System.out.println("\n--- Stack Frames ---");
    method1();

    // Memory information
    System.out.println("\n--- Runtime Memory Info ---");
    printMemoryInfo();

    // Stack overflow example (commented to prevent crash)
    System.out.println("\n--- Stack Overflow Example ---");
    System.out.println("Uncomment recursiveMethod() to see StackOverflowError");
    // recursiveMethod(1); // Would cause StackOverflowError
  }

  static void demonstrateMemoryAllocation() {
    // STACK: primitives and references
    int x = 10;           // Stack: primitive value
    int y = 20;           // Stack: primitive value
    String name = "John"; // Stack: reference, Heap: String object

    // HEAP: objects
    Person person = new Person("Alice", 25);
    // Stack: 'person' reference
    // Heap: Person object with name="Alice", age=25

    int[] numbers = {1, 2, 3, 4, 5};
    // Stack: 'numbers' reference
    // Heap: array object

    System.out.println("Primitives (x, y) stored on STACK: " + x + ", " + y);
    System.out.println("Object references stored on STACK");
    System.out.println("Actual objects stored on HEAP: " + person);
    System.out.println("Arrays stored on HEAP: length = " + numbers.length);

    System.out.println("\nVisualization:");
    System.out.println("STACK                    HEAP");
    System.out.println("------                   ------");
    System.out.println("x = 10                   ");
    System.out.println("y = 20                   ");
    System.out.println("name -----------------> \"John\"");
    System.out.println("person ---------------> Person{name=\"Alice\", age=25}");
    System.out.println("numbers --------------> [1, 2, 3, 4, 5]");
  }

  static void method1() {
    System.out.println("Entering method1 - new stack frame created");
    int a = 100;
    method2();
    System.out.println("Exiting method1 - stack frame destroyed");
  }

  static void method2() {
    System.out.println("  Entering method2 - new stack frame created");
    int b = 200;
    method3();
    System.out.println("  Exiting method2 - stack frame destroyed");
  }

  static void method3() {
    System.out.println("    Entering method3 - new stack frame created");
    int c = 300;
    System.out.println("    Current stack: main -> method1 -> method2 -> method3");
    System.out.println("    Exiting method3 - stack frame destroyed");
  }

  static void printMemoryInfo() {
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory() / (1024 * 1024);
    long allocatedMemory = runtime.totalMemory() / (1024 * 1024);
    long freeMemory = runtime.freeMemory() / (1024 * 1024);

    System.out.println("Heap Memory:");
    System.out.println("  Max: " + maxMemory + " MB");
    System.out.println("  Allocated: " + allocatedMemory + " MB");
    System.out.println("  Free: " + freeMemory + " MB");
    System.out.println("  Used: " + (allocatedMemory - freeMemory) + " MB");
    System.out.println("\nNote: Stack size is typically 512KB-1MB per thread (JVM dependent)");
  }

  // Uncomment to demonstrate StackOverflowError
  // static void recursiveMethod(int n) {
  //   System.out.println("Recursion level: " + n);
  //   recursiveMethod(n + 1); // No base case = infinite recursion
  // }

  static class Person {
    String name;
    int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return "Person{name='" + name + "', age=" + age + "}";
    }
  }
}
