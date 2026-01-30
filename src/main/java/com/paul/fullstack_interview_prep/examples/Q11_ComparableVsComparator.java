package com.paul.fullstack_interview_prep.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Q11: What is the difference between Comparable and Comparator?
 *
 * Comparable:
 * - Defines natural ordering
 * - Implemented by the class itself
 * - Single sorting sequence
 * - compareTo(Object o) method
 * - java.lang package
 *
 * Comparator:
 * - Defines custom ordering
 * - Implemented by separate class
 * - Multiple sorting sequences possible
 * - compare(Object o1, Object o2) method
 * - java.util package
 */
public class Q11_ComparableVsComparator {

  public static void main(String[] args) {
    System.out.println("=== Comparable vs Comparator Demo ===\n");

    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee(3, "Charlie", 50000));
    employees.add(new Employee(1, "Alice", 60000));
    employees.add(new Employee(2, "Bob", 55000));

    // COMPARABLE: Natural ordering (by id)
    System.out.println("--- Comparable (Natural Order by ID) ---");
    System.out.println("Before sorting: " + employees);
    Collections.sort(employees); // Uses compareTo
    System.out.println("After sorting: " + employees);

    // COMPARATOR: Custom ordering (by name)
    System.out.println("\n--- Comparator (Custom Order by Name) ---");
    Collections.sort(employees, new NameComparator());
    System.out.println("Sorted by name: " + employees);

    // COMPARATOR: Custom ordering (by salary)
    System.out.println("\n--- Comparator (Custom Order by Salary) ---");
    Collections.sort(employees, new SalaryComparator());
    System.out.println("Sorted by salary: " + employees);

    // Lambda comparators (Java 8+)
    System.out.println("\n--- Lambda Comparators ---");
    employees.sort((e1, e2) -> e2.salary - e1.salary); // Descending salary
    System.out.println("Sorted by salary (desc): " + employees);

    // Method reference comparators
    System.out.println("\n--- Method Reference Comparators ---");
    employees.sort(Comparator.comparing(Employee::getName));
    System.out.println("Sorted by name (method ref): " + employees);

    // Chained comparators
    System.out.println("\n--- Chained Comparators ---");
    employees.add(new Employee(4, "Alice", 45000)); // Another Alice
    employees.sort(Comparator
        .comparing(Employee::getName)
        .thenComparing(Employee::getSalary));
    System.out.println("Sorted by name, then salary: " + employees);
  }

  // Class implementing Comparable (natural ordering by id)
  static class Employee implements Comparable<Employee> {
    int id;
    String name;
    int salary;

    Employee(int id, String name, int salary) {
      this.id = id;
      this.name = name;
      this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
      return this.id - other.id; // Natural order by ID
    }

    public String getName() { return name; }
    public int getSalary() { return salary; }

    @Override
    public String toString() {
      return name + "(id=" + id + ", $" + salary + ")";
    }
  }

  // Comparator for sorting by name
  static class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
      return e1.name.compareTo(e2.name);
    }
  }

  // Comparator for sorting by salary
  static class SalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
      return e1.salary - e2.salary;
    }
  }
}
