---
title: "Day 14: Rate Limiter - Concept & Implementation"
description: "A summary of my 14th day's learning in the 60-day challenge, covering fundamentals of Rate Limiter and its Implementation."
keywords:
  - Rate Limiter
  - Fixed Window Algorithm
  - Sliding Window Algorithm
  - Day 14
  - Challenge
---

### Table of contents :
- [What is a Rate Limiter ?](#what-is-a-rate-limiter-)
- [Why Do We Need It? (Problems Rate Limiters Solve)](#why-do-we-need-it-problems-rate-limiters-solve)
- [Requirements and Goals of a Rate Limiter](#requirements-and-goals-of-a-rate-limiter)
- [Types of Rate Limiting](#types-of-rate-limiting)
- [Algorithms for Rate Limiting](#algorithms-for-rate-limiting)
- [Criteria for Choosing a Rate Limiting Strategy](#criteria-for-choosing-a-rate-limiting-strategy)
- [Design a Simple In-Memory Rate Limiter using Fixed Window Algorithm](#design-a-simple-in-memory-rate-limiter-using-fixed-window-algorithm)
- [Design a Simple In-Memory Rate Limiter using Sliding Window Algorithm](#design-a-simple-in-memory-rate-limiter-using-sliding-window-algorithm)

### What is a Rate Limiter ?
A Rate Limiter is a mechanism used in computer networks and systems to control the rate at which an activity can be performed. In the context of APIs and web services, it restricts the number of requests a client (user, application, or IP address) can make to a server or resource within a specified time window.

Essentially, it's a gatekeeper that ensures a service is not overwhelmed by too many requests from a single source or overall, helping to maintain performance, stability, and fairness.

### Why Do We Need It? (Problems Rate Limiters Solve)
Rate limiters are crucial for several reasons, addressing common challenges in distributed systems:

1. **Preventing Abuse and Attacks**:

   - **DDoS Attacks (Denial of Service)**: Limits prevent a single actor from flooding a service with requests to bring it down.
   - **Brute-Force Attacks**: Limits the number of login attempts, preventing attackers from rapidly guessing passwords.
   - **Web Scraping/Data Exfiltration**: Curbs automated bots from downloading excessive amounts of data.

2. **Ensuring Service Stability and Availability**:

   - Resource Protection: Prevents a single misbehaving client or a sudden surge in legitimate traffic from exhausting server resources (CPU, memory, network bandwidth, database connections), leading to slowdowns or crashes for all users.
   - Fair Usage: Ensures that all legitimate users get a fair share of the service's resources, preventing a few heavy users from monopolizing capacity.

3. Cost Optimization:

   - For cloud-based services where you pay per request or per resource usage, rate limiting helps control costs by preventing excessive, unnecessary calls.

4. API Monetization/Versioning:

   - API providers often use rate limiting to enforce different service tiers (e.g., free tier with lower limits, premium tier with higher limits).
   - It can help manage traffic during API migrations or versioning.


### Requirements and Goals of a Rate Limiter
A well-designed rate limiter should aim to achieve the following:

- Accuracy: Precisely enforce the configured limits without significant over or under-counting requests.
- Low Latency: Add minimal overhead to the request path.
- High Availability: The rate limiting system itself should be highly available, as it's a critical component in the request flow.
- Fault Tolerance: Continue operating correctly even if underlying components fail.
- Scalability: Be able to handle a large volume of requests and a growing number of clients/rules.
- Flexibility: Support various limiting criteria (per user, per IP, per endpoint, etc.) and different time windows.
- Configurability: Easy to define and update rate limiting rules.
- Transparency (for legitimate users): Provide clear feedback (e.g., HTTP 429 Too Many Requests status code) to clients when limits are hit, along with Retry-After headers.

### Types of Rate Limiting
Rate limiting can be applied at different levels:

1. Application-Level Rate Limiting:

   - Implemented within the application code or an API Gateway.
   - Pros: Can apply complex, business-logic-aware rules (e.g., "max 5 orders per user per day").
   - Cons: Consumes application resources; logic needs to be replicated if not centralized.

2. Web Server/Reverse Proxy Level Rate Limiting:

   - Implemented by web servers (e.g., Nginx, Apache) or dedicated reverse proxies/API Gateways (e.g., AWS API Gateway, Cloudflare, Kong).
   - Pros: Highly efficient, offloads work from application servers, good for basic HTTP requests.
   - Cons: Less granular, typically only uses request headers/IPs, not deep business logic.

3. Service Mesh Level Rate Limiting:

   - Implemented by service meshes (e.g., Istio, Linkerd) in a microservices environment.
   - Pros: Centralized control for inter-service communication, robust.
   - Cons: Adds complexity to the infrastructure.

### Algorithms for Rate Limiting
These algorithms determine how requests are counted and limited over time.

1. Fixed Window Counter:

  - Concept: Divides time into fixed-size windows (e.g., 1 minute). Each window has a counter. When a request arrives, the counter increments. If the counter exceeds the limit within the current window, the request is blocked. The counter resets at the start of each new window.
  - Pros: Simple to implement, low memory usage.
  - Cons:
      - Burstiness at Edges: Allows a client to send a full burst of requests at the very end of one window and another full burst at the very beginning of the next, effectively doubling the rate within a short period.
      - Can lead to "spikes" right after the counter resets.
  - Example: Limit 100 requests/minute. A client sends 100 requests at 0:59 and another 100 requests at 1:01. Total 200 requests in 2 minutes, but 200 requests within a 2-second span.

2. Sliding Log:

  - Concept: For each client, stores a timestamp of every request in a sorted log. When a new request arrives, it removes all timestamps older than the current time minus the window duration. If the number of remaining timestamps exceeds the limit, the request is blocked.
  - Pros: Very accurate, avoids the edge-case issues of Fixed Window.
  - Cons:
      - High Memory Usage: Requires storing a timestamp for every request, which can be memory-intensive for high-traffic clients.
      - High Computation: Removing old timestamps and sorting can be computationally expensive.

3. Sliding Window Counter (Hybrid):

  - Concept: Combines the efficiency of Fixed Window with the accuracy of Sliding Log. It uses multiple fixed windows and calculates an "average" count based on the current window's count and a weighted average of the previous window's count.
  - Pros: More accurate than Fixed Window, less memory-intensive than Sliding Log. Mitigates the "edge problem" to a large extent.
  - Cons: Still some approximation, not perfectly precise as Sliding Log.
  - Example: For a 1-minute limit, keep a counter for the current minute and the previous minute. If a request comes at 30 seconds into the current minute, the algorithm calculates: (requests_in_previous_minute * 0.5) + requests_in_current_minute.

4. Token Bucket:

  - Concept: Imagine a bucket with a fixed capacity. Tokens are added to the bucket at a constant rate. Each request consumes one token. If the bucket is empty, the request is blocked (or queued).
  - Pros:
     - Allows Bursts: Clients can send bursts of requests (up to the bucket capacity) if there are enough tokens.
     - Smooths Out Bursts: Limits the rate at which requests can be sustained over time, as tokens are added at a constant rate.
     - Low Resource Usage: Doesn't need to store a log of every request.
  - Cons: Requires two parameters (bucket size and refill rate), which can be tricky to tune.
  - Example: A bucket capacity of 100 tokens, refilled at 10 tokens/second. A client can send 100 requests instantly if the bucket is full. But then they can only send 10 requests per second after that until the bucket refills.

5. Leaky Bucket:

  - Concept: Similar to a bucket with a hole at the bottom. Requests are added to the bucket (queue) and processed at a constant, fixed output rate. If the bucket overflows (queue is full), new requests are dropped.
  - Pros: Produces a very smooth and constant output rate, regardless of input burstiness. Good for ensuring stable backend load.
  - Cons:
      - Queuing Introduces Latency: Requests might wait in the queue, increasing response times.
      - Lossy: If the bucket overflows, requests are simply dropped, which might not be desirable for all applications.
      - Limited ability to handle large bursts without dropping requests.

### Criteria for Choosing a Rate Limiting Strategy
When implementing a rate limiter, consider the following criteria:

1. Application Requirements:

- Strictness of Limit: How critical is it to strictly adhere to the defined rate? (e.g., financial transactions vs. social media feeds).
- Tolerance for Bursts: Should clients be allowed to send bursts of requests, or should the rate be strictly smooth?
- Tolerance for Dropped Requests: Is it acceptable to drop requests when limits are exceeded, or should they be queued/retried?
- Consistency Model: Does the application require strong consistency in rate limiting across distributed instances?

2. Traffic Patterns:

- Expected Request Volume: Low, medium, or high throughput?
- Nature of Traffic: Are requests typically steady, or are there frequent bursts?

3. Resource Constraints:

- Memory: How much memory can be allocated for storing state (timestamps, counters)?
- CPU: How much processing power can be dedicated to rate limiting logic?

4. Complexity of Implementation:

- Simpler algorithms are easier to implement and maintain. More complex ones offer higher accuracy or flexibility but come with increased development and operational overhead.

5. Location of Rate Limiter:

- Where in your architecture will the rate limiter reside (API Gateway, microservice, edge)? This influences the choice of tools and algorithms.






### Design a Simple In-Memory Rate Limiter using Fixed Window Algorithm
I have implemented its code in Java using HashMap
you can view the code here -> [Fixed Window Algorithm Implementation](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_14/FixedWindowAlgorithm.java)

### Design a Simple In-Memory Rate Limiter using Sliding Window Algorithm
I have implemented its code in Java using Deque
you can view the code here -> [Sliding Window Algorithm Implementation](https://github.com/HarshitPachori/SystemDesign_Devops__60DaysChallenge/tree/main/Day_14/SlidingWindowAlgorithm.java)

### Fixed Window Counter Algorithm v/s Sliding Window (Sliding Log) Algorithm
1. **Fixed Window Counter Algorithm**
- **Pros**:

   - **Extremely Simple and Efficient**: Very easy to implement and requires minimal memory (just a single counter per client per window) and CPU overhead, making it highly efficient for high-throughput scenarios.
   - **Low Resource Footprint**: Because it only tracks counts for discrete windows, its storage and computational needs are very low, making it performant.
- **Cons**:

   - **"Edge Problem" / Burstiness**: Allows for a potential burst of double the configured rate at the boundary of two windows. For example, if the limit is 100/minute, a client could send 100 requests at 0:59 and another 100 requests at 1:01, totaling 200 requests within a very short span.
   - **Less Accurate**: Does not truly enforce a rate over a rolling time period. It's a static, periodic reset, which can be less effective for strict rate control.

2. **Sliding Window (Sliding Log) Algorithm**
- Pros:

   - **High Accuracy**: Provides a highly accurate rate limit over any continuous, rolling time window. It genuinely ensures that no more than N requests occur within any T second interval.
   - **Prevents Bursts at Edges**: Effectively mitigates the "edge problem" seen in Fixed Window, ensuring a smoother request rate over time.
- Cons:

   - **High Memory Consumption**: Requires storing a timestamp for every request within the window for each client. For high limits or busy clients, this can consume significant memory.
   - **Higher Computational Overhead**: Involves more complex operations (adding, sorting, and pruning timestamps from a data structure like a deque), leading to more CPU usage compared to simple counter increments.
