---
title: "Day 18: Design Patterns"
description: "A summary of my 18th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Design Patterns
  - Prototype Pattern
  - Builder Pattern
  - Day 18
  - Challenge
---

### Table of contents :
- [Prototype Pattern](#prototype-pattern)
- [Java Code Example for Prototype Pattern  ](#java-code-example-for-adapter-pattern)
- [Adapter Pattern](#adapter-param)
- [Java Code Example for Adapter Pattern](#java-code-example-for-adapter-pattern)


### Prototype Pattern

- **What it is**:
The Prototype Pattern is a creational design pattern that allows you to create new objects by copying an existing object, known as the "prototype." It avoids the need for creating objects from scratch, especially when object creation is expensive or complex.

- **Purpose**:
To specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype. It's used when a system should be independent of how its products are created, composed, and represented, and when the classes to instantiate are specified at runtime.

- **Key Components**:

   - **Prototype (Interface/Abstract Class)**: Declares an interface for cloning itself. This usually involves a clone() method.

   - **Concrete Prototype**: Implements the clone() operation.

   - **Client**: Creates a new object by asking a prototype to clone itself.


- **When to Use It**:

   - When the system needs to be independent of how its products are created, composed, and represented.

   - When the classes to instantiate are specified at runtime (e.g., by dynamic loading).
   - When avoiding a hierarchy of factories parallel to the class hierarchy of products.

   - When instances of a class can have one of only a few different combinations of state, and creating each state by explicit construction is more expensive.



- **Pros of Abstract Factory**:

  - **Efficient Object Creation**: Can be more efficient than creating objects using constructors, especially for complex objects with many fields or resource-intensive initialization.

  - **Reduced Subclassing**: Avoids the need to create a new Concrete Creator (like in Factory Method) for every new Concrete Product. Instead, you just register a new prototype.
  - **Runtime Instantiation**: Allows new instances to be created from specific prototypes selected at runtime.


- **Cons of Abstract Factory**:

  - **Complex Cloning for Deep Copies**: Implementing the clone() method can be challenging, especially when dealing with complex objects that contain references to other objects (deep copy vs. shallow copy dilemma).

  - **Overhead if Not Needed**: For simple objects, the overhead of implementing the Cloneable interface and managing prototypes might outweigh the benefits.


### Java Code Example for Prototype Pattern 
- Prototype Pattern in Java
- To view the code click [Prototype Factory Java Code](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_18/PrototypePattern.java)

### Adapter Pattern

- **What it is**:
The Adapter Pattern is a structural design pattern that allows objects with incompatible interfaces to collaborate. It acts as a bridge between two incompatible interfaces.



- **Purpose**:
To convert the interface of a class into another interface that clients expect. It lets classes work together that couldn't otherwise because of incompatible interfaces, without modifying their existing source code.

- **Key Components**:

   - **Target (Interface)**: The interface that the client expects to use.
       - **Example**: MediaPlayer (with a play() method for MP3s)

   - **Adaptee (Existing Class)**: The existing class with the incompatible interface that you want to use.

      - **Example**:A AdvancedMediaPlayer (with playVlc() or playMp4() methods)

   - **Adapter (Class)**: 
   A  class that implements the Target interface and wraps an instance of the Adaptee. It translates calls from the Target interface into calls on the Adaptee's interface.

      - **Example**: MediaAdapter (implements MediaPlayer and uses AdvancedMediaPlayer)
   - **Client**: The code that uses the Target interface.

- **When to Use It**:

   - When you want to use an existing class, and its interface does not match the one you need.

   - When you want to create a reusable class that cooperates with unrelated or unforeseen classes, which don't necessarily have compatible interfaces.

   - (Object Adapter) When you need to adapt an existing class and its subclasses to an interface.

   - (Class Adapter - less common in Java due to single inheritance) When you need to adapt an existing class and its subclasses to an interface.


- **Pros of Adapter**:

   - **Reusability**: Allows you to reuse existing classes whose interfaces don't match the current system's requirements.

   - **Flexibility**:Provides a flexible way to introduce new functionalities by adapting new classes.

   - **Client Decoupling**: Decouples the client from the specific implementation details of the Adaptee.

- **Cons of Adapter**:

   - **Increased Complexity**:  Introduces a new class (the adapter) which can add complexity to the codebase, especially if many adapters are needed.

   - **Performance Overhead (Minor)**: A slight performance overhead due to the additional method call through the adapter, though usually negligible.


### Java Code Example for Adapter Pattern
- Adapter Pattern in Java
- To view the code click [Adapter pattern in java](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_18/AdapterPattern.java)

