package com.paul.fullstackinterviewprep.examples;

/**
 * Q12: What are Java access modifiers?
 *
 * public: Accessible from anywhere
 * protected: Accessible within package + subclasses
 * default (no modifier): Accessible within package only
 * private: Accessible within class only
 *
 * Visibility scope: private < default < protected < public
 */
public class Q12_AccessModifiers {

  public static void main(String[] args) {
    System.out.println("=== Java Access Modifiers Demo ===\n");

    AccessExample example = new AccessExample();

    // From same package, we can access:
    System.out.println("--- Accessing from same package ---");
    System.out.println("public field: " + example.publicField);
    System.out.println("protected field: " + example.protectedField);
    System.out.println("default field: " + example.defaultField);
    // System.out.println(example.privateField); // Cannot access - private

    example.publicMethod();
    example.protectedMethod();
    example.defaultMethod();
    // example.privateMethod(); // Cannot access - private

    // Demonstrate inheritance
    System.out.println("\n--- Accessing from subclass ---");
    ChildClass child = new ChildClass();
    child.accessParentMembers();

    // Summary table
    System.out.println("\n--- Access Modifier Summary ---");
    System.out.println("Modifier    | Class | Package | Subclass | World");
    System.out.println("------------|-------|---------|----------|------");
    System.out.println("public      |   ✓   |    ✓    |    ✓     |   ✓  ");
    System.out.println("protected   |   ✓   |    ✓    |    ✓     |   ✗  ");
    System.out.println("default     |   ✓   |    ✓    |    ✗     |   ✗  ");
    System.out.println("private     |   ✓   |    ✗    |    ✗     |   ✗  ");
  }

  static class AccessExample {
    public String publicField = "I'm public";
    protected String protectedField = "I'm protected";
    String defaultField = "I'm default (package-private)";
    private String privateField = "I'm private";

    public void publicMethod() {
      System.out.println("public method called");
      privateMethod(); // Can call private from within class
    }

    protected void protectedMethod() {
      System.out.println("protected method called");
    }

    void defaultMethod() {
      System.out.println("default method called");
    }

    private void privateMethod() {
      System.out.println("private method called (from within class)");
    }
  }

  static class ChildClass extends AccessExample {
    void accessParentMembers() {
      System.out.println("From child - public: " + publicField);
      System.out.println("From child - protected: " + protectedField);
      System.out.println("From child - default: " + defaultField); // Same package
      // System.out.println(privateField); // Cannot access - private

      publicMethod();
      protectedMethod();
      defaultMethod(); // Same package
      // privateMethod(); // Cannot access - private
    }
  }
}
