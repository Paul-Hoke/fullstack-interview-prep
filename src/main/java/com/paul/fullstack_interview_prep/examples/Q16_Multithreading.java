package com.paul.fullstack_interview_prep.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Q16: What is multithreading and how do you create threads in Java?
 *
 * Multithreading: Concurrent execution of multiple threads within a program.
 *
 * Ways to create threads:
 * 1. Extend Thread class
 * 2. Implement Runnable interface
 * 3. Implement Callable interface (returns result)
 * 4. Use ExecutorService (thread pool)
 *
 * Thread states: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
 */
public class Q16_Multithreading {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("=== Multithreading Demo ===\n");

    // Method 1: Extend Thread
    System.out.println("--- Method 1: Extend Thread ---");
    demonstrateExtendThread();

    // Method 2: Implement Runnable
    System.out.println("\n--- Method 2: Implement Runnable ---");
    demonstrateRunnable();

    // Method 3: Lambda Runnable
    System.out.println("\n--- Method 3: Lambda Runnable ---");
    demonstrateLambda();

    // Method 4: ExecutorService
    System.out.println("\n--- Method 4: ExecutorService ---");
    demonstrateExecutorService();

    // Thread methods
    System.out.println("\n--- Thread Methods ---");
    demonstrateThreadMethods();
  }

  static void demonstrateExtendThread() throws InterruptedException {
    Thread t1 = new MyThread("Thread-A");
    Thread t2 = new MyThread("Thread-B");

    t1.start();
    t2.start();

    t1.join(); // Wait for t1 to complete
    t2.join(); // Wait for t2 to complete
    System.out.println("Both threads completed");
  }

  static void demonstrateRunnable() throws InterruptedException {
    Runnable task = new MyRunnable();
    Thread t1 = new Thread(task, "Runnable-1");
    Thread t2 = new Thread(task, "Runnable-2");

    t1.start();
    t2.start();

    t1.join();
    t2.join();
    System.out.println("Both runnable threads completed");
  }

  static void demonstrateLambda() throws InterruptedException {
    Thread t = new Thread(() -> {
      for (int i = 0; i < 3; i++) {
        System.out.println("Lambda thread: " + i);
        try { Thread.sleep(50); } catch (InterruptedException e) {}
      }
    }, "Lambda-Thread");

    t.start();
    t.join();
  }

  static void demonstrateExecutorService() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    for (int i = 0; i < 4; i++) {
      int taskId = i;
      executor.submit(() -> {
        System.out.println("Task " + taskId + " running on " + Thread.currentThread().getName());
        try { Thread.sleep(100); } catch (InterruptedException e) {}
      });
    }

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);
    System.out.println("All executor tasks completed");
  }

  static void demonstrateThreadMethods() throws InterruptedException {
    Thread t = new Thread(() -> {
      System.out.println("Thread name: " + Thread.currentThread().getName());
      System.out.println("Thread priority: " + Thread.currentThread().getPriority());
      System.out.println("Is daemon: " + Thread.currentThread().isDaemon());
    }, "Demo-Thread");

    t.setPriority(Thread.MAX_PRIORITY);
    System.out.println("Thread state before start: " + t.getState());

    t.start();
    System.out.println("Thread state after start: " + t.getState());

    t.join();
    System.out.println("Thread state after completion: " + t.getState());
  }

  // Method 1: Extend Thread class
  static class MyThread extends Thread {
    MyThread(String name) {
      super(name);
    }

    @Override
    public void run() {
      for (int i = 0; i < 3; i++) {
        System.out.println(getName() + ": " + i);
        try { Thread.sleep(50); } catch (InterruptedException e) {}
      }
    }
  }

  // Method 2: Implement Runnable interface
  static class MyRunnable implements Runnable {
    @Override
    public void run() {
      for (int i = 0; i < 3; i++) {
        System.out.println(Thread.currentThread().getName() + ": " + i);
        try { Thread.sleep(50); } catch (InterruptedException e) {}
      }
    }
  }
}
