package com.paul.fullstack_interview_prep.examples;

/**
 * Q6: What is the difference between method overloading and method overriding?
 *
 * Overloading (Compile-time Polymorphism):
 * - Same method name, different parameters
 * - Occurs within the same class
 * - Return type can be different
 * - Resolved at compile time
 *
 * Overriding (Runtime Polymorphism):
 * - Same method signature in parent and child class
 * - Occurs between parent and child classes
 * - Return type must be same or covariant
 * - Resolved at runtime
 */
public class Q06_OverloadingVsOverriding {

  public static void main(String[] args) {
    System.out.println("=== Method Overloading vs Overriding Demo ===\n");

    // OVERLOADING
    System.out.println("--- Method Overloading ---");
    Calculator calc = new Calculator();
    System.out.println("add(5, 3) = " + calc.add(5, 3));
    System.out.println("add(5, 3, 2) = " + calc.add(5, 3, 2));
    System.out.println("add(5.5, 3.2) = " + calc.add(5.5, 3.2));

    // OVERRIDING
    System.out.println("\n--- Method Overriding ---");
    Animal animal = new Animal();
    Animal dog = new Dog();      // Polymorphism: parent reference, child object
    Animal cat = new Cat();

    animal.speak(); // Animal speaks
    dog.speak();    // Dog barks (overridden)
    cat.speak();    // Cat meows (overridden)

    System.out.println("\n--- Key Differences ---");
    System.out.println("Overloading: Resolved at COMPILE time (static binding)");
    System.out.println("Overriding: Resolved at RUNTIME (dynamic binding)");
  }

  // OVERLOADING: Same name, different parameters
  static class Calculator {
    int add(int a, int b) {
      System.out.print("add(int, int): ");
      return a + b;
    }

    int add(int a, int b, int c) {
      System.out.print("add(int, int, int): ");
      return a + b + c;
    }

    double add(double a, double b) {
      System.out.print("add(double, double): ");
      return a + b;
    }
  }

  // OVERRIDING: Child class redefines parent method
  static class Animal {
    void speak() {
      System.out.println("Animal makes a sound");
    }
  }

  static class Dog extends Animal {
    @Override
    void speak() {
      System.out.println("Dog barks: Woof!");
    }
  }

  static class Cat extends Animal {
    @Override
    void speak() {
      System.out.println("Cat meows: Meow!");
    }
  }
}
