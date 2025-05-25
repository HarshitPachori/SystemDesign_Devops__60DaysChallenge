---
title: "Day 02: SOLID Principles and SPOF"
description: "A summary of my 2nd day's learning in the 60-day challenge, covering basic of system design. SOLID principles, HTTP, TCP/IP, etc"
keywords:
  - SOLID Principles
  - Reliability Concepts
  - Capacity Estimation
  - HTTP, TCP/IP, RDBMS, NOSQL, Cache
  - Day 2
  - Challenge
---

### Table of contents :
- [What is SOLID Principle ?](#what-is-solid-principle-)
- [Reliability Concepts ?](#reliability-concepts-)
- [What is Capacity Estimation ?](#what-is-capacity-estimation-)
- [What is HTTP ?](#what-is-http-)
- [What is TCP/IP ?](#what-is-tcpip-)
- [What is Relational Database ?](#what-is-relational-database-)
- [What is Database Indexes ?](#what-is-database-indexes-)
- [What is NoSQL Database ?](#what-is-nosql-database-)
- [What is Cache ?](#what-is-cache-)


#### What is SOLID Principle ?
SOLID Principles are five fundamental principles of object-oriented programming (OOP) that guide software developers in creating more understandable, flexible, and maintainable code.
These are :
- **Single Responsibility Principle** : A class should have only one reason to change, meaning it has one primary responsibility.
- **Open/Closed Principle** : Software entities (classes, modules, functions) should be open for extension but closed for modification.
- **Liskov Substitution Principle** : Objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program.
- **Interface Segregation Principle** : Clients should not be forced to depend on interfaces they do not use; larger interfaces should be split into smaller, specific ones.
- **Dependency Inversion Principle** : High-level modules should not depend on low-level modules; both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

#### Reliability Concepts ?
- **Redundancy** : 
Redundancy is the duplication of critical components or functions of a system with the intention of increasing reliability, usually in the form of a backup or fail-safe. If one component fails, the redundant component can take over, preventing service interruption. This is crucial for high availability and fault tolerance in systems like data centers or network infrastructure.
- **Single Point of Failure (SPOF)** :
A Single Point of Failure (SPOF) is a component in a system whose failure would cause the entire system to stop functioning. SPOFs are critical vulnerabilities that undermine system reliability and availability. Identifying and eliminating SPOFs, often through redundancy, is a primary goal in designing robust and resilient systems.

#### What is Capacity Estimation ? 
Capacity estimation in system design is the process of predicting the resources (CPU, memory, storage, network) a system needs to handle expected user load and data growth. It involves analyzing metrics like daily active users (DAU), requests per second (RPS), and data volume. Accurate estimation prevents bottlenecks, ensures performance, and optimizes infrastructure costs, making the system scalable and efficient.


#### What is HTTP ?
HTTP (Hypertext Transfer Protocol) is an application-layer protocol for transmitting hypermedia documents, like HTML. It's the foundation of data communication for the World Wide Web, operating on a client-server model. A client (e.g., web browser) sends an HTTP request to a server, which then returns an HTTP response containing the requested resource or a status code.


#### What is TCP/IP ?
TCP/IP (Transmission Control Protocol/Internet Protocol) is a suite of communication protocols that define how data is exchanged over the internet and other networks. IP handles addressing and routing data packets between devices, ensuring they reach the correct destination. TCP ensures reliable, ordered, and error-checked delivery of these packets, breaking data into segments and reassembling them at the receiver.

#### What is Relational Database ?
A relational database organizes data into one or more tables (relations) with columns and rows. Each row represents a record, and columns represent attributes. Data relationships are established through primary and foreign keys, enabling powerful querying using SQL (Structured Query Language). They ensure data integrity and consistency via ACID (Atomicity, Consistency, Isolation, Durability) properties, making them suitable for structured, transactional data.

#### What is Database Indexes ?
Database indexes are special lookup tables that a database search engine can use to speed up data retrieval operations. Similar to an index in a book, they store pointers to the physical location of data rows, allowing the database to quickly find specific data without scanning the entire table. Indexes significantly improve read performance but can slightly slow down write operations.

#### What is NoSQL Database ?
NoSQL (Not Only SQL) databases are non-relational databases offering flexible schemas, often for handling large volumes of unstructured or semi-structured data. Unlike relational databases, they don't use tables with fixed schemas, providing diverse data models like document, key-value, graph, and wide-column. NoSQL databases prioritize horizontal scalability, high availability, and agile development, making them suitable for modern web, mobile, and big data applications.

#### What is Cache ?
A cache is a high-speed data storage layer that stores a subset of data, typically transient, so that future requests for that data can be served faster. By keeping frequently accessed data closer to the application or user (e.g., in RAM, CDN), caches reduce latency, decrease load on primary data stores (like databases), and improve system performance and responsiveness.
