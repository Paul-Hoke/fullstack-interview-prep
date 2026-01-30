package com.paul.fullstackinterviewprep.examples;

/**
 * Q4: What is the difference between abstract class and interface?
 *
 * Abstract Class:
 * - Can have abstract and concrete methods
 * - Can have instance variables (state)
 * - Can have constructors
 * - Single inheritance only
 * - Use when classes share common state/behavior
 *
 * Interface:
 * - All methods are public abstract by default (before Java 8)
 * - Can have default and static methods (Java 8+)
 * - Variables are public static final (constants)
 * - Multiple inheritance supported
 * - Use to define a contract/capability
 */
public class Q04_AbstractVsInterface {

  public static void main(String[] args) {
    System.out.println("=== Abstract Class vs Interface Demo ===\n");

    Dog dog = new Dog("Buddy");
    dog.eat();       // From abstract class
    dog.sleep();     // From abstract class (concrete method)
    dog.makeSound(); // Abstract method implementation
    dog.swim();      // From Swimmable interface
    dog.run();       // From Runnable interface

    System.out.println("\n--- Interface Default Method ---");
    dog.play(); // Default method from Swimmable
  }

  // Abstract class - shared state and behavior
  abstract static class Animal {
    protected String name; // Can have instance variables

    Animal(String name) { // Can have constructors
      this.name = name;
    }

    abstract void makeSound(); // Abstract method

    void sleep() { // Concrete method
      System.out.println(name + " is sleeping");
    }

    void eat() {
      System.out.println(name + " is eating");
    }
  }

  // Interface - defines capabilities
  interface Swimmable {
    void swim(); // Abstract method

    default void play() { // Default method (Java 8+)
      System.out.println("Playing in water!");
    }
  }

  interface Runnable {
    void run();
  }

  // Class extending abstract class and implementing multiple interfaces
  static class Dog extends Animal implements Swimmable, Runnable {
    Dog(String name) {
      super(name);
    }

    @Override
    void makeSound() {
      System.out.println(name + " says: Woof!");
    }

    @Override
    public void swim() {
      System.out.println(name + " is swimming");
    }

    @Override
    public void run() {
      System.out.println(name + " is running");
    }
  }
}
