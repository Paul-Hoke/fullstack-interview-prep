package com.paul.fullstackinterviewprep.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Q8: What is the difference between checked and unchecked exceptions?
 *
 * Checked Exceptions:
 * - Checked at compile time
 * - Must be handled (try-catch) or declared (throws)
 * - Extend Exception (but not RuntimeException)
 * - Examples: IOException, SQLException, FileNotFoundException
 *
 * Unchecked Exceptions:
 * - Not checked at compile time
 * - Don't need to be explicitly handled
 * - Extend RuntimeException
 * - Examples: NullPointerException, ArrayIndexOutOfBoundsException
 */
public class Q08_CheckedVsUnchecked {

  public static void main(String[] args) {
    System.out.println("=== Checked vs Unchecked Exceptions Demo ===\n");

    // UNCHECKED EXCEPTIONS
    System.out.println("--- Unchecked Exceptions (RuntimeException) ---");
    demonstrateUnchecked();

    // CHECKED EXCEPTIONS
    System.out.println("\n--- Checked Exceptions ---");
    demonstrateChecked();

    // CUSTOM EXCEPTIONS
    System.out.println("\n--- Custom Exceptions ---");
    demonstrateCustom();
  }

  static void demonstrateUnchecked() {
    // NullPointerException
    try {
      String str = null;
      str.length(); // NullPointerException
    } catch (NullPointerException e) {
      System.out.println("NullPointerException: Accessing null reference");
    }

    // ArrayIndexOutOfBoundsException
    try {
      int[] arr = {1, 2, 3};
      int value = arr[5]; // ArrayIndexOutOfBoundsException
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("ArrayIndexOutOfBoundsException: Invalid array index");
    }

    // ArithmeticException
    try {
      int result = 10 / 0; // ArithmeticException
    } catch (ArithmeticException e) {
      System.out.println("ArithmeticException: Division by zero");
    }

    System.out.println("Note: Unchecked exceptions don't require try-catch, but it's often good practice");
  }

  static void demonstrateChecked() {
    // FileNotFoundException - must be handled or declared
    try {
      readFile("nonexistent.txt");
    } catch (FileNotFoundException e) {
      System.out.println("FileNotFoundException: " + e.getMessage());
    }

    System.out.println("Note: Checked exceptions MUST be handled or declared with 'throws'");
  }

  // Method that throws checked exception - must declare it
  static void readFile(String filename) throws FileNotFoundException {
    File file = new File(filename);
    FileReader fr = new FileReader(file); // throws FileNotFoundException
  }

  static void demonstrateCustom() {
    // Custom checked exception
    try {
      validateAge(-5);
    } catch (InvalidAgeException e) {
      System.out.println("Custom checked exception: " + e.getMessage());
    }

    // Custom unchecked exception
    try {
      validateUsername("");
    } catch (InvalidUsernameException e) {
      System.out.println("Custom unchecked exception: " + e.getMessage());
    }
  }

  // Custom CHECKED exception (extends Exception)
  static class InvalidAgeException extends Exception {
    InvalidAgeException(String message) {
      super(message);
    }
  }

  // Custom UNCHECKED exception (extends RuntimeException)
  static class InvalidUsernameException extends RuntimeException {
    InvalidUsernameException(String message) {
      super(message);
    }
  }

  static void validateAge(int age) throws InvalidAgeException {
    if (age < 0) {
      throw new InvalidAgeException("Age cannot be negative: " + age);
    }
  }

  static void validateUsername(String username) {
    if (username == null || username.isEmpty()) {
      throw new InvalidUsernameException("Username cannot be empty");
    }
  }
}
