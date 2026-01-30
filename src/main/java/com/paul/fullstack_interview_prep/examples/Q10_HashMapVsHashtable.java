package com.paul.fullstack_interview_prep.examples;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Q10: What is the difference between HashMap and Hashtable?
 *
 * HashMap:
 * - NOT synchronized (not thread-safe)
 * - Allows one null key and multiple null values
 * - Faster in single-threaded environment
 * - Part of Java Collections Framework
 *
 * Hashtable:
 * - Synchronized (thread-safe)
 * - Does NOT allow null keys or values
 * - Slower due to synchronization
 * - Legacy class (since Java 1.0)
 *
 * Modern alternative: ConcurrentHashMap (thread-safe, better performance)
 */
public class Q10_HashMapVsHashtable {

  public static void main(String[] args) {
    System.out.println("=== HashMap vs Hashtable Demo ===\n");

    // Basic comparison
    System.out.println("--- Basic Differences ---");
    demonstrateBasicDifferences();

    // Null handling
    System.out.println("\n--- Null Handling ---");
    demonstrateNullHandling();

    // Thread safety
    System.out.println("\n--- Thread Safety Options ---");
    demonstrateThreadSafety();
  }

  static void demonstrateBasicDifferences() {
    HashMap<String, Integer> hashMap = new HashMap<>();
    Hashtable<String, Integer> hashtable = new Hashtable<>();

    hashMap.put("Apple", 1);
    hashMap.put("Banana", 2);
    hashtable.put("Apple", 1);
    hashtable.put("Banana", 2);

    System.out.println("HashMap: " + hashMap);
    System.out.println("Hashtable: " + hashtable);

    System.out.println("\nKey Differences:");
    System.out.println("1. HashMap is NOT synchronized");
    System.out.println("2. Hashtable IS synchronized (legacy)");
    System.out.println("3. HashMap allows null key/values");
    System.out.println("4. Hashtable does NOT allow nulls");
  }

  static void demonstrateNullHandling() {
    HashMap<String, String> hashMap = new HashMap<>();

    // HashMap allows null key
    hashMap.put(null, "null key value");
    hashMap.put("key1", null);
    hashMap.put("key2", null);
    System.out.println("HashMap with nulls: " + hashMap);
    System.out.println("Get null key: " + hashMap.get(null));

    // Hashtable throws NullPointerException
    Hashtable<String, String> hashtable = new Hashtable<>();
    try {
      hashtable.put(null, "value");
    } catch (NullPointerException e) {
      System.out.println("Hashtable null key: NullPointerException thrown!");
    }

    try {
      hashtable.put("key", null);
    } catch (NullPointerException e) {
      System.out.println("Hashtable null value: NullPointerException thrown!");
    }
  }

  static void demonstrateThreadSafety() {
    // Option 1: Hashtable (legacy, not recommended)
    Map<String, Integer> hashtable = new Hashtable<>();
    System.out.println("Hashtable: Thread-safe but legacy, avoid in new code");

    // Option 2: Synchronized HashMap
    Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
    System.out.println("Collections.synchronizedMap(): Thread-safe wrapper");

    // Option 3: ConcurrentHashMap (recommended)
    Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
    concurrentMap.put("Apple", 1);
    concurrentMap.put("Banana", 2);
    System.out.println("ConcurrentHashMap: " + concurrentMap);
    System.out.println("ConcurrentHashMap: Best for concurrent access (lock striping)");

    System.out.println("\nRecommendation:");
    System.out.println("- Single-threaded: Use HashMap");
    System.out.println("- Multi-threaded: Use ConcurrentHashMap");
    System.out.println("- Avoid Hashtable in new code");
  }
}
