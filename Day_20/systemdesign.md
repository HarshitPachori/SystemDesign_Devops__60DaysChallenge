---
title: "Day 20: Design Patterns"
description: "A summary of my 20th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Design Patterns
  - Strategy Pattern
  - Observer Pattern
  - Day 20
  - Challenge
---

### Table of contents :
- [Strategy Pattern](#strategy-pattern)
- [Java Code Example for Strategy Pattern](#java-code-example-for-strategy-pattern)
- [High-Level Design Patterns: Client-Server Pattern](#high-level-design-patterns-client-server-pattern)


### Strategy Pattern


- **What it is**:
The Strategy Pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. It allows the algorithm to vary independently from clients that use it. In simpler terms, it lets you pick and switch between different ways of doing something at runtime.



- **Purpose**:
To provide a way to change the behavior of an object at runtime without changing its class. Instead of implementing multiple behaviors directly within a class, you define each behavior as a separate "strategy" object, and the main class holds a reference to one of these strategy objects.




- **Key Components**:

   - **Context**: The class that uses a Strategy object. It maintains a reference to a Strategy object and delegates the execution of the algorithm to it. It does not know the concrete implementation of the strategy.
        - **Example**: PaymentProcessor

   - **Strategy (Interface/Abstract Class)**: Declares an interface common to all supported algorithms. The context uses this interface to call the concrete strategy.
        - **Example**: PaymentStrategy (with a pay() method)



   - **Concrete Strategies**: Implementations of the Strategy interface, providing specific algorithms or behaviors.
         - **Example**: CreditCardPayment, PayPalPayment, BitcoinPayment
- **When to use it**:
   - When you have multiple related algorithms, and you want to choose between them at runtime.
   - When you want to avoid conditioning logic (multiple if-else or switch statements) in a class that implements multiple behaviors.
   - When you want to define a class that implements multiple behaviors, and those behaviors are distinct and interchangeable.
   - When an algorithm uses data that clients shouldn't know about.

- **Pros of Strategy**:

  - **Loose Coupling**: Decouples the context from the specific algorithms it uses. The context only knows about the Strategy interface.
  - **Flexibility & Extensibility**: New strategies can be added easily without modifying the context or existing strategies.
  - **Eliminates Conditional Logic**: Replaces conditional statements with polymorphism, making the code cleaner and more maintainable.
  - **Improved Testability**: Individual strategies can be tested independently.

- **Cons of Strategy**:

  - **Increased Number of Classes**: Introduces new classes for each strategy, which can increase complexity for very simple problems.
  - **Client Awareness**: The client often needs to know the difference between strategies to select the appropriate one.


### Java Code Example for Strategy Pattern 
- Strategy Pattern in Java
- To view the code click [Strategy Pattern Java Code](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_19/StrategyPattern.java)

### High-Level Design Patterns: Client-Server Pattern

- **What it is**:
The Client-Server Pattern is one of the most fundamental and ubiquitous architectural patterns in distributed computing. It describes how clients (requesters of services) interact with servers (providers of services) over a network.

- **How it Works**:

   - **Client initiates Request**: A client application sends a request to a server. This request typically asks for data, to perform an action, or to access a resource.
   - **Server processes Request**: The server listens for requests from clients. When it receives a request, it processes it, which might involve retrieving data from a database, performing calculations, or interacting with other services.
   - **Server sends Response**: After processing, the server sends a response back to the client. This response contains the requested data or confirmation that the action was performed.
   - **Client receives Response**: The client receives the response and presents the information to the user or continues its own processing.



- **Key Components**:

   - **Client**: 
      - Initiates requests.
      - Typically provides a user interface (e.g., web browser, mobile app, desktop application).
      - Focuses on presentation and user interaction.
      - Knows the server's address.



   - **Server**: 
      - Listens for and responds to client requests.
      - Provides services, resources, and data.
      - Typically handles business logic, data storage, and backend processing.
      - Does not initiate requests to clients (unless it's a push-notification mechanism).
   - **Network**: The communication medium (e.g., Internet, LAN) that facilitates data exchange between clients and servers.

- **Pros of Client-Server**:

   - **Centralized Control**: Servers can manage resources, security, and data consistency from a central point.
   - **Scalability**: Servers can be scaled independently of clients, and new clients can be added without impacting existing ones.
   - **Ease of Maintenance**: Server-side updates can be deployed without requiring client updates (unless the API changes).
   - **Data Security**: Data is stored centrally on servers, which can implement robust security measures.
   - **Resource Sharing**: Multiple clients can share common resources (e.g., databases, printers) managed by the server.

- **Cons of Client-Server**:

   - **Single Point of Failure (Historically)**: If the server goes down, clients cannot access services. Modern architectures mitigate this with redundancy and load balancing.
   - **Network Dependence**: Requires a stable network connection between client and server.
   - **Server Bottleneck**: A single server might become a bottleneck under heavy load. (Mitigated by load balancing, auto-scaling, and distributed server architectures).
   - **Development Complexity**: Requires separate development for client and server components.

