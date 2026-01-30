package com.paul.fullstackinterviewprep.javaversions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Java 11 (LTS) - September 2018
 * String methods, HTTP Client, Files methods, var in lambdas.
 */
public class Java11Features {

  public static void main(String[] args) throws Exception {
    System.out.println("=== Java 11 Features ===\n");

    demonstrateStringMethods();
    demonstrateFileMethods();
    demonstrateVarInLambda();
    demonstratePredicateNot();
    demonstrateHttpClient();
  }

  static void demonstrateStringMethods() {
    System.out.println("--- New String Methods ---");

    // isBlank() - checks if empty or only whitespace
    System.out.println("\"   \".isBlank(): " + "   ".isBlank());       // true
    System.out.println("\"\".isBlank(): " + "".isBlank());             // true
    System.out.println("\"Hello\".isBlank(): " + "Hello".isBlank());   // false

    // strip(), stripLeading(), stripTrailing() - Unicode-aware trim
    String text = "  Hello World  ";
    System.out.println("\nOriginal: \"" + text + "\"");
    System.out.println("strip(): \"" + text.strip() + "\"");
    System.out.println("stripLeading(): \"" + text.stripLeading() + "\"");
    System.out.println("stripTrailing(): \"" + text.stripTrailing() + "\"");
    System.out.println("trim(): \"" + text.trim() + "\"");
    System.out.println("(strip() handles Unicode whitespace, trim() doesn't)");

    // repeat(n) - repeat string n times
    System.out.println("\n\"Ha\".repeat(3): " + "Ha".repeat(3));
    System.out.println("\"*\".repeat(10): " + "*".repeat(10));

    // lines() - split into stream of lines
    String multiline = "Line 1\nLine 2\nLine 3";
    System.out.println("\nMultiline string lines():");
    multiline.lines().forEach(line -> System.out.println("  " + line));

    // lines() with processing
    List<String> upperLines = multiline.lines()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    System.out.println("Uppercase lines: " + upperLines);

    System.out.println();
  }

  static void demonstrateFileMethods() throws Exception {
    System.out.println("--- Files.readString() / writeString() ---");

    // Create a temporary file
    Path tempFile = Files.createTempFile("java11-demo", ".txt");

    // writeString() - write string directly to file
    String content = "Hello, Java 11!\nThis is a test file.";
    Files.writeString(tempFile, content);
    System.out.println("Wrote to file: " + tempFile);

    // readString() - read entire file as string
    String readContent = Files.readString(tempFile);
    System.out.println("Read from file:\n" + readContent);

    // Clean up
    Files.delete(tempFile);
    System.out.println("Deleted temp file");

    System.out.println();
  }

  static void demonstrateVarInLambda() {
    System.out.println("--- var in Lambda Parameters ---");

    // Without var (Java 8+)
    List<String> names = List.of("Alice", "Bob", "Charlie");
    names.stream()
        .map(name -> name.toUpperCase())
        .forEach(System.out::println);

    // With var (Java 11) - allows annotations on parameters
    System.out.println("\nWith var (allows annotations):");
    names.stream()
        .map((var name) -> name.toUpperCase())
        .forEach(name -> System.out.println("  " + name));

    // Useful when you want to add annotations
    // .map((@NonNull var name) -> name.toUpperCase())

    System.out.println();
  }

  static void demonstratePredicateNot() {
    System.out.println("--- Predicate.not() ---");

    List<String> strings = List.of("", "Hello", "  ", "World", "");

    // Before Java 11
    List<String> nonBlankOld = strings.stream()
        .filter(s -> !s.isBlank())
        .collect(Collectors.toList());
    System.out.println("Before Java 11 (filter with !): " + nonBlankOld);

    // Java 11 - Predicate.not()
    List<String> nonBlankNew = strings.stream()
        .filter(Predicate.not(String::isBlank))
        .collect(Collectors.toList());
    System.out.println("Java 11 (Predicate.not()): " + nonBlankNew);

    System.out.println();
  }

  static void demonstrateHttpClient() throws Exception {
    System.out.println("--- HTTP Client API (Standard) ---");

    // Create HTTP Client
    HttpClient client = HttpClient.newHttpClient();

    // Create request
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://httpbin.org/get"))
        .header("Accept", "application/json")
        .GET()
        .build();

    System.out.println("Sending GET request to: " + request.uri());

    try {
      // Send request and get response
      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      System.out.println("Status code: " + response.statusCode());
      System.out.println("Response body (first 200 chars):");
      String body = response.body();
      System.out.println(body.substring(0, Math.min(200, body.length())) + "...");
    } catch (Exception e) {
      System.out.println("Request failed (network issue): " + e.getMessage());
      System.out.println("HTTP Client demo would send async/sync requests");
    }

    // Async example (just showing the API)
    System.out.println("\nAsync request example (API only):");
    System.out.println("  client.sendAsync(request, BodyHandlers.ofString())");
    System.out.println("      .thenApply(HttpResponse::body)");
    System.out.println("      .thenAccept(System.out::println);");

    System.out.println();
  }
}
