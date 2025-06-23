---
title: "Day 23: AWS Cloudfront"
description: "A summary of my 23th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 23
  - Challenge
---

### Table of contents :
- [What is AWS CloudFront ?](#what-is-aws-cloudfront-)
- [How Does AWS CloudFront Work ?](#how-does-aws-cloudfront-work-)
- [Key Benefits of Using AWS CloudFront](#key-benefits-of-using-aws-cloudfront)
- [Common Use Cases for AWS CloudFront](#common-use-cases-for-aws-cloudfront)


### What is AWS CloudFront ?
- Amazon CloudFront is a fast, highly secure, and programmable Content Delivery Network (CDN) service provided by Amazon Web Services (AWS). Its primary purpose is to speed up the distribution of static and dynamic web content (such as HTML, CSS, JavaScript, images, videos, and APIs) to users globally.


- CloudFront achieves this by caching content at a network of strategically located data centers around the world, known as edge locations or Points of Presence (PoPs).

### How Does AWS CloudFront Work ?
CloudFront's operation revolves around bringing content physically closer to your end-users:

1. **Content Origin**: You first define an "origin" for your content. This is where your original, definitive version of the content lives. Common origins include:

  - **Amazon S3 buckets**: Ideal for static assets like images, videos, or static websites.
  - **HTTP servers**: Any web server, whether on an AWS EC2 instance, an Elastic Load Balancer, or even an on-premises server (known as a "custom origin").
  - **AWS Elemental MediaPackage**: For video streaming workflows.
  - **AWS Lambda Function URLs / API Gateway**: For dynamic content and APIs.

2. **User Request**: When a user requests content (e.g., visits your website, loads an image) that you're serving through CloudFront:

  - The request is first routed via DNS to the nearest CloudFront edge location to the user in terms of latency.

3. **Cache Check**:

  - **If the content is in the edge location's cache**: CloudFront immediately serves the content directly from the cache to the user. This is the fastest delivery path.

4. **Origin Fetch (Cache Miss)**:

  - If the content is NOT in the edge location's cache: CloudFront forwards the request to your designated origin server.
  - The origin server retrieves the content and sends it back to the CloudFront edge location.
  - As soon as the first byte arrives, CloudFront begins forwarding the content to the user, minimizing perceived latency.

5. **Content Caching**:

  - After fetching from the origin, CloudFront also caches a copy of the content at that edge location. This ensures that subsequent requests for the same content from users near that edge location can be served directly from the cache, improving performance for everyone.

6. **Global AWS Network**: CloudFront leverages the highly optimized AWS global network backbone to efficiently route requests and fetch content from origins, minimizing reliance on the public internet for long distances.

### Key Benefits of Using AWS CloudFront
1. **Improved Performance & Low Latency**:

  - **Global Edge Network**: Content is cached physically closer to users, significantly reducing the round-trip time and load times for websites, applications, and streaming media.
  - **Optimized Routing**: Requests are routed over the highly optimized AWS global network backbone, bypassing public internet congestion.

2. **Enhanced Security**:

  - **DDoS Protection**: Integrates with AWS Shield Standard (default) for protection against common network and transport layer DDoS attacks. Can be enhanced with AWS Shield Advanced.
  - **Web Application Firewall (WAF) Integration**: Seamlessly integrates with AWS WAF to protect against common web exploits (e.g., SQL injection, XSS) at the edge.
  - **HTTPS Support**: Supports HTTPS (SSL/TLS) for encrypted communication between viewers and CloudFront, and optionally between CloudFront and your origin.
  - **Access Controls**: Provides features like Signed URLs and Signed Cookies to restrict access to private content, and Origin Access Control (OAC) to prevent direct access to S3 origins, ensuring content is only delivered via CloudFront.
  - **Field-Level Encryption**: Allows encryption of specific data fields at the edge.

3. **Cost Optimization**:

  - **Reduced Origin Load**: By serving cached content, CloudFront reduces the number of requests that hit your origin servers, potentially saving on origin compute and bandwidth costs.
  - **Reduced Data Transfer Out**: Data transfer from AWS origins (like S3, EC2) to CloudFront edge locations is generally not charged, helping optimize overall costs.
  - **Pay-as-you-go**: You only pay for the data transferred and requests made through CloudFront.

4. **Scalability & Reliability**:

  - **Automatic Scaling**: Automatically scales to handle massive traffic spikes without manual intervention.
  - **High Availability**: Distributes content across multiple edge locations, ensuring high availability and resilience to localized failures.
  - **Origin Failover**: Can be configured with primary and secondary origins to automatically switch to a backup origin if the primary becomes unavailable.

5. **Customization at the Edge (Edge Computing)**:

  - **CloudFront Functions**: Lightweight, low-latency JavaScript functions that run at AWS edge locations for simple customizations like URL rewrites, header manipulations, and basic access control.
  - **Lambda@Edge**: Allows running Node.js or Python code at AWS edge locations for more complex logic, such as dynamic content generation, A/B testing, or custom authentication.

### Common Use Cases for AWS CloudFront
- **Static Website Hosting**: Accelerating the delivery of HTML, CSS, JavaScript, and images for static websites (often combined with S3 as an origin).
- **Dynamic Content Delivery**: Improving the performance of dynamic web applications and APIs.
- **Video Streaming**: Delivering live and on-demand video with low buffering and high quality.
- **Software Distribution**: Fast and reliable delivery of software updates, game patches, and IoT device firmware.
- **API Acceleration**: Speeding up API calls by caching API responses or routing requests more efficiently.
- **Security Layer**: Acting as a first line of defense against DDoS attacks and providing web application security with WAF.
