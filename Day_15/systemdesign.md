---
title: "Day 15: Design Patterns"
description: "A summary of my 15th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Design Patterns
  - Day 15
  - Challenge
---

### Table of contents :
- [What are Design Patterns ?](#what-are-design-patterns-)
- [Design Patterns in LLD (Low-Level Design) and HLD (High-Level Design)](#design-patterns-in-lld-low-level-design-and-hld-high-level-design)
- [Types of Design Patterns (General Classification)](#types-of-design-patterns-general-classification)
- [Design Patterns in Low-Level Design (LLD - GoF Patterns)](#design-patterns-in-low-level-design-lld---gof-patterns)
- [Design Patterns in High-Level Design (HLD - Architectural Patterns)](#design-patterns-in-high-level-design-hld---architectural-patterns)


### What are Design Patterns ?
- A design pattern is a general, reusable solution to a commonly occurring problem within a given context in software design. It's not a finished design that can be directly transformed into code, but rather a description or template for how to solve a problem that can be used in many different situations.

- They provide a common vocabulary for developers to discuss solutions, leading to more maintainable, scalable, and understandable codebases. They are tried-and-true solutions that have evolved over time.

- **Key Aspects**:

  - **Problem**: Describes when to apply the pattern.
  - **Solution**: Describes the elements, their relationships, and collaborations.
  - **Consequences**: The results and trade-offs of applying the pattern.
  
- **Benefits of Using Design Patterns**:

   - **Common Vocabulary**: Facilitates communication among developers.
   - **Proven Solutions**: Utilizes well-tested and effective solutions, reducing trial-and-error.
   - **Improved Code Quality**: Leads to more maintainable, flexible, and extensible code.
   - **Accelerated Development**: Provides ready-made templates, speeding up the design phase.
   - **Better Understanding**: Helps new team members quickly grasp the system's architecture and design choices.

### Design Patterns in LLD (Low-Level Design) and HLD (High-Level Design)
The term "design pattern" can refer to different levels of abstraction in software design:

- **High-Level Design (HLD) / Architectural Design**: Focuses on the overall system structure, major components, their interactions, and external interfaces. It's about how the system is partitioned and how these partitions communicate. The patterns here are often called Architectural Patterns.
- **Low-Level Design (LLD) / Object-Oriented Design (OOD)**: Focuses on the internal structure of individual modules, classes, and their relationships. It details the implementation logic within components. The patterns here are typically the Gang of Four (GoF) patterns.

### Types of Design Patterns (General Classification)
The most famous classification comes from the book "Design Patterns: Elements of Reusable Object-Oriented Software" by the "Gang of Four" (GoF). These primarily apply to LLD.

- **Creational Patterns**: Deal with object creation mechanisms, trying to create objects in a manner suitable for the situation while increasing flexibility and reuse.

   - **Examples**: Factory Method, Abstract Factory, Singleton, Builder, Prototype.
- **Structural Patterns**: Deal with the composition of classes and objects, forming larger structures. They focus on how classes and objects are composed to form larger structures.

   - **Examples**: Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy.
- **Behavioral Patterns**: Deal with algorithms and the assignment of responsibilities between objects. They focus on the interaction and communication between objects.

   - **Examples**: Chain of Responsibility, Command, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor.

### Design Patterns in Low-Level Design (LLD - GoF Patterns)
In LLD, these patterns help you define the relationships and interactions between classes and objects within a specific module or component.

**Examples**:

- **Creational**:

   - **Singleton**: Ensures that a class has only one instance and provides a global point of access to that instance.
       - **Use Case**: Managing a single database connection pool or a configuration manager.
   - **Factory Method**: Defines an interface for creating an object, but lets subclasses alter the type of objects that will be created.
       - **Use Case**: Creating different types of documents (e.g., PDF, HTML) based on user input without exposing the concrete class creation logic.
- **Structural**:

   - **Adapter**: Allows objects with incompatible interfaces to collaborate.
       - **Use Case**: Integrating a new logging library into an existing system that expects a different logging interface.
   - **Decorator**: Attaches additional responsibilities to an object dynamically.
       - **Use Case**: Adding logging, encryption, or compression functionality to a data stream without modifying the core stream class.
- **Behavioral**:

   - **Observer**: Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
        - **Use Case**: UI elements reacting to data changes (e.g., a chart updating when underlying stock prices change).
   - **Strategy**: Defines a family of algorithms, encapsulates each one, and makes them interchangeable. The strategy lets the algorithm vary independently from clients that use it.
        - **Use Case**: Different payment methods (credit card, PayPal) for an e-commerce application, where the payment processing logic can be swapped.

### Design Patterns in High-Level Design (HLD - Architectural Patterns)
In HLD, these patterns dictate the fundamental structural organization of a software system. They provide a blueprint for the entire application or a significant part of it.

**Examples**:

- Client-Server:

   - **Concept**: Separates the client (user interface/application) from the server (data storage, business logic). Clients request resources or services from the server.
         - Use Case: Web applications (browser is client, web server is server), mobile apps.
- **Layered (N-Tier)**:

   - **Concept**: Divides the application into distinct, vertically stacked layers, each with a specific responsibility. Communication typically flows downwards (e.g., Presentation -> Business Logic -> Data Access).
         - **Use Case**: Traditional enterprise applications (e.g., Presentation Layer, Business Layer, Data Access Layer).
         - **Example Layers**: Presentation, Application, Business Logic, Data Access.
- **Microservices**:

   - **Concept**: Structures an application as a collection of loosely coupled, independently deployable services, each representing a distinct business capability and often having its own database.
         - **Use Case**: Large, complex applications requiring high scalability, independent development teams, and polyglot persistence/programming.
- **Event-Driven**:

   - **Concept**: Components communicate indirectly by producing and consuming events. A central event bus or message broker often facilitates this.
         - **Use Case**: Real-time data processing, IoT applications, systems requiring high scalability and asynchronous communication (e.g., order processing where payment and shipping are triggered by events).
- **Monolithic**:

   - **Concept**: A single, large, indivisible unit of code that contains all components of an application (UI, business logic, data access).
        - **Use Case**: Smaller applications, startups, or when rapid initial development is prioritized.
- **Peer-to-Peer (P2P)**:

   - **Concept**: Each node in the network can act as both a client and a server, directly sharing resources and services with other nodes.
        - **Use Case**: File sharing networks (e.g., BitTorrent), some blockchain applications.
- **Broker Pattern**:

   - **Concept**: A message broker mediates communication between clients and servers. Clients send requests to the broker, which forwards them to the appropriate server and sends responses back.
        - **Use Case**: Distributed systems where components need to communicate asynchronously and be decoupled (e.g., using RabbitMQ, Kafka).



