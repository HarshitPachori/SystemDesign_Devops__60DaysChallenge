---
title: "Day 38: Security"
description: "A summary of my 38th day's learning in the 60-day challenge, Security"
keywords:
  - Security
  - Day 38
  - Challenge
---

### Table of contents :
- [Authentication vs. Authorization (AuthN/AuthZ) 🔒](#authentication-vs-authorization-authnauthz-)
- [DDoS Protection 🛡️](#ddos-protection-️)
- [Data Encryption 🔐](#data-encryption-)



### Authentication vs. Authorization (AuthN/AuthZ) 🔒
While often used together, Authentication and Authorization are distinct concepts that form the basis of access control. Think of a club:

- **Authentication (AuthN)** is the process of verifying a person's identity. It answers the question, "Who are you?" In the club, this is showing your ID to the bouncer to prove you are the person on your ticket.


- **Authorization (AuthZ)** is the process of determining what an authenticated person is allowed to do. It answers the question, "What are you allowed to do?" Once inside the club, this is your wristband that grants you access to certain VIP areas but not others.


In system design, authentication always precedes authorization. A user must first prove their identity before the system can check their permissions.

**Common Implementation Patterns**
1. **Session-Based Authentication**:

 - **How it works**: When a user logs in, the server creates a unique session ID and stores it in a server-side database (e.g., Redis, a SQL database). This session ID is then sent back to the client, usually in a cookie. For every subsequent request, the client sends this session ID, and the server looks it up to verify the user's identity and retrieve their permissions.



 - **Pros**: Simple to implement, and sessions can be easily revoked in real-time.

 - **Cons**: Not scalable for microservices or distributed systems, as it requires a centralized session store that can become a bottleneck. It is a stateful approach.

2. **Token-Based Authentication (e.g., JWT)**:

   - **How it works**: When a user logs in, the server generates a JSON Web Token (JWT) that contains user identity and permissions data in its payload. The server signs this token with a secret key. The token is sent back to the client, which stores it (e.g., in local storage or a secure cookie) and sends it with every request in the Authorization header. The server can then verify the token's signature without a database lookup.



   - **Pros**: Stateless and highly scalable, as the server doesn't need to store session information. It works well in distributed systems and microservices.

   - **Cons**: Once issued, a JWT is valid until it expires and cannot be revoked instantly. This makes real-time permission changes challenging and requires careful management of short expiration times and refresh tokens.

### DDoS Protection 🛡️
A Distributed Denial of Service (DDoS) attack aims to overwhelm a system's resources with a flood of malicious traffic, making it unavailable to legitimate users. Protecting a system requires a multi-layered defense strategy.


1. **Edge Network Protection (Layer 3/4)**:

   - The first line of defense is at the network edge, where volumetric attacks (attacks that try to overwhelm bandwidth) are filtered.

   - **Traffic Scrubbing**: Malicious traffic is diverted to a "scrubbing center" where it's analyzed and filtered. Only clean traffic is forwarded to your services.

   - **Anycast Network**: Services like Cloudflare or AWS Shield use an Anycast network to distribute traffic across many data centers globally, effectively absorbing the attack volume by spreading it out.

2. **Application Layer Protection (Layer 7)**:

   - These attacks are harder to detect as they mimic legitimate user behavior (e.g., repeated login requests, excessive API calls).

   - **Web Application Firewall (WAF)**: A WAF sits in front of your web application, inspecting HTTP requests and blocking malicious traffic based on predefined rules. It can protect against common attacks like SQL injection and cross-site scripting (XSS).

   - **Rate Limiting**: This simple but effective technique limits the number of requests a single IP address or user can make in a given time frame.

   - **Bot Mitigation**: Using tools and services to identify and block automated bot traffic from reaching your application.

3. **Cloud-Based DDoS Services**:

   - Most major cloud providers offer built-in or managed DDoS protection services (e.g., AWS Shield, Azure DDoS Protection, Google Cloud Armor). These services leverage the massive scale of the cloud provider's network to automatically detect and mitigate attacks, often before they even reach your application.


### Data Encryption 🔐
Data encryption is the process of encoding data so that it can only be read by authorized parties. It's a critical component of any security strategy, and it's essential to protect data in its two primary states.

1. **Encryption at Rest (EaR)**:

   - **What it is**: Protecting data that is stored in databases, on disks, or in object storage (e.g., S3).

   - **Why it's needed**: Prevents unauthorized access if the physical storage medium is stolen or if an attacker gains access to the underlying storage system. The data is unreadable without the decryption key.

   - **How it's done**: Most cloud providers offer built-in encryption options. For example, AWS offers three primary methods for S3:

      - **SSE-S3**: AWS manages the encryption and keys entirely.

      - **SSE-KMS**: You use AWS Key Management Service (KMS) to manage your own keys, giving you more control and an audit trail.

      - **SSE-C**: You provide your own keys to AWS for encryption/decryption.

2. **Encryption in Transit (EIT)**:

   - **What it is**: Protecting data while it is moving across a network, from one point to another (e.g., between a user's browser and a server, or between microservices).

   - **Why it's needed**: Prevents "man-in-the-middle" attacks where an attacker intercepts communication and steals or alters the data.

   - **How it's done**:

      - **TLS/SSL**: This is the most common method, used to secure communication over HTTPS. TLS (Transport Layer Security) encrypts the data as it travels between the client and the server.

      - **VPNs**: For private network communication (e.g., connecting an on-premises data center to a cloud VPC), a VPN tunnel encrypts all traffic.

      - **Client-Side Encryption**: In highly sensitive cases, data can be encrypted on the client's device before being sent, ensuring only the intended recipient can decrypt it.