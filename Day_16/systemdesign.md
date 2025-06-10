---
title: "Day 16: Design Patterns"
description: "A summary of my 16th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Singleton Pattern
  - Factory Pattern
  - Day 16
  - Challenge
---

### Table of contents :
- [Singleton Pattern](#singleton-pattern)
- [Factory Method Pattern (The "Factory Pattern")](#factory-method-pattern-the-factory-pattern)



### Singleton Pattern
- **What it is**:
The Singleton Pattern ensures that a class has only one instance and provides a global point of access to that instance. It's used when exactly one object is needed to coordinate actions across the system.

- **Purpose**:
To control object creation, restricting the number of instances of a class to exactly one. This single instance is then globally accessible.

- **Key Characteristics**:

   - **Single Instance**: Guarantees that only one instance of the class can ever be created.
   - **Self-Instantiation**: The class itself is responsible for creating its single instance.
   - **Global Access Point**: Provides a way to access that single instance from anywhere in the application.
   - **Lazy Initialization (often)**: The instance is typically created only when it's first needed, not at application startup.

- **When to Use It**:

   - When there must be exactly one instance of a class, and it must be accessible to clients from a well-known access point.
   - **Examples**: A database connection pool, a logging utility, a configuration manager, a thread pool, or a printer spooler.


- **Pros of Singleton**:

   - **Controlled Access to Sole Instance**: Provides a strict control over how and when the single instance is accessed.
   - **Saves Resources**: Prevents multiple instantiations of resource-intensive objects (like database connections).

- **Cons of Singleton**:

   - **Global State (Anti-pattern often)**: Can introduce global state into an application, making testing, debugging, and maintaining code difficult due to hidden dependencies.
   - **Violates Single Responsibility Principle**: The class takes on the responsibility of managing its own instance, which is a secondary concern to its primary business logic.
   - **Difficult to Test**: Hard to mock or replace the single instance in unit tests, leading to tightly coupled code.

### Factory Method Pattern (The "Factory Pattern")
- **What it is**:
The Factory Method Pattern defines an interface for creating an object, but lets subclasses decide which class to instantiate. It defers instantiation to subclasses. This is a common form of the broader "Factory" concept.

- **Purpose**:
To encapsulate object creation logic. Instead of clients creating objects directly using new (or ClassName()), they ask a "factory" to create the object for them. This decouples the client code from the concrete classes it instantiates.

- **Key Characteristics**:

   - **Creator (Factory)**: A class that declares the factory method, which returns an object of a Product type.
   - **Product**: The interface for the objects the factory method creates.
   - **ConcreteCreator**: A class that implements the factory method to return a ConcreteProduct.
   - **ConcreteProduct**: A specific implementation of the Product interface.

- **When to Use It**:

   - When a class cannot anticipate the class of objects it must create.
   - When a class wants its subclasses to specify the objects it creates.
   - When you want to decouple the client code from the concrete classes it uses.
   - When creating objects involves complex logic (e.g., configuring them based on a type or environment).

- **Pros of Factory Method**:

   - **Loose Coupling**: Decouples the client code from concrete product classes. The client only interacts with the factory and the product interface, not specific implementations.
   - **Extensibility**: New product types can be introduced without modifying existing client code or factory implementations. You just add new ConcreteProduct and ConcreteCreator classes.
   - **Centralized Creation Logic**: Creation logic is encapsulated in the factory, making it easier to manage and modify.

- **Cons of Factory Method**:

   - **Increased Complexity**: Introduces new classes (Creator and ConcreteCreator) which can make the code more complex, especially for very simple object creation.
   - **Parallel Class Hierarchy**: Often creates a parallel class hierarchy (one for products, one for creators), which can be cumbersome.
