package com.paul.fullstack_interview_prep.examples;

/**
 * Q18: What is the volatile keyword in Java?
 *
 * volatile:
 * - Ensures visibility of changes across threads
 * - Reads/writes go directly to main memory (not CPU cache)
 * - Prevents compiler optimization that reorders instructions
 * - Does NOT provide atomicity (use synchronized or Atomic* for that)
 *
 * Use when:
 * - One thread writes, others read
 * - Flag variables for thread communication
 * - Double-checked locking (with synchronized)
 */
public class Q18_VolatileKeyword {

  // Without volatile: thread may never see the update
  private static boolean stopRequested = false;

  // With volatile: guaranteed visibility
  private static volatile boolean volatileStop = false;

  public static void main(String[] args) throws InterruptedException {
    System.out.println("=== Volatile Keyword Demo ===\n");

    // Demonstrate visibility problem
    System.out.println("--- Visibility Problem (without volatile) ---");
    demonstrateVisibilityProblem();

    // Demonstrate volatile solution
    System.out.println("\n--- Volatile Solution ---");
    demonstrateVolatileSolution();

    // Demonstrate limitations
    System.out.println("\n--- Volatile Limitations ---");
    demonstrateLimitations();

    // Double-checked locking
    System.out.println("\n--- Double-Checked Locking Pattern ---");
    demonstrateDoubleCheckedLocking();
  }

  static void demonstrateVisibilityProblem() throws InterruptedException {
    stopRequested = false;

    Thread worker = new Thread(() -> {
      int counter = 0;
      while (!stopRequested) {
        counter++;
        // Without volatile, JVM may cache stopRequested locally
        // and never see the update from main thread
      }
      System.out.println("Worker stopped after " + counter + " iterations");
    });

    worker.start();
    Thread.sleep(100);
    stopRequested = true; // Main thread sets flag
    worker.join(1000); // Wait max 1 second

    if (worker.isAlive()) {
      System.out.println("WARNING: Worker may not have stopped (visibility problem)");
      worker.interrupt(); // Force stop for demo
    }
  }

  static void demonstrateVolatileSolution() throws InterruptedException {
    volatileStop = false;

    Thread worker = new Thread(() -> {
      int counter = 0;
      while (!volatileStop) {
        counter++;
      }
      System.out.println("Worker stopped after " + counter + " iterations");
    });

    worker.start();
    Thread.sleep(100);
    volatileStop = true; // Guaranteed to be visible to worker
    worker.join(1000);

    System.out.println("Volatile ensures visibility across threads");
  }

  static void demonstrateLimitations() {
    System.out.println("volatile does NOT provide atomicity!");
    System.out.println("counter++ is 3 operations: read, increment, write");
    System.out.println("Use AtomicInteger or synchronized for compound operations");

    // Example: volatile counter is NOT thread-safe
    VolatileCounter counter = new VolatileCounter();
    System.out.println("\nVolatile counter (not atomic):");
    System.out.println("  Read: thread-safe");
    System.out.println("  Write: thread-safe");
    System.out.println("  Increment: NOT thread-safe (use AtomicInteger)");
  }

  static void demonstrateDoubleCheckedLocking() {
    System.out.println("Singleton with double-checked locking:");
    System.out.println("  private static volatile Singleton instance;");
    System.out.println("  ");
    System.out.println("  public static Singleton getInstance() {");
    System.out.println("    if (instance == null) {              // First check (no lock)");
    System.out.println("      synchronized (Singleton.class) {");
    System.out.println("        if (instance == null) {          // Second check (with lock)");
    System.out.println("          instance = new Singleton();");
    System.out.println("        }");
    System.out.println("      }");
    System.out.println("    }");
    System.out.println("    return instance;");
    System.out.println("  }");
    System.out.println("\nVolatile prevents instruction reordering during object creation");

    // Actual singleton usage
    Singleton s1 = Singleton.getInstance();
    Singleton s2 = Singleton.getInstance();
    System.out.println("\nSame instance? " + (s1 == s2));
  }

  static class VolatileCounter {
    private volatile int count = 0;

    int getCount() { return count; } // Safe
    void setCount(int value) { count = value; } // Safe
    void increment() { count++; } // NOT safe! (not atomic)
  }

  // Thread-safe singleton with volatile
  static class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
      if (instance == null) {
        synchronized (Singleton.class) {
          if (instance == null) {
            instance = new Singleton();
          }
        }
      }
      return instance;
    }
  }
}
