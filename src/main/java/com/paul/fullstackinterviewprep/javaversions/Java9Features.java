package com.paul.fullstackinterviewprep.javaversions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 9 - September 2017
 * Module system, collection factories, stream improvements.
 */
public class Java9Features {

  public static void main(String[] args) {
    System.out.println("=== Java 9 Features ===\n");

    demonstrateCollectionFactoryMethods();
    demonstrateStreamImprovements();
    demonstrateOptionalImprovements();
    demonstratePrivateInterfaceMethods();
    demonstrateTryWithResourcesImprovement();
  }

  static void demonstrateCollectionFactoryMethods() {
    System.out.println("--- Collection Factory Methods ---");

    // Immutable List
    List<String> list = List.of("A", "B", "C");
    System.out.println("List.of(\"A\", \"B\", \"C\"): " + list);

    // Immutable Set
    Set<Integer> set = Set.of(1, 2, 3, 4, 5);
    System.out.println("Set.of(1, 2, 3, 4, 5): " + set);

    // Immutable Map
    Map<String, Integer> map = Map.of(
        "one", 1,
        "two", 2,
        "three", 3
    );
    System.out.println("Map.of(...): " + map);

    // Map.ofEntries for more than 10 entries
    Map<String, Integer> largerMap = Map.ofEntries(
        Map.entry("a", 1),
        Map.entry("b", 2),
        Map.entry("c", 3)
    );
    System.out.println("Map.ofEntries(...): " + largerMap);

    // These collections are immutable
    try {
      list.add("D");
    } catch (UnsupportedOperationException e) {
      System.out.println("Cannot modify: UnsupportedOperationException thrown");
    }

    System.out.println();
  }

  static void demonstrateStreamImprovements() {
    System.out.println("--- Stream Improvements ---");

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // takeWhile - take elements while predicate is true
    List<Integer> taken = numbers.stream()
        .takeWhile(n -> n < 5)
        .collect(Collectors.toList());
    System.out.println("takeWhile(n < 5): " + taken);

    // dropWhile - drop elements while predicate is true
    List<Integer> dropped = numbers.stream()
        .dropWhile(n -> n < 5)
        .collect(Collectors.toList());
    System.out.println("dropWhile(n < 5): " + dropped);

    // ofNullable - creates empty stream for null, single-element stream otherwise
    Stream<String> stream1 = Stream.ofNullable("Hello");
    Stream<String> stream2 = Stream.ofNullable(null);
    System.out.println("Stream.ofNullable(\"Hello\").count(): " + stream1.count());
    System.out.println("Stream.ofNullable(null).count(): " + stream2.count());

    // iterate with predicate (like a for loop)
    System.out.print("Stream.iterate(0, n -> n < 10, n -> n + 2): ");
    Stream.iterate(0, n -> n < 10, n -> n + 2)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.println();
  }

  static void demonstrateOptionalImprovements() {
    System.out.println("--- Optional Improvements ---");

    Optional<String> present = Optional.of("Hello");
    Optional<String> empty = Optional.empty();

    // ifPresentOrElse
    System.out.print("present.ifPresentOrElse: ");
    present.ifPresentOrElse(
        value -> System.out.println(value),
        () -> System.out.println("Empty!")
    );

    System.out.print("empty.ifPresentOrElse: ");
    empty.ifPresentOrElse(
        value -> System.out.println(value),
        () -> System.out.println("Empty!")
    );

    // or - lazy alternative Optional
    Optional<String> result = empty.or(() -> Optional.of("Alternative"));
    System.out.println("empty.or(() -> Optional.of(\"Alternative\")): " + result);

    // stream - convert Optional to Stream
    List<String> list = present.stream().collect(Collectors.toList());
    System.out.println("present.stream().toList(): " + list);

    List<String> emptyList = empty.stream().collect(Collectors.toList());
    System.out.println("empty.stream().toList(): " + emptyList);

    System.out.println();
  }

  static void demonstratePrivateInterfaceMethods() {
    System.out.println("--- Private Interface Methods ---");

    Logger logger = new ConsoleLogger();
    logger.logInfo("This is an info message");
    logger.logWarning("This is a warning message");
    logger.logError("This is an error message");

    System.out.println();
  }

  interface Logger {
    // Public abstract method
    void log(String message);

    // Default methods using private helper
    default void logInfo(String message) {
      log(formatMessage("INFO", message));
    }

    default void logWarning(String message) {
      log(formatMessage("WARNING", message));
    }

    default void logError(String message) {
      log(formatMessage("ERROR", message));
    }

    // Private method - new in Java 9
    private String formatMessage(String level, String message) {
      return "[" + level + "] " + java.time.LocalTime.now() + " - " + message;
    }
  }

  static class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
      System.out.println(message);
    }
  }

  static void demonstrateTryWithResourcesImprovement() {
    System.out.println("--- Try-with-resources Improvement ---");

    // Java 7: Variable must be declared in try statement
    // try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) { }

    // Java 9: Can use effectively final variables
    System.out.println("Java 9 allows effectively final variables in try-with-resources");
    System.out.println("Example: final var resource = getResource();");
    System.out.println("         try (resource) { ... }");

    // Demonstration with a simple AutoCloseable
    MyResource resource = new MyResource("TestResource");
    try (resource) {
      resource.doWork();
    }

    System.out.println();
  }

  static class MyResource implements AutoCloseable {
    private final String name;

    MyResource(String name) {
      this.name = name;
      System.out.println("  Resource '" + name + "' opened");
    }

    void doWork() {
      System.out.println("  Resource '" + name + "' doing work");
    }

    @Override
    public void close() {
      System.out.println("  Resource '" + name + "' closed");
    }
  }
}
