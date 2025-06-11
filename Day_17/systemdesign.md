---
title: "Day 17: Design Patterns"
description: "A summary of my 17th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Design Patterns
  - Abstract Factory Pattern
  - Builder Pattern
  - Day 17
  - Challenge
---

### Table of contents :
- [Abstract Factory Pattern](#abstract-factory-pattern)
- [Java Code Example for Abstract Factory ](#java-code-example-for-abstract-factory)
- [Builder Pattern](#builder-pattern)
- [Java Code Example for Builder Pattern](#java-code-example-for-builder-pattern)


### Abstract Factory Pattern
- **What it is**:
The Abstract Factory Pattern provides an interface for creating families of related or dependent objects without specifying their concrete classes. It's a "factory of factories."

- **Purpose**:
To allow a client to create objects that belong to a related set (a "family") without needing to know the specific concrete classes of those objects. This is particularly useful when your system needs to be independent of how its products are created, composed, and represented.

- **Key Components**:

   - **Abstract Factory**: An interface or abstract class that declares a set of factory methods for creating each abstract product.
      - **Example**: GUIFactory (declares createButton(), createCheckbox())
   - **Concrete Factory**: A concrete implementation of the Abstract Factory. Each concrete factory implements the factory methods to create specific concrete products that belong to a particular "family" or "theme."
      - **Example**: WindowsGUIFactory (creates WindowsButton, WindowsCheckbox), MacOSGUIFactory (creates MacOSButton, MacOSCheckbox)
   - **Abstract Product**: An interface or abstract class for a type of product (e.g., Button).
      - **Example**: Button, Checkbox
   - **Concrete Product**: A concrete implementation of an Abstract Product. These products are created by a specific Concrete Factory and belong to a particular family.
      - **Example**: WindowsButton, MacOSButton, WindowsCheckbox, MacOSCheckbox
   - **Client**: Code that uses the Abstract Factory and Abstract Product interfaces. It does not interact with Concrete Factories or Concrete Products directly.

- **When to Use It**:

   - When a system needs to be independent of how its products are created, composed, and represented.
   - When a system needs to configure with one of multiple families of products.
   - When a family of related product objects is designed to be used together, and you need to enforce this constraint.
   - To provide a class library of products, and you want to reveal only their interfaces, not their implementations.

- **Analogy**:
Think of a computer parts store. An "Abstract Factory" is like the concept of "ComputerPartsFactory." A "Concrete Factory" would be "GamingComputerPartsFactory" (produces high-end CPU, GPU, RAM) or "OfficeComputerPartsFactory" (produces standard CPU, integrated GPU, basic RAM). The client just asks for "a CPU" or "a GPU" from the chosen factory, without caring if it's the gaming or office version.

- **Pros of Abstract Factory**:

  - **Ensures Product Compatibility**: Guarantees that the client only uses products that belong to the same family (e.g., all Windows GUI components, not a mix of Windows and macOS).
  - **Decoupling**: Decouples the client code from concrete product classes and concrete factory classes, making it easier to swap out entire product families.

- **Cons of Abstract Factory**:

  - **Increased Complexity**: Introduces many new interfaces and classes, which can over-complicate a system if the product families are not large or if future extensibility is not a primary concern.
  - **Difficulty Adding New Products**: Adding a new type of product (e.g., a "Radio Button") to the product family would require modifying the Abstract Factory interface and all Concrete Factories to include the new creation method.

### Java Code Example for Abstract Factory 
- Abstract Factory Pattern in Java
- To view the code click [Abstract Factory Java Code](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_17/AbstractFactoryPattern.java)

### Builder Pattern
- **What it is**:
The Builder Pattern separates the construction of a complex object from its representation, allowing the same construction process to create different representations. It's particularly useful when an object has many optional parameters or requires a step-by-step creation process.

- **Purpose**:
To create complex objects step-by-step, providing a clear and readable way to build objects with many possible configurations, avoiding a "telescoping constructor" anti-pattern (many overloaded constructors).

- **Key Components**:

   - **Product**: The complex object being built (e.g., Computer). This object typically has many attributes, some mandatory, some optional.
   - **Builder**: An abstract interface or class that declares the step-by-step creation methods for building parts of the Product object. It usually provides a method to return the final constructed Product.
      - **Example**: ComputerBuilder (declares buildCPU(), buildRAM(), buildStorage(), buildGraphicsCard(), getResult())
   - **Concrete Builder**: Implements the Builder interface to construct and assemble parts of the product. Each concrete builder can create a specific variation of the product.
      - **Example**: GamingComputerBuilder, OfficeComputerBuilder
   - Director (Optional but Recommended): An optional class that constructs an object using the Builder interface. It defines the order of building steps but doesn't know the exact implementation. Useful for encapsulating common building configurations.
      - **Example**: ComputerAssembler (takes a ComputerBuilder and calls its methods in a specific sequence to build a "standard" gaming PC).
   - **Client**: Interacts with the Director (or directly with the Builder) to get a Product object.

- **When to Use It**:

   - When the process of constructing a complex object should be independent of the object's parts and how they are assembled.
   - When the object being constructed can have multiple representations (e.g., a "gaming" computer vs. an "office" computer, both built using similar parts but configured differently).
   - When a complex object has many parameters, some optional, and you want to avoid a large number of overloaded constructors.
   - To produce different flavors of a product by varying the configuration of its parts.

- **Analogy**:
Building a custom car. The "Product" is the car. The "Builder" is the set of instructions and tools to build parts (e.g., "install engine," "paint car," "add tires"). "Concrete Builders" are specialized mechanics (e.g., "SportsCarMechanic" vs. "FamilyCarMechanic") who follow the same steps but use different parts/techniques. A "Director" could be a "Salesperson" who knows specific car configurations (e.g., "Build me the 'Luxury SUV' model" which implies a specific sequence of engine, interior, and exterior choices).


- **Pros of Builder**:

   - **Improves Readability and Maintainability**: Makes object creation code much cleaner and more readable, especially for objects with many optional parameters. The named methods of the builder make the configuration clear.
   - **Encapsulates Construction Logic**: The complex construction logic is encapsulated within the builder, separating it from the product's class.
   - **Supports Immutable Objects**: Allows you to create immutable Product objects, as all parameters are set during the building process before the final object is constructed.
   - **Flexible Object Construction**: The same builder interface can be used to construct different representations of the product (e.g., a GamingComputerBuilder and an OfficeComputerBuilder could implement a common ComputerBuilder interface if the parts are abstract enough).

- **Cons of Builder**:

   - **Increased Complexity**: Introduces more classes (Builder, Concrete Builders, Director) compared to simple constructors, which can be overkill for objects with only a few parameters.
   - **Requires Forethought**: Designing the builder interface and its methods requires careful planning if the product's complexity is expected to grow.

### Java Code Example for Builder Pattern
- Builder Pattern in Java
- To view the code click [Builder pattern in java](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_17/BuilderPattern.java)

