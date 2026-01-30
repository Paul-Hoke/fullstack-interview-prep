package com.paul.fullstackinterviewprep.examples;

import java.util.ArrayList;
import java.util.List;

/**
 * Q23: What is the difference between shallow copy and deep copy?
 *
 * Shallow Copy:
 * - Creates new object but references same nested objects
 * - Changes to nested objects affect both copies
 * - Faster, uses less memory
 *
 * Deep Copy:
 * - Creates new object AND copies all nested objects
 * - Changes to nested objects don't affect original
 * - Slower, uses more memory
 */
public class Q23_ShallowVsDeepCopy {

  public static void main(String[] args) {
    System.out.println("=== Shallow Copy vs Deep Copy Demo ===\n");

    // Shallow copy demonstration
    System.out.println("--- Shallow Copy ---");
    demonstrateShallowCopy();

    // Deep copy demonstration
    System.out.println("\n--- Deep Copy ---");
    demonstrateDeepCopy();

    // Copy constructors
    System.out.println("\n--- Copy Constructors ---");
    demonstrateCopyConstructor();

    // Clone method
    System.out.println("\n--- Clone Method ---");
    demonstrateClone();
  }

  static void demonstrateShallowCopy() {
    // Original object with nested object
    Address address = new Address("New York", "10001");
    Person original = new Person("Alice", address);

    // Shallow copy - shares the same Address object
    Person shallowCopy = original.shallowCopy();

    System.out.println("Original: " + original);
    System.out.println("Shallow Copy: " + shallowCopy);
    System.out.println("Same Address object? " + (original.address == shallowCopy.address));

    // Modify nested object in copy
    shallowCopy.address.city = "Los Angeles";
    shallowCopy.address.zip = "90001";

    System.out.println("\nAfter modifying copy's address:");
    System.out.println("Original: " + original);
    System.out.println("Shallow Copy: " + shallowCopy);
    System.out.println("PROBLEM: Original was also modified!");
  }

  static void demonstrateDeepCopy() {
    // Original object with nested object
    Address address = new Address("New York", "10001");
    Person original = new Person("Bob", address);

    // Deep copy - creates new Address object
    Person deepCopy = original.deepCopy();

    System.out.println("Original: " + original);
    System.out.println("Deep Copy: " + deepCopy);
    System.out.println("Same Address object? " + (original.address == deepCopy.address));

    // Modify nested object in copy
    deepCopy.address.city = "Chicago";
    deepCopy.address.zip = "60601";

    System.out.println("\nAfter modifying copy's address:");
    System.out.println("Original: " + original);
    System.out.println("Deep Copy: " + deepCopy);
    System.out.println("Original is NOT affected!");
  }

  static void demonstrateCopyConstructor() {
    // Using copy constructors for deep copy
    List<String> original = new ArrayList<>();
    original.add("A");
    original.add("B");

    // Shallow copy of list (but String is immutable, so safe here)
    List<String> copy = new ArrayList<>(original);

    copy.add("C");
    System.out.println("Original list: " + original);
    System.out.println("Copy list: " + copy);

    // With mutable objects
    List<StringBuilder> origSb = new ArrayList<>();
    origSb.add(new StringBuilder("Hello"));

    // Shallow copy - shares StringBuilder objects
    List<StringBuilder> copySb = new ArrayList<>(origSb);
    copySb.get(0).append(" World");

    System.out.println("\nWith mutable objects (shallow copy):");
    System.out.println("Original: " + origSb.get(0));
    System.out.println("Copy: " + copySb.get(0));
    System.out.println("Both modified because StringBuilder is shared!");
  }

  static void demonstrateClone() {
    CloneablePerson original = new CloneablePerson("Charlie", new Address("Boston", "02101"));

    try {
      // Using clone() - implements deep copy
      CloneablePerson cloned = (CloneablePerson) original.clone();

      cloned.address.city = "Seattle";

      System.out.println("Original: " + original);
      System.out.println("Cloned: " + cloned);
      System.out.println("Properly implemented clone() creates deep copy");
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  static class Address {
    String city;
    String zip;

    Address(String city, String zip) {
      this.city = city;
      this.zip = zip;
    }

    // Copy constructor for deep copy
    Address(Address other) {
      this.city = other.city;
      this.zip = other.zip;
    }

    @Override
    public String toString() {
      return city + " " + zip;
    }
  }

  static class Person {
    String name;
    Address address;

    Person(String name, Address address) {
      this.name = name;
      this.address = address;
    }

    // Shallow copy - shares Address reference
    Person shallowCopy() {
      return new Person(this.name, this.address);
    }

    // Deep copy - creates new Address
    Person deepCopy() {
      return new Person(this.name, new Address(this.address));
    }

    @Override
    public String toString() {
      return name + " @ " + address;
    }
  }

  static class CloneablePerson implements Cloneable {
    String name;
    Address address;

    CloneablePerson(String name, Address address) {
      this.name = name;
      this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
      CloneablePerson cloned = (CloneablePerson) super.clone();
      // Deep copy nested object
      cloned.address = new Address(this.address);
      return cloned;
    }

    @Override
    public String toString() {
      return name + " @ " + address;
    }
  }
}
