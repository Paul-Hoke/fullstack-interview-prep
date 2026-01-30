package com.paul.fullstackinterviewprep.examples;

/**
 * Q5: What are the four pillars of OOP?
 *
 * 1. Encapsulation: Bundling data and methods, hiding internal state
 * 2. Inheritance: Acquiring properties and behaviors from parent class
 * 3. Polymorphism: Same interface, different implementations
 * 4. Abstraction: Hiding complexity, showing only essential features
 */
public class Q05_OopPrinciples {

  public static void main(String[] args) {
    System.out.println("=== Four Pillars of OOP Demo ===\n");

    // 1. ENCAPSULATION
    System.out.println("--- 1. Encapsulation ---");
    BankAccount account = new BankAccount("12345", 1000);
    account.deposit(500);
    account.withdraw(200);
    System.out.println("Balance: $" + account.getBalance());
    // account.balance = -1000; // Cannot access - private field

    // 2. INHERITANCE
    System.out.println("\n--- 2. Inheritance ---");
    Car car = new Car("Toyota", "Camry", 4);
    car.displayInfo();

    // 3. POLYMORPHISM
    System.out.println("\n--- 3. Polymorphism ---");
    Shape[] shapes = {new Circle(5), new Rectangle(4, 6)};
    for (Shape shape : shapes) {
      System.out.println(shape.getClass().getSimpleName() + " area: " + shape.calculateArea());
    }

    // 4. ABSTRACTION
    System.out.println("\n--- 4. Abstraction ---");
    PaymentProcessor creditCard = new CreditCardPayment();
    PaymentProcessor paypal = new PayPalPayment();
    creditCard.processPayment(100);
    paypal.processPayment(50);
  }

  // ENCAPSULATION: Private fields with public getters/setters
  static class BankAccount {
    private String accountNumber;
    private double balance;

    BankAccount(String accountNumber, double balance) {
      this.accountNumber = accountNumber;
      this.balance = balance;
    }

    public void deposit(double amount) {
      if (amount > 0) balance += amount;
    }

    public void withdraw(double amount) {
      if (amount > 0 && amount <= balance) balance -= amount;
    }

    public double getBalance() {
      return balance;
    }
  }

  // INHERITANCE: Child class inherits from parent
  static class Vehicle {
    protected String brand;
    protected String model;

    Vehicle(String brand, String model) {
      this.brand = brand;
      this.model = model;
    }

    void displayInfo() {
      System.out.println("Vehicle: " + brand + " " + model);
    }
  }

  static class Car extends Vehicle {
    private int doors;

    Car(String brand, String model, int doors) {
      super(brand, model);
      this.doors = doors;
    }

    @Override
    void displayInfo() {
      System.out.println("Car: " + brand + " " + model + " with " + doors + " doors");
    }
  }

  // POLYMORPHISM: Same method, different behavior
  abstract static class Shape {
    abstract double calculateArea();
  }

  static class Circle extends Shape {
    private double radius;

    Circle(double radius) {
      this.radius = radius;
    }

    @Override
    double calculateArea() {
      return Math.PI * radius * radius;
    }
  }

  static class Rectangle extends Shape {
    private double width, height;

    Rectangle(double width, double height) {
      this.width = width;
      this.height = height;
    }

    @Override
    double calculateArea() {
      return width * height;
    }
  }

  // ABSTRACTION: Hide implementation details
  interface PaymentProcessor {
    void processPayment(double amount);
  }

  static class CreditCardPayment implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
      System.out.println("Processing credit card payment of $" + amount);
    }
  }

  static class PayPalPayment implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
      System.out.println("Processing PayPal payment of $" + amount);
    }
  }
}
