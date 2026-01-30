package com.paul.fullstackinterviewprep.examples;

/**
 * Q3: What is the difference between String, StringBuilder, and StringBuffer?
 *
 * String: Immutable, thread-safe, stored in String pool
 * StringBuilder: Mutable, NOT thread-safe, faster for single-threaded operations
 * StringBuffer: Mutable, thread-safe (synchronized), slower than StringBuilder
 */
public class Q03_StringBuilderBuffer {

  public static void main(String[] args) {
    System.out.println("=== String vs StringBuilder vs StringBuffer Demo ===\n");

    // String immutability
    System.out.println("--- String (Immutable) ---");
    String str = "Hello";
    String original = str;
    str = str + " World"; // Creates new String object
    System.out.println("Original reference unchanged: " + original);
    System.out.println("New string: " + str);

    // StringBuilder - mutable, not synchronized
    System.out.println("\n--- StringBuilder (Mutable, Not Thread-Safe) ---");
    StringBuilder sb = new StringBuilder("Hello");
    sb.append(" World");
    System.out.println("StringBuilder modified in place: " + sb);

    // StringBuffer - mutable, synchronized
    System.out.println("\n--- StringBuffer (Mutable, Thread-Safe) ---");
    StringBuffer sbuf = new StringBuffer("Hello");
    sbuf.append(" World");
    System.out.println("StringBuffer modified in place: " + sbuf);

    // Performance comparison
    System.out.println("\n--- Performance Comparison (10000 concatenations) ---");

    long start = System.nanoTime();
    String s = "";
    for (int i = 0; i < 10000; i++) {
      s += "a";
    }
    System.out.println("String concatenation: " + (System.nanoTime() - start) / 1_000_000 + " ms");

    start = System.nanoTime();
    StringBuilder sb2 = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      sb2.append("a");
    }
    System.out.println("StringBuilder: " + (System.nanoTime() - start) / 1_000_000 + " ms");
    System.out.println("sb2 is: " + sb2);

    start = System.nanoTime();
    StringBuffer sbuf2 = new StringBuffer();
    for (int i = 0; i < 10000; i++) {
      sbuf2.append("a");
    }
    System.out.println("StringBuffer: " + (System.nanoTime() - start) / 1_000_000 + " ms");
  }
}
