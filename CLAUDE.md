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

Standard Maven layout with base package `com.paul.fullstack_interview_prep`:
- `src/main/java` - Application source code
  - `examples/` - 30 Java interview question examples (Q01-Q30), each with runnable main method
- `src/main/resources` - Configuration (`application.properties`) and static assets
- `src/test/java` - Test classes using JUnit 5 and Spring Boot Test

## Running Example Classes

```bash
# Run a specific interview question example
./mvnw exec:java -Dexec.mainClass="com.paul.fullstack_interview_prep.examples.Q01_JdkJreJvm"
```
