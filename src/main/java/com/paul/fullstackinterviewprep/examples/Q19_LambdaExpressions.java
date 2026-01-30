package com.paul.fullstackinterviewprep.examples;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Q19: What are Lambda Expressions in Java?
 *
 * Lambda: Anonymous function that can be passed as argument.
 * Syntax: (parameters) -> expression or (parameters) -> { statements }
 *
 * Benefits:
 * - Concise code
 * - Functional programming style
 * - Works with functional interfaces (single abstract method)
 *
 * Common functional interfaces:
 * - Predicate<T>: T -> boolean
 * - Function<T,R>: T -> R
 * - Consumer<T>: T -> void
 * - Supplier<T>: () -> T
 * - BiFunction<T,U,R>: (T,U) -> R
 */
public class Q19_LambdaExpressions {

  public static void main(String[] args) {
    System.out.println("=== Lambda Expressions Demo ===\n");

    // Basic syntax
    System.out.println("--- Basic Lambda Syntax ---");
    demonstrateBasicSyntax();

    // Common functional interfaces
    System.out.println("\n--- Common Functional Interfaces ---");
    demonstrateFunctionalInterfaces();

    // With collections
    System.out.println("\n--- Lambdas with Collections ---");
    demonstrateWithCollections();

    // Method references
    System.out.println("\n--- Method References ---");
    demonstrateMethodReferences();
  }

  static void demonstrateBasicSyntax() {
    // No parameters
    Runnable r1 = () -> System.out.println("No parameters lambda");
    r1.run();

    // One parameter (parentheses optional)
    Consumer<String> c1 = s -> System.out.println("One param: " + s);
    c1.accept("Hello");

    // Multiple parameters
    BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
    System.out.println("Two params: 5 + 3 = " + add.apply(5, 3));

    // Block body with return
    BiFunction<Integer, Integer, Integer> max = (a, b) -> {
      if (a > b) return a;
      else return b;
    };
    System.out.println("Block body: max(5, 3) = " + max.apply(5, 3));
  }

  static void demonstrateFunctionalInterfaces() {
    // Predicate: T -> boolean
    Predicate<Integer> isEven = n -> n % 2 == 0;
    System.out.println("Predicate: isEven(4) = " + isEven.test(4));
    System.out.println("Predicate: isEven(5) = " + isEven.test(5));

    // Function: T -> R
    Function<String, Integer> length = s -> s.length();
    System.out.println("Function: length(\"Hello\") = " + length.apply("Hello"));

    // Consumer: T -> void
    Consumer<String> printer = s -> System.out.println("Consumer: " + s);
    printer.accept("Printed message");

    // Supplier: () -> T
    Supplier<Double> random = () -> Math.random();
    System.out.println("Supplier: random() = " + random.get());

    // BiFunction: (T, U) -> R
    BiFunction<String, String, String> concat = (a, b) -> a + b;
    System.out.println("BiFunction: concat = " + concat.apply("Hello, ", "World"));

    // Custom functional interface
    Calculator calc = (a, b) -> a * b;
    System.out.println("Custom: multiply(4, 5) = " + calc.calculate(4, 5));
  }

  static void demonstrateWithCollections() {
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

    // forEach with lambda
    System.out.print("forEach: ");
    names.forEach(name -> System.out.print(name + " "));
    System.out.println();

    // filter with predicate
    System.out.print("Filter (length > 4): ");
    names.stream()
        .filter(name -> name.length() > 4)
        .forEach(name -> System.out.print(name + " "));
    System.out.println();

    // map with function
    System.out.print("Map (uppercase): ");
    names.stream()
        .map(name -> name.toUpperCase())
        .forEach(name -> System.out.print(name + " "));
    System.out.println();

    // sort with comparator
    System.out.print("Sort (by length): ");
    names.stream()
        .sorted((a, b) -> a.length() - b.length())
        .forEach(name -> System.out.print(name + " "));
    System.out.println();
  }

  static void demonstrateMethodReferences() {
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

    // Instance method reference
    System.out.print("Method ref (println): ");
    names.forEach(System.out::println);

    // Static method reference
    Function<String, Integer> parser = Integer::parseInt;
    System.out.println("Static method ref: parseInt(\"42\") = " + parser.apply("42"));

    // Constructor reference
    Supplier<StringBuilder> sbSupplier = StringBuilder::new;
    StringBuilder sb = sbSupplier.get();
    sb.append("Constructor reference!");
    System.out.println("Constructor ref: " + sb);

    // Instance method of arbitrary object
    List<String> words = Arrays.asList("banana", "apple", "cherry");
    System.out.print("Sorted with method ref: ");
    words.stream()
        .sorted(String::compareToIgnoreCase)
        .forEach(w -> System.out.print(w + " "));
    System.out.println();
  }

  // Custom functional interface
  @FunctionalInterface
  interface Calculator {
    int calculate(int a, int b);
  }
}
