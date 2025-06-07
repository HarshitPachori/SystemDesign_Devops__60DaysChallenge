---
title: "Day 13: Distributed Transactions - 2PC v/s Saga Pattern"
description: "A summary of my 13th day's learning in the 60-day challenge, covering fundamentals of Distributed Transactions - 2PC v/s Saga Pattern."
keywords:
  - Distributed Transactions
  - Two-Phase Commit (2PC)
  - Saga Pattern
  - Day 13
  - Challenge
---

### Table of contents :
- [What are Distributed Transactions ?](#what-are-distributed-transactions-)
- [How 2PC Works (The Two Phases) ?](#how-2pc-works-the-two-phases-)
- [Challenges and Limitations of 2PC](#challenges-and-limitations-of-2pc)
- [What is Saga Pattern (Conceptual) ?](#what-is-saga-pattern-conceptual-)
- [How the Saga Pattern Works (Conceptual) ?](#how-the-saga-pattern-works-conceptual-)
- [Two Main Saga Implementations](#two-main-saga-implementations)
- [Benefits of Saga Pattern](#benefits-of-saga-pattern)
- [Challenges of Saga Pattern](#challenges-of-saga-pattern)

### What are Distributed Transactions ?
- A distributed transaction is a set of operations that update data across two or more independent data stores (e.g., separate databases, or different microservices each with their own database) and must either all succeed (commit) or all fail (rollback) together. The goal is to maintain ACID properties (Atomicity, Consistency, Isolation, Durability) even when data is geographically dispersed or managed by different systems.

    - **Atomicity**: All operations in the transaction complete successfully, or none of them do. If any part fails, the entire transaction is undone.
    - **Consistency**: The transaction brings the data from one valid state to another valid state.
    - **Isolation**: Concurrent transactions do not interfere with each other; their results are the same as if they executed sequentially.
    - **Durability**: Once a transaction is committed, its changes are permanent and survive system failures.
- In a monolithic application, maintaining ACID is relatively straightforward within a single database. However, in a distributed system (like microservices where each service might have its own database), ensuring atomicity and consistency across service boundaries becomes highly challenging.


### What is Two-Phase Commit (2PC) ?
The Two-Phase Commit (2PC) protocol is a classic distributed transaction protocol designed to ensure atomicity across multiple participants (databases or resource managers). It involves a coordinator (often a transaction manager) and multiple participants.


### How 2PC Works (The Two Phases) ?

- **Phase 1: Prepare (Vote Phase)**

    - Coordinator sends "Prepare" request: The coordinator sends a "prepare" message to all participating resource managers. This message asks each participant if they are ready to commit the transaction.

    - **Participants prepare**:
         - Each participant performs the necessary work for the transaction (e.g., deducting money, updating inventory).
         - They acquire locks on the resources they intend to modify to ensure isolation.
         - They write all changes to a durable log (undo and redo logs) so that they can either commit or roll back if a failure occurs.
         - They then respond to the coordinator with either a "Yes, I'm ready to commit" or "No, I cannot commit" vote. A "Yes" vote is a promise that the participant will commit if instructed, even if it crashes and recovers.

- **Phase 2: Commit / Rollback (Decision Phase)**

     - **Coordinator makes decision**:
          - If ALL participants respond with "Yes," the coordinator decides to commit the transaction.
          - If ANY participant responds with "No" or a timeout occurs, the coordinator decides to rollback (abort) the transaction.
     - **Coordinator sends "Commit" or "Rollback" instruction**:
          - If the decision is to commit, the coordinator sends a "commit" message to all participants.
          - If the decision is to rollback, the coordinator sends a "rollback" message to all participants.
     - **Participants execute decision**:
          - Participants execute the commit or rollback operation and release their locks.
          - They then send an acknowledgment back to the coordinator.

### Challenges and Limitations of 2PC

- **Blocking (Single Point of Failure)**: If the coordinator fails after participants have prepared but before the commit/rollback decision is finalized, participants remain blocked, holding locks on resources indefinitely. This can lead to resource unavailability.
- **Performance Overhead**: Requires multiple network round trips between the coordinator and all participants, leading to increased latency. This makes it unsuitable for high-throughput systems.
- **Scalability**: The global coordination and locking can limit overall system scalability.
- **Complexity**: Implementing a robust 2PC protocol with proper failure handling and recovery is very complex.
- **Not Cloud-Native**: Many modern cloud-native databases and microservices frameworks do not natively support distributed 2PC.

Due to these limitations, 2PC is often avoided in modern, highly distributed microservices architectures in favor of alternative patterns.

### What is Saga Pattern (Conceptual) ?
The Saga Pattern is a way to manage long-lived distributed transactions in microservices architectures where strict ACID guarantees of a single database are not feasible or desirable. Instead of a single, atomic global transaction, a saga is a sequence of local transactions, where each local transaction is committed by a single service.

- **Core Idea**: If a step in the saga fails, compensating transactions are executed to undo the changes made by the preceding successful local transactions. This achieves eventual consistency rather than immediate strong consistency.
- **Local Transaction**: An atomic operation performed by a single service on its own database, committing its changes independently.
- **Compensating Transaction**: An operation that semantically undoes a preceding local transaction. It doesn't physically roll back the local transaction but performs an action that negates its effect (e.g., refund a payment, increase inventory).

### How the Saga Pattern Works (Conceptual) ?

Imagine an e-commerce order process:

- **Order Service**: Creates an order (local transaction 1) and publishes an "Order Created" event.
- **Payment Service**: Subscribes to "Order Created." Processes payment (local transaction 2).
    - **Success**: Publishes "Payment Processed" event.
    - **Failure**: Publishes "Payment Failed" event.
           - **Compensating action**: If payment fails, the Payment Service might inform the Order Service, which then updates the order status to "Cancelled."
- **Inventory Service**: Subscribes to "Payment Processed." Reserves inventory (local transaction 3).
    - **Success**: Publishes "Inventory Reserved" event.
    - **Failure**: Publishes "Inventory Reservation Failed" event.
           - **Compensating action**: If inventory reservation fails, the Inventory Service might trigger a "Release Order" event. The Payment Service would then issue a refund (compensating transaction for local transaction 2), and the Order Service would cancel the order (compensating transaction for local transaction 1).
- **Shipping Service**: Subscribes to "Inventory Reserved." Schedules shipment (local transaction 4).


### Two Main Saga Implementations

- **Choreography-based Saga**:

     - Services communicate directly by publishing and subscribing to events.
     - Each service executes its local transaction and then publishes an event that triggers the next step in the saga.
     - **Pros**: Highly decentralized, no single point of failure (no central coordinator).
     - **Cons**: Can become complex to manage and debug as the number of services and local transactions grows, making it harder to understand the overall flow.

- **Orchestration-based Saga**:

     - A central orchestrator (a dedicated service or workflow engine) manages the entire saga flow.
     - The orchestrator sends commands to each service, waits for responses/events, and decides the next step (either forward or a compensating action).
     - **Pros**: Centralized control makes it easier to understand the saga's flow, easier to debug and monitor.
     - **Cons**: The orchestrator can become a single point of failure or a bottleneck if not designed for high availability and scalability.

### Benefits of Saga Pattern

- **Loose Coupling**: Services are highly independent and don't need to know about the internal workings of other services.
- **Scalability**: Avoids global locks, allowing for greater parallelism and scalability.
- **Resilience**: Tolerates failures better than 2PC as local transactions can commit independently, and compensating transactions handle failures without blocking the entire system.
- **Technology Heterogeneity**: Services can use different database technologies.

### Challenges of Saga Pattern

- **Eventual Consistency**: Data might be temporarily inconsistent across services until the saga completes or rolls back. This requires careful application design to handle potential inconsistencies.
- **Complexity of Compensating Transactions**: Designing and implementing compensating transactions can be very challenging, especially for complex workflows or operations that are hard to undo (e.g., sending a physical email).
- **Debugging**: Tracing issues across a chain of events and compensating transactions can be difficult, requiring good distributed tracing tools.
- **Lack of Isolation**: Intermediate states are visible, which can lead to "dirty reads" if not handled carefully by the application logic.

The Saga Pattern is a powerful alternative to 2PC for ensuring consistency in highly distributed systems, but it shifts the complexity from the infrastructure level to the application design level, requiring careful consideration of eventual consistency and compensation logic.

