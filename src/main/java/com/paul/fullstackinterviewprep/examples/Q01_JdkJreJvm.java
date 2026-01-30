package com.paul.fullstackinterviewprep.examples;

/**
 * Q1: What is the difference between JDK, JRE, and JVM?
 *
 * JVM (Java Virtual Machine): Executes Java bytecode. Platform-specific.
 * JRE (Java Runtime Environment): JVM + libraries needed to run Java programs.
 * JDK (Java Development Kit): JRE + development tools (compiler, debugger, etc.)
 *
 * Hierarchy: JDK > JRE > JVM
 */
public class Q01_JdkJreJvm {

  public static void main(String[] args) {
    System.out.println("=== JDK, JRE, JVM Demo ===\n");

    // Display runtime information
    System.out.println("Java Version: " + System.getProperty("java.version"));
    System.out.println("JVM Name: " + System.getProperty("java.vm.name"));
    System.out.println("JVM Vendor: " + System.getProperty("java.vm.vendor"));
    System.out.println("Java Home (JRE): " + System.getProperty("java.home"));

    System.out.println("\n--- Explanation ---");
    System.out.println("JVM: The virtual machine running this code right now");
    System.out.println("JRE: The runtime environment (JVM + core libraries)");
    System.out.println("JDK: Development kit used to compile this .java file into .class bytecode");
  }
}
