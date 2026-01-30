package com.paul.fullstackinterviewprep.examples;

/**
 * Q26: What is Dependency Injection?
 *
 * Dependency Injection (DI): Design pattern where dependencies are provided
 * to a class rather than created by the class itself.
 *
 * Benefits:
 * - Loose coupling
 * - Easier testing (mock dependencies)
 * - Better maintainability
 * - Follows Inversion of Control (IoC) principle
 *
 * Types:
 * 1. Constructor Injection (preferred)
 * 2. Setter Injection
 * 3. Field Injection (not recommended)
 */
public class Q26_DependencyInjection {

  public static void main(String[] args) {
    System.out.println("=== Dependency Injection Demo ===\n");

    // Without DI (tightly coupled)
    System.out.println("--- Without Dependency Injection ---");
    demonstrateWithoutDI();

    // With DI (loosely coupled)
    System.out.println("\n--- With Dependency Injection ---");
    demonstrateWithDI();

    // Different injection types
    System.out.println("\n--- Types of Injection ---");
    demonstrateInjectionTypes();

    // Testing benefits
    System.out.println("\n--- Testing Benefits ---");
    demonstrateTesting();
  }

  static void demonstrateWithoutDI() {
    // Problem: UserService creates its own dependencies
    TightlyCoupledUserService service = new TightlyCoupledUserService();
    service.saveUser("Alice");

    System.out.println("Problems:");
    System.out.println("- Cannot change database implementation");
    System.out.println("- Cannot test without real database");
    System.out.println("- Hard to maintain");
  }

  static void demonstrateWithDI() {
    // Solution: Dependencies are injected from outside
    Database prodDb = new MySQLDatabase();
    EmailService emailService = new SmtpEmailService();

    UserService service = new UserService(prodDb, emailService);
    service.saveUser("Bob");

    System.out.println("\nBenefits:");
    System.out.println("- Can swap implementations easily");
    System.out.println("- Can inject mocks for testing");
    System.out.println("- Loose coupling");
  }

  static void demonstrateInjectionTypes() {
    // 1. Constructor Injection (Recommended)
    System.out.println("1. Constructor Injection (Recommended):");
    Database db = new MySQLDatabase();
    ConstructorInjection ci = new ConstructorInjection(db);
    ci.doWork();

    // 2. Setter Injection
    System.out.println("\n2. Setter Injection:");
    SetterInjection si = new SetterInjection();
    si.setDatabase(db);
    si.doWork();

    // 3. Field Injection (via reflection - not shown, used by frameworks)
    System.out.println("\n3. Field Injection (Framework-only, not recommended):");
    System.out.println("   Uses @Autowired or @Inject annotations");
    System.out.println("   Makes testing harder, hides dependencies");
  }

  static void demonstrateTesting() {
    // Using a mock/fake database for testing
    Database mockDb = new MockDatabase();
    EmailService mockEmail = new MockEmailService();

    UserService service = new UserService(mockDb, mockEmail);
    service.saveUser("TestUser");

    System.out.println("\nWith DI, we can inject mock implementations:");
    System.out.println("- No real database needed");
    System.out.println("- No real email sent");
    System.out.println("- Fast, isolated tests");
  }

  // ========== TIGHTLY COUPLED (BAD) ==========
  static class TightlyCoupledUserService {
    // Creates its own dependency - BAD!
    private final MySQLDatabase database = new MySQLDatabase();

    void saveUser(String name) {
      database.save("users", name);
    }
  }

  // ========== LOOSELY COUPLED (GOOD) ==========
  interface Database {
    void save(String table, String data);
  }

  interface EmailService {
    void send(String to, String message);
  }

  static class MySQLDatabase implements Database {
    public void save(String table, String data) {
      System.out.println("   MySQL: Saving '" + data + "' to " + table);
    }
  }

  static class SmtpEmailService implements EmailService {
    public void send(String to, String message) {
      System.out.println("   SMTP: Sending '" + message + "' to " + to);
    }
  }

  // Mock implementations for testing
  static class MockDatabase implements Database {
    public void save(String table, String data) {
      System.out.println("   MockDB: Would save '" + data + "' to " + table);
    }
  }

  static class MockEmailService implements EmailService {
    public void send(String to, String message) {
      System.out.println("   MockEmail: Would send '" + message + "' to " + to);
    }
  }

  // UserService with dependency injection
  static class UserService {
    private final Database database;
    private final EmailService emailService;

    // Constructor Injection
    UserService(Database database, EmailService emailService) {
      this.database = database;
      this.emailService = emailService;
    }

    void saveUser(String name) {
      database.save("users", name);
      emailService.send(name + "@example.com", "Welcome!");
    }
  }

  // ========== INJECTION TYPES ==========

  // 1. Constructor Injection
  static class ConstructorInjection {
    private final Database database;

    ConstructorInjection(Database database) {
      this.database = database;
    }

    void doWork() {
      System.out.println("   Constructor injection - immutable, required dependency");
    }
  }

  // 2. Setter Injection
  static class SetterInjection {
    private Database database;

    void setDatabase(Database database) {
      this.database = database;
    }

    void doWork() {
      System.out.println("   Setter injection - optional dependency, can be changed");
    }
  }
}
