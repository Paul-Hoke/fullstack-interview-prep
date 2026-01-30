package com.paul.fullstackinterviewprep.javaversions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Java 10 - March 2018
 * Local variable type inference (var keyword).
 */
public class Java10Features {

  public static void main(String[] args) {
    System.out.println("=== Java 10 Features ===\n");

    demonstrateVarKeyword();
    demonstrateUnmodifiableCollections();
    demonstrateOptionalOrElseThrow();
  }

  static void demonstrateVarKeyword() {
    System.out.println("--- Local Variable Type Inference (var) ---");

    // Basic usage - type inferred from right side
    var message = "Hello, World!";  // String
    var number = 42;                 // int
    var pi = 3.14159;               // double

    System.out.println("var message = \"Hello, World!\": " + message + " (type: String)");
    System.out.println("var number = 42: " + number + " (type: int)");
    System.out.println("var pi = 3.14159: " + pi + " (type: double)");

    // With complex types - reduces verbosity
    var list = new ArrayList<String>();  // ArrayList<String>
    list.add("Apple");
    list.add("Banana");
    System.out.println("var list = new ArrayList<String>(): " + list);

    var map = new HashMap<String, List<Integer>>();  // HashMap<String, List<Integer>>
    map.put("numbers", List.of(1, 2, 3));
    System.out.println("var map = new HashMap<String, List<Integer>>(): " + map);

    // With streams
    var names = List.of("Alice", "Bob", "Charlie");
    var upperNames = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    System.out.println("var upperNames = stream result: " + upperNames);

    // In for loops
    System.out.print("var in for-each loop: ");
    for (var name : names) {
      System.out.print(name + " ");
    }
    System.out.println();

    // In traditional for loop
    System.out.print("var in traditional for loop: ");
    for (var i = 0; i < 5; i++) {
      System.out.print(i + " ");
    }
    System.out.println();

    // Limitations
    System.out.println("\n--- var Limitations ---");
    System.out.println("Cannot use var for:");
    System.out.println("  - Class fields");
    System.out.println("  - Method parameters");
    System.out.println("  - Method return types");
    System.out.println("  - Variables without initializer: var x; (error)");
    System.out.println("  - null initialization: var x = null; (error)");
    System.out.println("  - Lambda expressions: var f = (x) -> x; (error)");
    System.out.println("  - Array initializers: var arr = {1, 2, 3}; (error)");

    System.out.println();
  }

  static void demonstrateUnmodifiableCollections() {
    System.out.println("--- Unmodifiable Collections (copyOf) ---");

    // Create modifiable list
    List<String> original = new ArrayList<>();
    original.add("A");
    original.add("B");
    original.add("C");

    // Create unmodifiable copy
    List<String> copy = List.copyOf(original);

    System.out.println("Original list: " + original);
    System.out.println("Copied list: " + copy);

    // Modify original
    original.add("D");
    System.out.println("After adding 'D' to original:");
    System.out.println("  Original: " + original);
    System.out.println("  Copy: " + copy + " (unchanged - it's a copy)");

    // Try to modify copy
    try {
      copy.add("E");
    } catch (UnsupportedOperationException e) {
      System.out.println("Cannot modify copy: UnsupportedOperationException thrown");
    }

    // Same for Set and Map
    var setCopy = Set.copyOf(java.util.Set.of(1, 2, 3));
    var mapCopy = Map.copyOf(Map.of("key", "value"));
    System.out.println("Set.copyOf(): " + setCopy);
    System.out.println("Map.copyOf(): " + mapCopy);

    System.out.println();
  }

  static void demonstrateOptionalOrElseThrow() {
    System.out.println("--- Optional.orElseThrow() ---");

    Optional<String> present = Optional.of("Hello");
    Optional<String> empty = Optional.empty();

    // orElseThrow() with no arguments - throws NoSuchElementException
    String value = present.orElseThrow();
    System.out.println("present.orElseThrow(): " + value);

    try {
      empty.orElseThrow();  // Throws NoSuchElementException
    } catch (java.util.NoSuchElementException e) {
      System.out.println("empty.orElseThrow(): NoSuchElementException thrown");
    }

    // Compare with orElseThrow(Supplier) from Java 8
    try {
      empty.orElseThrow(() -> new IllegalStateException("Value not found"));
    } catch (IllegalStateException e) {
      System.out.println("empty.orElseThrow(supplier): " + e.getMessage());
    }

    System.out.println();
  }
}
