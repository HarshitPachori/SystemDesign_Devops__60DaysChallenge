---
title: "Day 24: Microservice"
description: "A summary of my 23th day's learning in the 60-day challenge, microservice"
keywords:
  - Microservice
  - Day 24
  - Challenge
---

### Table of contents :
- [Microservices Communication: Synchronous vs. Asynchronous](#microservices-communication-synchronous-vs-asynchronous)
- [Synchronous Communication](#synchronous-communication)
- [When to Use Synchronous Communication ?](#when-to-use-synchronous-communication-)
- [Challenges with Synchronous Communication in Microservices](#challenges-with-synchronous-communication-in-microservices)
- [Asynchronous Communication](#asynchronous-communication)
- [When to Use Asynchronous Communication ?](#when-to-use-asynchronous-communication-)



### Microservices Communication: Synchronous vs. Asynchronous

In a microservices architecture, how services talk to each other is a critical design decision with significant implications for performance, scalability, and resilience.

### Synchronous Communication
- **Concept**:
When one service (the client) makes a request to another service (the server) and waits for a response before continuing its own processing. This is a direct, blocking interaction.

- **Common Technologies**:

   - **REST (Representational State Transfer) over HTTP/HTTPS**:
      - **Mechanism**: Services communicate using standard HTTP methods (GET, POST, PUT, DELETE) and exchange data typically in JSON or XML format.
      - **Pros**: Widespread adoption, statelessness (simplifies scaling), human-readable, easy to debug with standard browser tools.
      - **Cons**: Less efficient for high-volume, low-latency scenarios compared to binary protocols. No built-in streaming.
   - **gRPC (Google Remote Procedure Call)**:
      - **Mechanism**: Uses Protocol Buffers for defining service interfaces and data structures, and HTTP/2 for transport. Generates highly efficient client/server stubs.
      - **Pros**: High performance (binary serialization, HTTP/2 multiplexing, header compression), strong type-checking (from Protobufs), supports streaming (unary, server-side, client-side, bidirectional).
      - **Cons**: Steeper learning curve, requires code generation, less human-readable for debugging, browser support requires a proxy.

### When to Use Synchronous Communication ?

- When an immediate response is required for the client to proceed (e.g., user login, retrieving real-time data, credit card authorization).
- For simple request-response interactions where the client needs the result right away.
- When there's a strong dependency between the services involved in a single operation.

### Challenges with Synchronous Communication in Microservices

- **Tight Coupling**: Services become directly dependent on each other's availability and response times.
- **Cascading Failures**: If a downstream service fails or becomes slow, it can directly impact upstream services, leading to system-wide degradation.
- **Scalability Limitations**: The calling service is blocked, potentially limiting its throughput if the called service is slow.
- **Latency**: Each hop adds network latency.

### Asynchronous Communication
- **Concept**:
When one service sends a message or event and does not wait for an immediate response. Instead, it continues its own processing, and the response (if any) is handled later, often by another service. This typically involves an intermediary (like a message queue or event bus).

- **Common Technologies**:

   - **Message Queues (e.g., Apache Kafka, RabbitMQ, AWS SQS, Azure Service Bus)**:

      - **Mechanism**: A producer service sends messages to a queue. A consumer service reads messages from the queue independently. The queue acts as a buffer and decouples producer from consumer.
      - **Pros**:
         - **Decoupling**: Producers and consumers don't need to know about each other or be simultaneously available.
         - **Resilience**: Messages are buffered, so consumers can process them even if the producer is temporarily unavailable. Retries and dead-letter queues enhance reliability.
         - **Scalability**: Producers can send messages at a high rate, and consumers can scale independently to process them.
         - **Load Leveling**: Smooths out traffic spikes.
      - **Cons**: Increased complexity (managing message brokers), eventual consistency might be a consideration, difficult to trace end-to-end flow.

   - **Event Bus / Event Streaming (e.g., Apache Kafka, AWS EventBridge, Google Cloud Pub/Sub)**:

      - **Mechanism**: Similar to message queues but often with a focus on "events" (facts that something happened). Producers publish events to an event bus/stream, and multiple consumers can subscribe to events they are interested in. Events are typically immutable and stored for a period.
      - **Pros**:
         - **Extreme Decoupling**: Publishers don't know who consumes events. Multiple consumers can react to the same event.
         - **Real-time Processing**: Enables real-time data pipelines and reactions.
         - **Auditability**: Event streams can serve as a durable log of all system changes.
      - **Cons**: Eventual consistency, difficult to ensure all consumers processed an event correctly, schema evolution for events can be complex.

### When to Use Asynchronous Communication ?

- When immediate response is not required (e.g., sending email notifications, processing orders in the background, updating search indexes).
- For long-running operations.
- To handle high-throughput, bursty workloads.
- To enable multiple services to react to the same piece of information without direct coupling.
- To increase system resilience by isolating failures.


