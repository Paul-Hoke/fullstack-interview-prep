package com.paul.fullstackinterviewprep.javaversions;

import java.util.List;
import java.util.Map;

/**
 * Java 22 - March 2024
 * Unnamed Variables, Statements Before super() (preview), Stream Gatherers (preview).
 */
public class Java22Features {

  public static void main(String[] args) {
    System.out.println("=== Java 22 Features ===\n");

    demonstrateUnnamedVariables();
    demonstrateUnnamedPatterns();
    demonstrateStatementsBeforeSuper();
  }

  static void demonstrateUnnamedVariables() {
    System.out.println("--- Unnamed Variables (_) ---");

    // In catch blocks - when exception isn't used
    try {
      Integer.parseInt("not a number");
    } catch (NumberFormatException _) {
      System.out.println("Parse failed (exception not needed)");
    }

    // In for-each - when element isn't needed
    List<String> items = List.of("a", "b", "c", "d", "e");
    int count = 0;
    for (String _ : items) {
      count++;
    }
    System.out.println("Count: " + count);

    // In lambdas - unused parameters
    Map<String, Integer> scores = Map.of("Alice", 95, "Bob", 87, "Charlie", 92);
    scores.forEach((_, value) -> System.out.println("Score: " + value));

    // Multiple unused variables
    try {
      throw new Exception("Error 1", new RuntimeException("Cause"));
    } catch (Exception _) {
      System.out.println("Caught and ignored");
    }

    // In try-with-resources - when resource not directly used
    System.out.println("\nIn try-with-resources:");
    try (var _ = new AutoCloseable() {
      {
        System.out.println("  Resource opened");
      }

      @Override
      public void close() {
        System.out.println("  Resource closed");
      }
    }) {
      System.out.println("  Doing work...");
    } catch (Exception e) {
      // ignore
    }

    // In enhanced for with index (workaround pattern)
    System.out.println("\nIterating without needing element:");
    int index = 0;
    for (String _ : List.of("x", "y", "z")) {
      System.out.println("  Processing index " + index++);
    }

    System.out.println();
  }

  static void demonstrateUnnamedPatterns() {
    System.out.println("--- Unnamed Patterns in switch/instanceof ---");

    record Point(int x, int y) {}
    record Line(Point start, Point end) {}

    // When you only need some components
    Object obj = new Line(new Point(0, 0), new Point(10, 20));

    if (obj instanceof Line(Point(int x1, int y1), _)) {
      System.out.println("Line starting at (" + x1 + ", " + y1 + ")");
      // Don't care about end point
    }

    // In switch expressions
    sealed interface Shape permits Circ, Rect, Triangle {}
    record Circ(int radius) implements Shape {}
    record Rect(int width, int height) implements Shape {}
    record Triangle(int base, int height) implements Shape {}

    Shape[] shapes = {new Circ(5), new Rect(4, 6), new Triangle(3, 8)};

    System.out.println("\nChecking shape types:");
    for (Shape shape : shapes) {
      String name = switch (shape) {
        case Circ(_) -> "Circle";
        case Rect(_, _) -> "Rectangle";
        case Triangle(_, _) -> "Triangle";
      };
      System.out.println("  " + name);
    }

    // When you only care about one field
    System.out.println("\nExtracting specific values:");
    for (Shape shape : shapes) {
      String info = switch (shape) {
        case Circ(int r) -> "Radius: " + r;
        case Rect(int w, _) -> "Width: " + w;  // Only care about width
        case Triangle(_, int h) -> "Height: " + h;  // Only care about height
      };
      System.out.println("  " + info);
    }

    System.out.println();
  }

  static void demonstrateStatementsBeforeSuper() {
    System.out.println("--- Statements Before super() (Preview) ---");

    // Traditional: super() must be first statement
    System.out.println("Traditional constructor:");
    System.out.println("  super() or this() must be first statement");
    System.out.println("  Validation happens after object creation");

    // Java 22 Preview: Can have statements before super()
    // This is a preview feature, showing the concept:
    System.out.println("\nJava 22 (Preview) allows:");
    System.out.println("  - Validation before super()");
    System.out.println("  - Compute values before super()");
    System.out.println("  - Logging before super()");

    // Conceptual example (actual syntax requires preview mode)
    /*
    class PositiveInteger extends Number {
        int value;

        PositiveInteger(int value) {
            if (value <= 0) {
                throw new IllegalArgumentException("Must be positive");
            }
            super();  // Now can come after statements!
            this.value = value;
        }
    }
    */

    // Workaround we can demonstrate
    System.out.println("\nDemonstration using static validation:");
    try {
      new ValidatedValue(-5);
    } catch (IllegalArgumentException e) {
      System.out.println("  Validation failed: " + e.getMessage());
    }

    var valid = new ValidatedValue(42);
    System.out.println("  Valid value created: " + valid.getValue());

    System.out.println();
  }

  static class BaseClass {
    BaseClass() {
      System.out.println("  BaseClass constructor called");
    }
  }

  static class ValidatedValue extends BaseClass {
    private final int value;

    ValidatedValue(int value) {
      super();  // In Java 22 preview, validation can come before this
      this.value = validate(value);
      System.out.println("  ValidatedValue constructor completed");
    }

    private static int validate(int value) {
      if (value < 0) {
        throw new IllegalArgumentException("Value must be non-negative");
      }
      return value;
    }

    int getValue() {
      return value;
    }
  }
}
