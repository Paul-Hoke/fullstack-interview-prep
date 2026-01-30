package com.paul.fullstackinterviewprep.examples;

/**
 * Q27: What is the difference between Composition and Inheritance?
 *
 * Inheritance (IS-A):
 * - "Dog IS-A Animal"
 * - Child class inherits from parent class
 * - Tight coupling
 * - Can lead to fragile base class problem
 *
 * Composition (HAS-A):
 * - "Car HAS-A Engine"
 * - Class contains instances of other classes
 * - Loose coupling
 * - More flexible, preferred in most cases
 *
 * Principle: "Favor composition over inheritance"
 */
public class Q27_CompositionVsInheritance {

  public static void main(String[] args) {
    System.out.println("=== Composition vs Inheritance Demo ===\n");

    // Inheritance example
    System.out.println("--- Inheritance (IS-A) ---");
    demonstrateInheritance();

    // Problem with inheritance
    System.out.println("\n--- Problems with Inheritance ---");
    demonstrateInheritanceProblem();

    // Composition example
    System.out.println("\n--- Composition (HAS-A) ---");
    demonstrateComposition();

    // When to use each
    System.out.println("\n--- Guidelines ---");
    printGuidelines();
  }

  static void demonstrateInheritance() {
    Dog dog = new Dog("Buddy");
    dog.eat();   // Inherited from Animal
    dog.bark();  // Dog-specific

    Cat cat = new Cat("Whiskers");
    cat.eat();   // Inherited from Animal
    cat.meow();  // Cat-specific

    // Polymorphism works
    InheritanceAnimal animal = dog;
    animal.eat();
  }

  static void demonstrateInheritanceProblem() {
    // Problem: Inflexible hierarchy
    System.out.println("Problem: What if we need a RobotDog?");
    System.out.println("- RobotDog doesn't eat (but Animal.eat() is inherited)");
    System.out.println("- RobotDog can't extend both Robot and Animal");
    System.out.println("- Changing Animal affects all subclasses");

    // Fragile base class problem
    System.out.println("\nFragile base class: Changing parent can break children");
  }

  static void demonstrateComposition() {
    // Compose behaviors
    Walkable legs = () -> System.out.println("Walking on legs");
    Swimmable fins = () -> System.out.println("Swimming with fins");
    Flyable wings = () -> System.out.println("Flying with wings");

    // Duck can walk, swim, and fly - composed behaviors
    ComposedAnimal duck = new ComposedAnimal("Duck", legs, fins, wings);
    duck.performActions();

    System.out.println();

    // Fish can only swim - null for other behaviors
    ComposedAnimal fish = new ComposedAnimal("Fish", null, fins, null);
    fish.performActions();

    System.out.println();

    // Can easily create RobotDog with composition
    System.out.println("RobotDog with composition:");
    Walkable robotLegs = () -> System.out.println("Walking on mechanical legs");
    Barkable robotBark = () -> System.out.println("Synthetic bark!");
    RobotDog robotDog = new RobotDog("RoboDog", robotLegs, robotBark);
    robotDog.walk();
    robotDog.bark();
    System.out.println("No eating needed - no forced inheritance!");
  }

  static void printGuidelines() {
    System.out.println("Use INHERITANCE when:");
    System.out.println("- True IS-A relationship exists");
    System.out.println("- You need to reuse code in many subclasses");
    System.out.println("- You need polymorphism");
    System.out.println("- The relationship is stable and unlikely to change");

    System.out.println("\nUse COMPOSITION when:");
    System.out.println("- HAS-A relationship is more appropriate");
    System.out.println("- You want to combine behaviors flexibly");
    System.out.println("- You want loose coupling");
    System.out.println("- Behavior might change at runtime");
    System.out.println("- Multiple inheritance is needed (Java doesn't support it)");

    System.out.println("\nRule of thumb: Favor composition over inheritance");
  }

  // ========== INHERITANCE EXAMPLE ==========
  static class InheritanceAnimal {
    String name;

    InheritanceAnimal(String name) {
      this.name = name;
    }

    void eat() {
      System.out.println(name + " is eating");
    }
  }

  static class Dog extends InheritanceAnimal {
    Dog(String name) { super(name); }

    void bark() {
      System.out.println(name + " says: Woof!");
    }
  }

  static class Cat extends InheritanceAnimal {
    Cat(String name) { super(name); }

    void meow() {
      System.out.println(name + " says: Meow!");
    }
  }

  // ========== COMPOSITION EXAMPLE ==========
  interface Walkable {
    void walk();
  }

  interface Swimmable {
    void swim();
  }

  interface Flyable {
    void fly();
  }

  interface Barkable {
    void bark();
  }

  // Composed from behaviors
  static class ComposedAnimal {
    private final String name;
    private final Walkable walkBehavior;
    private final Swimmable swimBehavior;
    private final Flyable flyBehavior;

    ComposedAnimal(String name, Walkable walk, Swimmable swim, Flyable fly) {
      this.name = name;
      this.walkBehavior = walk;
      this.swimBehavior = swim;
      this.flyBehavior = fly;
    }

    void performActions() {
      System.out.println(name + " can:");
      if (walkBehavior != null) walkBehavior.walk();
      if (swimBehavior != null) swimBehavior.swim();
      if (flyBehavior != null) flyBehavior.fly();
    }
  }

  // RobotDog - would be impossible with inheritance
  static class RobotDog {
    private final String name;
    private final Walkable walkBehavior;
    private final Barkable barkBehavior;

    RobotDog(String name, Walkable walk, Barkable bark) {
      this.name = name;
      this.walkBehavior = walk;
      this.barkBehavior = bark;
    }

    void walk() { walkBehavior.walk(); }
    void bark() { barkBehavior.bark(); }
    // No eat() - not forced by inheritance!
  }
}
