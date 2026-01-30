package com.paul.fullstack_interview_prep.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Q24: What is immutability and how do you create an immutable class?
 *
 * Immutable: Object whose state cannot be changed after creation.
 *
 * Benefits:
 * - Thread-safe (no synchronization needed)
 * - Can be cached and shared safely
 * - Predictable, no side effects
 * - Good for hash keys
 *
 * Rules to create immutable class:
 * 1. Declare class as final (prevent subclassing)
 * 2. Make all fields private and final
 * 3. No setter methods
 * 4. Deep copy mutable objects in constructor
 * 5. Return copies of mutable fields (defensive copying)
 */
public class Q24_Immutability {

  public static void main(String[] args) {
    System.out.println("=== Immutability Demo ===\n");

    // Built-in immutable classes
    System.out.println("--- Built-in Immutable Classes ---");
    demonstrateBuiltIn();

    // Custom immutable class
    System.out.println("\n--- Custom Immutable Class ---");
    demonstrateCustomImmutable();

    // Common mistakes
    System.out.println("\n--- Common Mistakes ---");
    demonstrateCommonMistakes();

    // Java Records (immutable by design)
    System.out.println("\n--- Java Records (Immutable by Design) ---");
    demonstrateRecords();
  }

  static void demonstrateBuiltIn() {
    // String is immutable
    String s1 = "Hello";
    String s2 = s1.concat(" World");
    System.out.println("String: s1 = " + s1 + ", s2 = " + s2);
    System.out.println("s1 unchanged - String is immutable");

    // Integer, Long, etc. are immutable
    Integer i1 = 100;
    Integer i2 = i1;
    i2 = i2 + 50; // Creates new Integer
    System.out.println("Integer: i1 = " + i1 + ", i2 = " + i2);

    // Immutable collections (Java 9+)
    List<String> immutableList = List.of("A", "B", "C");
    try {
      immutableList.add("D");
    } catch (UnsupportedOperationException e) {
      System.out.println("List.of() returns immutable list");
    }
  }

  static void demonstrateCustomImmutable() {
    List<String> hobbies = new ArrayList<>();
    hobbies.add("Reading");
    hobbies.add("Gaming");

    ImmutablePerson person = new ImmutablePerson("Alice", 30, hobbies);

    System.out.println("Person: " + person);

    // Try to modify through original list reference
    hobbies.add("Hacking");
    System.out.println("After modifying original list: " + person);
    System.out.println("Person's hobbies unchanged - defensive copy in constructor");

    // Try to modify through getter
    List<String> gotHobbies = person.getHobbies();
    try {
      gotHobbies.add("Breaking");
    } catch (UnsupportedOperationException e) {
      System.out.println("Cannot modify returned hobbies - defensive copy on get");
    }
  }

  static void demonstrateCommonMistakes() {
    // Mistake: Not copying mutable fields
    System.out.println("Mistake 1: Not making defensive copies");
    BrokenImmutable broken = new BrokenImmutable(new ArrayList<>(List.of("A", "B")));
    broken.getItems().add("C"); // Can modify!
    System.out.println("Broken immutable was modified: " + broken.getItems());

    // Mistake: Returning mutable field directly
    System.out.println("\nMistake 2: Returning reference to mutable field");
    System.out.println("Always return a copy or unmodifiable view");
  }

  static void demonstrateRecords() {
    // Records are immutable by default (Java 16+)
    ImmutableRecord record = new ImmutableRecord("Bob", 25);
    System.out.println("Record: " + record);
    System.out.println("Record provides: constructor, getters, equals, hashCode, toString");

    // But be careful with mutable fields in records!
    List<String> items = new ArrayList<>();
    items.add("Item1");
    RecordWithList recordList = new RecordWithList("Test", items);
    items.add("Item2"); // Can still modify!
    System.out.println("Record with mutable field (be careful!): " + recordList.items());
  }

  // Properly immutable class
  static final class ImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public ImmutablePerson(String name, int age, List<String> hobbies) {
      this.name = name;
      this.age = age;
      // Defensive copy in constructor
      this.hobbies = new ArrayList<>(hobbies);
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    // Return unmodifiable copy
    public List<String> getHobbies() {
      return Collections.unmodifiableList(hobbies);
    }

    @Override
    public String toString() {
      return "ImmutablePerson{name='" + name + "', age=" + age + ", hobbies=" + hobbies + "}";
    }
  }

  // Broken "immutable" class - don't do this!
  static final class BrokenImmutable {
    private final List<String> items;

    public BrokenImmutable(List<String> items) {
      this.items = items; // Wrong: should copy!
    }

    public List<String> getItems() {
      return items; // Wrong: should return copy!
    }
  }

  // Record - immutable by design
  record ImmutableRecord(String name, int age) {}

  // Record with mutable field - needs careful handling
  record RecordWithList(String name, List<String> items) {
    // Could add compact constructor to make defensive copy
    // public RecordWithList {
    //   items = new ArrayList<>(items);
    // }
  }
}
