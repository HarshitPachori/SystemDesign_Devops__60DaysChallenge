---
title: "Day 04: Load Balancing"
description: "A summary of my 4th day's learning in the 60-day challenge, covering basic of Caching"
keywords:
  - Caching
  - Caching Type
  - Day 2
  - Challenge
---

### Table of contents :
- [Caching Introduction](#caching-introduction)
- [What is Caching ?](#what-is-caching-)
- [Why is Caching Used ?](#why-is-caching-used-)
- [Advantages of Caching](#advantages-of-caching)
- [Types of Caching](#types-of-caching)


#### Caching Introduction 
Caching is a fundamental optimization technique in computing that involves storing copies of frequently accessed data in a temporary, high-speed storage location called a "cache." The goal is to reduce the need to re-fetch or re-compute data from its original, slower source, thereby speeding up access and improving overall system performance.

### What is Caching ?
Caching is the process of storing data that is likely to be requested again in a faster-access memory layer. When a system needs data, it first checks the cache. If the data is found (a "cache hit"), it's retrieved quickly. If not (a "cache miss"), the system fetches it from the slower primary source, and often stores a copy in the cache for future use.

### Why is Caching Used ?
Caching is essential because it bridges the performance gap between fast CPUs and slower data storage mediums (like databases or disks) or remote services. It significantly reduces latency, improves throughput, lowers the load on backend systems, and ultimately enhances the user experience by delivering data much faster.

### Advantages of Caching 

- **Improved Performance/Speed** : Faster data retrieval due to data being closer to the consumer (CPU, application, user).

- **Reduced Latency** : Less time spent waiting for data from slower sources.

- **Decreased Database/Backend Load** : Offloads requests from primary data stores, preventing them from being overwhelmed.

- **Cost Savings** : Can reduce the need for larger, more expensive primary resources by making existing ones more efficient.

- **Enhanced User Experience** : Faster loading times lead to more responsive applications and satisfied users.


### Types of Caching 
Caching can occur at various layers within a system :

- **Browser Cache**: Stores web content (images, CSS, JS) on the user's local device to speed up subsequent visits.
- **Application-Level Cache**: Managed by the application itself, storing frequently accessed data in RAM or a local file system (e.g., in-memory caches like Guava Cache).
- **Database Cache**: Built into database systems to store frequently queried data or query results to avoid re-executing complex queries.
- **Distributed Cache**: A shared, in-memory data store accessible by multiple application instances across different servers (e.g., Redis, Memcached). Ideal for scaling and consistency across distributed applications.
- **CDN (Content Delivery Network) Cache**: Distributes static and sometimes dynamic content to edge servers globally, serving content from locations geographically closer to users.
- **Proxy Cache/Reverse Proxy Cache**: A server (like Nginx or Squid) that sits in front of web servers and stores frequently requested web content to serve it directly without involving the origin server.
- **Operating System Cache**: OS-level caching of disk I/O and file system data.
- **CPU Cache**: Hardware-level caches (L1, L2, L3) directly on the CPU, storing frequently used instructions and data for extremely fast access.



