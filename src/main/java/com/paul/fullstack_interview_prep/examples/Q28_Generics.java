package com.paul.fullstack_interview_prep.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Q28: What are Generics in Java?
 *
 * Generics: Enable types (classes and interfaces) to be parameters when defining
 * classes, interfaces, and methods.
 *
 * Benefits:
 * - Type safety at compile time
 * - Elimination of casts
 * - Code reusability
 *
 * Key concepts:
 * - Type parameters: <T>, <E>, <K, V>
 * - Bounded types: <T extends Number>
 * - Wildcards: <?>, <? extends T>, <? super T>
 * - Type erasure: Generics are removed at runtime
 */
public class Q28_Generics {

  public static void main(String[] args) {
    System.out.println("=== Generics Demo ===\n");

    // Why generics
    System.out.println("--- Why Generics? ---");
    demonstrateWhyGenerics();

    // Generic class
    System.out.println("\n--- Generic Class ---");
    demonstrateGenericClass();

    // Generic method
    System.out.println("\n--- Generic Method ---");
    demonstrateGenericMethod();

    // Bounded types
    System.out.println("\n--- Bounded Type Parameters ---");
    demonstrateBoundedTypes();

    // Wildcards
    System.out.println("\n--- Wildcards ---");
    demonstrateWildcards();
  }

  static void demonstrateWhyGenerics() {
    // Without generics (before Java 5)
    List rawList = new ArrayList();
    rawList.add("String");
    rawList.add(123); // No compile error, but dangerous!

    // Dangerous cast required
    // String s = (String) rawList.get(1); // ClassCastException at runtime!

    System.out.println("Without generics: No type safety, runtime errors possible");

    // With generics
    List<String> safeList = new ArrayList<>();
    safeList.add("String");
    // safeList.add(123); // Compile error! Type safety.

    String s = safeList.get(0); // No cast needed
    System.out.println("With generics: Type safety, no casts: " + s);
  }

  static void demonstrateGenericClass() {
    // Generic Box class
    Box<String> stringBox = new Box<>("Hello");
    System.out.println("String Box: " + stringBox.get());

    Box<Integer> intBox = new Box<>(42);
    System.out.println("Integer Box: " + intBox.get());

    // Generic Pair class
    Pair<String, Integer> pair = new Pair<>("Age", 25);
    System.out.println("Pair: " + pair.getKey() + " = " + pair.getValue());
  }

  static void demonstrateGenericMethod() {
    // Generic method works with any type
    Integer[] intArray = {1, 2, 3, 4, 5};
    String[] strArray = {"A", "B", "C"};

    System.out.println("Print Integer array:");
    printArray(intArray);

    System.out.println("Print String array:");
    printArray(strArray);

    // Generic method with return type
    String first = getFirst(Arrays.asList("X", "Y", "Z"));
    System.out.println("First element: " + first);
  }

  static void demonstrateBoundedTypes() {
    // Upper bounded: T must be Number or subclass
    System.out.println("Sum of integers: " + sum(Arrays.asList(1, 2, 3, 4, 5)));
    System.out.println("Sum of doubles: " + sum(Arrays.asList(1.5, 2.5, 3.0)));

    // NumericBox only accepts Numbers
    NumericBox<Integer> intNumBox = new NumericBox<>(10);
    NumericBox<Double> doubleNumBox = new NumericBox<>(3.14);
    // NumericBox<String> strBox; // Compile error! String is not a Number

    System.out.println("Integer NumericBox doubled: " + intNumBox.doubleValue());
    System.out.println("Double NumericBox doubled: " + doubleNumBox.doubleValue());
  }

  static void demonstrateWildcards() {
    List<Integer> integers = Arrays.asList(1, 2, 3);
    List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);
    List<String> strings = Arrays.asList("A", "B", "C");

    // Unbounded wildcard: <?> - read only, any type
    System.out.println("Unbounded wildcard (read any):");
    printList(integers);
    printList(strings);

    // Upper bounded: <? extends Number> - read only, Number or subclass
    System.out.println("\nUpper bounded (read Numbers):");
    printNumbers(integers);
    printNumbers(doubles);
    // printNumbers(strings); // Compile error!

    // Lower bounded: <? super Integer> - write, Integer or superclass
    System.out.println("\nLower bounded (write Integers):");
    List<Number> numbers = new ArrayList<>();
    addIntegers(numbers);
    System.out.println("After adding integers: " + numbers);

    // PECS: Producer Extends, Consumer Super
    System.out.println("\nPECS Rule:");
    System.out.println("  Producer (read): use <? extends T>");
    System.out.println("  Consumer (write): use <? super T>");
  }

  // ========== GENERIC CLASS ==========
  static class Box<T> {
    private T value;

    Box(T value) { this.value = value; }
    T get() { return value; }
    void set(T value) { this.value = value; }
  }

  static class Pair<K, V> {
    private K key;
    private V value;

    Pair(K key, V value) { this.key = key; this.value = value; }
    K getKey() { return key; }
    V getValue() { return value; }
  }

  // ========== BOUNDED TYPE ==========
  static class NumericBox<T extends Number> {
    private T value;

    NumericBox(T value) { this.value = value; }

    double doubleValue() {
      return value.doubleValue() * 2;
    }
  }

  // ========== GENERIC METHODS ==========
  static <T> void printArray(T[] array) {
    for (T element : array) {
      System.out.print("  " + element);
    }
    System.out.println();
  }

  static <T> T getFirst(List<T> list) {
    return list.isEmpty() ? null : list.get(0);
  }

  // Bounded type parameter
  static <T extends Number> double sum(List<T> numbers) {
    double sum = 0;
    for (T n : numbers) {
      sum += n.doubleValue();
    }
    return sum;
  }

  // ========== WILDCARDS ==========
  // Unbounded wildcard
  static void printList(List<?> list) {
    for (Object item : list) {
      System.out.print("  " + item);
    }
    System.out.println();
  }

  // Upper bounded wildcard (producer)
  static void printNumbers(List<? extends Number> numbers) {
    for (Number n : numbers) {
      System.out.print("  " + n);
    }
    System.out.println();
  }

  // Lower bounded wildcard (consumer)
  static void addIntegers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
    list.add(3);
  }
}
