---
title: "Day 12: Intro to API Gateway"
description: "A summary of my 12th day's learning in the 60-day challenge, covering fundamentals of Database Sharding and techniques."
keywords:
  - Database Sharding / Partition
  - Day 12
  - Challenge
---

### Table of contents :
- [What is Database Partitioning ?](#what-is-database-partitioning-)
- [What is Database Sharding ?](#what-is-database-sharding-)
- [Sharding/Partitioning Techniques](#shardingpartitioning-techniques)
- [Challenges in Database Sharding](#challenges-in-database-sharding)

### What is Database Partitioning ?
Partitioning is the process of dividing a large logical database table into smaller, more manageable physical pieces called partitions. These partitions can reside on the same database server or different database servers.

- **Vertical Partitioning**: Divides a table by its columns. Related columns are grouped into separate tables, with the primary key usually duplicated across them.

    - **Use Case**: When a table has many columns, and some are frequently accessed while others are rarely used. Separating them reduces the amount of data read for common queries.
    - **Example**: A users table with user_id, name, email (frequently accessed) and address_history, purchase_preferences (less frequently accessed) can be split.
- **Horizontal Partitioning (Sharding)**: Divides a table by its rows. Each partition contains a subset of the rows, but all partitions have the same schema. This is what sharding refers to when partitions are distributed across different servers.

   - **Use Case**: When a table has a massive number of rows that exceeds the capacity of a single server, or when you need to distribute the read/write load.

### What is Database Sharding ?
Sharding is a specific type of horizontal partitioning where the partitions (called shards) are stored on separate database server instances. Each shard is an independent database with its own resources (CPU, memory, storage). All shards together form a single logical database.

- **Purpose of Sharding**:
    - **Scalability**: Overcomes the limitations of a single database server by distributing data and load across multiple machines (horizontal scaling).
    - **Performance**: Queries and operations can be executed in parallel on smaller datasets, leading to improved response times.
    - **High Availability/Fault Isolation**: If one shard fails, only a portion of the data/application is affected, not the entire system.
- **Shared-Nothing Architecture**: Each physical shard operates independently and doesn't share hardware or software resources with other shards.
- **Shard Key**: A chosen column (or set of columns) that determines how data is distributed across shards. This is crucial for sharding strategy.


### Sharding/Partitioning Techniques
The choice of partitioning technique determines how data is distributed across partitions/shards.

1. **Hash Partitioning (or Key-Based Sharding)**:

  - **How it works**: A hash function is applied to the shard key (e.g., user_id, product_id). The output of the hash function determines which partition/shard the data belongs to (e.g., hash(key) % number_of_shards).
  - **Advantages**:
       - **Even Data Distribution**: Tends to distribute data very evenly across all shards, minimizing the risk of hotspots related to data volume.
       - Simple Logic: The routing logic is straightforward: calculate the hash and modulus.
  - **Disadvantages**:
       - **Inefficient Range Queries**: Range queries (e.g., "get all users registered between Jan and March") become inefficient because data for a range is scattered across all shards, requiring a "fan-out" query to all shards and then aggregation.
       - **Rebalancing Difficulty**: Adding or removing shards (resharding) is very complex and expensive because the hash function's output changes, requiring almost all data to be rehashed and moved. This often involves downtime or complex migrations.
  - **Use Cases**: When queries primarily involve exact key lookups (e.g., fetching a user by user_id), and you need uniform data distribution.

2. **Range Partitioning**:

   - **How it works**: Data is divided into partitions based on continuous ranges of values of the partition key. Each partition is assigned a specific range (e.g., user_id 1-100 to Shard A, user_id 101-200 to Shard B, or transactions from Jan-Mar to Shard A, Apr-Jun to Shard B).
   - **Advantages**:
         - **Efficient Range Queries**: Queries involving ranges on the partition key are very efficient, as they only need to query a specific subset of shards.
         - **Easier Rebalancing (potentially)**: Adding new shards (e.g., for future date ranges) can be simpler, as new data simply goes to the new shard without impacting existing data. Splitting existing ranges can be done.
   - **Disadvantages**:
         - **Hotspots (Uneven Distribution)**: A significant risk. If most activity (reads/writes) falls within a single range (e.g., current month's data, or a range containing very popular users), that shard can become a hotspot, leading to performance bottlenecks.
         - **Manual Range Definition**: Requires careful planning of ranges to prevent uneven data distribution and hotspots.
   - **Use Cases**: Time-series data, geographically distributed data, applications with clear logical data segmentation.

3. **List Partitioning**:

  - **How it works**: Data is partitioned based on discrete, predefined values in the partition key (e.g., country = 'USA' to Shard A, country = 'UK' to Shard B).
  - **Advantages**: Direct and intuitive mapping of data to specific partitions.
  - **Disadvantages**: Requires explicit management of values; if new values appear, they must be added to a partition or handled as errors. Can also lead to hotspots if some list values are more popular.
  - **Use Cases**: Data segmented by regions, departments, or other categorical values.

4. **Composite Partitioning**:

  - Combines two partitioning methods (e.g., range + hash) for finer-grained control and to mitigate the disadvantages of a single method.

### Challenges in Database Sharding

1. **Hotspots (Uneven Load Distribution)**:

   - **Problem**: Occurs when a disproportionate amount of read/write traffic is directed to a single shard, overwhelming its resources while other shards remain underutilized. This nullifies the benefits of sharding for that particular workload.
   - **Causes**:
        - **Poor Shard Key Choice**: A shard key that doesn't distribute data evenly (e.g., country if 90% of users are from one country, or creation_timestamp in range sharding where new data is heavily written).
        - **"Celebrity Problem"**: A few highly popular entities (e.g., a celebrity user, a viral product) causing extreme load on the shard containing their data.
   - **Mitigation**:
        - **Composite Shard Keys**: Use a combination of keys (e.g., user_id + timestamp) to ensure better distribution.
        - **Salting**: Add a random component to the shard key to spread hot data.
        - **Rebalancing**: Manually or automatically redistribute data to balance load.
        - **Caching**: Cache frequently accessed hot data.
        - **Vertical Partitioning/Denormalization**: Store hot attributes separately or duplicate them for quick access.

2. **Data Rebalancing**:

   - **Problem**: The process of redistributing data across shards when the distribution becomes uneven (due to growth, changing access patterns, or adding/removing shards). This is often complex, resource-intensive, and can impact performance during the operation.
   - **Why it's needed**: To alleviate hotspots, add new capacity, or remove old shards.
   - **Challenges**:
        - **Complexity**: Involves identifying imbalances, migrating data chunks, updating routing metadata, and ensuring data consistency during the move.
        - **Resource Intensive**: Data migration consumes network I/O, CPU, and disk resources on both source and destination shards.
        - **Downtime/Performance Impact**: Can cause performance degradation or even downtime if not handled carefully (especially for applications that require strong consistency).
        - **Data Integrity**: Ensuring that no data is lost or corrupted during the move is critical.
   - **Approaches**:
        - **Manual Rebalancing**: DBA-driven process (risky, complex for large scale).
        - **Automated Rebalancing (e.g., MongoDB's Balancer)**: Some NoSQL databases have built-in balancers that automatically detect uneven data distribution and migrate "chunks" or "ranges" of data between shards in the background. This is typically done with minimal application impact.
        - **Virtual Partitions**: Mapping shard keys to a larger number of virtual partitions, which are then mapped to physical shards. This makes it easier to move virtual partitions between physical shards without re-hashing all data.

3. **Cross-Shard Joins and Transactions**:

   - **Problem**: If a query needs data from multiple shards, or a transaction needs to update data across multiple shards, it becomes significantly more complex.
   - **Challenges**:
         - **Latency**: Requires fetching data from multiple network locations.
         - **Complexity**: Requires application-level logic to perform joins or coordinate distributed transactions (e.g., Two-Phase Commit, Saga pattern), which are hard to implement reliably.
   - **Mitigation**:
         - **Denormalization**: Duplicate frequently joined data across shards.
         - **Application-Level Joins**: Fetch data from multiple shards and join it in the application code.
         - **Analyze Query Patterns**: Design shard keys to co-locate frequently joined data on the same shard.

4. **Increased Operational Complexity**:

   - Managing multiple database instances (shards) means more components to monitor, back up, patch, and maintain.
