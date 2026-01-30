package com.paul.fullstack_interview_prep.examples;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Q22: What is the difference between fail-fast and fail-safe iterators?
 *
 * Fail-Fast Iterator:
 * - Throws ConcurrentModificationException if collection is modified during iteration
 * - Works directly on the collection
 * - Examples: ArrayList, HashMap, HashSet
 *
 * Fail-Safe Iterator:
 * - Does NOT throw exception if collection is modified
 * - Works on a copy/snapshot of the collection
 * - Examples: CopyOnWriteArrayList, ConcurrentHashMap
 *
 * Note: Use iterator.remove() to safely remove during iteration with fail-fast
 */
public class Q22_FailFastVsFailSafe {

  public static void main(String[] args) {
    System.out.println("=== Fail-Fast vs Fail-Safe Iterators Demo ===\n");

    // Fail-fast demonstration
    System.out.println("--- Fail-Fast (ArrayList) ---");
    demonstrateFailFast();

    // Safe removal with iterator
    System.out.println("\n--- Safe Removal with Iterator.remove() ---");
    demonstrateSafeRemoval();

    // Fail-safe demonstration
    System.out.println("\n--- Fail-Safe (CopyOnWriteArrayList) ---");
    demonstrateFailSafe();

    // Comparison
    System.out.println("\n--- Summary ---");
    printSummary();
  }

  static void demonstrateFailFast() {
    List<String> list = new ArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.add("D");

    System.out.println("Original list: " + list);
    System.out.println("Attempting to modify during iteration...");

    try {
      for (String item : list) {
        System.out.println("Processing: " + item);
        if (item.equals("B")) {
          list.remove(item); // Modifying during iteration
        }
      }
    } catch (ConcurrentModificationException e) {
      System.out.println("ConcurrentModificationException caught!");
      System.out.println("Fail-fast: Collection was modified during iteration");
    }

    // Also happens with add()
    list = new ArrayList<>(List.of("A", "B", "C"));
    try {
      for (String item : list) {
        if (item.equals("B")) {
          list.add("X"); // Adding during iteration
        }
      }
    } catch (ConcurrentModificationException e) {
      System.out.println("ConcurrentModificationException on add() too!");
    }
  }

  static void demonstrateSafeRemoval() {
    List<String> list = new ArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.add("D");

    System.out.println("Original list: " + list);

    // Using iterator.remove() is safe
    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
      String item = iterator.next();
      System.out.println("Processing: " + item);
      if (item.equals("B") || item.equals("C")) {
        iterator.remove(); // Safe removal!
        System.out.println("  Removed: " + item);
      }
    }

    System.out.println("After safe removal: " + list);

    // Java 8+ removeIf
    list = new ArrayList<>(List.of("A", "B", "C", "D"));
    list.removeIf(s -> s.equals("B") || s.equals("C"));
    System.out.println("Using removeIf: " + list);
  }

  static void demonstrateFailSafe() {
    // CopyOnWriteArrayList is fail-safe
    List<String> list = new CopyOnWriteArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");
    list.add("D");

    System.out.println("Original list: " + list);
    System.out.println("Modifying during iteration (no exception)...");

    for (String item : list) {
      System.out.println("Processing: " + item);
      if (item.equals("B")) {
        list.remove(item);
        list.add("X");
        System.out.println("  Modified list (but iterator sees old snapshot)");
      }
    }

    System.out.println("After iteration: " + list);
    System.out.println("Note: Iterator worked on snapshot, didn't see 'X'");

    // Demonstrate snapshot behavior
    System.out.println("\nSnapshot behavior:");
    list = new CopyOnWriteArrayList<>(List.of("1", "2", "3"));
    Iterator<String> iter = list.iterator();
    list.add("4"); // Add after iterator created
    System.out.print("Iterator sees: ");
    while (iter.hasNext()) {
      System.out.print(iter.next() + " ");
    }
    System.out.println("\nActual list: " + list);
  }

  static void printSummary() {
    System.out.println("| Feature          | Fail-Fast           | Fail-Safe              |");
    System.out.println("|------------------|---------------------|------------------------|");
    System.out.println("| Exception        | ConcurrentModExc.   | No exception           |");
    System.out.println("| Works on         | Original collection | Copy/Snapshot          |");
    System.out.println("| Memory           | No extra memory     | Extra memory for copy  |");
    System.out.println("| Examples         | ArrayList, HashMap  | CopyOnWriteArrayList   |");
    System.out.println("| Use case         | Single-threaded     | Concurrent access      |");
  }
}
