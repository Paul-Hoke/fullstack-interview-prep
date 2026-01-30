package com.paul.fullstackinterviewprep.examples;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Q29: What is Reflection in Java?
 *
 * Reflection: Ability to inspect and modify runtime behavior of classes,
 * interfaces, fields, and methods at runtime.
 *
 * Use cases:
 * - Frameworks (Spring, Hibernate)
 * - Testing frameworks (JUnit)
 * - IDEs and debuggers
 * - Serialization
 *
 * Drawbacks:
 * - Performance overhead
 * - Security restrictions
 * - Breaks encapsulation
 * - No compile-time type checking
 */
public class Q29_Reflection {

  public static void main(String[] args) throws Exception {
    System.out.println("=== Reflection Demo ===\n");

    // Getting Class object
    System.out.println("--- Getting Class Object ---");
    demonstrateClassObject();

    // Inspecting class
    System.out.println("\n--- Inspecting Class ---");
    demonstrateInspection();

    // Accessing fields
    System.out.println("\n--- Accessing Fields ---");
    demonstrateFieldAccess();

    // Invoking methods
    System.out.println("\n--- Invoking Methods ---");
    demonstrateMethodInvocation();

    // Creating instances
    System.out.println("\n--- Creating Instances ---");
    demonstrateInstantiation();
  }

  static void demonstrateClassObject() throws Exception {
    // Three ways to get Class object

    // 1. Using .class
    Class<Person> class1 = Person.class;
    System.out.println("1. Person.class: " + class1.getName());

    // 2. Using getClass() on instance
    Person person = new Person("Alice", 25);
    Class<?> class2 = person.getClass();
    System.out.println("2. person.getClass(): " + class2.getName());

    // 3. Using Class.forName()
    Class<?> class3 = Class.forName("com.paul.fullstackinterviewprep.examples.Q29_Reflection$Person");
    System.out.println("3. Class.forName(): " + class3.getName());

    System.out.println("All same class? " + (class1 == class2 && class2 == class3));
  }

  static void demonstrateInspection() {
    Class<Person> clazz = Person.class;

    // Class info
    System.out.println("Class name: " + clazz.getSimpleName());
    System.out.println("Package: " + clazz.getPackageName());
    System.out.println("Superclass: " + clazz.getSuperclass().getSimpleName());
    System.out.println("Is interface? " + clazz.isInterface());

    // Modifiers
    int modifiers = clazz.getModifiers();
    System.out.println("Is public? " + Modifier.isPublic(modifiers));
    System.out.println("Is static? " + Modifier.isStatic(modifiers));

    // Fields
    System.out.println("\nDeclared fields:");
    for (Field field : clazz.getDeclaredFields()) {
      System.out.println("  " + field.getType().getSimpleName() + " " + field.getName());
    }

    // Methods
    System.out.println("\nDeclared methods:");
    for (Method method : clazz.getDeclaredMethods()) {
      System.out.println("  " + method.getName() + "()");
    }

    // Constructors
    System.out.println("\nConstructors:");
    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
      System.out.println("  " + constructor);
    }
  }

  static void demonstrateFieldAccess() throws Exception {
    Person person = new Person("Bob", 30);
    Class<?> clazz = person.getClass();

    // Access private field
    Field nameField = clazz.getDeclaredField("name");
    nameField.setAccessible(true); // Bypass private access

    System.out.println("Original name: " + nameField.get(person));

    // Modify private field
    nameField.set(person, "Charlie");
    System.out.println("Modified name: " + nameField.get(person));
    System.out.println("Person object: " + person.getName());

    // Access static field
    Field counterField = clazz.getDeclaredField("counter");
    counterField.setAccessible(true);
    System.out.println("Static counter: " + counterField.get(null));
  }

  static void demonstrateMethodInvocation() throws Exception {
    Person person = new Person("Dave", 28);
    Class<?> clazz = person.getClass();

    // Invoke public method
    Method getNameMethod = clazz.getMethod("getName");
    String name = (String) getNameMethod.invoke(person);
    System.out.println("getName() returned: " + name);

    // Invoke method with parameters
    Method setNameMethod = clazz.getMethod("setName", String.class);
    setNameMethod.invoke(person, "Eve");
    System.out.println("After setName('Eve'): " + person.getName());

    // Invoke private method
    Method secretMethod = clazz.getDeclaredMethod("secretMethod");
    secretMethod.setAccessible(true);
    String result = (String) secretMethod.invoke(person);
    System.out.println("Private secretMethod() returned: " + result);

    // Invoke static method
    Method staticMethod = clazz.getMethod("getCounter");
    int counter = (int) staticMethod.invoke(null); // null for static
    System.out.println("Static getCounter(): " + counter);
  }

  static void demonstrateInstantiation() throws Exception {
    Class<Person> clazz = Person.class;

    // Default constructor (if exists)
    // Person p1 = clazz.newInstance(); // Deprecated

    // Using Constructor
    Constructor<Person> constructor = clazz.getConstructor(String.class, int.class);
    Person p2 = constructor.newInstance("Frank", 35);
    System.out.println("Created via reflection: " + p2.getName() + ", " + p2.getAge());

    // Private constructor
    Constructor<Person> privateConstructor = clazz.getDeclaredConstructor();
    privateConstructor.setAccessible(true);
    Person p3 = privateConstructor.newInstance();
    System.out.println("Created via private constructor: " + p3.getName());
  }

  // Sample class for reflection
  static class Person {
    private static int counter = 0;
    private String name;
    private int age;

    // Private constructor
    private Person() {
      this.name = "Unknown";
      this.age = 0;
      counter++;
    }

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
      counter++;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }

    public static int getCounter() { return counter; }

    private String secretMethod() {
      return "This is secret!";
    }
  }
}
