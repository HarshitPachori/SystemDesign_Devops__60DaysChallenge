---
title: "Day 10: Intro to Microservices Architecture"
description: "A summary of my 10th day's learning in the 60-day challenge, covering fundamentals of Microservices Architecture."
keywords:
  - Microservices
  - Day 10
  - Challenge
---

### Table of contents :
- [What are Microservices ?](#what-are-microservices-)
- [Advantages of Microservices](#advantages-of-microservices)
- [Disadvantages of Microservices](#disadvantages-of-microservices)
- [What is Service Discovery ?](#what-is-service-discovery-)
- [What is API Gateway ?](#what-is-api-gateway-)
- [What is Distributed Tracing ?](#what-is-distributed-tracing-)
- [What is Circuit Breaker Pattern ?](#what-is-circuit-breaker-pattern-)
- [What is Event-Driven Architecture (EDA) / Event Bus ?](#what-is-event-driven-architecture-eda--event-bus-)
- [What is Database per Service ?](#what-is-database-per-service-)


### What are Microservices ?
Microservices is an architectural style that structures an application as a collection of small, autonomous, loosely coupled services. Each service is self-contained, implementing a single business capability, and can be developed, deployed, and scaled independently. These services communicate with each other over a network, typically using lightweight protocols like HTTP/REST or message queues.

Think of it as breaking down a large, monolithic application into smaller, manageable pieces, where each piece (microservice) focuses on doing one thing well.


### Advantages of Microservices
- **Modularity and Maintainability**: Smaller codebases are easier to understand, develop, and maintain. Developers can focus on a single service without worrying about the entire application.
- **Independent Deployment**: Each service can be deployed independently without affecting other services. This enables faster release cycles and reduces the risk of deploying new features.
- **Scalability**: Services can be scaled independently based on their specific load requirements. If only one part of the application (e.g., the order processing service) experiences high traffic, only that service needs to be scaled, optimizing resource usage.
- **Technology Heterogeneity**: Different services can be built using different programming languages, frameworks, and databases, allowing teams to choose the best tool for the job.
- **Fault Isolation**: A failure in one service is less likely to bring down the entire application. The impact is contained to the failing service, improving overall system resilience.
- **Team Autonomy**: Small, cross-functional teams can own and manage individual services end-to-end, leading to increased agility and faster development.

### Disadvantages of Microservices
- **Increased Complexity**: Managing a distributed system with many independent services is inherently more complex than a monolith. This includes deployment, monitoring, debugging, and data consistency.
- **Distributed Data Management**: Maintaining data consistency across multiple services, each potentially having its own database, is challenging. Distributed transactions are complex to implement.
- **Inter-Service Communication Overhead**: Network latency and the overhead of inter-service calls can impact performance.
- **Operational Overhead**: Requires robust infrastructure for service discovery, load balancing, centralized logging, distributed tracing, and monitoring.
- **Testing Complexity**: Testing interactions between many independent services can be more difficult than testing a single monolithic application.
- **Debugging Challenges**: Tracing a request through multiple services can be complex, requiring sophisticated distributed tracing tools



### What is Service Discovery ?
Service Discovery is a mechanism that allows microservices to find and communicate with each other without hardcoding their network locations (IP addresses and ports). In a dynamic microservices environment where services are frequently scaled up/down, restarted, or moved, their network locations change. Service discovery solves this problem by providing a registry where services can register themselves and clients can look them up.

- **How it works**:
    - **Service Registration**: When a microservice starts, it registers itself with a service registry, providing its network location and metadata.
    - **Service Lookup**: When a client microservice needs to communicate with another service, it queries the service registry to get the available instances and their locations.
    - **Load Balancing**: The client or a load balancer can then choose an instance from the available list, often using a load-balancing algorithm.
    - **Examples of Service Discovery Tools**: Eureka, Consul, Apache ZooKeeper, AWS Cloud Map, Kubernetes DNS.

### What is API Gateway ?
- An API Gateway is a single entry point for all client requests to a microservices application. It acts as a reverse proxy that routes requests to the appropriate microservice.
- **Use** :
    - **Request Routing**: Directs incoming requests to the correct backend service.
    - **Authentication/Authorization**: Centralizes security concerns.
    - **Rate Limiting/Throttling**: Controls traffic flow to protect backend services.
    - **Response Aggregation**: Can combine responses from multiple services into a single response for the client.
    - **Protocol Translation**: Can translate between different protocols (e.g., REST to gRPC).
- **Example**: AWS API Gateway, Netflix Zuul, Spring Cloud Gateway.


### What is Distributed Tracing ?
- A technique used to monitor and profile requests as they flow through multiple services in a distributed system. It collects data about each step of a request (latency, errors) and correlates them into a single "trace."
- **Use** :
    - **Debugging**: Helps pinpoint the exact service causing latency or errors in a complex request path.
    - **Performance Optimization**: Identifies bottlenecks and slow services.
    - **Observability**: Provides deep insights into how requests are processed across the entire system.
- **Example**: OpenTelemetry, Jaeger, Zipkin, AWS X-Ray.

### What is Circuit Breaker Pattern ?
- A design pattern used to prevent a single failing service from causing cascading failures across an entire distributed system. It monitors calls to a service, and if the error rate exceeds a threshold, it "trips" (opens the circuit) and prevents further calls to that service for a period.
- **Use** :
   - **Fault Tolerance**: Prevents clients from repeatedly trying to access a failing service, which can overwhelm it and other dependent services.
   - **Resilience**: Allows the failing service time to recover without being hammered by continuous requests.
   - **Degraded Functionality**: Enables the system to provide partial functionality instead of complete failure.
- **Example**: Hystrix (though deprecated, its concepts are widely adopted), Resilience4j.

### What is Event-Driven Architecture (EDA) / Event Bus ?
- An architectural pattern where services communicate by producing and consuming "events." An event is a significant change in state (e.g., "Order Placed," "User Registered"). Services don't directly call each other; they publish events to an event bus (or message broker), and other interested services subscribe to and react to these events.

- **Use** :
   - **Extreme Decoupling**: Services are highly independent; publishers don't know who consumes their events, and consumers don't know who publishes them.
   - **Asynchronous Processing**: Naturally asynchronous, improving responsiveness.
   - **Scalability**: Easily scale by adding more consumers to handle events.
   - **Extensibility**: Easy to add new functionalities by simply adding new event consumers without modifying existing services.
- **Example**: Apache Kafka, Amazon Kinesis, RabbitMQ (used as an event bus), Google Cloud Pub/Sub.

### What is Database per Service ?
- A principle in microservices architecture where each microservice owns its own private database. No other service can directly access another service's database.

- **Use** :
   - **Autonomy**: Services are truly independent, including their data storage.
   - **Technology Choice**: Each service can choose the best database technology for its specific data needs (e.g., a relational database for user data, a document database for product catalogs).
   - **Decoupling**: Prevents tight coupling at the database level, which is a common problem in monoliths.
   - **Scalability**: Databases can be scaled independently with their respective services.

- **Challenges**:
   - **Data Consistency**: Maintaining consistency across services (e.g., distributed transactions) becomes complex. Often solved with eventual consistency and patterns like Saga.
   - **Data Duplication**: Some data might need to be replicated across services for efficient querying.
   - **Data Joins**: Joins across different services' data require API calls or data replication, not direct database joins.

