package com.paul.fullstackinterviewprep.javaversions;

import java.util.List;

/**
 * Java 23 - September 2024
 * Primitive Types in Patterns (preview), Module Import Declarations (preview),
 * Markdown Documentation Comments.
 */
public class Java23Features {

  public static void main(String[] args) {
    System.out.println("=== Java 23 Features ===\n");

    demonstratePrimitivePatterns();
    demonstrateModuleImports();
    demonstrateMarkdownDocs();
    demonstrateStreamGatherers();
  }

  static void demonstratePrimitivePatterns() {
    System.out.println("--- Primitive Types in Patterns (Preview) ---");

    // Pattern matching with primitive types
    System.out.println("Matching on primitive values in switch:");

    int[] statusCodes = {200, 301, 404, 500, 418};
    for (int code : statusCodes) {
      String message = switch (code) {
        case 200 -> "OK";
        case 301 -> "Moved Permanently";
        case 404 -> "Not Found";
        case 500 -> "Internal Server Error";
        case int c when c >= 400 && c < 500 -> "Client Error: " + c;
        case int c when c >= 500 -> "Server Error: " + c;
        default -> "Unknown: " + code;
      };
      System.out.println("  " + code + ": " + message);
    }

    // With numeric conversions (preview feature concept)
    System.out.println("\nNumeric pattern matching:");
    Number[] numbers = {42, 3.14, 100L, (short) 10};
    for (Number num : numbers) {
      String type = switch (num) {
        case Integer i -> "Integer: " + i;
        case Double d -> "Double: " + d;
        case Long l -> "Long: " + l;
        case Short s -> "Short: " + s;
        default -> "Other: " + num;
      };
      System.out.println("  " + type);
    }

    System.out.println();
  }

  static void demonstrateModuleImports() {
    System.out.println("--- Module Import Declarations (Preview) ---");

    // Preview feature - import entire module
    System.out.println("New syntax (preview):");
    System.out.println("  import module java.base;");
    System.out.println("  import module java.sql;");
    System.out.println();
    System.out.println("This imports all public classes from the module.");
    System.out.println("Reduces boilerplate when using many classes from a module.");

    // Example of classes that would be imported with 'import module java.base'
    System.out.println("\n'import module java.base' would provide access to:");
    System.out.println("  - java.util.* (List, Map, Set, etc.)");
    System.out.println("  - java.io.* (File, InputStream, etc.)");
    System.out.println("  - java.time.* (LocalDate, Duration, etc.)");
    System.out.println("  - java.util.stream.* (Stream, Collectors, etc.)");
    System.out.println("  - and many more...");

    System.out.println();
  }

  static void demonstrateMarkdownDocs() {
    System.out.println("--- Markdown Documentation Comments ---");

    System.out.println("Java 23 supports Markdown syntax in JavaDoc!");
    System.out.println();
    System.out.println("Old style:");
    System.out.println("  /**");
    System.out.println("   * Calculates the sum of two numbers.");
    System.out.println("   * @param a the first number");
    System.out.println("   * @param b the second number");
    System.out.println("   * @return the sum of a and b");
    System.out.println("   */");
    System.out.println();
    System.out.println("New Markdown style (with ///):");
    System.out.println("  /// # Calculate Sum");
    System.out.println("  ///");
    System.out.println("  /// Calculates the **sum** of two numbers.");
    System.out.println("  ///");
    System.out.println("  /// ## Parameters");
    System.out.println("  /// - `a`: the first number");
    System.out.println("  /// - `b`: the second number");
    System.out.println("  ///");
    System.out.println("  /// ## Returns");
    System.out.println("  /// The sum of a and b");
    System.out.println("  ///");
    System.out.println("  /// ## Example");
    System.out.println("  /// ```java");
    System.out.println("  /// int result = sum(2, 3); // returns 5");
    System.out.println("  /// ```");

    // Actual method with Markdown docs (see below)
    int result = sum(5, 3);
    System.out.println("\nsum(5, 3) = " + result);

    System.out.println();
  }

  /// # Calculate Sum
  ///
  /// Calculates the **sum** of two numbers.
  ///
  /// ## Parameters
  /// - `a`: the first number
  /// - `b`: the second number
  ///
  /// ## Returns
  /// The sum of a and b
  ///
  /// ## Example
  /// ```java
  /// int result = sum(2, 3); // returns 5
  /// ```
  static int sum(int a, int b) {
    return a + b;
  }

  static void demonstrateStreamGatherers() {
    System.out.println("--- Stream Gatherers (Second Preview) ---");

    // Stream Gatherers provide custom intermediate operations
    System.out.println("Stream Gatherers allow custom intermediate operations.");
    System.out.println("Built-in gatherers (preview):");
    System.out.println("  - Gatherers.windowFixed(size)");
    System.out.println("  - Gatherers.windowSliding(size)");
    System.out.println("  - Gatherers.fold(...)");
    System.out.println("  - Gatherers.scan(...)");

    // Simulating window operations (actual Gatherers API is preview)
    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    System.out.println("\nOriginal list: " + numbers);

    // Simulated fixed window of size 3
    System.out.println("\nFixed window (size 3) - concept:");
    System.out.println("  [1, 2, 3]");
    System.out.println("  [4, 5, 6]");
    System.out.println("  [7, 8, 9]");
    System.out.println("  [10]");

    // Simulated sliding window of size 3
    System.out.println("\nSliding window (size 3) - concept:");
    System.out.println("  [1, 2, 3]");
    System.out.println("  [2, 3, 4]");
    System.out.println("  [3, 4, 5]");
    System.out.println("  ... and so on");

    // When Gatherers are standard, you'll be able to:
    System.out.println("\nWith Gatherers API:");
    System.out.println("  numbers.stream()");
    System.out.println("      .gather(Gatherers.windowSliding(3))");
    System.out.println("      .toList();");

    System.out.println();
  }
}
