package com.paul.fullstackinterviewprep.examples;

import java.util.ArrayList;
import java.util.List;

/**
 * Q25: What are Design Patterns? Explain common ones.
 *
 * Design Patterns: Reusable solutions to common software design problems.
 *
 * Categories:
 * - Creational: Object creation (Singleton, Factory, Builder)
 * - Structural: Class composition (Adapter, Decorator, Facade)
 * - Behavioral: Object interaction (Observer, Strategy, Command)
 */
public class Q25_DesignPatterns {

  public static void main(String[] args) {
    System.out.println("=== Design Patterns Demo ===\n");

    // Creational Patterns
    System.out.println("--- CREATIONAL PATTERNS ---\n");
    demonstrateSingleton();
    demonstrateFactory();
    demonstrateBuilder();

    // Structural Patterns
    System.out.println("\n--- STRUCTURAL PATTERNS ---\n");
    demonstrateAdapter();
    demonstrateDecorator();

    // Behavioral Patterns
    System.out.println("\n--- BEHAVIORAL PATTERNS ---\n");
    demonstrateObserver();
    demonstrateStrategy();
  }

  // ========== SINGLETON ==========
  static void demonstrateSingleton() {
    System.out.println("1. SINGLETON - Single instance");
    DatabaseConnection db1 = DatabaseConnection.getInstance();
    DatabaseConnection db2 = DatabaseConnection.getInstance();
    System.out.println("   Same instance? " + (db1 == db2));
    db1.query("SELECT * FROM users");
  }

  static class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {}

    public static DatabaseConnection getInstance() {
      if (instance == null) {
        synchronized (DatabaseConnection.class) {
          if (instance == null) {
            instance = new DatabaseConnection();
          }
        }
      }
      return instance;
    }

    public void query(String sql) {
      System.out.println("   Executing: " + sql);
    }
  }

  // ========== FACTORY ==========
  static void demonstrateFactory() {
    System.out.println("\n2. FACTORY - Create objects without specifying class");
    Animal dog = AnimalFactory.createAnimal("dog");
    Animal cat = AnimalFactory.createAnimal("cat");
    dog.speak();
    cat.speak();
  }

  interface Animal {
    void speak();
  }

  static class Dog implements Animal {
    public void speak() { System.out.println("   Dog: Woof!"); }
  }

  static class Cat implements Animal {
    public void speak() { System.out.println("   Cat: Meow!"); }
  }

  static class AnimalFactory {
    public static Animal createAnimal(String type) {
      return switch (type.toLowerCase()) {
        case "dog" -> new Dog();
        case "cat" -> new Cat();
        default -> throw new IllegalArgumentException("Unknown animal");
      };
    }
  }

  // ========== BUILDER ==========
  static void demonstrateBuilder() {
    System.out.println("\n3. BUILDER - Step-by-step object construction");
    Pizza pizza = new Pizza.Builder()
        .size("Large")
        .cheese(true)
        .pepperoni(true)
        .mushrooms(false)
        .build();
    System.out.println("   " + pizza);
  }

  static class Pizza {
    private final String size;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;

    private Pizza(Builder builder) {
      this.size = builder.size;
      this.cheese = builder.cheese;
      this.pepperoni = builder.pepperoni;
      this.mushrooms = builder.mushrooms;
    }

    static class Builder {
      private String size;
      private boolean cheese;
      private boolean pepperoni;
      private boolean mushrooms;

      Builder size(String size) { this.size = size; return this; }
      Builder cheese(boolean cheese) { this.cheese = cheese; return this; }
      Builder pepperoni(boolean pepperoni) { this.pepperoni = pepperoni; return this; }
      Builder mushrooms(boolean mushrooms) { this.mushrooms = mushrooms; return this; }
      Pizza build() { return new Pizza(this); }
    }

    @Override
    public String toString() {
      return "Pizza{size='" + size + "', cheese=" + cheese + ", pepperoni=" + pepperoni + "}";
    }
  }

  // ========== ADAPTER ==========
  static void demonstrateAdapter() {
    System.out.println("4. ADAPTER - Make incompatible interfaces work together");
    LegacyPrinter legacyPrinter = new LegacyPrinter();
    ModernPrinter adapter = new PrinterAdapter(legacyPrinter);
    adapter.print("Hello from adapter!");
  }

  interface ModernPrinter {
    void print(String text);
  }

  static class LegacyPrinter {
    void printDocument(String doc) {
      System.out.println("   Legacy printing: " + doc);
    }
  }

  static class PrinterAdapter implements ModernPrinter {
    private final LegacyPrinter legacyPrinter;

    PrinterAdapter(LegacyPrinter legacyPrinter) {
      this.legacyPrinter = legacyPrinter;
    }

    public void print(String text) {
      legacyPrinter.printDocument(text);
    }
  }

  // ========== DECORATOR ==========
  static void demonstrateDecorator() {
    System.out.println("\n5. DECORATOR - Add behavior dynamically");
    Coffee coffee = new SimpleCoffee();
    System.out.println("   " + coffee.getDescription() + " $" + coffee.getCost());

    coffee = new MilkDecorator(coffee);
    System.out.println("   " + coffee.getDescription() + " $" + coffee.getCost());

    coffee = new SugarDecorator(coffee);
    System.out.println("   " + coffee.getDescription() + " $" + coffee.getCost());
  }

  interface Coffee {
    double getCost();
    String getDescription();
  }

  static class SimpleCoffee implements Coffee {
    public double getCost() { return 2.0; }
    public String getDescription() { return "Simple Coffee"; }
  }

  static abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
  }

  static class MilkDecorator extends CoffeeDecorator {
    MilkDecorator(Coffee coffee) { super(coffee); }
    public double getCost() { return coffee.getCost() + 0.5; }
    public String getDescription() { return coffee.getDescription() + " + Milk"; }
  }

  static class SugarDecorator extends CoffeeDecorator {
    SugarDecorator(Coffee coffee) { super(coffee); }
    public double getCost() { return coffee.getCost() + 0.2; }
    public String getDescription() { return coffee.getDescription() + " + Sugar"; }
  }

  // ========== OBSERVER ==========
  static void demonstrateObserver() {
    System.out.println("6. OBSERVER - Notify dependents of state changes");
    NewsAgency agency = new NewsAgency();
    agency.addObserver(news -> System.out.println("   Channel 1: " + news));
    agency.addObserver(news -> System.out.println("   Channel 2: " + news));
    agency.publishNews("Breaking News!");
  }

  interface NewsObserver {
    void update(String news);
  }

  static class NewsAgency {
    private final List<NewsObserver> observers = new ArrayList<>();

    void addObserver(NewsObserver observer) { observers.add(observer); }
    void publishNews(String news) {
      observers.forEach(o -> o.update(news));
    }
  }

  // ========== STRATEGY ==========
  static void demonstrateStrategy() {
    System.out.println("\n7. STRATEGY - Interchangeable algorithms");
    PaymentProcessor processor = new PaymentProcessor();

    processor.setStrategy(amount -> System.out.println("   Paid $" + amount + " with Credit Card"));
    processor.pay(100);

    processor.setStrategy(amount -> System.out.println("   Paid $" + amount + " with PayPal"));
    processor.pay(50);
  }

  interface PaymentStrategy {
    void pay(double amount);
  }

  static class PaymentProcessor {
    private PaymentStrategy strategy;

    void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    void pay(double amount) { strategy.pay(amount); }
  }
}
