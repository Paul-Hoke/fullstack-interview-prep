package com.paul.fullstackinterviewprep.javaversions;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Java 12 - March 2019
 * Switch expressions (preview), String methods, Compact Number Formatting.
 */
public class Java12Features {

  public static void main(String[] args) {
    System.out.println("=== Java 12 Features ===\n");

    demonstrateSwitchExpressions();
    demonstrateStringMethods();
    demonstrateCompactNumberFormatting();
  }

  static void demonstrateSwitchExpressions() {
    System.out.println("--- Switch Expressions ---");

    // Traditional switch statement (before Java 12)
    String day = "WEDNESDAY";
    String typeOld;
    switch (day) {
      case "MONDAY":
      case "TUESDAY":
      case "WEDNESDAY":
      case "THURSDAY":
      case "FRIDAY":
        typeOld = "Weekday";
        break;
      case "SATURDAY":
      case "SUNDAY":
        typeOld = "Weekend";
        break;
      default:
        typeOld = "Unknown";
    }
    System.out.println("Traditional switch: " + day + " -> " + typeOld);

    // Switch expression with arrow syntax (Java 12+)
    String typeNew = switch (day) {
      case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
      case "SATURDAY", "SUNDAY" -> "Weekend";
      default -> "Unknown";
    };
    System.out.println("Switch expression: " + day + " -> " + typeNew);

    // Switch expression with block and yield
    int dayNumber = 3;
    String dayName = switch (dayNumber) {
      case 1 -> "Monday";
      case 2 -> "Tuesday";
      case 3 -> {
        System.out.println("  (Computing day 3...)");
        yield "Wednesday";
      }
      case 4 -> "Thursday";
      case 5 -> "Friday";
      case 6, 7 -> "Weekend";
      default -> {
        System.out.println("  (Invalid day number)");
        yield "Unknown";
      }
    };
    System.out.println("Day " + dayNumber + " is: " + dayName);

    // Returning values from switch
    int month = 2;
    int year = 2024;
    int daysInMonth = switch (month) {
      case 1, 3, 5, 7, 8, 10, 12 -> 31;
      case 4, 6, 9, 11 -> 30;
      case 2 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
      default -> throw new IllegalArgumentException("Invalid month: " + month);
    };
    System.out.println("Days in month " + month + " of " + year + ": " + daysInMonth);

    System.out.println();
  }

  static void demonstrateStringMethods() {
    System.out.println("--- String Methods: indent() and transform() ---");

    // indent(n) - adjusts indentation
    String text = "Hello\nWorld";
    System.out.println("Original:");
    System.out.println(text);

    System.out.println("\nindent(4):");
    System.out.println(text.indent(4));

    System.out.println("indent(-2) on indented text:");
    String indented = "    Hello\n    World";
    System.out.println(indented.indent(-2));

    // transform() - apply function to string
    String result = "hello"
        .transform(s -> s.toUpperCase())
        .transform(s -> s + "!")
        .transform(s -> "[" + s + "]");
    System.out.println("transform() chain result: " + result);

    // Practical use of transform
    String processed = "  java 12 features  "
        .transform(String::strip)
        .transform(String::toUpperCase)
        .transform(s -> s.replace(" ", "_"));
    System.out.println("Processed string: " + processed);

    System.out.println();
  }

  static void demonstrateCompactNumberFormatting() {
    System.out.println("--- Compact Number Formatting ---");

    // Short format
    NumberFormat shortFormat = NumberFormat.getCompactNumberInstance(
        Locale.US, NumberFormat.Style.SHORT);

    System.out.println("Short format (US):");
    System.out.println("  1,000 -> " + shortFormat.format(1000));
    System.out.println("  10,000 -> " + shortFormat.format(10000));
    System.out.println("  1,000,000 -> " + shortFormat.format(1000000));
    System.out.println("  1,500,000 -> " + shortFormat.format(1500000));
    System.out.println("  1,000,000,000 -> " + shortFormat.format(1000000000));

    // Long format
    NumberFormat longFormat = NumberFormat.getCompactNumberInstance(
        Locale.US, NumberFormat.Style.LONG);

    System.out.println("\nLong format (US):");
    System.out.println("  1,000 -> " + longFormat.format(1000));
    System.out.println("  1,000,000 -> " + longFormat.format(1000000));
    System.out.println("  1,000,000,000 -> " + longFormat.format(1000000000));

    // Different locale
    NumberFormat germanFormat = NumberFormat.getCompactNumberInstance(
        Locale.GERMANY, NumberFormat.Style.SHORT);

    System.out.println("\nShort format (German):");
    System.out.println("  1,000 -> " + germanFormat.format(1000));
    System.out.println("  1,000,000 -> " + germanFormat.format(1000000));

    // With decimal places
    shortFormat.setMaximumFractionDigits(2);
    System.out.println("\nWith 2 decimal places:");
    System.out.println("  1,234 -> " + shortFormat.format(1234));
    System.out.println("  1,234,567 -> " + shortFormat.format(1234567));

    System.out.println();
  }
}
