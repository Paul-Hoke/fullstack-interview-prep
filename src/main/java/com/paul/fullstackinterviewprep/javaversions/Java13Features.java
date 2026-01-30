package com.paul.fullstackinterviewprep.javaversions;

/**
 * Java 13 - September 2019
 * Text blocks (preview), switch yield keyword.
 */
public class Java13Features {

  public static void main(String[] args) {
    System.out.println("=== Java 13 Features ===\n");

    demonstrateTextBlocks();
    demonstrateSwitchYield();
  }

  static void demonstrateTextBlocks() {
    System.out.println("--- Text Blocks (Preview in 13, Standard in 15) ---");

    // Traditional multi-line string (before Java 13)
    String jsonOld = "{\n" +
        "    \"name\": \"John\",\n" +
        "    \"age\": 30,\n" +
        "    \"city\": \"New York\"\n" +
        "}";
    System.out.println("Traditional string concatenation:");
    System.out.println(jsonOld);

    // Text block (Java 13+)
    String jsonNew = """
        {
            "name": "John",
            "age": 30,
            "city": "New York"
        }
        """;
    System.out.println("\nText block:");
    System.out.println(jsonNew);

    // HTML example
    String html = """
        <html>
            <head>
                <title>Hello</title>
            </head>
            <body>
                <h1>Hello, World!</h1>
            </body>
        </html>
        """;
    System.out.println("HTML text block:");
    System.out.println(html);

    // SQL example
    String sql = """
        SELECT id, name, email
        FROM users
        WHERE status = 'ACTIVE'
          AND created_at > '2024-01-01'
        ORDER BY name
        """;
    System.out.println("SQL text block:");
    System.out.println(sql);

    // Escape sequences in text blocks
    String withEscapes = """
        Tab:\there
        Backslash: \\
        Quote: "no escape needed"
        Triple quote: \"""
        """;
    System.out.println("Escape sequences:");
    System.out.println(withEscapes);

    // Controlling line endings and whitespace
    String noTrailingNewline = """
        This text block
        has no trailing newline""";
    System.out.println("No trailing newline: [" + noTrailingNewline + "]");

    // Using \ to prevent line break
    String singleLine = """
        This is a very long line that we want to \
        break in the source code but not in the output""";
    System.out.println("\nLine continuation with \\:");
    System.out.println(singleLine);

    // String interpolation (manual - templates came later)
    String name = "Alice";
    int age = 25;
    String template = """
        {
            "name": "%s",
            "age": %d
        }
        """.formatted(name, age);
    System.out.println("With String.formatted():");
    System.out.println(template);

    System.out.println();
  }

  static void demonstrateSwitchYield() {
    System.out.println("--- Switch with yield ---");

    // yield keyword for returning values from switch blocks
    int dayNumber = 4;
    String dayType = switch (dayNumber) {
      case 1, 2, 3, 4, 5 -> {
        System.out.println("  Processing weekday...");
        yield "Weekday";
      }
      case 6, 7 -> {
        System.out.println("  Processing weekend...");
        yield "Weekend";
      }
      default -> {
        System.out.println("  Invalid day!");
        yield "Unknown";
      }
    };
    System.out.println("Day " + dayNumber + " is a: " + dayType);

    // yield is required when using block syntax
    String grade = "B";
    String description = switch (grade) {
      case "A" -> "Excellent";
      case "B" -> {
        String msg = "Good job!";
        System.out.println("  " + msg);
        yield "Good";  // yield required in block
      }
      case "C" -> "Average";
      case "D" -> "Below Average";
      case "F" -> "Failing";
      default -> {
        if (grade.isEmpty()) {
          yield "No grade";
        }
        yield "Invalid grade";
      }
    };
    System.out.println("Grade " + grade + ": " + description);

    // Colon syntax with yield (traditional switch expression)
    int month = 2;
    int days = switch (month) {
      case 1: case 3: case 5: case 7: case 8: case 10: case 12:
        yield 31;
      case 4: case 6: case 9: case 11:
        yield 30;
      case 2:
        yield 28;
      default:
        throw new IllegalArgumentException("Invalid month");
    };
    System.out.println("Month " + month + " has " + days + " days");

    System.out.println();
  }
}
