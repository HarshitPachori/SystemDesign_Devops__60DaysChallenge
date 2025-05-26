---
title: "Day 03: Load Balancing"
description: "A summary of my 3rd day's learning in the 60-day challenge, covering basic of Load Balancing"
keywords:
  - Load Balancing
  - Round Robin
  - Day 2
  - Challenge
---

### Table of contents :
- [What is Load Balancing ?](#what-is-load-balancing-)
- [Benefits of Load Balancing](#benefits-of-load-balancing)
- [What is Load Balancer ?](#what-is-load-balancer-)
- [What is Round Robin ?](#what-is-round-robin-)
- [Use of Round Robin in Load Balancing](#use-of-round-robin-in-load-balancing)
- [What is Least Connection ?](#what-is-least-connection-)
- [Use of LEast Connection in Load Balancing](#use-of-least-connection-in-load-balancing)
- [Advantages of Round Robin and Least Connection in Load Balancing:](#advantages-of-round-robin-and-least-connection-in-load-balancing)
- [What is Thrashing ?](#what-is-thrashing-)
- [What are Threads ?](#what-are-threads-)
- [What is Consistent Hashing ?](#what-is-consistent-hashing-)
- [What is Sharding ?](#what-is-sharding-)


#### What is Load Balancing ?
Load balancing is the process of distributing incoming network traffic across multiple servers or resources. Its primary goal is to ensure that no single server becomes overwhelmed, which improves the overall performance, reliability, and availability of an application or website.

### Benefits of Load Balancing


- **Improved Availability & Uptime** : By distributing traffic across multiple servers, if one server fails or needs maintenance, the load balancer automatically redirects requests to healthy servers, preventing downtime and ensuring continuous service.

- **Enhanced Performance** : It prevents individual servers from becoming overloaded, leading to faster response times and a smoother user experience. Traffic is efficiently spread, utilizing server capacity optimally
- **Scalability** : Load balancing facilitates horizontal scaling. As traffic grows, you can simply add more servers to the backend pool, and the load balancer will automatically include them, allowing your application to handle increasing demand.

- **Increased Reliability & Fault Tolerance** : It eliminates single points of failure by providing redundancy. If a server goes down, the system remains operational because other servers can pick up the slack.

- **Efficient Resource Utilization** : Load balancers ensure that all servers are utilized effectively, preventing some from being idle while others are overtaxed, leading to better ROI on infrastructure.

- **Security** : Many load balancers offer security features like SSL/TLS termination, DDoS attack mitigation by absorbing and distributing malicious traffic, and acting as a central point for applying security policies.


### What is Load Balancer ?
A "load balancer" acts as a traffic cop, sitting in front of your servers. It continuously monitors their health and intelligently routes client requests to the most appropriate, available, and healthy server, preventing bottlenecks and maximizing resource utilization.


#### What is Round Robin ?
Round Robin is one of the simplest and most commonly used load balancing algorithms. In this method, the load balancer distributes incoming client requests to servers in a sequential, rotating manner. Each new request goes to the next server in the list, returning to the beginning after reaching the last server.


### Use of Round Robin in Load Balancing
Its primary use in load balancing is to achieve basic, even distribution of traffic among a set of identical servers. It's easy to implement and ensures that all servers receive an equal number of requests over time. However, it doesn't account for server capacity or current load, so a busy server might still receive a new request if it's its turn.

#### What is Least Connection ? 
Least Connection is a dynamic load balancing algorithm that directs new incoming requests to the server with the fewest active connections at that moment. Unlike static algorithms, it considers the current load on each server. This helps prevent overloading a server that might be handling long-lived connections, even if it hasn't received many new requests recently.


#### Use of LEast Connection in Load Balancing
It's used when server processing times for requests might vary significantly or when connections have different durations. By intelligently routing traffic to less busy servers, Least Connection aims to optimize resource utilization, reduce response times, and ensure a more balanced distribution of actual workload across the server pool.


### Advantages of Round Robin and Least Connection in Load Balancing:

- **Round Robin Advantages**:

    - **Simplicity**: Easy to understand and implement.
    - **Even Distribution**: Ensures a relatively fair and equal distribution of requests over time when servers have identical capabilities.
    - **Low Overhead**: Requires minimal computational resources from the load balancer.

- **Least Connection Advantages**:

    - **Dynamic Load Awareness**: Considers the current actual load (active connections) on servers, leading to more efficient resource utilization.
    - **Better Performance for Variable Workloads**: Adapts well to environments where requests have varying processing times or connection durations, preventing slower servers from becoming bottlenecks.
    - I**mproved Responsiveness**: Directs traffic to less busy servers, generally resulting in faster response times for users.



#### What is Thrashing ?
- In computer science, thrashing refers to a state where a computer system spends an excessive amount of time and resources on non-productive tasks, leading to a severe degradation in performance. It commonly occurs in systems using virtual memory when there isn't enough physical RAM to accommodate all active processes.


- This causes the operating system to constantly swap data pages between the slower disk storage (virtual memory) and faster RAM. The CPU becomes busy with this futile "swapping" work rather than executing actual application instructions, leading to very low CPU utilization despite the system being extremely slow. It's like trying to juggle too many tasks on a small desk, spending all your time shuffling papers instead of actually working.

#### What are Threads ?
- Threads are the smallest units of execution within a process. Think of a process as a running application (like your web browser). A process can have one or more threads.


- Each thread within the same process shares the same memory space, code, and resources (like open files). This allows threads to communicate and cooperate easily. While a process provides a complete execution environment, threads are the actual sequences of instructions that the CPU executes. Using multiple threads (multithreading) allows a program to perform several tasks concurrently, improving responsiveness and utilization of multi-core processors.


#### What is Consistent Hashing ?
Consistent hashing is a special kind of hashing that minimizes the number of keys that need to be remapped when the number of servers (or cache nodes) in a distributed system changes. Instead of directly mapping keys to servers, it maps both servers and keys onto a circular hash space. When a server is added or removed, only a small fraction of keys needs to be reallocated, preventing a complete rehash of the data and ensuring better system stability and availability.




#### What is Sharding ?
Sharding (also known as horizontal partitioning) is a database partitioning technique that divides a large database into smaller, more manageable parts called "shards." Each shard is an independent database, typically hosted on a separate server, containing a subset of the data. Sharding improves scalability by distributing the data and workload across multiple machines, allowing for greater read/write throughput and storage capacity than a single database can provide.


