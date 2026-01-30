package com.paul.fullstackinterviewprep.javaversions;

/**
 * Java 15 - September 2020
 * Text Blocks (standard), Sealed Classes (preview).
 */
public class Java15Features {

  public static void main(String[] args) {
    System.out.println("=== Java 15 Features ===\n");

    demonstrateTextBlocks();
    demonstrateSealedClasses();
    demonstrateStringFormatted();
  }

  static void demonstrateTextBlocks() {
    System.out.println("--- Text Blocks (Standard) ---");

    // Text blocks are now standard (no longer preview)
    String json = """
        {
            "name": "Java 15",
            "features": [
                "Text Blocks",
                "Sealed Classes",
                "Hidden Classes"
            ],
            "releaseDate": "2020-09-15"
        }
        """;
    System.out.println("JSON text block:");
    System.out.println(json);

    // New escape sequences in Java 15
    // \s - single space (prevents trailing whitespace removal)
    String poem = """
        Roses are red,  \s
        Violets are blue,\s
        Java 15,        \s
        Has text blocks too!
        """;
    System.out.println("With \\s escape (preserves trailing spaces):");
    System.out.println(poem);

    // \<newline> - line continuation
    String longLine = """
        This is a very long line that would be hard to read \
        if we couldn't break it across multiple lines in the \
        source code while keeping it as a single line in output.""";
    System.out.println("Line continuation with \\:");
    System.out.println(longLine);

    System.out.println();
  }

  static void demonstrateSealedClasses() {
    System.out.println("--- Sealed Classes (Preview in 15, Standard in 17) ---");

    // Sealed classes restrict which classes can extend them
    Shape circle = new Circle(5);
    Shape rectangle = new Rectangle(4, 6);
    Shape square = new Square(4);

    System.out.println("Circle area: " + circle.area());
    System.out.println("Rectangle area: " + rectangle.area());
    System.out.println("Square area: " + square.area());

    // Benefits of sealed classes:
    System.out.println("\nBenefits of sealed classes:");
    System.out.println("1. Controlled inheritance - only permitted subclasses");
    System.out.println("2. Exhaustive pattern matching in switch");
    System.out.println("3. Better domain modeling");

    // Pattern matching with sealed types
    for (Shape shape : new Shape[]{circle, rectangle, square}) {
      String description = describeShape(shape);
      System.out.println(description);
    }

    System.out.println();
  }

  // Sealed class - restricts which classes can extend it
  sealed interface Shape permits Circle, Rectangle, Square {
    double area();
  }

  // final - cannot be extended
  static final class Circle implements Shape {
    private final double radius;

    Circle(double radius) {
      this.radius = radius;
    }

    @Override
    public double area() {
      return Math.PI * radius * radius;
    }

    public double radius() {
      return radius;
    }
  }

  // final - cannot be extended
  static final class Rectangle implements Shape {
    private final double width;
    private final double height;

    Rectangle(double width, double height) {
      this.width = width;
      this.height = height;
    }

    @Override
    public double area() {
      return width * height;
    }

    public double width() {
      return width;
    }

    public double height() {
      return height;
    }
  }

  // non-sealed - can be extended by anyone
  static non-sealed class Square implements Shape {
    private final double side;

    Square(double side) {
      this.side = side;
    }

    @Override
    public double area() {
      return side * side;
    }

    public double side() {
      return side;
    }
  }

  static String describeShape(Shape shape) {
    return switch (shape) {
      case Circle c -> "Circle with radius " + c.radius();
      case Rectangle r -> "Rectangle " + r.width() + "x" + r.height();
      case Square s -> "Square with side " + s.side();
    };
  }

  static void demonstrateStringFormatted() {
    System.out.println("--- String.formatted() ---");

    // New formatted() instance method (alternative to String.format())
    String name = "Alice";
    int age = 30;
    double score = 95.5;

    // Old way
    String oldWay = String.format("Name: %s, Age: %d, Score: %.1f", name, age, score);
    System.out.println("String.format(): " + oldWay);

    // New way - formatted() instance method
    String newWay = "Name: %s, Age: %d, Score: %.1f".formatted(name, age, score);
    System.out.println("formatted(): " + newWay);

    // With text blocks
    String report = """
        Student Report
        ==============
        Name:  %s
        Age:   %d
        Score: %.1f%%
        Grade: %s
        """.formatted(name, age, score, score >= 90 ? "A" : "B");
    System.out.println("\nText block with formatted():");
    System.out.println(report);

    System.out.println();
  }
}
