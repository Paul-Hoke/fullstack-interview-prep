# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=FullstackInterviewPrepApplicationTests

# Run a single test method
./mvnw test -Dtest=FullstackInterviewPrepApplicationTests#contextLoads
```

## Technology Stack

- **Java 25** with Spring Boot 4.0.2
- **Maven** for build management (use `./mvnw` wrapper)
- **H2** in-memory database with H2 Console enabled
- **Lombok** for boilerplate reduction (configured as annotation processor)
- **Spring Web MVC** for REST endpoints

## Project Structure

Standard Maven layout with base package `com.paul.fullstackinterviewprep`:
- `src/main/java` - Application source code
  - `examples/` - 30 Java interview question examples (Q01-Q30), each with runnable main method
- `src/main/resources` - Configuration (`application.properties`) and static assets
- `src/test/java` - Test classes using JUnit 5 and Spring Boot Test

## Running Example Classes

```bash
# Run a specific interview question example
./mvnw exec:java -Dexec.mainClass="com.paul.fullstackinterviewprep.examples.Q01_JdkJreJvm"

# Run a HackerRank or javaversions example the same way
./mvnw exec:java -Dexec.mainClass="com.paul.fullstackinterviewprep.hackerrank.medium.JavaRegex"
./mvnw exec:java -Dexec.mainClass="com.paul.fullstackinterviewprep.javaversions.Java24Features"
```

## Architecture

The project has two modes of operation:

1. **Standalone examples** — Every class under `examples/`, `hackerrank/medium/`, and `javaversions/` has a `public static void main` and runs independently via `exec:java`. These have no Spring dependencies.

2. **Spring Boot application** — `./mvnw spring-boot:run` starts a web server. The `dependencyinjection/` package demonstrates Spring IoC; the H2 console is accessible at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:interviewdb`, user `sa`, no password). Database schema and seed data are in `src/main/resources/db/schema.sql` and `data.sql`.

### Package layout

| Package | Purpose |
|---------|---------|
| `examples/` | Q01–Q30 standalone interview question demos |
| `hackerrank/medium/` | HackerRank medium-difficulty coding problems |
| `javaversions/` | Feature demos per Java release (8, 9, 11–16, 24) |
| `dependencyinjection/` | Spring DI with `@Configuration`, `@Bean`, and `ApplicationListener` |

### Example class conventions

- Use static nested classes to keep a topic self-contained in one file.
- Each file's `main()` prints output that demonstrates the concept.
- No Spring context is needed unless the class lives under `dependencyinjection/`.
