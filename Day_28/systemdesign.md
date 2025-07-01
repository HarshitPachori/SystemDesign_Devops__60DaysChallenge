---
title: "Day 28: Observability - The Three Pillars "
description: "A summary of my 28th day's learning in the 60-day challenge, Observability - The Three Pillars"
keywords:
  - Observability - The Three Pillars
  - Day 28
  - Challenge
---

### Table of contents :
- [Introduction](#introduction)
- [Observability: The Three Pillars](#observability-the-three-pillars)
- [How the Three Pillars Work Together](#how-the-three-pillars-work-together)




### Introduction 
Observability is a crucial concept, especially in complex, distributed systems like microservices, and it's built upon three foundational pillars: Metrics, Logging, and Tracing.

Before diving into the pillars, let's briefly clarify Observability vs. Monitoring:

- **Monitoring**: Focuses on known unknowns. You define what you want to measure (e.g., CPU usage, error rates) and set alerts based on predefined thresholds. It tells you if something is wrong.

- **Observability**: Focuses on unknown unknowns. It's the ability to infer the internal state of a system by examining its external outputs (telemetry data). It helps you understand why something is wrong, even for issues you didn't anticipate. Observability is about asking arbitrary questions about your system and getting answers from the data it emits.

Now, let's explore the three pillars:

### Observability: The Three Pillars
The three pillars of observability are Metrics, Logging, and Tracing. Each provides a unique lens into your system's behavior, and when used together, they offer a comprehensive view for diagnosing issues, optimizing performance, and ensuring reliability.

1. **Metrics**
- **What they are**: Metrics are numerical measurements captured over time. They are aggregations of data points that represent the health, performance, and behavior of your systems and applications. Metrics are quantitative values.

- **Purpose**:

   - **Quantitative Insights**: Tell you "what" is happening (e.g., "CPU utilization is 80%", "response time is 500ms", "error rate is 5%").

   - **System Health**: Provide a high-level overview of system health and performance trends.

   - **Alerting & Dashboards**: Ideal for setting up alerts (e.g., "alert if CPU > 90%") and creating dashboards for real-time monitoring.

   - **Capacity Planning**: Track resource utilization over time to predict future needs.

- **Characteristics**:

   - **Aggregatable**: Designed to be aggregated over time and across multiple instances (e.g., average CPU across all web servers).

   - **Low Cardinality**: Typically have a limited set of labels/dimensions, making them efficient to store and query.

   - **Time-series data**: Stored as a series of data points associated with a timestamp.

- **Examples**:

   - **System Metrics**: CPU utilization, memory usage, disk I/O, network throughput.

   - **Application Metrics**: Request rate, error rate, response latency, queue size, active users.

   - **Business Metrics**: Number of successful orders, conversion rate, revenue per hour.

- **Tools**: Prometheus, Grafana (for visualization), AWS CloudWatch, Azure Monitor, Google Cloud Monitoring.

2. **Logging**
- **What they are**: Logs are discrete, timestamped records of events that occur within a system. They provide a detailed, granular account of what the system is doing at any given moment.

- **Purpose**:

   - **Detailed Context**: Tell you "why" something happened (e.g., "User 'X' attempted to log in and failed due to invalid password at Y time from Z IP address").

   - **Debugging & Troubleshooting**: Crucial for pinpointing the exact cause of an error or unexpected behavior. You can search for specific error messages, user IDs, or transaction IDs.

   - **Auditing & Compliance**: Provide an immutable record of system activities, essential for security investigations and regulatory compliance.

- **Characteristics**:

   - **Event-based**: Each log entry represents a specific event.

   - **High Cardinality**: Can contain a wide variety of information, including free-form text, making them less suitable for direct aggregation than metrics.

   - **Structured vs. Unstructured**: Can be plain text (unstructured) or structured (e.g., JSON), with structured logs being much easier to parse and query programmatically.

- **Examples**:

   - Error messages, warnings, informational messages.

   - User login/logout events.

   - Database query execution.

   - API request/response details.

- **Tools**: Logstash, Fluentd (for collection/processing), Elasticsearch, Splunk, Loki, AWS CloudWatch Logs, Azure Monitor Logs.

3. **Tracing (Distributed Tracing)**
- **What they are**: Traces (or distributed traces) represent the end-to-end journey of a single request or transaction as it flows through multiple services in a distributed system. A trace is composed of multiple "spans."

   - **Span**: A single operation within a trace (e.g., an API call, a database query, a function execution). Each span has a name, start/end time, and metadata.

   - **Trace ID**: A unique identifier that links all spans belonging to the same request.

- **Purpose**:

   - **End-to-End Visibility**: Tell you "where" a request spent its time and "which services" it interacted with (e.g., "a user request hit the API Gateway, then Service A, then Service B, which called a database, and Service B then failed").

   - **Performance Bottleneck Identification**: Visually identify latency hotspots across service boundaries.

   - **Dependency Analysis**: Understand the complex call graph and dependencies between microservices.

   - **Root Cause Analysis in Distributed Systems**: When an error occurs, tracing helps you quickly pinpoint which service or component failed and how that failure propagated.

- **Characteristics**:

   - **Contextual**: Provides context across service boundaries.

   - **Causal Relationship**: Shows the parent-child relationships between operations.

   - **High Volume**: Can generate a significant amount of data, often requiring sampling in production.

- **Examples**:

   - A user clicking a button that triggers multiple microservice calls.

   - An order processing workflow involving inventory, payment, and shipping services.

- **Tools**: Jaeger, Zipkin, AWS X-Ray, Google Cloud Trace, OpenTelemetry (standard for instrumentation).

### How the Three Pillars Work Together
The true power of observability comes from combining these three pillars:

- **Metrics** can tell you that something is wrong (e.g., "The error rate on the checkout service spiked").

- **Traces** can then help you identify which specific request failed and where in the distributed system the failure occurred (e.g., "This particular checkout request failed when Service B called the Payment Gateway").

- **Logs** from the relevant service(s) and timeframes identified by the trace can then provide the detailed context and error messages needed to understand why the failure happened (e.g., "The log from Service B shows 'Payment Gateway returned 401 Unauthorized'").

