package com.paul.fullstackinterviewprep.javaversions;

import java.util.List;
import java.util.stream.Stream;

/**
 * Java 16 - March 2021
 * Records (standard), Pattern Matching instanceof (standard), Stream.toList().
 */
public class Java16Features {

  public static void main(String[] args) {
    System.out.println("=== Java 16 Features ===\n");

    demonstrateRecordsStandard();
    demonstratePatternMatchingStandard();
    demonstrateStreamToList();
    demonstrateDayPeriodSupport();
  }

  static void demonstrateRecordsStandard() {
    System.out.println("--- Records (Standard) ---");

    // Records are now standard (not preview)
    var user = new User("alice", "alice@example.com", 25);
    System.out.println("User: " + user);
    System.out.println("Username: " + user.username());
    System.out.println("Email: " + user.email());

    // Local records (records inside methods)
    record Point(int x, int y) {
      double distanceTo(Point other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
      }
    }

    var p1 = new Point(0, 0);
    var p2 = new Point(3, 4);
    System.out.println("\nLocal record Point: " + p1 + " to " + p2);
    System.out.println("Distance: " + p1.distanceTo(p2));

    // Records can implement interfaces
    record TimestampedMessage(String content, long timestamp) implements Printable {
      @Override
      public String toPrintableString() {
        return "[" + timestamp + "] " + content;
      }
    }

    var msg = new TimestampedMessage("Hello", System.currentTimeMillis());
    System.out.println("\nRecord implementing interface:");
    System.out.println(msg.toPrintableString());

    // Records with static members
    System.out.println("\nStatic factory: " + User.anonymous());

    System.out.println();
  }

  record User(String username, String email, int age) {
    // Compact constructor for validation
    public User {
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username required");
      }
      if (age < 0) {
        throw new IllegalArgumentException("Age must be non-negative");
      }
      // email can be null (optional)
    }

    // Static factory method
    public static User anonymous() {
      return new User("anonymous", null, 0);
    }

    // Additional instance method
    public boolean isAdult() {
      return age >= 18;
    }
  }

  interface Printable {
    String toPrintableString();
  }

  static void demonstratePatternMatchingStandard() {
    System.out.println("--- Pattern Matching for instanceof (Standard) ---");

    // Pattern matching is now standard
    Object[] values = {"Hello", 42, 3.14, List.of(1, 2, 3), null};

    for (Object value : values) {
      String result = switch (value) {
        case null -> "null value";
        case String s -> "String of length " + s.length();
        case Integer i -> "Integer: " + i;
        case Double d -> "Double: " + d;
        case List<?> list -> "List with " + list.size() + " elements";
        default -> "Unknown type: " + value.getClass().getSimpleName();
      };
      System.out.println(result);
    }

    // Pattern variable scope
    Object obj = "test";
    if (!(obj instanceof String s)) {
      System.out.println("Not a string");
      return;
    }
    // s is in scope here because we would have returned if it wasn't a String
    System.out.println("\nString value: " + s.toUpperCase());

    System.out.println();
  }

  static void demonstrateStreamToList() {
    System.out.println("--- Stream.toList() ---");

    // Before Java 16
    List<String> oldWay = Stream.of("a", "b", "c")
        .map(String::toUpperCase)
        .collect(java.util.stream.Collectors.toList());
    System.out.println("collect(Collectors.toList()): " + oldWay);

    // Java 16 - simpler toList()
    List<String> newWay = Stream.of("a", "b", "c")
        .map(String::toUpperCase)
        .toList();
    System.out.println("toList(): " + newWay);

    // Note: toList() returns an unmodifiable list
    try {
      newWay.add("D");
    } catch (UnsupportedOperationException e) {
      System.out.println("toList() returns unmodifiable list: UnsupportedOperationException");
    }

    // Practical example
    List<Integer> evenSquares = Stream.iterate(1, n -> n + 1)
        .limit(10)
        .filter(n -> n % 2 == 0)
        .map(n -> n * n)
        .toList();
    System.out.println("Even squares (1-10): " + evenSquares);

    // Works with parallel streams too
    List<String> parallel = Stream.of("apple", "banana", "cherry")
        .parallel()
        .map(String::toUpperCase)
        .toList();
    System.out.println("Parallel stream toList(): " + parallel);

    System.out.println();
  }

  static void demonstrateDayPeriodSupport() {
    System.out.println("--- Day Period Support ---");

    // New pattern 'B' for day period (morning, afternoon, evening, night)
    var formatter = java.time.format.DateTimeFormatter.ofPattern("h:mm B");

    var times = List.of(
        java.time.LocalTime.of(6, 0),
        java.time.LocalTime.of(10, 30),
        java.time.LocalTime.of(14, 0),
        java.time.LocalTime.of(18, 30),
        java.time.LocalTime.of(22, 0)
    );

    System.out.println("Time formatting with day period (B pattern):");
    for (var time : times) {
      System.out.println("  " + time + " -> " + formatter.format(time));
    }

    System.out.println();
  }
}
