package com.paul.fullstack_interview_prep.examples;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Q9: What is the difference between ArrayList and LinkedList?
 *
 * ArrayList:
 * - Backed by dynamic array
 * - Fast random access: O(1)
 * - Slow insertion/deletion in middle: O(n)
 * - Better for read-heavy operations
 *
 * LinkedList:
 * - Backed by doubly-linked list
 * - Slow random access: O(n)
 * - Fast insertion/deletion: O(1) if you have the node
 * - Better for write-heavy operations with frequent add/remove
 * - Implements Deque (can be used as queue/stack)
 */
public class Q09_ArrayListVsLinkedList {

  public static void main(String[] args) {
    System.out.println("=== ArrayList vs LinkedList Demo ===\n");

    // Basic usage
    System.out.println("--- Basic Operations ---");
    demonstrateBasicOperations();

    // Performance comparison
    System.out.println("\n--- Performance Comparison ---");
    comparePerformance();

    // LinkedList as Deque
    System.out.println("\n--- LinkedList as Deque ---");
    demonstrateDeque();
  }

  static void demonstrateBasicOperations() {
    List<String> arrayList = new ArrayList<>();
    List<String> linkedList = new LinkedList<>();

    // Both implement List interface
    arrayList.add("Apple");
    arrayList.add("Banana");
    arrayList.add("Cherry");

    linkedList.add("Apple");
    linkedList.add("Banana");
    linkedList.add("Cherry");

    System.out.println("ArrayList: " + arrayList);
    System.out.println("LinkedList: " + linkedList);

    // Random access
    System.out.println("\nRandom access (get index 1):");
    System.out.println("ArrayList.get(1): " + arrayList.get(1) + " - O(1)");
    System.out.println("LinkedList.get(1): " + linkedList.get(1) + " - O(n)");
  }

  static void comparePerformance() {
    int size = 100000;
    ArrayList<Integer> arrayList = new ArrayList<>();
    LinkedList<Integer> linkedList = new LinkedList<>();

    // Populate lists
    for (int i = 0; i < size; i++) {
      arrayList.add(i);
      linkedList.add(i);
    }

    // Test random access
    long start = System.nanoTime();
    for (int i = 0; i < 10000; i++) {
      arrayList.get(size / 2);
    }
    long arrayListGetTime = System.nanoTime() - start;

    start = System.nanoTime();
    for (int i = 0; i < 10000; i++) {
      linkedList.get(size / 2);
    }
    long linkedListGetTime = System.nanoTime() - start;

    System.out.println("Random access (10000 gets at middle):");
    System.out.println("ArrayList: " + arrayListGetTime / 1_000_000 + " ms");
    System.out.println("LinkedList: " + linkedListGetTime / 1_000_000 + " ms");

    // Test insertion at beginning
    ArrayList<Integer> arrayList2 = new ArrayList<>();
    LinkedList<Integer> linkedList2 = new LinkedList<>();

    start = System.nanoTime();
    for (int i = 0; i < 10000; i++) {
      arrayList2.add(0, i); // Insert at beginning
    }
    long arrayListAddTime = System.nanoTime() - start;

    start = System.nanoTime();
    for (int i = 0; i < 10000; i++) {
      linkedList2.addFirst(i); // Insert at beginning
    }
    long linkedListAddTime = System.nanoTime() - start;

    System.out.println("\nInsertion at beginning (10000 inserts):");
    System.out.println("ArrayList: " + arrayListAddTime / 1_000_000 + " ms");
    System.out.println("LinkedList: " + linkedListAddTime / 1_000_000 + " ms");
  }

  static void demonstrateDeque() {
    LinkedList<String> deque = new LinkedList<>();

    // Use as stack (LIFO)
    deque.push("First");
    deque.push("Second");
    deque.push("Third");
    System.out.println("Stack (push): " + deque);
    System.out.println("Stack (pop): " + deque.pop());
    System.out.println("After pop: " + deque);

    // Use as queue (FIFO)
    LinkedList<String> queue = new LinkedList<>();
    queue.offer("First");
    queue.offer("Second");
    queue.offer("Third");
    System.out.println("\nQueue (offer): " + queue);
    System.out.println("Queue (poll): " + queue.poll());
    System.out.println("After poll: " + queue);
  }
}
