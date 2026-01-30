package com.paul.fullstackinterviewprep.javaversions;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Java 24 - March 2025
 * Stream Gatherers (standard), Class-File API (standard), Ahead-of-Time Compilation.
 */
public class Java24Features {

  public static void main(String[] args) {
    System.out.println("=== Java 24 Features ===\n");

    demonstrateStreamGatherers();
    demonstrateClassFileApi();
    demonstrateAotCompilation();
  }

  static void demonstrateStreamGatherers() {
    System.out.println("--- Stream Gatherers (Standard) ---");

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println("Original: " + numbers);

    // Fixed-size windows
    System.out.println("\nwindowFixed(3) - non-overlapping windows:");
    List<List<Integer>> fixedWindows = numbers.stream()
        .gather(Gatherers.windowFixed(3))
        .toList();
    for (List<Integer> window : fixedWindows) {
      System.out.println("  " + window);
    }

    // Sliding windows
    System.out.println("\nwindowSliding(4) - overlapping windows:");
    List<List<Integer>> slidingWindows = numbers.stream()
        .gather(Gatherers.windowSliding(4))
        .toList();
    for (List<Integer> window : slidingWindows) {
      System.out.println("  " + window);
    }

    // Fold - accumulate with initial value
    System.out.println("\nfold() - accumulate values:");
    Integer sum = numbers.stream()
        .gather(Gatherers.fold(() -> 0, Integer::sum))
        .findFirst()
        .orElse(0);
    System.out.println("  Sum: " + sum);

    String concatenated = Stream.of("a", "b", "c", "d")
        .gather(Gatherers.fold(() -> "", (acc, s) -> acc + s))
        .findFirst()
        .orElse("");
    System.out.println("  Concatenated: " + concatenated);

    // Scan - like fold but emits intermediate values
    System.out.println("\nscan() - running totals:");
    List<Integer> runningTotals = numbers.stream()
        .gather(Gatherers.scan(() -> 0, Integer::sum))
        .toList();
    System.out.println("  Running totals: " + runningTotals);

    // Practical example: Moving average
    System.out.println("\nPractical: Calculate 3-period moving averages");
    List<Double> prices = List.of(100.0, 102.0, 101.0, 105.0, 107.0, 104.0);
    System.out.println("  Prices: " + prices);

    List<Double> movingAverages = prices.stream()
        .gather(Gatherers.windowSliding(3))
        .map(window -> window.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0))
        .toList();
    System.out.println("  Moving averages: " + movingAverages);

    // Combining gatherers with other stream operations
    System.out.println("\nCombined operations:");
    List<Integer> result = numbers.stream()
        .filter(n -> n % 2 == 0)  // Keep evens: [2, 4, 6, 8, 10]
        .gather(Gatherers.windowFixed(2))  // Pair them: [[2,4], [6,8], [10]]
        .map(window -> window.stream().mapToInt(Integer::intValue).sum())
        .toList();
    System.out.println("  Even numbers paired and summed: " + result);

    System.out.println();
  }

  static void demonstrateClassFileApi() {
    System.out.println("--- Class-File API (Standard) ---");

    System.out.println("The Class-File API allows reading, writing, and transforming");
    System.out.println("Java class files programmatically.");
    System.out.println();
    System.out.println("Use cases:");
    System.out.println("  - Bytecode analysis tools");
    System.out.println("  - Code generation frameworks");
    System.out.println("  - Bytecode manipulation (like ASM, but built-in)");
    System.out.println("  - Creating dynamic proxies");
    System.out.println();

    // Conceptual example - reading class info
    System.out.println("Example - Reading class metadata:");
    System.out.println("  ClassFile cf = ClassFile.of();");
    System.out.println("  ClassModel model = cf.parse(bytes);");
    System.out.println("  for (MethodModel method : model.methods()) {");
    System.out.println("      System.out.println(method.methodName());");
    System.out.println("  }");
    System.out.println();

    // Conceptual example - generating a class
    System.out.println("Example - Generating a simple class:");
    System.out.println("  byte[] bytes = ClassFile.of().build(");
    System.out.println("      ClassDesc.of(\"com.example.Hello\"),");
    System.out.println("      classBuilder -> {");
    System.out.println("          classBuilder.withMethod(");
    System.out.println("              \"greet\",");
    System.out.println("              MethodTypeDesc.of(CD_void),");
    System.out.println("              ACC_PUBLIC | ACC_STATIC,");
    System.out.println("              methodBuilder -> { ... }");
    System.out.println("          );");
    System.out.println("      });");
    System.out.println();

    System.out.println("Benefits over ASM/ByteBuddy:");
    System.out.println("  - Part of the JDK (no external dependency)");
    System.out.println("  - Updated automatically with new class file versions");
    System.out.println("  - Type-safe API design");
    System.out.println("  - Immutable, compositional model");

    System.out.println();
  }

  static void demonstrateAotCompilation() {
    System.out.println("--- Ahead-of-Time (AOT) Compilation Improvements ---");

    System.out.println("Java 24 improves AOT capabilities for better startup time.");
    System.out.println();
    System.out.println("Key improvements:");
    System.out.println("  1. Class Data Sharing (CDS) improvements");
    System.out.println("     - More classes can be archived");
    System.out.println("     - Faster startup by loading pre-parsed classes");
    System.out.println();
    System.out.println("  2. Leyden Project progress");
    System.out.println("     - Static compilation of Java programs");
    System.out.println("     - Constrained environments support");
    System.out.println();

    System.out.println("Creating a CDS archive:");
    System.out.println("  # Create class list");
    System.out.println("  java -Xshare:off -XX:DumpLoadedClassList=classes.lst -jar app.jar");
    System.out.println();
    System.out.println("  # Create archive");
    System.out.println("  java -Xshare:dump -XX:SharedArchiveFile=app.jsa \\");
    System.out.println("       -XX:SharedClassListFile=classes.lst");
    System.out.println();
    System.out.println("  # Run with archive");
    System.out.println("  java -Xshare:on -XX:SharedArchiveFile=app.jsa -jar app.jar");
    System.out.println();

    System.out.println("Startup improvements:");
    System.out.println("  - Without CDS: ~500ms startup");
    System.out.println("  - With CDS:    ~200ms startup");
    System.out.println("  - Future AOT:  ~50ms startup (goal)");

    System.out.println();
  }
}
