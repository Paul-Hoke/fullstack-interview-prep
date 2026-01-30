package com.paul.fullstack_interview_prep.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Q20: What are Streams in Java?
 *
 * Stream: Sequence of elements supporting sequential and parallel aggregate operations.
 *
 * Characteristics:
 * - Not a data structure (doesn't store elements)
 * - Functional in nature (doesn't modify source)
 * - Lazily evaluated (intermediate operations are lazy)
 * - Can be consumed only once
 *
 * Operations:
 * - Intermediate: filter, map, sorted, distinct, limit, skip (return Stream)
 * - Terminal: collect, forEach, reduce, count, findFirst, anyMatch (trigger execution)
 */
public class Q20_Streams {

  public static void main(String[] args) {
    System.out.println("=== Java Streams Demo ===\n");

    // Creating streams
    System.out.println("--- Creating Streams ---");
    demonstrateCreation();

    // Intermediate operations
    System.out.println("\n--- Intermediate Operations ---");
    demonstrateIntermediateOps();

    // Terminal operations
    System.out.println("\n--- Terminal Operations ---");
    demonstrateTerminalOps();

    // Collectors
    System.out.println("\n--- Collectors ---");
    demonstrateCollectors();

    // Parallel streams
    System.out.println("\n--- Parallel Streams ---");
    demonstrateParallelStreams();
  }

  static void demonstrateCreation() {
    // From collection
    List<String> list = Arrays.asList("a", "b", "c");
    Stream<String> fromList = list.stream();
    System.out.println("From List: " + fromList.collect(Collectors.toList()));

    // From array
    String[] array = {"x", "y", "z"};
    Stream<String> fromArray = Arrays.stream(array);
    System.out.println("From Array: " + fromArray.collect(Collectors.toList()));

    // Using Stream.of()
    Stream<Integer> fromOf = Stream.of(1, 2, 3, 4, 5);
    System.out.println("Stream.of(): " + fromOf.collect(Collectors.toList()));

    // Using Stream.generate() and limit()
    Stream<Double> generated = Stream.generate(Math::random).limit(3);
    System.out.println("Generated: " + generated.collect(Collectors.toList()));

    // Using IntStream.range()
    IntStream range = IntStream.range(1, 6);
    System.out.println("IntStream.range(1, 6): " + Arrays.toString(range.toArray()));
  }

  static void demonstrateIntermediateOps() {
    List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6);

    // filter
    System.out.print("filter (even): ");
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // map
    System.out.print("map (squared): ");
    numbers.stream()
        .map(n -> n * n)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // sorted
    System.out.print("sorted: ");
    numbers.stream()
        .sorted()
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // distinct
    List<Integer> withDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
    System.out.print("distinct: ");
    withDuplicates.stream()
        .distinct()
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // limit and skip
    System.out.print("skip(2).limit(3): ");
    numbers.stream()
        .skip(2)
        .limit(3)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // peek (for debugging)
    System.out.println("peek (debugging):");
    numbers.stream()
        .filter(n -> n > 5)
        .peek(n -> System.out.println("  After filter: " + n))
        .map(n -> n * 2)
        .peek(n -> System.out.println("  After map: " + n))
        .limit(2)
        .collect(Collectors.toList());
  }

  static void demonstrateTerminalOps() {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    // collect
    List<Integer> doubled = numbers.stream()
        .map(n -> n * 2)
        .collect(Collectors.toList());
    System.out.println("collect: " + doubled);

    // count
    long count = numbers.stream().filter(n -> n > 2).count();
    System.out.println("count (>2): " + count);

    // reduce
    int sum = numbers.stream().reduce(0, Integer::sum);
    System.out.println("reduce (sum): " + sum);

    Optional<Integer> max = numbers.stream().reduce(Integer::max);
    System.out.println("reduce (max): " + max.orElse(0));

    // findFirst / findAny
    Optional<Integer> first = numbers.stream().filter(n -> n > 2).findFirst();
    System.out.println("findFirst (>2): " + first.orElse(-1));

    // anyMatch / allMatch / noneMatch
    boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
    boolean allPositive = numbers.stream().allMatch(n -> n > 0);
    boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
    System.out.println("anyMatch (even): " + anyEven);
    System.out.println("allMatch (positive): " + allPositive);
    System.out.println("noneMatch (negative): " + noneNegative);
  }

  static void demonstrateCollectors() {
    List<Person> people = Arrays.asList(
        new Person("Alice", 30, "Engineering"),
        new Person("Bob", 25, "Marketing"),
        new Person("Charlie", 35, "Engineering"),
        new Person("David", 28, "Marketing")
    );

    // toList
    List<String> names = people.stream()
        .map(Person::name)
        .collect(Collectors.toList());
    System.out.println("toList (names): " + names);

    // joining
    String joined = people.stream()
        .map(Person::name)
        .collect(Collectors.joining(", "));
    System.out.println("joining: " + joined);

    // groupingBy
    Map<String, List<Person>> byDept = people.stream()
        .collect(Collectors.groupingBy(Person::department));
    System.out.println("groupingBy (dept): " + byDept);

    // partitioningBy
    Map<Boolean, List<Person>> over30 = people.stream()
        .collect(Collectors.partitioningBy(p -> p.age() > 30));
    System.out.println("partitioningBy (>30): " + over30);

    // averagingInt
    double avgAge = people.stream()
        .collect(Collectors.averagingInt(Person::age));
    System.out.println("averagingInt (age): " + avgAge);
  }

  static void demonstrateParallelStreams() {
    List<Integer> numbers = IntStream.rangeClosed(1, 10)
        .boxed()
        .collect(Collectors.toList());

    // Sequential
    System.out.print("Sequential: ");
    numbers.stream()
        .map(n -> {
          System.out.print(Thread.currentThread().getName().substring(0, 4) + " ");
          return n * 2;
        })
        .collect(Collectors.toList());
    System.out.println();

    // Parallel
    System.out.print("Parallel: ");
    numbers.parallelStream()
        .map(n -> {
          System.out.print(Thread.currentThread().getName().substring(0, 4) + " ");
          return n * 2;
        })
        .collect(Collectors.toList());
    System.out.println();
    System.out.println("Note: Parallel uses multiple threads from ForkJoinPool");
  }

  record Person(String name, int age, String department) {}
}
