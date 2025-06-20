---
title: "Day 21: Design Patterns"
description: "A summary of my 21th day's learning in the 60-day challenge, covering fundamentals of Design Patterns"
keywords:
  - Design Patterns
  - Day 21
  - Challenge
---

### Table of contents :
- [Layered (N-Tier) Pattern](#layered-n-tier-pattern)
- [Monolithic Pattern](#monolithic-pattern)
- [Peer-to-Peer (P2P) Pattern](#peer-to-peer-p2p-pattern)
- [Broker Pattern](#broker-pattern)
- [Microservices Pattern](#microservices-pattern)
- [Event-Driven Pattern](#event-driven-pattern)


### Layered (N-Tier) Pattern
- **Concept**: Divides an application into distinct, vertically stacked layers, each with specific responsibilities. Communication flows in a generally unidirectional manner (e.g., Presentation -> Business Logic -> Data Access).
- **Pros**:
   - **Separation of Concerns**: Each layer focuses on a specific responsibility, making the system easier to understand and manage.
   - **Maintainability & Testability**: Changes in one layer ideally don't affect others, simplifying maintenance and allowing independent testing of layers.
- **Cons**:
   - **Performance Overhead**: Requests often travel through multiple layers, which can add latency.
   - **Rigidity**: Adding a new feature might require changes across multiple layers, potentially slowing down development.

### Monolithic Pattern
- **Concept**: A single, large, indivisible unit of code where all components (UI, business logic, data access, etc.) are tightly coupled and deployed together as one application.
- **Pros**:
   - **Simplicity (Initial)**: Easy to develop, deploy, and test initially for smaller applications.
   - **Shared Resources**: Components can easily share data and functionality within the same process, avoiding network overhead.
- **Cons**:
   - **Scalability Challenges**: Entire application must be scaled, even if only a small part is under heavy load.
   - **Slow Development & Deployment**: Large codebase makes development slow, and deployments become infrequent due to high risk.

### Peer-to-Peer (P2P) Pattern

- **Concept**: A decentralized architecture where each node (peer) in the network can act as both a client and a server, directly sharing resources and services with other nodes. There's no central authority.
- **Pros**:
   - **High Scalability & Resilience**: Can scale massively as more peers join; highly fault-tolerant as there's no single point of failure.
   - **Decentralization**: No reliance on central servers, reducing infrastructure costs and censorship risks.
- **Cons**:
   - **Complex Management**: Difficult to manage, update, and ensure security/consistency across many decentralized nodes.
   - **Discovery Issues**: Finding resources or other peers can be challenging without a centralized directory.

### Broker Pattern

- **Concept**: Introduces a "broker" component that mediates communication between clients and servers. Clients send requests to the broker, which forwards them to the appropriate server and routes responses back. The broker decouples clients from servers.
- **Pros**:
   - **Loose Coupling**: Clients and servers are unaware of each other's direct identities, only communicating via the broker.
   - **Location Transparency**: Clients don't need to know where servers are located.
- **Cons**:
   - **Single Point of Failure (Broker)**: The broker can become a bottleneck or a single point of failure if not highly available and scalable.
   - **Increased Complexity**: Introduces an additional layer of abstraction, adding complexity to the system.

### Microservices Pattern

- **Concept**: Structures an application as a collection of loosely coupled, independently deployable services, each representing a distinct business capability. Services communicate over a network, often via APIs or message queues, and typically have their own dedicated data stores.
- **Pros**:
   - **Independent Scalability**: Each service can be scaled independently based on its specific needs, optimizing resource usage.
   - **Technological Heterogeneity**: Teams can choose the best technology stack for each service.
   - **Improved Agility**: Small, independent teams can develop, deploy, and iterate faster.
- **Cons**:
   - **Increased Operational Complexity**: Distributed systems are harder to manage, monitor, and debug (e.g., distributed transactions, logging, tracing).
   - **Data Consistency Challenges**: Maintaining consistency across multiple independent data stores requires careful design (e.g., Saga Pattern).

### Event-Driven Pattern

- **Concept**: Components communicate indirectly by publishing and consuming events. Instead of direct requests, components react to events that signify a change in state or occurrence of an action. Often utilizes a message broker or event bus.
- **Pros**:
   - **High Decoupling**: Producers and consumers of events are completely unaware of each other, leading to highly flexible and extensible systems.
   - **Scalability & Responsiveness**: Asynchronous nature allows components to process events independently, improving throughput and responsiveness.
- **Cons**:
   - **Debugging Challenges**: Tracing the flow of events across multiple asynchronous components can be difficult.
   - **Eventual Consistency**: Data consistency might not be immediate, requiring careful handling in application logic.
