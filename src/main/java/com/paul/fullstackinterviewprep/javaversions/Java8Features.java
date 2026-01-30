package com.paul.fullstackinterviewprep.javaversions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Java 8 (LTS) - March 2014
 * The most significant Java release, introducing functional programming.
 */
public class Java8Features {

  public static void main(String[] args) {
    System.out.println("=== Java 8 Features ===\n");

    demonstrateLambdaExpressions();
    demonstrateStreamApi();
    demonstrateOptional();
    demonstrateMethodReferences();
    demonstrateDefaultMethods();
    demonstrateFunctionalInterfaces();
    demonstrateDateTimeApi();
  }

  static void demonstrateLambdaExpressions() {
    System.out.println("--- Lambda Expressions ---");

    List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

    // Before Java 8 - Anonymous inner class
    System.out.println("Before Java 8 (anonymous class):");
    names.forEach(new Consumer<String>() {
      @Override
      public void accept(String name) {
        System.out.println("  " + name);
      }
    });

    // Java 8 - Lambda expression
    System.out.println("Java 8 (lambda):");
    names.forEach(name -> System.out.println("  " + name));

    // Lambda with multiple statements
    names.forEach(name -> {
      String upper = name.toUpperCase();
      System.out.println("  Uppercase: " + upper);
    });

    System.out.println();
  }

  static void demonstrateStreamApi() {
    System.out.println("--- Stream API ---");

    List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

    // Filter
    List<String> longNames = names.stream()
        .filter(n -> n.length() > 3)
        .collect(Collectors.toList());
    System.out.println("Names with length > 3: " + longNames);

    // Map
    List<String> upperNames = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    System.out.println("Uppercase names: " + upperNames);

    // Filter + Map + Collect
    List<String> result = names.stream()
        .filter(n -> n.startsWith("A") || n.startsWith("C"))
        .map(String::toLowerCase)
        .collect(Collectors.toList());
    System.out.println("Starts with A or C (lowercase): " + result);

    // Reduce
    int totalLength = names.stream()
        .mapToInt(String::length)
        .sum();
    System.out.println("Total length of all names: " + totalLength);

    // Count
    long count = names.stream()
        .filter(n -> n.contains("e"))
        .count();
    System.out.println("Names containing 'e': " + count);

    System.out.println();
  }

  static void demonstrateOptional() {
    System.out.println("--- Optional ---");

    // Creating Optional
    Optional<String> present = Optional.of("Hello");
    Optional<String> empty = Optional.empty();
    Optional<String> nullable = Optional.ofNullable(null);

    System.out.println("present.isPresent(): " + present.isPresent());
    System.out.println("empty.isPresent(): " + empty.isPresent());
    System.out.println("nullable.isPresent(): " + nullable.isPresent());

    // Getting values safely
    System.out.println("present.get(): " + present.get());
    System.out.println("empty.orElse(\"default\"): " + empty.orElse("default"));
    System.out.println("nullable.orElseGet(() -> \"computed\"): " + nullable.orElseGet(() -> "computed"));

    // Transforming with map
    Optional<Integer> length = present.map(String::length);
    System.out.println("present.map(length): " + length.orElse(0));

    // Chaining
    String result = Optional.of("  hello  ")
        .map(String::trim)
        .map(String::toUpperCase)
        .orElse("");
    System.out.println("Chained transformations: " + result);

    System.out.println();
  }

  static void demonstrateMethodReferences() {
    System.out.println("--- Method References ---");

    List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

    // Static method reference
    System.out.println("Static method reference (Integer::parseInt):");
    List<String> numbers = Arrays.asList("1", "2", "3");
    List<Integer> parsed = numbers.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    System.out.println("  Parsed: " + parsed);

    // Instance method reference
    System.out.println("Instance method reference (String::toUpperCase):");
    names.stream()
        .map(String::toUpperCase)
        .forEach(n -> System.out.println("  " + n));

    // Constructor reference
    System.out.println("Constructor reference (StringBuilder::new):");
    List<StringBuilder> builders = names.stream()
        .map(StringBuilder::new)
        .collect(Collectors.toList());
    System.out.println("  Created " + builders.size() + " StringBuilders");

    System.out.println();
  }

  static void demonstrateDefaultMethods() {
    System.out.println("--- Default Methods in Interfaces ---");

    Greeting english = new EnglishGreeting();
    Greeting spanish = new SpanishGreeting();

    english.greet("World");
    english.greetWithTime("World");  // Uses default method

    spanish.greet("Mundo");
    spanish.greetWithTime("Mundo");  // Uses default method

    System.out.println();
  }

  interface Greeting {
    void greet(String name);

    // Default method - new in Java 8
    default void greetWithTime(String name) {
      System.out.println("  [" + LocalDateTime.now().toLocalTime() + "] ");
      greet(name);
    }

    // Static method in interface - new in Java 8
    static void printWelcome() {
      System.out.println("Welcome!");
    }
  }

  static class EnglishGreeting implements Greeting {
    @Override
    public void greet(String name) {
      System.out.println("  Hello, " + name + "!");
    }
  }

  static class SpanishGreeting implements Greeting {
    @Override
    public void greet(String name) {
      System.out.println("  Hola, " + name + "!");
    }
  }

  static void demonstrateFunctionalInterfaces() {
    System.out.println("--- Functional Interfaces ---");

    // Predicate<T> - T -> boolean
    Predicate<Integer> isEven = n -> n % 2 == 0;
    System.out.println("isEven(4): " + isEven.test(4));
    System.out.println("isEven(5): " + isEven.test(5));

    // Function<T, R> - T -> R
    Function<String, Integer> length = String::length;
    System.out.println("length(\"Hello\"): " + length.apply("Hello"));

    // Consumer<T> - T -> void
    Consumer<String> printer = s -> System.out.println("  Consuming: " + s);
    printer.accept("Test message");

    // Composing predicates
    Predicate<Integer> isPositive = n -> n > 0;
    Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
    System.out.println("isEvenAndPositive(4): " + isEvenAndPositive.test(4));
    System.out.println("isEvenAndPositive(-4): " + isEvenAndPositive.test(-4));

    System.out.println();
  }

  static void demonstrateDateTimeApi() {
    System.out.println("--- New Date/Time API (java.time) ---");

    // LocalDate
    LocalDate today = LocalDate.now();
    LocalDate birthday = LocalDate.of(1990, Month.JUNE, 15);
    System.out.println("Today: " + today);
    System.out.println("Birthday: " + birthday);

    // LocalDateTime
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime meeting = LocalDateTime.of(2024, 12, 25, 10, 30, 0);
    System.out.println("Now: " + now);
    System.out.println("Meeting: " + meeting);

    // Formatting
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    System.out.println("Formatted: " + now.format(formatter));

    // Date arithmetic
    LocalDate nextWeek = today.plusWeeks(1);
    LocalDate lastMonth = today.minusMonths(1);
    System.out.println("Next week: " + nextWeek);
    System.out.println("Last month: " + lastMonth);

    System.out.println();
  }
}
