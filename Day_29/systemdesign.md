---
title: "Day 29: Data Consistency Models - Strong, Eventual, and Causal Consistency"
description: "A summary of my 29th day's learning in the 60-day challenge, Data Consistency Models - Strong, Eventual, and Causal Consistency"
keywords:
  - Data Consistency Models - Strong, Eventual, and Causal Consistency
  - Day 29
  - Challenge
---

### Table of contents :
- [Data Consistency Models: Strong, Eventual, and Causal Consistency](#data-consistency-models-strong-eventual-and-causal-consistency)




### Data Consistency Models: Strong, Eventual, and Causal Consistency
In distributed systems, where data is replicated across multiple nodes (servers, databases, data centers) for scalability, fault tolerance, and availability, data consistency models define the rules for how and when data changes become visible to different processes or users. Choosing the right consistency model is a fundamental design decision that impacts a system's performance, availability, and the complexity of application logic.

The CAP Theorem is a foundational concept here, stating that a distributed system can only guarantee two out of three properties simultaneously:

- **Consistency (C)**: All nodes see the same data at the same time.

- **Availability (A)**: Every request receives a response, without guarantee that it is the latest write.

- **Partition Tolerance (P)**: The system continues to operate despite arbitrary message loss or failure of parts of the system.

In practice, distributed systems must always be Partition Tolerant (P). Therefore, you typically have to choose between Consistency (C) and Availability (A). This choice directly relates to the consistency model you adopt.

Let's explore the three primary consistency models:

1. **Strong Consistency**
   - **Concept**:
     Strong consistency, often referred to as Linearizability or Atomic Consistency, is the strictest consistency model. It guarantees that once a write operation completes, all subsequent read operations will immediately reflect that write, regardless of which node is accessed. The system behaves as if there is only a single copy of the data, and all operations are executed one after another in a global, real-time order.

   - **Characteristics**:

      - **Immediate Visibility**: All clients see the most recent, up-to-date version of data at the same time.

      - **Global Ordering**: Operations appear to occur instantaneously and in a total order consistent with real-time. If operation A completes before operation B begins, then A must appear to precede B.

      - **No Stale Reads**: You are guaranteed to never read outdated data.

   - **Pros**:

      - **Simplified Application Logic**: Developers don't need to worry about handling stale data or conflicts, as the system ensures data is always consistent.

      - **Data Integrity**: Critical for applications where data correctness is paramount and even momentary inconsistencies are unacceptable.

   - **Cons**:

      - **Lower Availability (A)**: To maintain strong consistency during a network partition, the system must sacrifice availability. If a node cannot communicate with others to confirm a write, it might block requests or become unavailable.

      - **Higher Latency**: Requires significant coordination and synchronization across all nodes (e.g., using two-phase commit (2PC) or consensus algorithms like Paxos/Raft), which adds overhead and latency to write operations.

      - **Reduced Scalability**: The synchronization overhead can limit horizontal scalability, especially in geographically dispersed systems.

   - **Use Cases**:

      - Financial transactions (e.g., banking, stock trading).

      - Inventory management systems (where accurate stock levels are critical).

      - Medical records.

      - Any system where immediate data accuracy is non-negotiable.

   - **Examples**:

      - Traditional relational databases (e.g., PostgreSQL, MySQL) with ACID transactions.

      - Google Spanner (a globally distributed database that achieves strong consistency).

2. **Eventual Consistency**
- **Concept**:
Eventual consistency is a weaker consistency model that prioritizes availability (A) and partition tolerance (P) over immediate consistency. It guarantees that if no new updates are made to a given data item, all replicas of that item will eventually converge to the same value. However, during the propagation period, different nodes might temporarily return conflicting or stale data.

- **Characteristics**:

   - **Asynchronous Propagation**: Updates are propagated asynchronously across nodes in the background.

   - **Temporary Inconsistency**: After a write, a subsequent read might return an older version of the data from a replica that hasn't yet received the update.

   - **Convergence**: Given enough time and no further writes, all replicas will eventually become consistent.

   - **Conflict Resolution**: Systems often employ strategies like "last write wins" (LWW) or application-specific logic to resolve conflicts that arise from concurrent updates.

- **Pros**:

   - **High Availability (A)**: The system remains available and responsive even during network partitions or node failures, as writes can be acknowledged locally before full replication.

   - **Low Latency**: Write operations are typically faster as they don't wait for all replicas to acknowledge.

   - **High Scalability**: Easier to scale horizontally across many nodes and geographical regions.

- **Cons**:

   - **Complex Application Logic**: Developers must design applications to handle potential stale reads and conflicts, which can add complexity.

   - **Read-After-Write Inconsistency**: A client might write data and then immediately read it back, only to see the old version (though many systems offer "read-your-writes" consistency as a stronger variant).

   - **Unpredictable Convergence Time**: There's no guarantee on when consistency will be achieved.

- **Use Cases**:

   - Social media feeds (e.g., seeing an older post for a few seconds is acceptable).

   - E-commerce shopping carts (adding an item might not immediately reflect on another device).

   - DNS propagation.

   - Any system where high availability and scalability are more critical than immediate, absolute consistency.

- **Examples**:

   - Amazon DynamoDB (offers both eventual and strongly consistent reads).

   - Apache Cassandra.

   - Apache CouchDB.

   - Many NoSQL databases.

3. **Causal Consistency**
- **Concept**:
Causal consistency is a consistency model that sits between strong and eventual consistency. It ensures that operations that are causally related (i.e., one operation happens before another, or one operation is a consequence of another) are seen in the same order by all processes. However, it allows concurrent (causally unrelated) operations to be observed in different orders by different processes.

- **Characteristics**:

   - **Preserves Causal Order**: If event A "happened before" event B (e.g., B was initiated after observing A), then all processes that observe both A and B will observe A before B.

   - **Allows Concurrent Divergence**: If two events are causally independent (concurrent), their order of observation can vary across different processes.

   - **"Happened-Before" Relation**: Often implemented using mechanisms like vector clocks or Lamport timestamps to track causal dependencies.

- **Pros**:

   - **More Intuitive Behavior**: Often aligns better with human intuition about cause and effect than pure eventual consistency.

   - **Better Availability/Performance than Strong Consistency**: Does not require a global total order for all operations, reducing synchronization overhead.

   - **Stronger Guarantees than Eventual Consistency**: Provides more predictable behavior for causally related operations.

- **Cons**:

   - **More Complex Implementation**: Requires tracking causal dependencies, which adds overhead compared to eventual consistency.

   - **Not a Total Order**: Does not guarantee a total ordering of all operations, only causally related ones.

   - **Still Allows Stale Reads**: A process might still read stale data if that data is not causally related to a recent write it performed or observed.

- **Use Cases**:

   - Collaborative editing applications (e.g., Google Docs, where changes by one user are seen by others in the correct causal order).

   - Messaging systems (ensuring replies appear after the messages they respond to).

   - Version control systems.

   - Distributed social networks where the order of comments or replies matters.

- **Examples**:

   - Many distributed databases and systems that need to balance consistency with high availability for interactive, collaborative applications.
