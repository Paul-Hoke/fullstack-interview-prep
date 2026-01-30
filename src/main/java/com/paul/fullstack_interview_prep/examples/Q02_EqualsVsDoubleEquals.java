package com.paul.fullstack_interview_prep.examples;

/**
 * Q2: What is the difference between == and .equals()?
 *
 * == compares reference (memory address) for objects, value for primitives
 * .equals() compares content/value (when properly overridden)
 */
public class Q02_EqualsVsDoubleEquals {

  public static void main(String[] args) {
    System.out.println("=== == vs .equals() Demo ===\n");

    // Primitive comparison
    int a = 5;
    int b = 5;
    System.out.println("Primitives: a == b: " + (a == b)); // true

    // String pool example
    String s1 = "hello";
    String s2 = "hello";
    String s3 = new String("hello");

    System.out.println("\n--- String Comparison ---");
    System.out.println("s1 = \"hello\", s2 = \"hello\", s3 = new String(\"hello\")");
    System.out.println("s1 == s2: " + (s1 == s2));           // true (same pool reference)
    System.out.println("s1 == s3: " + (s1 == s3));           // false (different objects)
    System.out.println("s1.equals(s3): " + s1.equals(s3));   // true (same content)

    // Custom object example
    System.out.println("\n--- Custom Object Comparison ---");
    Person p1 = new Person("John", 25);
    Person p2 = new Person("John", 25);
    System.out.println("p1 == p2: " + (p1 == p2));           // false (different objects)
    System.out.println("p1.equals(p2): " + p1.equals(p2));   // true (equals overridden)
  }

  static class Person {
    String name;
    int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Person person = (Person) obj;
      return age == person.age && name.equals(person.name);
    }
  }
}
