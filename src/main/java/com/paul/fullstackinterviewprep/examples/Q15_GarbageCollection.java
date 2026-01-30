package com.paul.fullstackinterviewprep.examples;

import java.lang.ref.WeakReference;

/**
 * Q15: What is Garbage Collection in Java?
 *
 * Garbage Collection (GC):
 * - Automatic memory management
 * - Identifies and removes unreachable objects
 * - Runs in background (daemon thread)
 * - Cannot be forced, only requested (System.gc())
 *
 * Object becomes eligible for GC when:
 * - No references point to it
 * - All references are set to null
 * - Object goes out of scope
 *
 * GC Algorithms: Serial, Parallel, G1, ZGC, Shenandoah
 */
public class Q15_GarbageCollection {

  public static void main(String[] args) {
    System.out.println("=== Garbage Collection Demo ===\n");

    // Demonstrate object eligibility
    System.out.println("--- Objects Eligible for GC ---");
    demonstrateGCEligibility();

    // Memory before/after
    System.out.println("\n--- Memory Usage ---");
    demonstrateMemoryUsage();

    // Weak references
    System.out.println("\n--- Weak References ---");
    demonstrateWeakReference();

    // GC info
    System.out.println("\n--- GC Information ---");
    printGCInfo();
  }

  static void demonstrateGCEligibility() {
    // Case 1: Reference set to null
    GCObject obj1 = new GCObject("Object1");
    obj1 = null; // Object1 now eligible for GC
    System.out.println("Case 1: obj1 = null -> eligible for GC");

    // Case 2: Reference reassigned
    GCObject obj2 = new GCObject("Object2");
    obj2 = new GCObject("Object3"); // Object2 now eligible for GC
    System.out.println("Case 2: obj2 reassigned -> Object2 eligible for GC");

    // Case 3: Object created in method (goes out of scope)
    createAndAbandon();
    System.out.println("Case 3: Object created in method -> eligible when method returns");

    // Case 4: Island of isolation
    GCObject objA = new GCObject("A");
    GCObject objB = new GCObject("B");
    objA.reference = objB;
    objB.reference = objA;
    objA = null;
    objB = null;
    System.out.println("Case 4: Circular references, both null -> both eligible for GC");
  }

  static void createAndAbandon() {
    GCObject temp = new GCObject("Temporary");
    // temp goes out of scope when method returns
  }

  static void demonstrateMemoryUsage() {
    Runtime runtime = Runtime.getRuntime();

    // Before creating objects
    long before = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Memory used before: " + before / 1024 + " KB");

    // Create many objects
    Object[] objects = new Object[100000];
    for (int i = 0; i < objects.length; i++) {
      objects[i] = new byte[100];
    }

    long afterCreation = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Memory used after creating objects: " + afterCreation / 1024 + " KB");

    // Make objects eligible for GC
    objects = null;

    // Request garbage collection (not guaranteed)
    System.gc();

    // Wait a bit for GC to run
    try { Thread.sleep(100); } catch (InterruptedException e) {}

    long afterGC = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Memory used after GC request: " + afterGC / 1024 + " KB");
    System.out.println("Note: System.gc() is a request, not a command");
  }

  static void demonstrateWeakReference() {
    // Strong reference
    GCObject strong = new GCObject("Strong");
    System.out.println("Strong reference created");

    // Weak reference
    WeakReference<GCObject> weak = new WeakReference<>(new GCObject("Weak"));
    System.out.println("Weak reference created: " + weak.get());

    // Request GC
    System.gc();
    try { Thread.sleep(100); } catch (InterruptedException e) {}

    System.out.println("After GC:");
    System.out.println("Strong reference still exists: " + (strong != null));
    System.out.println("Weak reference may be collected: " + weak.get());
    System.out.println("Note: Weak references are collected when only weak refs exist");
  }

  static void printGCInfo() {
    System.out.println("Available GC info:");
    java.lang.management.ManagementFactory.getGarbageCollectorMXBeans()
        .forEach(gc -> {
          System.out.println("  GC Name: " + gc.getName());
          System.out.println("  Collection count: " + gc.getCollectionCount());
          System.out.println("  Collection time: " + gc.getCollectionTime() + " ms");
        });
  }

  static class GCObject {
    String name;
    GCObject reference; // For circular reference demo

    GCObject(String name) {
      this.name = name;
    }

    @Override
    protected void finalize() {
      System.out.println("  finalize() called for: " + name);
    }
  }
}
