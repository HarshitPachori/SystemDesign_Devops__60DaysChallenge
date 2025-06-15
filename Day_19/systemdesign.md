---
title: "Day 19: Design Patterns"
description: "A summary of my 19th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Design Patterns
  - Decorator Pattern
  - Observer Pattern
  - Day 19
  - Challenge
---

### Table of contents :
- [Decorator Pattern](#decorator-pattern)
- [Java Code Example for Decorator Pattern](#java-code-example-for-decorator-pattern)
- [Observer Pattern](#observer-pattern)
- [Java Code Example for Observer Pattern](#java-code-example-for-observer-pattern)


### Decorator Pattern


- **What it is**:
The Decorator Pattern is a structural design pattern that allows you to add new functionalities to an object dynamically without altering its structure. It provides a flexible alternative to subclassing for extending functionality.

- **When to use it**:
- To add responsibilities to individual objects dynamically and transparently, without affecting other objects.
- For responsibilities that can be withdrawn.
- When extension by subclassing is impractical. Sometimes a large number of independent extensions are possible and would produce an explosion of subclasses to support every combination.


- **Key Components**:

   - **Component (Interface or Abstract Class)**: Defines the interface for objects that can have responsibilities added to them dynamically.


   - **Concrete Prototype**: The concrete implementation of the Component interface, the object to which additional responsibilities will be attached.


   - **Decorator (Abstract Class)**: An abstract class that implements the Component interface and maintains a reference to a Component object. It typically delegates the work to the wrapped Component.
   - **Concrete Decorator(s)**: Concrete implementations of the Decorator. They add specific functionalities before or after calling the wrapped Component's method.


### Java Code Example for Decorator Pattern 
- Decorator Pattern in Java
- To view the code click [Decorator Factory Java Code](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_19/DecoratorPattern.java)

### Observer Pattern

- **What it is**:
The Observer Pattern is a behavioral design pattern that defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.


- **Key Components**:

   - **Subject (Observable)**: The object that holds the state and notifies its observers when the state changes. It typically has methods to attach, detach, and notify observers.


   - **Observer**: The interface or abstract class that defines the update method that subjects call to notify their observers.


   - **Concrete Subject**: 
  A concrete implementation of the Subject. It maintains the state and a list of observers. When its state changes, it iterates through its observers and calls their update method.

   - **Concrete Observer(s)**: Concrete implementations of the Observer interface. They register with a Concrete Subject and implement the update method to react to state changes.


- **When to Use It**:

   - When a change to one object requires changing others, and you don't know how many objects need to be changed.
   - When an object should be able to notify other objects without making assumptions about who these objects are.

### Java Code Example for Observer Pattern
- Observer Pattern in Java
- To view the code click [Observer pattern in java](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_19/ObserverPattern.java)

