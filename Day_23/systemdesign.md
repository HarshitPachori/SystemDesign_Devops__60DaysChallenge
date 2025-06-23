---
title: "Day 23: Twitter Feed"
description: "A summary of my 23th day's learning in the 60-day challenge, designing a Twitter Feed"
keywords:
  - Twitter Feed
  - Day 23
  - Challenge
---

### Table of contents :
- [System Design: Twitter Feed](#system-design-twitter-feed)



### System Design: Twitter Feed
A Twitter feed system needs to handle massive scale, low latency, and real-time updates. It's primarily a read-heavy system, meaning users consume far more tweets than they publish.


- **Objective**: To design a highly scalable and available system capable of allowing users to publish short messages (tweets) and consume personalized timelines from the users they follow.

1. **Functional Requirements**
- **Tweet Posting**: Users can compose and publish new tweets (text, images, videos, GIFs).
- **Following/Unfollowing**: Users can follow other users to see their tweets in their timeline.
- **Timeline Generation**: Display a personalized feed (Home Timeline) of tweets from followed users, typically in reverse chronological order or based on relevance (algorithmic feed).
- **User Profiles**: View tweets published by a specific user (User Timeline).
- **Media Support**: Tweets can include rich media.
- **Likes/Retweets/Replies**: Users can interact with tweets.
- **Search**: Search for tweets, hashtags, and users.

2. **Non-Functional Requirements**
- **High Availability**: The system must be available almost all the time (e.g., 99.999% for reads, 99.9% for writes).
- **Low Latency**:
   - **Tweet Publishing**: Should be very fast (e.g., < 200ms).
   - **Timeline Fetching**: Feeds should load quickly (e.g., < 500ms for initial load, near real-time for new tweets).
- **Scalability**:
   - **Massive Reads**: Handle billions of timeline reads per day.
   - **High Writes**: Ingest millions of new tweets per second.
   - **Concurrent Users**: Support millions of concurrent active users.
- **Durability**: Stored tweets and media should be highly durable (no data loss).
- **Consistency**: Eventual consistency is generally acceptable for timelines (a slight delay in seeing a new tweet is okay). Strong consistency might be needed for user critical data (e.g., follower counts).
- **Reliability**: The system should tolerate failures gracefully.

3. **High-Level Components and Data Flow**
The Twitter feed system is typically built using a Microservices Architecture, heavily relying on Event-Driven Patterns and extensive Caching.

**Core Services / Components**:
1. **Client Applications**:

   - Web (React/Vue/Angular), Mobile (iOS/Android) apps.
   - Interact with the backend via REST APIs and potentially WebSockets for real-time updates.

2. **API Gateway / Load Balancers**:

   - **Purpose**: Entry point for all client requests. Routes requests to appropriate microservices. Handles SSL termination, rate limiting, and basic authentication.
   - **Technologies**: Nginx, AWS API Gateway, Azure API Management.

3. **User Service**:

   - **Responsibility**: Manages user profiles (registration, login, profile updates), authentication, and user relationships (who follows whom).
   - **Database**: Typically a relational database (e.g., MySQL, PostgreSQL) for user data and a graph database (e.g., Neo4j, custom graph store like Twitter's FlockDB) or a NoSQL database (e.g., Cassandra/DynamoDB) for follower/following relationships due to the graph-like nature of the data.

4. **Tweet Service**:

   - **Responsibility**: Handles posting new tweets, storing tweet content, managing media attachments, and retrieving individual tweets by ID.
   - **Database**: A highly scalable NoSQL database (e.g., Apache Cassandra, HBase, or a custom distributed key-value store) for storing the massive volume of tweets.
   - **Media Storage**: Object Storage like AWS S3 for images, videos, GIFs.

5. **Fanout Service (Critical for Timelines)**:

   - **Purpose**: Distributes tweets to followers' timelines. This is where the choice of "Fan-out on Write" vs. "Fan-out on Read" comes into play.
   - **Fan-out on Write (Push Model)**:
      - **Mechanism**: When a user tweets, the tweet is immediately "pushed" into the individual inboxes/timelines of all their followers.
      - **Pros**: Very fast read times for timelines (feeds are pre-computed).
      - **Cons**: High write amplification (one tweet can generate millions of writes for a celebrity's followers), inefficient for inactive followers.
      - **Use for**: Most common users (e.g., < 10k followers). Involves caching layers (e.g., Redis, Memcached) to store pre-generated timelines.
   - **Fan-out on Read (Pull Model)**:
      - **Mechanism**: When a user requests their timeline, the system "pulls" tweets from all the users they follow and aggregates them on the fly.
      - **Pros**: Low write amplification (tweet stored once), efficient for users with many inactive followers.
      - **Cons**: Higher read latency (timeline needs to be computed on demand), can be slow if a user follows many active users.
      - **Use for**: Users with a massive number of followers (celebrities). These "celebrity" tweets are fetched on demand when a follower loads their timeline and merged with the pre-computed feed.
   - **Hybrid Approach (Common)**: Use fan-out on write for most users and fan-out on read for high-follower accounts.

6. **Timeline Service**:

   - **Responsibility**: Aggregates and serves the personalized timeline to users. Interacts with the Fanout Service/cache and potentially the Tweet Service for real-time data.
   - **Caching**: Heavily relies on distributed caches (e.g., Redis Cluster, Memcached) to store pre-computed timelines (for fan-out on write) or frequently accessed tweets.

7. **Search Service**:

   - **Responsibility**: Indexes tweet content, hashtags, and user data for fast full-text search.
   - **Technologies**: Apache Solr, Elasticsearch (Managed AWS OpenSearch Service).
   - **Data Flow**: Tweet Service publishes events (e.g., via Kafka) for new/updated tweets, which the Search Service consumes to update its index.

8. **Notification Service**:

   - **Responsibility**: Handles notifications for likes, retweets, replies, new followers.
   - **Technologies**: Message queues (e.g., Apache Kafka, RabbitMQ, AWS SQS/SNS) and push notification services (e.g., AWS SNS for mobile push).

9. **Event / Message Queue**:

   - **Purpose**: Decouples services, handles asynchronous processing, and buffers high write volumes. When a tweet is posted, an event is sent to the queue, and various services (Fanout, Search, Notification) consume it independently.
   - **Technologies**: Apache Kafka, AWS Kinesis, AWS SQS/SNS.

10. **Monitoring & Logging**:

   - **Purpose**: Collect metrics (CloudWatch), logs (CloudWatch Logs, ELK stack), and traces (Zipkin, Jaeger) for system health, performance analysis, debugging, and auditing (CloudTrail).

**Data Flow (Simplified)**:

- **Posting a Tweet**:

1. Client sends POST /tweet request to API Gateway.
2. API Gateway forwards to Tweet Service.
3. Tweet Service validates, stores tweet content in Tweet Database (e.g., Cassandra).
4. If media is attached, media is uploaded to S3; Tweet Service stores S3 URL.
5. Tweet Service publishes "Tweet Created" event to Message Queue (e.g., Kafka).
6. **Fanout Service consumes event**:
   - **For most followers**: pushes tweet ID to follower's timeline cache (e.g., Redis list/sorted set).
   - **For celebrity followers**: stores tweet ID in a separate "celebrity timeline" for on-demand read.
7. **Search Service consumes event**: indexes tweet content into Search Index.
8. **Notification Service consumes event**: sends notifications to relevant users.
9. Tweet Service returns success response to Client.

- **Fetching Home Timeline**:

1. Client sends GET /timeline request (with user ID) to API Gateway.
API Gateway forwards to Timeline Service.
2. Timeline Service retrieves tweet IDs from the user's pre-computed timeline in Cache (e.g., Redis).
3. If user follows celebrities, it might also fetch latest tweets from "celebrity timelines."
4. Timeline Service sends retrieved tweet IDs to a Tweet Hydration Service (can be part of Tweet Service or separate).
5. Tweet Hydration Service fetches full tweet content (text, media URLs) from Tweet Database and S3 (for media).
6. Timeline Service applies ranking (if algorithmic feed) and returns sorted, hydrated tweets to Client.
7. CDN serves media files efficiently to the client.

