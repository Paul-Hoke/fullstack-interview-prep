package com.paul.fullstackinterviewprep.javaversions;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * Java 17 (LTS) - September 2021
 * Sealed Classes (standard), Pattern Matching for switch (preview),
 * Enhanced Pseudo-Random Number Generators.
 */
public class Java17Features {

  public static void main(String[] args) {
    System.out.println("=== Java 17 Features (LTS) ===\n");

    demonstrateSealedClassesStandard();
    demonstratePatternMatchingSwitch();
    demonstrateEnhancedRandomGenerators();
    demonstrateHexFormatting();
  }

  static void demonstrateSealedClassesStandard() {
    System.out.println("--- Sealed Classes (Standard) ---");

    // Sealed classes are now standard
    Result<String> success = new Success<>("Data loaded successfully");
    Result<String> failure = new Failure<>("Connection timeout");

    System.out.println("Success: " + processResult(success));
    System.out.println("Failure: " + processResult(failure));

    // Sealed interface with records
    Expression expr = new Add(
        new Multiply(new Literal(2), new Literal(3)),
        new Literal(4)
    );
    System.out.println("\nExpression: (2 * 3) + 4 = " + evaluate(expr));

    // Sealed classes with switch
    System.out.println("\nExhaustive switch on sealed types:");
    for (var status : new Status[]{new Active(), new Inactive("Vacation"), new Pending(5)}) {
      System.out.println("  " + getStatusMessage(status));
    }

    System.out.println();
  }

  // Sealed interface - only Success and Failure can implement
  sealed interface Result<T> permits Success, Failure {}

  record Success<T>(T value) implements Result<T> {}
  record Failure<T>(String error) implements Result<T> {}

  static <T> String processResult(Result<T> result) {
    return switch (result) {
      case Success<T> s -> "Success: " + s.value();
      case Failure<T> f -> "Error: " + f.error();
    };
  }

  // Expression tree with sealed types
  sealed interface Expression permits Literal, Add, Multiply {}

  record Literal(int value) implements Expression {}
  record Add(Expression left, Expression right) implements Expression {}
  record Multiply(Expression left, Expression right) implements Expression {}

  static int evaluate(Expression expr) {
    return switch (expr) {
      case Literal(int value) -> value;
      case Add(var left, var right) -> evaluate(left) + evaluate(right);
      case Multiply(var left, var right) -> evaluate(left) * evaluate(right);
    };
  }

  // Status sealed hierarchy
  sealed interface Status permits Active, Inactive, Pending {}
  record Active() implements Status {}
  record Inactive(String reason) implements Status {}
  record Pending(int daysRemaining) implements Status {}

  static String getStatusMessage(Status status) {
    return switch (status) {
      case Active() -> "Account is active";
      case Inactive(String reason) -> "Inactive: " + reason;
      case Pending(int days) -> "Pending: " + days + " days remaining";
    };
  }

  static void demonstratePatternMatchingSwitch() {
    System.out.println("--- Pattern Matching for switch ---");

    // Type patterns in switch
    Object[] objects = {"Hello", 42, 3.14, null, true};

    for (Object obj : objects) {
      String result = switch (obj) {
        case null -> "null";
        case String s -> "String: \"" + s + "\"";
        case Integer i -> "Integer: " + i;
        case Double d -> "Double: " + d;
        case Boolean b -> "Boolean: " + b;
        default -> "Unknown";
      };
      System.out.println(result);
    }

    // Guarded patterns
    System.out.println("\nGuarded patterns (when clause):");
    for (int i : new int[]{-5, 0, 5, 100}) {
      String category = switch (i) {
        case int n when n < 0 -> "negative";
        case int n when n == 0 -> "zero";
        case int n when n < 10 -> "small positive";
        default -> "large positive";
      };
      System.out.println("  " + i + " is " + category);
    }

    // Null handling in switch
    System.out.println("\nNull handling in switch:");
    String str = null;
    String result = switch (str) {
      case null -> "null string";
      case String s when s.isEmpty() -> "empty string";
      case String s -> "string: " + s;
    };
    System.out.println("  " + result);

    System.out.println();
  }

  static void demonstrateEnhancedRandomGenerators() {
    System.out.println("--- Enhanced Pseudo-Random Number Generators ---");

    // New RandomGenerator interface
    RandomGenerator random = RandomGenerator.getDefault();
    System.out.println("Default generator: " + random.getClass().getSimpleName());

    System.out.print("Random ints: ");
    random.ints(5, 1, 100).forEach(n -> System.out.print(n + " "));
    System.out.println();

    // List available algorithms
    System.out.println("\nAvailable algorithms:");
    RandomGeneratorFactory.all()
        .limit(5)
        .forEach(factory -> System.out.println("  " + factory.name()));

    // Use specific algorithm
    RandomGenerator xoroshiro = RandomGenerator.of("Xoroshiro128PlusPlus");
    System.out.println("\nUsing Xoroshiro128PlusPlus:");
    System.out.print("  Random doubles: ");
    xoroshiro.doubles(3, 0, 1)
        .mapToObj(d -> String.format("%.3f", d))
        .forEach(s -> System.out.print(s + " "));
    System.out.println();

    // Jumpable generators (for parallel processing)
    System.out.println("\nJumpable generators allow creating independent streams");
    System.out.println("for parallel processing without correlation.");

    System.out.println();
  }

  static void demonstrateHexFormatting() {
    System.out.println("--- HexFormat Utility ---");

    java.util.HexFormat hex = java.util.HexFormat.of();
    java.util.HexFormat hexUpper = java.util.HexFormat.of().withUpperCase();
    java.util.HexFormat hexDelim = java.util.HexFormat.ofDelimiter(":");

    byte[] bytes = {0x48, 0x65, 0x6c, 0x6c, 0x6f};  // "Hello"

    System.out.println("Byte array: " + new String(bytes));
    System.out.println("Hex (lowercase): " + hex.formatHex(bytes));
    System.out.println("Hex (uppercase): " + hexUpper.formatHex(bytes));
    System.out.println("Hex (with delimiter): " + hexDelim.formatHex(bytes));

    // Parse hex string to bytes
    byte[] parsed = hex.parseHex("48656c6c6f");
    System.out.println("Parsed from hex: " + new String(parsed));

    // Single value formatting
    System.out.println("\nSingle value formatting:");
    System.out.println("  toHexDigits(255): " + hex.toHexDigits(255));
    System.out.println("  toHexDigits(16): " + hex.toHexDigits((byte) 16));

    System.out.println();
  }
}
