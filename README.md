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
