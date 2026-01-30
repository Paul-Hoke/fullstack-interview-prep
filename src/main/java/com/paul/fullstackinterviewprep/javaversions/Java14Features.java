package com.paul.fullstackinterviewprep.javaversions;

import java.util.List;

/**
 * Java 14 - March 2020
 * Records (preview), Pattern Matching for instanceof (preview),
 * Helpful NullPointerExceptions, Switch Expressions (standard).
 */
public class Java14Features {

  public static void main(String[] args) {
    System.out.println("=== Java 14 Features ===\n");

    demonstrateRecords();
    demonstratePatternMatchingInstanceof();
    demonstrateHelpfulNPE();
    demonstrateSwitchExpressions();
  }

  static void demonstrateRecords() {
    System.out.println("--- Records (Preview in 14, Standard in 16) ---");

    // Record declaration - immutable data carrier
    // record Point(int x, int y) {} - defined below as static

    // Creating record instances
    Point p1 = new Point(3, 4);
    Point p2 = new Point(3, 4);
    Point p3 = new Point(0, 0);

    System.out.println("Point p1 = new Point(3, 4)");
    System.out.println("Point p2 = new Point(3, 4)");
    System.out.println("Point p3 = new Point(0, 0)");

    // Accessor methods (no 'get' prefix)
    System.out.println("\nAccessor methods:");
    System.out.println("  p1.x(): " + p1.x());
    System.out.println("  p1.y(): " + p1.y());

    // Auto-generated toString
    System.out.println("\ntoString():");
    System.out.println("  p1: " + p1);

    // Auto-generated equals
    System.out.println("\nequals():");
    System.out.println("  p1.equals(p2): " + p1.equals(p2));
    System.out.println("  p1.equals(p3): " + p1.equals(p3));

    // Auto-generated hashCode
    System.out.println("\nhashCode():");
    System.out.println("  p1.hashCode(): " + p1.hashCode());
    System.out.println("  p2.hashCode(): " + p2.hashCode());

    // Record with validation (compact constructor)
    Person person = new Person("Alice", 25);
    System.out.println("\nPerson with validation: " + person);

    try {
      new Person("", 25);
    } catch (IllegalArgumentException e) {
      System.out.println("Validation error: " + e.getMessage());
    }

    // Record with additional methods
    System.out.println("\nRecord with methods:");
    System.out.println("  p1.distanceFromOrigin(): " + p1.distanceFromOrigin());

    System.out.println();
  }

  // Record definition
  record Point(int x, int y) {
    // Additional method
    double distanceFromOrigin() {
      return Math.sqrt(x * x + y * y);
    }
  }

  // Record with compact constructor for validation
  record Person(String name, int age) {
    // Compact constructor - no parameter list
    Person {
      if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Name cannot be blank");
      }
      if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
      }
    }
  }

  static void demonstratePatternMatchingInstanceof() {
    System.out.println("--- Pattern Matching for instanceof (Preview in 14, Standard in 16) ---");

    // Traditional instanceof (before Java 14)
    Object obj = "Hello, World!";
    if (obj instanceof String) {
      String s = (String) obj;  // Explicit cast required
      System.out.println("Traditional: length = " + s.length());
    }

    // Pattern matching instanceof (Java 14+)
    if (obj instanceof String s) {  // Pattern variable 's'
      System.out.println("Pattern matching: length = " + s.length());
      System.out.println("  uppercase = " + s.toUpperCase());
    }

    // Works with else branch too
    Object number = 42;
    if (number instanceof Integer i) {
      System.out.println("Integer: " + i + " * 2 = " + (i * 2));
    } else {
      System.out.println("Not an integer");
    }

    // In complex conditions
    Object value = List.of(1, 2, 3);
    if (value instanceof List<?> list && !list.isEmpty()) {
      System.out.println("Non-empty list with " + list.size() + " elements");
    }

    // Multiple type checks
    Object[] objects = {"Hello", 42, 3.14, true};
    System.out.println("\nProcessing different types:");
    for (Object o : objects) {
      String result;
      if (o instanceof String s) {
        result = "String: \"" + s + "\"";
      } else if (o instanceof Integer i) {
        result = "Integer: " + i;
      } else if (o instanceof Double d) {
        result = "Double: " + d;
      } else if (o instanceof Boolean b) {
        result = "Boolean: " + b;
      } else {
        result = "Unknown type";
      }
      System.out.println("  " + result);
    }

    System.out.println();
  }

  static void demonstrateHelpfulNPE() {
    System.out.println("--- Helpful NullPointerExceptions ---");

    System.out.println("Before Java 14:");
    System.out.println("  NullPointerException at line X");
    System.out.println("\nJava 14+ with -XX:+ShowCodeDetailsInExceptionMessages:");
    System.out.println("  Cannot invoke \"String.length()\" because \"str\" is null");

    // Demonstration
    try {
      Container container = new Container(null);
      int length = container.value().length();  // NPE here
    } catch (NullPointerException e) {
      System.out.println("\nActual NPE message:");
      System.out.println("  " + e.getMessage());
    }

    // Chained calls
    try {
      Outer outer = new Outer(new Middle(null));
      outer.middle().inner().value();  // NPE in chain
    } catch (NullPointerException e) {
      System.out.println("\nChained call NPE:");
      System.out.println("  " + e.getMessage());
    }

    System.out.println();
  }

  record Container(String value) {}
  record Inner(String value) {}
  record Middle(Inner inner) {}
  record Outer(Middle middle) {}

  static void demonstrateSwitchExpressions() {
    System.out.println("--- Switch Expressions (Standard in Java 14) ---");

    // Now standard, not preview
    DayOfWeek day = DayOfWeek.WEDNESDAY;

    String activity = switch (day) {
      case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Work";
      case SATURDAY -> "Relax";
      case SUNDAY -> "Rest";
    };
    System.out.println(day + ": " + activity);

    // Exhaustiveness check
    int numLetters = switch (day) {
      case MONDAY -> 6;
      case TUESDAY -> 7;
      case WEDNESDAY -> 9;
      case THURSDAY -> 8;
      case FRIDAY -> 6;
      case SATURDAY -> 8;
      case SUNDAY -> 6;
    };  // Compiler ensures all cases are covered
    System.out.println(day + " has " + numLetters + " letters");

    System.out.println();
  }

  enum DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
  }
}
