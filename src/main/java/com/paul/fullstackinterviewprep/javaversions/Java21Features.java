package com.paul.fullstackinterviewprep.javaversions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java 21 (LTS) - September 2023
 * Virtual Threads (standard), Sequenced Collections, Record Patterns (standard),
 * Pattern Matching for switch (standard), String Templates (preview).
 */
public class Java21Features {

  public static void main(String[] args) throws Exception {
    System.out.println("=== Java 21 Features (LTS) ===\n");

    demonstrateVirtualThreads();
    demonstrateSequencedCollections();
    demonstrateRecordPatterns();
    demonstratePatternMatchingSwitch();
  }

  static void demonstrateVirtualThreads() throws Exception {
    System.out.println("--- Virtual Threads (Standard) ---");

    // Simple virtual thread
    Thread vThread = Thread.startVirtualThread(() -> {
      System.out.println("Running in virtual thread: " + Thread.currentThread());
    });
    vThread.join();

    // Virtual thread with builder
    Thread.Builder builder = Thread.ofVirtual().name("my-virtual-thread");
    Thread vt = builder.start(() -> {
      System.out.println("Named virtual thread: " + Thread.currentThread().getName());
    });
    vt.join();

    // Virtual thread executor - creates a new virtual thread for each task
    System.out.println("\nVirtual thread executor (10 tasks):");
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 10; i++) {
        int taskId = i;
        executor.submit(() -> {
          try {
            Thread.sleep(Duration.ofMillis(100));
            System.out.println("  Task " + taskId + " completed on " +
                Thread.currentThread().getName().substring(0, 20) + "...");
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        });
      }
    }  // Executor auto-closed, waits for all tasks

    // Comparing platform vs virtual threads
    System.out.println("\nPlatform thread: " + Thread.currentThread());
    System.out.println("  isVirtual: " + Thread.currentThread().isVirtual());

    Thread.startVirtualThread(() -> {
      System.out.println("Virtual thread: " + Thread.currentThread());
      System.out.println("  isVirtual: " + Thread.currentThread().isVirtual());
    }).join();

    // Virtual threads are lightweight - can create millions
    System.out.println("\nVirtual threads are lightweight:");
    System.out.println("  Platform threads: ~1MB stack each, OS-managed");
    System.out.println("  Virtual threads: ~few KB, JVM-managed, millions possible");

    System.out.println();
  }

  static void demonstrateSequencedCollections() {
    System.out.println("--- Sequenced Collections ---");

    // SequencedCollection - List, Deque, SortedSet, LinkedHashSet
    SequencedCollection<String> list = new ArrayList<>(List.of("A", "B", "C"));
    System.out.println("Original: " + list);

    // New first/last methods
    System.out.println("getFirst(): " + list.getFirst());
    System.out.println("getLast(): " + list.getLast());

    // Add at beginning/end
    list.addFirst("Z");
    list.addLast("D");
    System.out.println("After addFirst(Z) and addLast(D): " + list);

    // Remove from beginning/end
    list.removeFirst();
    list.removeLast();
    System.out.println("After removeFirst() and removeLast(): " + list);

    // Reversed view
    SequencedCollection<String> reversed = list.reversed();
    System.out.println("Reversed view: " + reversed);
    System.out.println("Original unchanged: " + list);

    // SequencedSet
    System.out.println("\nSequencedSet:");
    SequencedSet<Integer> set = new LinkedHashSet<>(List.of(1, 2, 3, 4, 5));
    System.out.println("Set: " + set);
    System.out.println("First: " + set.getFirst() + ", Last: " + set.getLast());
    System.out.println("Reversed: " + set.reversed());

    // SequencedMap
    System.out.println("\nSequencedMap:");
    SequencedMap<String, Integer> map = new LinkedHashMap<>();
    map.put("one", 1);
    map.put("two", 2);
    map.put("three", 3);
    System.out.println("Map: " + map);
    System.out.println("firstEntry(): " + map.firstEntry());
    System.out.println("lastEntry(): " + map.lastEntry());

    // Put at beginning
    map.putFirst("zero", 0);
    System.out.println("After putFirst(zero, 0): " + map);

    // Poll (remove) first/last
    var polled = map.pollFirstEntry();
    System.out.println("pollFirstEntry(): " + polled);
    System.out.println("Map after poll: " + map);

    System.out.println();
  }

  static void demonstrateRecordPatterns() {
    System.out.println("--- Record Patterns (Standard) ---");

    // Basic record pattern
    record Point(int x, int y) {}
    Object obj = new Point(3, 4);

    if (obj instanceof Point(int x, int y)) {
      System.out.println("Point coordinates: x=" + x + ", y=" + y);
      System.out.println("Distance from origin: " + Math.sqrt(x * x + y * y));
    }

    // Nested record patterns
    record Line(Point start, Point end) {}
    Object line = new Line(new Point(0, 0), new Point(3, 4));

    if (line instanceof Line(Point(int x1, int y1), Point(int x2, int y2))) {
      System.out.println("\nLine from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")");
      double length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
      System.out.println("Length: " + length);
    }

    // Record patterns in switch
    sealed interface Shape permits Circle, Rect {}
    record Circle(Point center, int radius) implements Shape {}
    record Rect(Point topLeft, Point bottomRight) implements Shape {}

    Shape[] shapes = {
        new Circle(new Point(0, 0), 5),
        new Rect(new Point(0, 0), new Point(4, 3))
    };

    System.out.println("\nRecord patterns in switch:");
    for (Shape shape : shapes) {
      String description = switch (shape) {
        case Circle(Point(int x, int y), int r) ->
            "Circle at (" + x + "," + y + ") with radius " + r +
                ", area = " + (Math.PI * r * r);
        case Rect(Point(int x1, int y1), Point(int x2, int y2)) ->
            "Rectangle from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")" +
                ", area = " + Math.abs((x2 - x1) * (y2 - y1));
      };
      System.out.println("  " + description);
    }

    // With var
    if (line instanceof Line(var start, var end)) {
      System.out.println("\nUsing var: start=" + start + ", end=" + end);
    }

    System.out.println();
  }

  static void demonstratePatternMatchingSwitch() {
    System.out.println("--- Pattern Matching for switch (Standard) ---");

    // Comprehensive pattern matching (now standard)
    Object[] values = {"Hello", 42, 3.14, null, List.of(1, 2, 3), new int[]{1, 2}};

    for (Object value : values) {
      String result = switch (value) {
        case null -> "null value";
        case String s when s.length() > 5 -> "Long string: " + s;
        case String s -> "Short string: " + s;
        case Integer i when i < 0 -> "Negative int: " + i;
        case Integer i -> "Positive int: " + i;
        case Double d -> "Double: " + d;
        case List<?> list when list.isEmpty() -> "Empty list";
        case List<?> list -> "List with " + list.size() + " elements";
        case int[] arr -> "int array with " + arr.length + " elements";
        default -> "Unknown: " + value.getClass().getSimpleName();
      };
      System.out.println(result);
    }

    // Exhaustiveness with sealed types
    System.out.println("\nExhaustive switch with sealed types:");

    sealed interface Coin permits Penny, Nickel, Dime, Quarter {}
    record Penny() implements Coin {}
    record Nickel() implements Coin {}
    record Dime() implements Coin {}
    record Quarter() implements Coin {}

    Coin[] coins = {new Penny(), new Nickel(), new Dime(), new Quarter()};
    int total = 0;
    for (Coin coin : coins) {
      int value = switch (coin) {
        case Penny() -> 1;
        case Nickel() -> 5;
        case Dime() -> 10;
        case Quarter() -> 25;
      };  // No default needed - compiler knows all cases covered
      total += value;
      System.out.println("  " + coin.getClass().getSimpleName() + " = " + value + " cents");
    }
    System.out.println("Total: " + total + " cents");

    System.out.println();
  }
}
