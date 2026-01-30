package com.paul.fullstackinterviewprep.examples;

import java.util.Optional;

/**
 * Q21: What is the Optional class in Java?
 *
 * Optional: Container object that may or may not contain a non-null value.
 *
 * Purpose:
 * - Avoid NullPointerException
 * - Express "no value" explicitly
 * - Force handling of absent values
 *
 * Key methods:
 * - of(), ofNullable(), empty() - creation
 * - isPresent(), isEmpty() - checking
 * - get(), orElse(), orElseGet(), orElseThrow() - retrieval
 * - map(), flatMap(), filter() - transformation
 */
public class Q21_OptionalClass {

  public static void main(String[] args) {
    System.out.println("=== Optional Class Demo ===\n");

    // Creating Optional
    System.out.println("--- Creating Optional ---");
    demonstrateCreation();

    // Checking and retrieving
    System.out.println("\n--- Checking and Retrieving ---");
    demonstrateRetrieval();

    // Transformations
    System.out.println("\n--- Transformations ---");
    demonstrateTransformations();

    // Real-world usage
    System.out.println("\n--- Real-World Usage ---");
    demonstrateRealWorld();
  }

  static void demonstrateCreation() {
    // Optional.of() - throws NPE if null
    Optional<String> opt1 = Optional.of("Hello");
    System.out.println("Optional.of(\"Hello\"): " + opt1);

    // Optional.ofNullable() - handles null
    Optional<String> opt2 = Optional.ofNullable(null);
    System.out.println("Optional.ofNullable(null): " + opt2);

    Optional<String> opt3 = Optional.ofNullable("World");
    System.out.println("Optional.ofNullable(\"World\"): " + opt3);

    // Optional.empty()
    Optional<String> opt4 = Optional.empty();
    System.out.println("Optional.empty(): " + opt4);
  }

  static void demonstrateRetrieval() {
    Optional<String> present = Optional.of("Value");
    Optional<String> absent = Optional.empty();

    // isPresent() / isEmpty()
    System.out.println("present.isPresent(): " + present.isPresent());
    System.out.println("absent.isEmpty(): " + absent.isEmpty());

    // get() - throws NoSuchElementException if empty
    System.out.println("present.get(): " + present.get());
    // absent.get(); // Would throw NoSuchElementException

    // orElse() - returns default if empty
    System.out.println("absent.orElse(\"Default\"): " + absent.orElse("Default"));

    // orElseGet() - lazy evaluation with Supplier
    System.out.println("absent.orElseGet(() -> \"Computed\"): " +
        absent.orElseGet(() -> "Computed"));

    // orElseThrow() - throws exception if empty
    try {
      absent.orElseThrow(() -> new RuntimeException("No value!"));
    } catch (RuntimeException e) {
      System.out.println("orElseThrow: " + e.getMessage());
    }

    // ifPresent() - execute action if present
    present.ifPresent(v -> System.out.println("ifPresent: " + v));

    // ifPresentOrElse() - Java 9+
    absent.ifPresentOrElse(
        v -> System.out.println("Present: " + v),
        () -> System.out.println("ifPresentOrElse: Value is absent")
    );
  }

  static void demonstrateTransformations() {
    Optional<String> name = Optional.of("john doe");

    // map() - transform value
    Optional<String> upper = name.map(String::toUpperCase);
    System.out.println("map(toUpperCase): " + upper.orElse(""));

    Optional<Integer> length = name.map(String::length);
    System.out.println("map(length): " + length.orElse(0));

    // filter() - keep value if matches predicate
    Optional<String> longName = name.filter(n -> n.length() > 5);
    System.out.println("filter(length > 5): " + longName.orElse("filtered out"));

    Optional<String> shortName = name.filter(n -> n.length() < 3);
    System.out.println("filter(length < 3): " + shortName.orElse("filtered out"));

    // flatMap() - avoid Optional<Optional<T>>
    Optional<String> result = name.flatMap(Q21_OptionalClass::findUpperCase);
    System.out.println("flatMap: " + result.orElse("empty"));

    // Chaining
    String finalResult = Optional.of("  hello world  ")
        .map(String::trim)
        .map(String::toUpperCase)
        .filter(s -> s.length() > 5)
        .orElse("too short");
    System.out.println("Chained operations: " + finalResult);
  }

  static Optional<String> findUpperCase(String s) {
    return Optional.of(s.toUpperCase());
  }

  static void demonstrateRealWorld() {
    // Simulating database lookup
    UserRepository repo = new UserRepository();

    // Without Optional (null-prone)
    System.out.println("--- Without Optional (risky) ---");
    User userOld = repo.findByIdOld(1);
    if (userOld != null) {
      System.out.println("Found: " + userOld.name);
    }

    // With Optional (safe)
    System.out.println("\n--- With Optional (safe) ---");
    repo.findById(1)
        .map(u -> u.name)
        .ifPresent(name -> System.out.println("Found: " + name));

    // Not found case
    String result = repo.findById(999)
        .map(u -> u.name)
        .orElse("User not found");
    System.out.println("Lookup 999: " + result);

    // Nested Optional handling
    System.out.println("\n--- Nested Optional ---");
    Optional<Address> address = repo.findById(1)
        .flatMap(User::getAddress);
    System.out.println("Address: " + address.map(a -> a.city).orElse("No address"));
  }

  static class User {
    String name;
    Address address;

    User(String name, Address address) {
      this.name = name;
      this.address = address;
    }

    Optional<Address> getAddress() {
      return Optional.ofNullable(address);
    }
  }

  static class Address {
    String city;

    Address(String city) {
      this.city = city;
    }
  }

  static class UserRepository {
    User findByIdOld(int id) {
      if (id == 1) return new User("Alice", new Address("New York"));
      return null; // Risky!
    }

    Optional<User> findById(int id) {
      if (id == 1) return Optional.of(new User("Alice", new Address("New York")));
      return Optional.empty(); // Safe!
    }
  }
}
