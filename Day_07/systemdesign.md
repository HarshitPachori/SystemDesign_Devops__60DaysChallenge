---
title: "Day 07: CAP Theorem and types of Databases"
description: "A summary of my 7th day's learning in the 60-day challenge, covering fundamentals of Databases and CAP Theorem."
keywords:
  - CAP Theorem
  - Database Types
  - Day 5
  - Challenge
---

### Table of contents :
- [What is CAP Theorem ?](#what-is-cap-theorem-)
- [Why "Pick Two"?](#why-pick-two)
- [What are Different Types of Databases ? ](#what-are-different-types-of-databases-)
- [Choosing Between SQL and NoSQL](#choosing-between-sql-and-nosql)


### What is CAP Theorem ?
The CAP theorem (also known as Brewer's theorem) is a fundamental principle in distributed systems design. It states that a distributed data store can only simultaneously guarantee at most two of the following three properties:

- **Consistency (C)**: All nodes in the system see the same data at the same time. After a write operation, any subsequent read operation across any node in the system should return the most recent data.
- **Availability (A)**: Every request received by a non-failing node in the system must result in a response (not an error or timeout), even if some nodes are down. The system must remain operational.

- **Partition Tolerance (P)**: The system continues to operate despite arbitrary network partitions. A network partition occurs when communication between nodes is disrupted, effectively splitting the system into isolated groups of nodes that cannot communicate with each other.

### Why "Pick Two"?
In a distributed system, network partitions are an inevitable reality (due to network failures, cable cuts, etc.). Therefore, Partition Tolerance (P) is almost always a mandatory requirement for any robust distributed system.

This forces system designers to choose between:

- **CP (Consistency and Partition Tolerance)**: The system prioritizes consistency. If a network partition occurs, it will sacrifice availability by refusing to serve requests (or returning an error) on the "disconnected" side of the partition until consistency can be guaranteed across all nodes. Databases like MongoDB (in its default configuration), HBase, and traditional relational databases often lean towards CP.

- **AP (Availability and Partition Tolerance)**: The system prioritizes availability. If a network partition occurs, it will continue to serve requests on both sides of the partition, even if it means returning stale or eventually consistent data. This often leads to "eventual consistency." Databases like Cassandra, CouchDB, and Amazon DynamoDB (and S3) are often AP systems.

- You cannot have CA (Consistency and Availability) without sacrificing P, which is generally not feasible in real-world distributed systems where network failures are assumed.

- The CAP theorem guides the design choices for distributed databases and services, helping developers decide which trade-offs are acceptable for their specific application's needs.

### What are Different Types of Databases ?  
- When building applications, choosing the right database is crucial. The two major paradigms are SQL (Relational Databases) and NoSQL (Non-Relational Databases). Each has distinct characteristics, advantages, and disadvantages, making them suitable for different types of workloads and data structures.



1. **SQL Databases (Relational Databases)**
SQL databases are based on the relational model, where data is organized into tables (relations) with predefined schemas, rows, and columns. Relationships between data in different tables are established using foreign keys. They use Structured Query Language (SQL) for defining, manipulating, and querying data.


- **Key Characteristics**:

     - **Structure**: Data is stored in tables with a fixed, predefined schema (rows and columns).
     - **Schema**: Rigid schema. Changes require careful planning and can be complex (e.g., adding a column to a large table).
     - **Relationships**: Strong relationships between tables, enforced by foreign keys and integrity constraints.
     - **Query Language**: Uses SQL, a powerful and standardized language for complex queries, joins, and aggregations.
     - **ACID Properties**: Strongly adhere to ACID (Atomicity, Consistency, Isolation, Durability) properties, ensuring data integrity and reliable transactions. This is critical for financial transactions, inventory systems, etc.

     - **Scalability**: Traditionally scale vertically (scaling up) by adding more resources (CPU, RAM, faster storage) to a single server. Horizontal scaling (sharding) is possible but often more complex to implement manually.

- **Advantages**:

    - **Data Integrity**: Strong ACID compliance ensures data consistency and reliability.
    - **Complex Queries**: SQL is powerful for complex joins, aggregations, and analytical queries.
    - **Structured Data**: Excellent for highly structured data with clear relationships.
    - **Maturity & Community**: Mature technology with a large ecosystem, extensive tooling, and strong community support.
    - **Data Normalization**: Reduces data redundancy and improves data integrity.

- **Disadvantages**:

    - **Rigid Schema**: Less flexible for rapidly changing data requirements or unstructured data.
    - **Scalability Limitations**: Vertical scaling has limits, and horizontal scaling can be complex. Can become a bottleneck for very high write loads or massive datasets.
    - **Handling Unstructured Data**: Not well-suited for unstructured or semi-structured data (like JSON, XML, images, videos).
    - **Cost**: Scaling up powerful single servers can become expensive.

- **Common SQL Databases**:

   - MySQL
   - PostgreSQL
   - Oracle Database
   - Microsoft SQL Server
   - SQLite

2. **NoSQL Databases (Non-Relational Databases)**

NoSQL databases (often interpreted as "Not only SQL") were developed to address the limitations of SQL databases, especially concerning the need for more flexible schemas, horizontal scalability, and handling massive amounts of diverse data types. They do not adhere to the rigid table-based relational model.


- **Key Characteristics**:

   - **Structure**: Flexible or dynamic schema. Can handle unstructured, semi-structured, and structured data.
   - **Schema**: Schema-less or dynamic schema. You can add new fields without affecting existing data, making development faster and more agile.
   - **Relationships**: Relationships are handled differently, often through embedding data or application-side logic, rather than strict database-level constraints.
   - **Query Language**: Each NoSQL database often has its own API or query language (e.g., MongoDB's query language, Cassandra Query Language (CQL)), which may or may not resemble SQL.
   - **CAP Theorem**: Often prioritize Availability (A) and Partition Tolerance (P) over strong Consistency (C), leading to "eventual consistency" in many cases.
   - **Scalability**: Primarily scale horizontally (scaling out) by distributing data across many servers/nodes, making them ideal for big data and high-traffic applications.

- **Types of NoSQL Databases**:

   - **Key-Value Stores**: Simplest model; data stored as a collection of key-value pairs. (e.g., Redis, DynamoDB, Memcached)

   - **Use Cases**: Caching, session management, real-time data.
   - **Document Databases**: Stores data in flexible, semi-structured "documents" (often JSON, BSON, XML). (e.g., MongoDB, Couchbase, Firestore)

   - **Use Cases**: Content management, e-commerce catalogs, user profiles.
   - **Column-Family Stores (Wide-Column Stores)**: Stores data in rows containing dynamically defined columns, grouped into "column families." Optimized for heavy write loads and large datasets. (e.g., Apache Cassandra, HBase) 
   - **Use Cases**: Time-series data, analytics, IoT, distributed logging.
   - **Graph Databases**: Stores data as nodes and edges, representing entities and their relationships. Optimized for traversing relationships. (e.g., Neo4j, Amazon Neptune)

   - **Use Cases**: Social networks, recommendation engines, fraud detection.

- **Advantages**:

   - **Flexibility (Schema)**: Highly flexible schema makes it easy to adapt to evolving data models.
   - **Scalability**: Designed for horizontal scaling, allowing them to handle massive amounts of data and high traffic loads.
   - **High Performance**: Optimized for specific data models and access patterns, often providing very high read/write performance.
   - **Big Data & Unstructured Data**: Excellent for handling large volumes of unstructured or semi-structured data.
   - **High Availability**: Often built with replication and distribution, ensuring high availability and fault tolerance.

- **Disadvantages**:

   - Consistency Trade-offs: Many are eventually consistent, which might not be suitable for applications requiring strict transactional consistency.
   - Complex Querying: Lack a standardized query language like SQL; complex joins across different "documents" or data entities can be challenging or require application-level logic.
   - Maturity: Some NoSQL technologies are newer and may have smaller communities or less mature tooling compared to established SQL databases.
   - Data Modeling Complexity: Can sometimes be more complex to model data effectively, especially for highly interconnected data.


### Choosing Between SQL and NoSQL
The choice depends entirely on your specific application requirements:

1. **Choose SQL when**:

- Your data has a clear, predefined, and stable structure.
- You need strong data integrity and ACID compliance (e.g., financial transactions).
- You require complex queries involving joins and aggregations.
- Your scaling needs are primarily vertical or can be met by sharding within the SQL paradigm.
- You value maturity, broad tool support, and a large developer community.

2. **Choose NoSQL when**:

- Your data is unstructured, semi-structured, or rapidly evolving.
- You need extreme horizontal scalability to handle massive data volumes and high traffic.
High availability and resilience to network partitions are paramount.
- Your application can tolerate eventual consistency.
- You have specific access patterns that align well with one of the NoSQL data models (e.g., simple key-value lookups, document retrieval, graph traversals).
- You need agile development and fast iteration cycles.
- It's also common to use a polyglot persistence approach, where different types of databases (SQL and various NoSQL types) are used within the same application to leverage the strengths of each for different parts of the data.

