---
title: "Day 06: Caching Deep Dive"
description: "A summary of my 6th day's learning in the 60-day challenge, covering fundamentals of Caching with Creating a simple Key - Value Store like Redis"
keywords:
  - Caching
  - Caching Policy
  - Day 5
  - Challenge
---

### Table of contents :
- [What is Database Scaling ?](#what-is-database-scaling-)
- [What are Data Stores ?](#what-are-data-stores-)
- [What is Data Replication ?](#what-is-data-replication-)
- [What is Master-Slave Replicas ?](#what-is-master-slave-replicas-)
- [What is Multi-Master Replicas ?](#what-is-multi-master-replicas-)
- [What are Read Replicas ?](#what-are-read-replicas-)
- [How are NoSQL Databases Optimized ?](#how-are-nosql-databases-optimized-)
- [How are SQL Databases Optimized ?](#how-are-sql-databases-optimized-)
- [What are Location-Based Databases ?](#what-are-location-based-databases-)
- [What is Database Migration ?](#what-is-database-migration-)
- [What are Bloom Filters ?](#what-are-bloom-filters-)
- [System Design Case Study 1 : Design a simple Key - Value Store like Redis](#system-design-case-study-1--design-a-simple-key---value-store-like-redis)
 

### What is Database Scaling ?
- Database scaling refers to the methods and processes used to increase a database's capacity to handle growing amounts of data, user traffic, and query load while maintaining performance and efficiency. As applications grow, their databases often become a bottleneck, leading to slow response times and degraded user experience.

- Scaling aims to prevent these issues by adding or adjusting resources to the database system. This can involve enhancing its ability to store more data, process more transactions, or manage more concurrent user requests.


### What are Data Stores ?  
Data stores are repositories or systems designed to hold and manage data. This broad term encompasses various technologies like relational databases (SQL), NoSQL databases, data warehouses, data lakes, and caching systems. They are chosen based on the type of data, access patterns, scalability needs, and consistency requirements of an application.




### What is Data Replication ?
Data replication is the process of creating and maintaining multiple copies of data across different nodes or servers within a database system. The primary goals are to improve data availability (by having backups), enhance fault tolerance (if one copy fails, others exist), and increase read scalability (by allowing reads from multiple copies).


### What is Master-Slave Replicas ?
Master-Slave replication is a data replication setup where one database server (the "master") handles all write operations, and its changes are asynchronously or synchronously copied to one or more other servers (the "slaves" or "replicas"). Slaves primarily handle read operations. This model improves read scalability and provides data redundancy.


### What is Multi-Master Replicas ?

Multi-Master replication is a setup where multiple database servers can accept write operations, and their changes are then synchronized among all participating masters. This offers higher write availability and improved write scalability compared to master-slave, as well as better fault tolerance. However, it introduces complexities in resolving conflicts when the same data is modified concurrently on different masters.


### What are Read Replicas ?

Read Replicas are secondary, read-only copies of a primary database. They are typically used in master-slave or similar replication setups. Their purpose is to offload read queries from the primary (master) database, thereby improving the performance and scalability of read-heavy applications and reducing the load on the master.

### How are NoSQL Databases Optimized ?
NoSQL databases are optimized for different use cases than SQL databases. They are often optimized for:

- **Horizontal Scalability**: Easily scale out by adding more servers.
- **High Availability**: Often built with replication and partitioning from the ground up.
- **Flexible Schema**: Accommodate unstructured or semi-structured data without predefined schemas.
- **Specific Access Patterns**: Optimized for particular data models (e.g., key-value, document, graph) and related query patterns.
- **High Write Throughput**: Many are designed for rapid ingestion of large data volumes.


### How are SQL Databases Optimized ?
SQL databases are optimized for:

- **Data Integrity**: Strong ACID guarantees (Atomicity, Consistency, Isolation, Durability) ensuring reliable transactions.
- **Structured Data**: Efficiently store and query highly structured data with complex relationships.
- **Complex Queries**: Optimized for complex joins and analytical queries using SQL.
- **Vertical Scaling**: Traditionally optimized by scaling up (more CPU, RAM) a single server, though horizontal scaling options exist (sharding, read replicas).
- **Indexing**: Heavily rely on indexing for fast data retrieval

### What are Location-Based Databases ?
Location-based databases (or geospatial databases) are specialized databases optimized for storing, querying, and analyzing spatial or geographic data. They can store points, lines, and polygons representing real-world locations and support operations like finding objects within a radius, calculating distances, or determining proximity. Examples include PostGIS extension for PostgreSQL, MongoDB's geospatial indexes, and AWS DynamoDB's geospatial capabilities.

### What is Database Migration ?
Database migration is the process of moving data from one database system to another. This can involve moving data from an on-premises database to a cloud database, from one cloud provider to another, or from an old version/type of database to a newer one (e.g., relational to NoSQL, or PostgreSQL to MySQL). It often includes schema conversion, data transformation, and ensuring data integrity during the transfer.


### What are Bloom Filters ?
Bloom filters are space-efficient probabilistic data structures used to test whether an element is a member of a set. They can tell you with certainty if an element is not in the set, but if it says an element is in the set, there's a small probability of a "false positive" (meaning it might not actually be there). They are used to quickly check for existence and avoid costly lookups, common in caching or to prevent unnecessary database queries.

### System Design Case Study 1 : Design a simple Key - Value Store like Redis
- For Our Implementation refer `~/Day_06/SimpleKeyValueStore.java` file   . In this we have simulated a Java Class based Key - Value store using HashMap / ConcurrentMap.

- **How a production level system is designed** : 
- **Goal**: Design a simple, in-memory, highly available Key-Value store with basic Put/Get operations.

- **Core Components**:

  - **Client**: Interacts with the store via network requests.
  - **Load Balancer**: Distributes client requests across multiple KV store nodes.
  - **KV Nodes**:
       - **In-Memory Store**: A hash map (std::unordered_map or similar) to store key-value pairs directly in RAM for fast access.
       - **Persistence (Optional but Recommended)**: Periodically snapshotting data to disk (e.g., RDB-like snapshotting) or appending operations to an AOF (Append-Only File) for durability.
       - **Replication**: Master-slave setup for high availability and read scaling. A master handles writes, slaves replicate data and handle reads. If the master fails, a slave can be promoted.
       - **Heartbeats/Health Checks**: Nodes send periodic pings to a monitoring service (or each other) to detect failures.

- **Scalability Considerations**:

  - **Horizontal Scaling**: Add more KV nodes for increased capacity.
  - **Consistent Hashing**: Use consistent hashing to distribute keys across multiple nodes, minimizing rebalancing when nodes are added/removed.

- **Simple Put/Get Flow**:

  - **PUT Request**: Client -> Load Balancer -> Master KV Node -> Master writes to memory & logs to AOF/replicates -> Acknowledges client.
  - **GET Request**: Client -> Load Balancer -> Any KV Node (Master or Slave) -> Node looks up key in memory -> Returns value or "not found."

