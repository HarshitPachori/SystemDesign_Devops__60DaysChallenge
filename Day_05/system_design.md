---
title: "Day 05: Caching Deep Dive"
description: "A summary of my 5th day's learning in the 60-day challenge, covering fundamentals of Caching"
keywords:
  - Caching
  - Caching Policy
  - Day 5
  - Challenge
---

### Table of contents :
- [What is Cache invalidation ?](#what-is-cache-invalidation-)
- [What is Cache Eviction ?](#what-is-cache-eviction-)
- [What is Eviction Policy ?](#what-is-eviction-policy-)
- [What is LRU ?](#what-is-lru-)
- [what is LFU ?](#what-is-lfu-)
 

### What is Cache invalidation ?
- Cache invalidation is the process of marking or removing cached data as outdated or "stale" when the original data source changes. It's crucial for maintaining data consistency, ensuring that users always receive the most up-to-date information.
- Without proper invalidation, a system might serve old, incorrect data from its cache, even if the primary data has been updated. This leads to discrepancies and can cause issues like incorrect displays, faulty calculations, or even security vulnerabilities. It's a complex problem in distributed systems, often referred to as "one of the hardest problems in computer science."


### What is Cache Eviction ?  
- Cache eviction is the process of removing data from a cache to free up space when the cache reaches its capacity limit. Since caches have finite memory, when new data needs to be stored and the cache is full, existing data must be "evicted" to make room.
- This process is governed by cache eviction policies (also known as replacement algorithms), which determine which data to remove. Common policies include:


- **Least Recently Used (LRU)**: Evicts the item that has not been accessed for the longest time.

- **Least Frequently Used (LFU)**: Evicts the item that has been accessed the fewest times.

- **First-In, First-Out (FIFO)**: Evicts the oldest item in the cache, regardless of its usage.

- **Random Replacement (RR)**: Evicts a randomly chosen item.

- **Time-to-Live (TTL)**: Evicts items after a predetermined time period, even if they are still being used.





### What is Eviction Policy ?
- An eviction policy, in the context of caching, is a strategy or algorithm that a cache uses to decide which data item to remove when the cache is full and new data needs to be stored. Since caches have limited capacity, they must make choices about which existing data to discard to make room for incoming items.

- The goal of an eviction policy is to maximize the "cache hit" rate (the percentage of times requested data is found in the cache) and minimize cache misses. Different policies prioritize different aspects of data usage and recency, such as how recently an item was accessed, how frequently it's used, or simply its age.

### What is LRU ? 
- LRU stands for Least Recently Used. It's a very common cache eviction policy (or algorithm). When a cache needs to free up space, the LRU policy dictates that the item that has not been accessed or used for the longest period of time should be removed first.



- The logic behind LRU is that if data hasn't been used recently, it's less likely to be used again in the near future, making it a good candidate for eviction to make space for potentially more relevant, newer data. It's often implemented using a combination of a hash map and a doubly-linked list.

### what is LFU ? 
- LFU stands for Least Frequently Used. It's another cache eviction policy that aims to discard the item from the cache that has been accessed the fewest number of times.

- The rationale behind LFU is that items accessed very frequently are likely to be accessed again in the future, so they should remain in the cache. Conversely, items used rarely are good candidates for eviction when space is needed. Implementing LFU typically involves maintaining a count of access frequencies for each item in the cache.