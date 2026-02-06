# Fullstack Interview Prep

A Spring Boot project containing the top 30 Java interview questions with runnable code examples.

## Getting Started

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Run a specific example
./mvnw exec:java -Dexec.mainClass="com.paul.fullstackinterviewprep.examples.Q01_JdkJreJvm"
```

## Top 30 Java Interview Questions

Each question has a corresponding example class with a `main` method that can be run independently.

### Core Java Fundamentals

| # | Question | Example Class |
|---|----------|---------------|
| 1 | [What is the difference between JDK, JRE, and JVM?](#q1-jdk-jre-jvm) | [Q01_JdkJreJvm.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q01_JdkJreJvm.java) |
| 2 | [What is the difference between == and .equals()?](#q2-equals-vs-) | [Q02_EqualsVsDoubleEquals.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q02_EqualsVsDoubleEquals.java) |
| 3 | [What is the difference between String, StringBuilder, and StringBuffer?](#q3-string-stringbuilder-stringbuffer) | [Q03_StringBuilderBuffer.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q03_StringBuilderBuffer.java) |
| 4 | [What is the difference between abstract class and interface?](#q4-abstract-class-vs-interface) | [Q04_AbstractVsInterface.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q04_AbstractVsInterface.java) |
| 5 | [What are the four pillars of OOP?](#q5-oop-principles) | [Q05_OopPrinciples.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q05_OopPrinciples.java) |
| 6 | [What is the difference between method overloading and overriding?](#q6-overloading-vs-overriding) | [Q06_OverloadingVsOverriding.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q06_OverloadingVsOverriding.java) |
| 7 | [What is the difference between final, finally, and finalize?](#q7-final-finally-finalize) | [Q07_FinalFinallyFinalize.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q07_FinalFinallyFinalize.java) |

### Exceptions and Error Handling

| # | Question | Example Class |
|---|----------|---------------|
| 8 | [What is the difference between checked and unchecked exceptions?](#q8-checked-vs-unchecked-exceptions) | [Q08_CheckedVsUnchecked.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q08_CheckedVsUnchecked.java) |

### Collections Framework

| # | Question | Example Class |
|---|----------|---------------|
| 9 | [What is the difference between ArrayList and LinkedList?](#q9-arraylist-vs-linkedlist) | [Q09_ArrayListVsLinkedList.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q09_ArrayListVsLinkedList.java) |
| 10 | [What is the difference between HashMap and Hashtable?](#q10-hashmap-vs-hashtable) | [Q10_HashMapVsHashtable.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q10_HashMapVsHashtable.java) |
| 11 | [What is the difference between Comparable and Comparator?](#q11-comparable-vs-comparator) | [Q11_ComparableVsComparator.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q11_ComparableVsComparator.java) |
| 22 | [What is the difference between fail-fast and fail-safe iterators?](#q22-fail-fast-vs-fail-safe) | [Q22_FailFastVsFailSafe.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q22_FailFastVsFailSafe.java) |

### Keywords and Modifiers

| # | Question | Example Class |
|---|----------|---------------|
| 12 | [What are Java access modifiers?](#q12-access-modifiers) | [Q12_AccessModifiers.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q12_AccessModifiers.java) |
| 13 | [What is the static keyword?](#q13-static-keyword) | [Q13_StaticKeyword.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q13_StaticKeyword.java) |
| 18 | [What is the volatile keyword?](#q18-volatile-keyword) | [Q18_VolatileKeyword.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q18_VolatileKeyword.java) |

### Memory Management

| # | Question | Example Class |
|---|----------|---------------|
| 14 | [What is the difference between stack and heap memory?](#q14-stack-vs-heap) | [Q14_StackVsHeap.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q14_StackVsHeap.java) |
| 15 | [What is Garbage Collection?](#q15-garbage-collection) | [Q15_GarbageCollection.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q15_GarbageCollection.java) |

### Multithreading and Concurrency

| # | Question | Example Class |
|---|----------|---------------|
| 16 | [What is multithreading and how do you create threads?](#q16-multithreading) | [Q16_Multithreading.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q16_Multithreading.java) |
| 17 | [What is synchronization?](#q17-synchronization) | [Q17_Synchronization.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q17_Synchronization.java) |

### Java 8+ Features

| # | Question | Example Class |
|---|----------|---------------|
| 19 | [What are Lambda Expressions?](#q19-lambda-expressions) | [Q19_LambdaExpressions.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q19_LambdaExpressions.java) |
| 20 | [What are Streams?](#q20-streams) | [Q20_Streams.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q20_Streams.java) |
| 21 | [What is the Optional class?](#q21-optional-class) | [Q21_OptionalClass.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q21_OptionalClass.java) |
| 30 | [What are Functional Interfaces?](#q30-functional-interfaces) | [Q30_FunctionalInterfaces.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q30_FunctionalInterfaces.java) |

### Object-Oriented Design

| # | Question | Example Class |
|---|----------|---------------|
| 23 | [What is the difference between shallow copy and deep copy?](#q23-shallow-vs-deep-copy) | [Q23_ShallowVsDeepCopy.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q23_ShallowVsDeepCopy.java) |
| 24 | [What is immutability?](#q24-immutability) | [Q24_Immutability.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q24_Immutability.java) |
| 25 | [What are Design Patterns?](#q25-design-patterns) | [Q25_DesignPatterns.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q25_DesignPatterns.java) |
| 26 | [What is Dependency Injection?](#q26-dependency-injection) | [Q26_DependencyInjection.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q26_DependencyInjection.java), [Spring DI Examples](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/) |
| 27 | [What is the difference between Composition and Inheritance?](#q27-composition-vs-inheritance) | [Q27_CompositionVsInheritance.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q27_CompositionVsInheritance.java) |

### Advanced Topics

| # | Question | Example Class |
|---|----------|---------------|
| 28 | [What are Generics?](#q28-generics) | [Q28_Generics.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q28_Generics.java) |
| 29 | [What is Reflection?](#q29-reflection) | [Q29_Reflection.java](src/main/java/com/paul/fullstackinterviewprep/examples/Q29_Reflection.java) |

---

## Question Details

### Q1: JDK, JRE, JVM

- **JVM (Java Virtual Machine)**: Executes Java bytecode. Platform-specific.
- **JRE (Java Runtime Environment)**: JVM + libraries needed to run Java programs.
- **JDK (Java Development Kit)**: JRE + development tools (compiler, debugger, etc.)

**Hierarchy**: JDK > JRE > JVM

### Q2: equals() vs ==

- `==` compares reference (memory address) for objects, value for primitives
- `.equals()` compares content/value (when properly overridden)

### Q3: String, StringBuilder, StringBuffer

| Type | Mutability | Thread-Safe | Performance |
|------|------------|-------------|-------------|
| String | Immutable | Yes | Slow for concatenation |
| StringBuilder | Mutable | No | Fast (single-threaded) |
| StringBuffer | Mutable | Yes | Slower (synchronized) |

### Q4: Abstract Class vs Interface

| Feature | Abstract Class | Interface |
|---------|----------------|-----------|
| Methods | Abstract + concrete | Abstract + default (Java 8+) |
| Variables | Instance variables | Constants only |
| Constructors | Yes | No |
| Inheritance | Single | Multiple |

### Q5: OOP Principles

1. **Encapsulation**: Bundling data and methods, hiding internal state
2. **Inheritance**: Acquiring properties from parent class
3. **Polymorphism**: Same interface, different implementations
4. **Abstraction**: Hiding complexity, showing only essentials

### Q6: Overloading vs Overriding

| Feature | Overloading | Overriding |
|---------|-------------|------------|
| Definition | Same name, different params | Same signature in child |
| Resolution | Compile-time | Runtime |
| Scope | Same class | Parent-child classes |

### Q7: final, finally, finalize

- **final**: Constants, prevent inheritance/overriding
- **finally**: Block that always executes after try-catch
- **finalize**: GC callback before object destruction (deprecated)

### Q8: Checked vs Unchecked Exceptions

| Type | Checked at | Must Handle | Examples |
|------|------------|-------------|----------|
| Checked | Compile-time | Yes | IOException, SQLException |
| Unchecked | Runtime | No | NullPointerException, ArrayIndexOutOfBoundsException |

### Q9: ArrayList vs LinkedList

| Feature | ArrayList | LinkedList |
|---------|-----------|------------|
| Backing | Dynamic array | Doubly-linked list |
| Random access | O(1) | O(n) |
| Insert/Delete | O(n) | O(1) at ends |
| Use case | Read-heavy | Write-heavy |

### Q10: HashMap vs Hashtable

| Feature | HashMap | Hashtable |
|---------|---------|-----------|
| Thread-safe | No | Yes |
| Null keys | One allowed | Not allowed |
| Performance | Faster | Slower |
| Modern alternative | ConcurrentHashMap | ConcurrentHashMap |

### Q11: Comparable vs Comparator

- **Comparable**: Natural ordering, implemented by the class itself
- **Comparator**: Custom ordering, separate class, multiple orderings possible

### Q12: Access Modifiers

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| public | ✓ | ✓ | ✓ | ✓ |
| protected | ✓ | ✓ | ✓ | ✗ |
| default | ✓ | ✓ | ✗ | ✗ |
| private | ✓ | ✗ | ✗ | ✗ |

### Q13: Static Keyword

- **static variable**: Shared across all instances
- **static method**: Called without creating object
- **static block**: Executed once when class is loaded
- **static nested class**: Doesn't need outer instance

### Q14: Stack vs Heap

| Feature | Stack | Heap |
|---------|-------|------|
| Stores | Local variables, method calls | Objects, instance variables |
| Speed | Faster | Slower |
| Size | Limited | Larger |
| Thread | Per-thread | Shared |

### Q15: Garbage Collection

Automatic memory management that removes unreachable objects. Objects become eligible when no references point to them.

### Q16: Multithreading

Ways to create threads:
1. Extend `Thread` class
2. Implement `Runnable` interface
3. Implement `Callable` interface
4. Use `ExecutorService`

### Q17: Synchronization

Mechanism to control access to shared resources:
- `synchronized` method
- `synchronized` block
- `ReentrantLock`

### Q18: Volatile Keyword

Ensures visibility of changes across threads. Reads/writes go directly to main memory.

### Q19: Lambda Expressions

Anonymous functions: `(parameters) -> expression`

### Q20: Streams

Sequence of elements supporting aggregate operations:
- **Intermediate**: filter, map, sorted (lazy)
- **Terminal**: collect, forEach, reduce (eager)

### Q21: Optional Class

Container that may or may not contain a value. Avoids NullPointerException.

### Q22: Fail-Fast vs Fail-Safe

| Type | Exception | Works on | Examples |
|------|-----------|----------|----------|
| Fail-fast | ConcurrentModificationException | Original collection | ArrayList, HashMap |
| Fail-safe | None | Copy/snapshot | CopyOnWriteArrayList |

### Q23: Shallow vs Deep Copy

- **Shallow**: New object, same nested references
- **Deep**: New object, copied nested objects

### Q24: Immutability

Object whose state cannot change after creation. Rules:
1. Class is final
2. Fields are private final
3. No setters
4. Defensive copying

### Q25: Design Patterns

- **Creational**: Singleton, Factory, Builder
- **Structural**: Adapter, Decorator, Facade
- **Behavioral**: Observer, Strategy, Command

### Q26: Dependency Injection

Dependencies provided to a class rather than created by it. Types:
1. Constructor Injection (preferred)
2. Setter Injection
3. Field Injection

#### Spring DI Examples

The [`dependencyinjection`](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/) package demonstrates real Spring Framework DI patterns:

| Class | Description |
|-------|-------------|
| [DIComponent](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/DIComponent.java) | Bean defined using `@Component` stereotype annotation |
| [DIConfiguration](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/DIConfiguration.java) | Java-based configuration using `@Configuration` + `@Bean` |
| [BaseBean](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/BaseBean.java) | Abstract base class for bean inheritance |
| [MyBean1](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/MyBean1.java) | Extends BaseBean, overrides `getName()` |
| [MyBean2](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/MyBean2.java) | Extends BaseBean, uses inherited `getName()` |
| [StartupApplicationListener](src/main/java/com/paul/fullstackinterviewprep/dependencyinjection/StartupApplicationListener.java) | Demonstrates constructor injection with `@RequiredArgsConstructor` |

**Key Concepts Demonstrated:**
- `@Component` vs `@Configuration` + `@Bean` for bean registration
- Constructor injection using Lombok's `@RequiredArgsConstructor`
- `@EventListener` with `ApplicationReadyEvent` for startup logic
- Bean inheritance and method overriding

Run the application to see the DI in action:
```bash
./mvnw spring-boot:run
```

### Q27: Composition vs Inheritance

- **Inheritance (IS-A)**: "Dog IS-A Animal" - tight coupling
- **Composition (HAS-A)**: "Car HAS-A Engine" - loose coupling

Principle: *Favor composition over inheritance*

### Q28: Generics

Type parameters enabling type-safe code:
- `<T>` - type parameter
- `<T extends Number>` - bounded type
- `<?>`, `<? extends T>`, `<? super T>` - wildcards

### Q29: Reflection

Inspect and modify runtime behavior of classes at runtime. Used by frameworks.

### Q30: Functional Interfaces

Interface with exactly one abstract method. Used with lambdas.

Built-in examples:
- `Predicate<T>`: T → boolean
- `Function<T,R>`: T → R
- `Consumer<T>`: T → void
- `Supplier<T>`: () → T

---

## Java Version Features (8+)

Each Java version has a corresponding runnable example class in the [`javaversions`](src/main/java/com/paul/fullstackinterviewprep/javaversions/) package.

```bash
# Run a specific version example
./mvnw exec:java -Dexec.mainClass="com.paul.fullstackinterviewprep.javaversions.Java8Features"
```

### Version Overview

| Version | Release | Type | Example Class | Key Features |
|---------|---------|------|---------------|--------------|
| [Java 8](#java-8-lts) | Mar 2014 | LTS | [Java8Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java8Features.java) | Lambdas, Streams, Optional, Date/Time API |
| [Java 9](#java-9) | Sep 2017 | | [Java9Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java9Features.java) | Modules, `List.of()`, `takeWhile`/`dropWhile` |
| [Java 10](#java-10) | Mar 2018 | | [Java10Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java10Features.java) | `var` keyword, `List.copyOf()` |
| [Java 11](#java-11-lts) | Sep 2018 | LTS | [Java11Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java11Features.java) | String methods, HTTP Client, `Files.readString()` |
| [Java 12](#java-12) | Mar 2019 | | [Java12Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java12Features.java) | Switch expressions, `indent()`, `transform()` |
| [Java 13](#java-13) | Sep 2019 | | [Java13Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java13Features.java) | Text blocks (preview), `yield` |
| [Java 14](#java-14) | Mar 2020 | | [Java14Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java14Features.java) | Records (preview), Pattern matching instanceof |
| [Java 15](#java-15) | Sep 2020 | | [Java15Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java15Features.java) | Text blocks (standard), Sealed classes (preview) |
| [Java 16](#java-16) | Mar 2021 | | [Java16Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java16Features.java) | Records (standard), `Stream.toList()` |
| [Java 17](#java-17-lts) | Sep 2021 | LTS | [Java17Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java17Features.java) | Sealed classes (standard), Pattern switch |
| [Java 21](#java-21-lts) | Sep 2023 | LTS | [Java21Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java21Features.java) | Virtual threads, Sequenced collections |
| [Java 22](#java-22) | Mar 2024 | | [Java22Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java22Features.java) | Unnamed variables (`_`), Gatherers (preview) |
| [Java 23](#java-23) | Sep 2024 | | [Java23Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java23Features.java) | Primitive patterns, Markdown docs |
| [Java 24](#java-24) | Mar 2025 | | [Java24Features.java](src/main/java/com/paul/fullstackinterviewprep/javaversions/Java24Features.java) | Stream Gatherers (standard), Class-File API |

---

### Java 8 (LTS)

**March 2014** - The most significant Java release, introducing functional programming.

| Feature | Description |
|---------|-------------|
| Lambda Expressions | Anonymous functions: `(x) -> x * 2` |
| Stream API | Functional operations on collections |
| Optional | Container to avoid null checks |
| Default Methods | Interface methods with implementations |
| Method References | Shorthand: `String::toUpperCase` |
| New Date/Time API | `java.time` package |
| Functional Interfaces | Single abstract method interfaces |

### Java 9

**September 2017**

| Feature | Description |
|---------|-------------|
| Module System (Jigsaw) | Modularize the JDK and applications |
| JShell | Interactive REPL for Java |
| Private Interface Methods | Private methods in interfaces |
| Collection Factory Methods | `List.of()`, `Set.of()`, `Map.of()` |
| Stream Improvements | `takeWhile`, `dropWhile`, `ofNullable` |

### Java 10

**March 2018**

| Feature | Description |
|---------|-------------|
| Local Variable Type Inference | `var` keyword for local variables |
| Unmodifiable Collections | `copyOf()` methods |
| Optional.orElseThrow() | No-arg version throws NoSuchElementException |

### Java 11 (LTS)

**September 2018**

| Feature | Description |
|---------|-------------|
| String Methods | `isBlank()`, `lines()`, `strip()`, `repeat()` |
| Files Methods | `readString()`, `writeString()` |
| HTTP Client API | Standardized from incubator |
| var in Lambda | `var` in lambda parameters |

### Java 12

**March 2019**

| Feature | Description |
|---------|-------------|
| Switch Expressions (Preview) | Switch as expression with arrow syntax |
| String Methods | `indent()`, `transform()` |
| Compact Number Formatting | Format numbers like "1K", "2M" |

### Java 13

**September 2019**

| Feature | Description |
|---------|-------------|
| Text Blocks (Preview) | Multi-line string literals `"""` |
| Switch yield | `yield` keyword for returning values |

### Java 14

**March 2020**

| Feature | Description |
|---------|-------------|
| Switch Expressions (Standard) | Finalized from preview |
| Records (Preview) | Immutable data classes |
| Pattern Matching instanceof (Preview) | Type patterns |
| Helpful NullPointerExceptions | Detailed NPE messages |

### Java 15

**September 2020**

| Feature | Description |
|---------|-------------|
| Text Blocks (Standard) | Finalized from preview |
| Sealed Classes (Preview) | Restrict class hierarchy |

### Java 16

**March 2021**

| Feature | Description |
|---------|-------------|
| Records (Standard) | Finalized from preview |
| Pattern Matching instanceof (Standard) | Finalized |
| Stream.toList() | Convenient terminal operation |

### Java 17 (LTS)

**September 2021**

| Feature | Description |
|---------|-------------|
| Sealed Classes (Standard) | Finalized from preview |
| Pattern Matching for switch (Preview) | Type patterns in switch |
| Enhanced Random Generators | New RandomGenerator interface |
| HexFormat | Hex string formatting utility |

### Java 21 (LTS)

**September 2023**

| Feature | Description |
|---------|-------------|
| Virtual Threads (Standard) | Lightweight threads (millions possible) |
| Sequenced Collections | `addFirst`, `addLast`, `getFirst`, `getLast`, `reversed` |
| Record Patterns (Standard) | Deconstruct records in patterns |
| Pattern Matching for switch (Standard) | Finalized with guards |

### Java 22

**March 2024**

| Feature | Description |
|---------|-------------|
| Unnamed Variables | `_` for unused variables |
| Statements Before super() (Preview) | Validate before calling super |
| Stream Gatherers (Preview) | Custom intermediate operations |

### Java 23

**September 2024**

| Feature | Description |
|---------|-------------|
| Primitive Types in Patterns (Preview) | Match on primitives in switch |
| Module Import Declarations (Preview) | `import module java.base;` |
| Markdown Documentation Comments | Use `///` with Markdown syntax |

### Java 24

**March 2025**

| Feature | Description |
|---------|-------------|
| Stream Gatherers (Standard) | `windowFixed`, `windowSliding`, `fold`, `scan` |
| Class-File API (Standard) | Parse and generate class files |

### Java 25 (Upcoming)

**September 2025** - Expected LTS

| Feature | Description |
|---------|-------------|
| Value Classes | Inline classes for performance |
| Project Valhalla | Enhanced primitive types |

---

### LTS Release Schedule

| Version | Release | Support Until |
|---------|---------|---------------|
| Java 8 | Mar 2014 | Dec 2030+ |
| Java 11 | Sep 2018 | Sep 2026+ |
| Java 17 | Sep 2021 | Sep 2029+ |
| Java 21 | Sep 2023 | Sep 2031+ |
| Java 25 | Sep 2025 | TBD |

**Note**: Non-LTS versions receive updates for only 6 months.

---

## Top 30 SQL Interview Questions

The application initializes an H2 in-memory database on startup with sample data. Access the H2 console at:
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:interviewdb`
- Username: `sa`
- Password: (leave empty)

Each question has a corresponding SQL file with runnable examples against the sample database.

### Database Schema

The sample database includes:
- **employees** - Employee records with hierarchy (manager_id self-reference)
- **departments** - Department information with budgets
- **customers** - Customer data for order examples
- **products** - Product catalog with categories
- **categories** - Hierarchical product categories
- **suppliers** - Product suppliers
- **orders** - Customer orders
- **order_items** - Order line items (many-to-many)
- **inventory** - Stock change tracking
- **audit_log** - For trigger demonstrations

See [schema.sql](src/main/resources/db/schema.sql) and [data.sql](src/main/resources/db/data.sql) for details.

### SQL Basics

| # | Question | SQL File |
|---|----------|----------|
| 1 | [What is SQL and basic SELECT statement?](#sql-q1-select-basics) | [Q01_SelectBasics.sql](src/main/resources/db/sql/Q01_SelectBasics.sql) |
| 2 | [How do you filter data using WHERE clause?](#sql-q2-where-clause) | [Q02_WhereClause.sql](src/main/resources/db/sql/Q02_WhereClause.sql) |
| 3 | [How do you sort results using ORDER BY?](#sql-q3-order-by) | [Q03_OrderBy.sql](src/main/resources/db/sql/Q03_OrderBy.sql) |

### JOINs and Set Operations

| # | Question | SQL File |
|---|----------|----------|
| 4 | [What are the different types of JOINs?](#sql-q4-joins) | [Q04_Joins.sql](src/main/resources/db/sql/Q04_Joins.sql) |
| 8 | [What are UNION, INTERSECT, and EXCEPT?](#sql-q8-set-operations) | [Q08_UnionIntersectExcept.sql](src/main/resources/db/sql/Q08_UnionIntersectExcept.sql) |
| 25 | [What is a self-join and when would you use it?](#sql-q25-self-join) | [Q25_SelfJoin.sql](src/main/resources/db/sql/Q25_SelfJoin.sql) |

### Aggregation and Grouping

| # | Question | SQL File |
|---|----------|----------|
| 5 | [What is GROUP BY and HAVING clause?](#sql-q5-group-by-having) | [Q05_GroupByHaving.sql](src/main/resources/db/sql/Q05_GroupByHaving.sql) |
| 6 | [What are aggregate functions in SQL?](#sql-q6-aggregate-functions) | [Q06_AggregateFunctions.sql](src/main/resources/db/sql/Q06_AggregateFunctions.sql) |
| 14 | [What are window functions?](#sql-q14-window-functions) | [Q14_WindowFunctions.sql](src/main/resources/db/sql/Q14_WindowFunctions.sql) |

### Subqueries and CTEs

| # | Question | SQL File |
|---|----------|----------|
| 7 | [What are subqueries and how do you use them?](#sql-q7-subqueries) | [Q07_Subqueries.sql](src/main/resources/db/sql/Q07_Subqueries.sql) |
| 21 | [What are Common Table Expressions (CTEs)?](#sql-q21-ctes) | [Q21_CTEs.sql](src/main/resources/db/sql/Q21_CTEs.sql) |
| 24 | [What is the difference between EXISTS and IN?](#sql-q24-exists-vs-in) | [Q24_ExistsVsIn.sql](src/main/resources/db/sql/Q24_ExistsVsIn.sql) |

### Functions

| # | Question | SQL File |
|---|----------|----------|
| 9 | [How do you handle NULL values in SQL?](#sql-q9-null-handling) | [Q09_NullHandling.sql](src/main/resources/db/sql/Q09_NullHandling.sql) |
| 10 | [How do you use CASE expressions?](#sql-q10-case-expressions) | [Q10_CaseExpressions.sql](src/main/resources/db/sql/Q10_CaseExpressions.sql) |
| 11 | [What are common string functions in SQL?](#sql-q11-string-functions) | [Q11_StringFunctions.sql](src/main/resources/db/sql/Q11_StringFunctions.sql) |
| 12 | [What are common date/time functions in SQL?](#sql-q12-date-functions) | [Q12_DateFunctions.sql](src/main/resources/db/sql/Q12_DateFunctions.sql) |
| 13 | [What are common numeric functions in SQL?](#sql-q13-numeric-functions) | [Q13_NumericFunctions.sql](src/main/resources/db/sql/Q13_NumericFunctions.sql) |

### Database Objects

| # | Question | SQL File |
|---|----------|----------|
| 15 | [What are views and why use them?](#sql-q15-views) | [Q15_Views.sql](src/main/resources/db/sql/Q15_Views.sql) |
| 16 | [What are indexes and how do they improve performance?](#sql-q16-indexes) | [Q16_Indexes.sql](src/main/resources/db/sql/Q16_Indexes.sql) |
| 17 | [What are constraints in SQL?](#sql-q17-constraints) | [Q17_Constraints.sql](src/main/resources/db/sql/Q17_Constraints.sql) |
| 19 | [What are stored procedures and functions?](#sql-q19-stored-procedures) | [Q19_StoredProcedures.sql](src/main/resources/db/sql/Q19_StoredProcedures.sql) |
| 20 | [What are triggers?](#sql-q20-triggers) | [Q20_Triggers.sql](src/main/resources/db/sql/Q20_Triggers.sql) |

### Data Manipulation

| # | Question | SQL File |
|---|----------|----------|
| 23 | [How do INSERT, UPDATE, and DELETE work?](#sql-q23-insert-update-delete) | [Q23_InsertUpdateDelete.sql](src/main/resources/db/sql/Q23_InsertUpdateDelete.sql) |

### Transactions and Concurrency

| # | Question | SQL File |
|---|----------|----------|
| 18 | [What are transactions and ACID properties?](#sql-q18-transactions) | [Q18_Transactions.sql](src/main/resources/db/sql/Q18_Transactions.sql) |
| 27 | [What are deadlocks and how do you prevent them?](#sql-q27-deadlocks) | [Q27_DeadlocksLocking.sql](src/main/resources/db/sql/Q27_DeadlocksLocking.sql) |

### Performance and Design

| # | Question | SQL File |
|---|----------|----------|
| 22 | [What is database normalization?](#sql-q22-normalization) | [Q22_Normalization.sql](src/main/resources/db/sql/Q22_Normalization.sql) |
| 26 | [How do you implement pagination in SQL?](#sql-q26-pagination) | [Q26_Pagination.sql](src/main/resources/db/sql/Q26_Pagination.sql) |
| 28 | [How do you optimize SQL queries?](#sql-q28-query-optimization) | [Q28_QueryOptimization.sql](src/main/resources/db/sql/Q28_QueryOptimization.sql) |

### Data Types and Security

| # | Question | SQL File |
|---|----------|----------|
| 29 | [What are the common SQL data types?](#sql-q29-data-types) | [Q29_DataTypes.sql](src/main/resources/db/sql/Q29_DataTypes.sql) |
| 30 | [What are prepared statements and why use them?](#sql-q30-prepared-statements) | [Q30_PreparedStatements.sql](src/main/resources/db/sql/Q30_PreparedStatements.sql) |

---

## SQL Question Details

### SQL Q1: SELECT Basics

The `SELECT` statement retrieves data from tables:
- `SELECT *` - all columns
- `SELECT col1, col2` - specific columns
- `SELECT DISTINCT` - remove duplicates
- Column aliases with `AS`
- `LIMIT` and `OFFSET` for pagination

### SQL Q2: WHERE Clause

Filter rows with conditions:
- Comparison: `=`, `!=`, `<`, `>`, `<=`, `>=`
- Logical: `AND`, `OR`, `NOT`
- Range: `BETWEEN`
- List: `IN`
- Pattern: `LIKE` with `%` and `_`
- Null check: `IS NULL`, `IS NOT NULL`

### SQL Q3: ORDER BY

Sort results:
- `ASC` (ascending, default)
- `DESC` (descending)
- Multiple columns
- `NULLS FIRST` / `NULLS LAST`

### SQL Q4: JOINs

| Type | Description |
|------|-------------|
| INNER JOIN | Only matching rows |
| LEFT JOIN | All left + matching right |
| RIGHT JOIN | Matching left + all right |
| FULL OUTER JOIN | All from both tables |
| CROSS JOIN | Cartesian product |
| Self JOIN | Table joined to itself |

### SQL Q5: GROUP BY and HAVING

- `GROUP BY` groups rows for aggregation
- `HAVING` filters groups (after grouping)
- `WHERE` filters rows (before grouping)

### SQL Q6: Aggregate Functions

| Function | Description |
|----------|-------------|
| COUNT() | Count rows |
| SUM() | Sum values |
| AVG() | Average value |
| MIN() | Minimum value |
| MAX() | Maximum value |

### SQL Q7: Subqueries

Queries nested inside other queries:
- Scalar subquery (single value)
- IN/NOT IN subqueries
- EXISTS/NOT EXISTS (correlated)
- Subquery in FROM (derived table)
- Subquery in SELECT

### SQL Q8: Set Operations

| Operation | Description |
|-----------|-------------|
| UNION | Combine, remove duplicates |
| UNION ALL | Combine, keep duplicates |
| INTERSECT | Only in both |
| EXCEPT | In first, not in second |

### SQL Q9: NULL Handling

- `IS NULL` / `IS NOT NULL` for checking
- `COALESCE(a, b, c)` - first non-NULL
- `NULLIF(a, b)` - NULL if equal
- NULL in arithmetic = NULL
- Aggregates ignore NULL

### SQL Q10: CASE Expressions

Conditional logic in SQL:
```sql
CASE
  WHEN condition THEN result
  ELSE default
END
```

### SQL Q11: String Functions

`CONCAT`, `LENGTH`, `UPPER`, `LOWER`, `TRIM`, `SUBSTRING`, `REPLACE`, `LPAD`, `RPAD`

### SQL Q12: Date Functions

`CURRENT_DATE`, `EXTRACT`, `DATEADD`, `DATEDIFF`, `YEAR`, `MONTH`, `DAY`

### SQL Q13: Numeric Functions

`ROUND`, `FLOOR`, `CEILING`, `ABS`, `MOD`, `POWER`, `SQRT`

### SQL Q14: Window Functions

Calculations across related rows without collapsing:
- `ROW_NUMBER()`, `RANK()`, `DENSE_RANK()`
- `LAG()`, `LEAD()`
- `SUM() OVER()`, `AVG() OVER()`
- `PARTITION BY` for grouping
- `ORDER BY` for ordering

### SQL Q15: Views

Virtual tables from saved queries:
- Simplify complex queries
- Provide abstraction
- Implement security
- Encapsulate business logic

### SQL Q16: Indexes

Data structures for faster queries:
- B-tree (default)
- Composite (multiple columns)
- Unique indexes
- Trade-off: faster reads, slower writes

### SQL Q17: Constraints

| Constraint | Purpose |
|------------|---------|
| PRIMARY KEY | Unique row identifier |
| FOREIGN KEY | Referential integrity |
| UNIQUE | No duplicate values |
| NOT NULL | Required value |
| CHECK | Custom validation |
| DEFAULT | Default value |

### SQL Q18: Transactions

ACID properties:
- **Atomicity**: All or nothing
- **Consistency**: Valid state to valid state
- **Isolation**: Concurrent independence
- **Durability**: Permanent once committed

Commands: `BEGIN`, `COMMIT`, `ROLLBACK`, `SAVEPOINT`

### SQL Q19: Stored Procedures

Precompiled SQL code stored in database:
- Encapsulate business logic
- Reduce network traffic
- Improved security
- Code reuse

### SQL Q20: Triggers

Automatic actions on data changes:
- BEFORE/AFTER triggers
- INSERT/UPDATE/DELETE events
- Use for auditing, validation, cascading

### SQL Q21: CTEs (Common Table Expressions)

Named temporary result sets:
```sql
WITH cte_name AS (
  SELECT ...
)
SELECT * FROM cte_name;
```
- Improve readability
- Enable recursion
- Can reference multiple times

### SQL Q22: Normalization

| Form | Rule |
|------|------|
| 1NF | Atomic values, unique rows |
| 2NF | No partial dependencies |
| 3NF | No transitive dependencies |

Reduces redundancy, improves integrity.

### SQL Q23: INSERT, UPDATE, DELETE

- `INSERT INTO ... VALUES` - add rows
- `UPDATE ... SET ... WHERE` - modify rows
- `DELETE FROM ... WHERE` - remove rows

Always use WHERE with UPDATE/DELETE!

### SQL Q24: EXISTS vs IN

| Aspect | IN | EXISTS |
|--------|-----|--------|
| NULL handling | Issues with NOT IN | Handles correctly |
| Execution | Materializes subquery | Stops at first match |
| Best for | Small result sets | Large tables, correlated |

### SQL Q25: Self-Join

Table joined to itself:
- Employee-manager hierarchies
- Category trees
- Finding related rows
- Comparing rows in same table

### SQL Q26: Pagination

| Method | Pros | Cons |
|--------|------|------|
| LIMIT/OFFSET | Simple | Slow for large offsets |
| Keyset | Efficient | Can't jump to page |
| ROW_NUMBER | Flexible | Still numbers all rows |

### SQL Q27: Deadlocks

Prevention strategies:
- Access resources in consistent order
- Keep transactions short
- Use appropriate isolation levels
- Set lock timeouts

### SQL Q28: Query Optimization

1. Use `EXPLAIN` to analyze
2. Add indexes on WHERE/JOIN/ORDER BY columns
3. Avoid `SELECT *`
4. Rewrite subqueries as JOINs
5. Use `LIMIT` to restrict results
6. Avoid functions on indexed columns

### SQL Q29: Data Types

| Category | Types |
|----------|-------|
| Numeric | INT, DECIMAL, FLOAT |
| String | CHAR, VARCHAR, TEXT |
| Date/Time | DATE, TIME, TIMESTAMP |
| Boolean | BOOLEAN |
| Binary | BLOB, VARBINARY |

### SQL Q30: Prepared Statements

Benefits:
- **Security**: Prevents SQL injection
- **Performance**: Query parsed once
- **Maintainability**: Cleaner code

Always use for user input!
