package com.paul.fullstack_interview_prep.examples;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * Q30: What are Functional Interfaces in Java?
 *
 * Functional Interface: Interface with exactly one abstract method (SAM).
 * Can have multiple default or static methods.
 *
 * Used with:
 * - Lambda expressions
 * - Method references
 *
 * Built-in functional interfaces (java.util.function):
 * - Predicate<T>: T -> boolean
 * - Function<T,R>: T -> R
 * - Consumer<T>: T -> void
 * - Supplier<T>: () -> T
 * - BiFunction<T,U,R>: (T,U) -> R
 * - UnaryOperator<T>: T -> T
 * - BinaryOperator<T>: (T,T) -> T
 */
public class Q30_FunctionalInterfaces {

  public static void main(String[] args) {
    System.out.println("=== Functional Interfaces Demo ===\n");

    // Custom functional interface
    System.out.println("--- Custom Functional Interface ---");
    demonstrateCustom();

    // Built-in functional interfaces
    System.out.println("\n--- Built-in Functional Interfaces ---");
    demonstrateBuiltIn();

    // Primitive specializations
    System.out.println("\n--- Primitive Specializations ---");
    demonstratePrimitives();

    // Composing functions
    System.out.println("\n--- Composing Functions ---");
    demonstrateComposition();

    // Real-world usage
    System.out.println("\n--- Real-World Usage ---");
    demonstrateRealWorld();
  }

  static void demonstrateCustom() {
    // Custom functional interface
    @FunctionalInterface
    interface Calculator {
      int calculate(int a, int b);

      // Can have default methods
      default int addTen(int x) { return x + 10; }

      // Can have static methods
      static int zero() { return 0; }
    }

    // Using with lambda
    Calculator add = (a, b) -> a + b;
    Calculator multiply = (a, b) -> a * b;
    Calculator max = Math::max; // Method reference

    System.out.println("add(5, 3) = " + add.calculate(5, 3));
    System.out.println("multiply(5, 3) = " + multiply.calculate(5, 3));
    System.out.println("max(5, 3) = " + max.calculate(5, 3));
    System.out.println("add.addTen(5) = " + add.addTen(5));
    System.out.println("Calculator.zero() = " + Calculator.zero());

    // @FunctionalInterface is optional but recommended
    System.out.println("\n@FunctionalInterface annotation:");
    System.out.println("- Optional but recommended");
    System.out.println("- Compile error if more than one abstract method");
  }

  static void demonstrateBuiltIn() {
    // Predicate<T>: T -> boolean
    Predicate<Integer> isEven = n -> n % 2 == 0;
    System.out.println("Predicate isEven(4): " + isEven.test(4));

    // Function<T, R>: T -> R
    Function<String, Integer> length = String::length;
    System.out.println("Function length(\"Hello\"): " + length.apply("Hello"));

    // Consumer<T>: T -> void
    Consumer<String> printer = System.out::println;
    System.out.print("Consumer printer: ");
    printer.accept("Hello from Consumer!");

    // Supplier<T>: () -> T
    Supplier<Double> random = Math::random;
    System.out.println("Supplier random: " + random.get());

    // BiFunction<T, U, R>: (T, U) -> R
    BiFunction<String, String, String> concat = (a, b) -> a + b;
    System.out.println("BiFunction concat: " + concat.apply("Hello, ", "World!"));

    // UnaryOperator<T>: T -> T (extends Function<T,T>)
    UnaryOperator<Integer> square = x -> x * x;
    System.out.println("UnaryOperator square(5): " + square.apply(5));

    // BinaryOperator<T>: (T, T) -> T (extends BiFunction<T,T,T>)
    BinaryOperator<Integer> sum = Integer::sum;
    System.out.println("BinaryOperator sum(3, 7): " + sum.apply(3, 7));

    // BiPredicate<T, U>: (T, U) -> boolean
    BiPredicate<String, Integer> lengthCheck = (s, len) -> s.length() > len;
    System.out.println("BiPredicate lengthCheck(\"Hello\", 3): " + lengthCheck.test("Hello", 3));

    // BiConsumer<T, U>: (T, U) -> void
    BiConsumer<String, Integer> printPair = (k, v) -> System.out.println("BiConsumer: " + k + "=" + v);
    printPair.accept("age", 25);
  }

  static void demonstratePrimitives() {
    // Avoid boxing/unboxing with primitive specializations
    System.out.println("Primitive specializations avoid boxing/unboxing:");

    // IntPredicate, LongPredicate, DoublePredicate
    IntPredicate isPositive = n -> n > 0;
    System.out.println("IntPredicate isPositive(5): " + isPositive.test(5));

    // IntFunction<R>, LongFunction<R>, DoubleFunction<R>
    IntFunction<String> intToString = Integer::toString;
    System.out.println("IntFunction intToString(42): " + intToString.apply(42));

    // ToIntFunction<T>, ToLongFunction<T>, ToDoubleFunction<T>
    ToIntFunction<String> stringLength = String::length;
    System.out.println("ToIntFunction stringLength(\"Hi\"): " + stringLength.applyAsInt("Hi"));

    // IntConsumer, LongConsumer, DoubleConsumer
    IntConsumer printInt = n -> System.out.println("IntConsumer: " + n);
    printInt.accept(100);

    // IntSupplier, LongSupplier, DoubleSupplier
    IntSupplier randomInt = () -> (int) (Math.random() * 100);
    System.out.println("IntSupplier randomInt: " + randomInt.getAsInt());

    // IntUnaryOperator, LongUnaryOperator, DoubleUnaryOperator
    IntUnaryOperator doubleIt = n -> n * 2;
    System.out.println("IntUnaryOperator doubleIt(5): " + doubleIt.applyAsInt(5));

    // IntBinaryOperator, LongBinaryOperator, DoubleBinaryOperator
    IntBinaryOperator multiply = (a, b) -> a * b;
    System.out.println("IntBinaryOperator multiply(3, 4): " + multiply.applyAsInt(3, 4));
  }

  static void demonstrateComposition() {
    // Predicate composition
    Predicate<Integer> isEven = n -> n % 2 == 0;
    Predicate<Integer> isPositive = n -> n > 0;

    Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
    Predicate<Integer> isEvenOrPositive = isEven.or(isPositive);
    Predicate<Integer> isOdd = isEven.negate();

    System.out.println("isEvenAndPositive(4): " + isEvenAndPositive.test(4));
    System.out.println("isEvenOrPositive(-2): " + isEvenOrPositive.test(-2));
    System.out.println("isOdd(5): " + isOdd.test(5));

    // Function composition
    Function<Integer, Integer> times2 = x -> x * 2;
    Function<Integer, Integer> plus10 = x -> x + 10;

    Function<Integer, Integer> times2ThenPlus10 = times2.andThen(plus10);
    Function<Integer, Integer> plus10ThenTimes2 = times2.compose(plus10);

    System.out.println("\ntimes2.andThen(plus10).apply(5): " + times2ThenPlus10.apply(5)); // (5*2)+10=20
    System.out.println("times2.compose(plus10).apply(5): " + plus10ThenTimes2.apply(5)); // (5+10)*2=30

    // Consumer composition
    Consumer<String> log = s -> System.out.print("[LOG] " + s);
    Consumer<String> newLine = s -> System.out.println();
    Consumer<String> logWithNewLine = log.andThen(newLine);
    logWithNewLine.accept("Composed consumer");
  }

  static void demonstrateRealWorld() {
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

    // Filter with Predicate
    System.out.println("Names with length > 3:");
    names.stream()
        .filter(name -> name.length() > 3)
        .forEach(name -> System.out.println("  " + name));

    // Transform with Function
    System.out.println("\nUppercase names:");
    names.stream()
        .map(String::toUpperCase)
        .forEach(name -> System.out.println("  " + name));

    // Reduce with BinaryOperator
    String allNames = names.stream()
        .reduce("", (a, b) -> a + (a.isEmpty() ? "" : ", ") + b);
    System.out.println("\nConcatenated: " + allNames);

    // Custom processing with functional interfaces
    System.out.println("\nCustom processing:");
    processData(
        names,
        name -> name.startsWith("A") || name.startsWith("E"),
        String::toUpperCase,
        name -> System.out.println("  Processed: " + name)
    );
  }

  // Generic method using functional interfaces
  static <T, R> void processData(
      List<T> data,
      Predicate<T> filter,
      Function<T, R> transformer,
      Consumer<R> action) {
    data.stream()
        .filter(filter)
        .map(transformer)
        .forEach(action);
  }
}
