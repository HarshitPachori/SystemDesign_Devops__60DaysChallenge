---
title: "Day 35: Canary Deployments"
description: "A summary of my 35th day's learning in the 60-day challenge, Canary Deployments"
keywords:
  - Canary Deployments
  - Day 35
  - Challenge
---

### Table of contents :
- [Understanding Canary Deployments](#understanding-canary-deployments)



### Understanding Canary Deployments
Canary deployment is an advanced software release strategy that allows you to gradually roll out a new version of an application to a small subset of users before making it available to the entire user base. This approach is named after the historical practice of using canaries in coal mines to detect toxic gases; if the canary showed signs of distress, it served as an early warning for miners to evacuate.

In software, the "canary" is the new version of your application, and the small group of users exposed to it acts as the early warning system. By monitoring the performance and behavior of this new version in a live production environment with minimal exposure, you can quickly detect issues and mitigate risk.

- **The Conceptual Process**
A canary deployment can be broken down into a series of logical steps, often managed by a deployment pipeline and a load balancer or service mesh.

   - **Initial State**: The current, stable version of your application (Version 1) is running and handling 100% of the live user traffic.

   - **Canary Introduction**: A new version of the application (Version 2) is deployed alongside Version 1. A small portion of user traffic—for example, 1-5%—is then intelligently routed to this new "canary" instance. The vast majority of users (95-99%) continue to use Version 1.

   - **Monitoring and Evaluation**: During this phase, the new version is rigorously monitored. The key is to compare metrics between the canary (Version 2) and the stable version (Version 1). Key metrics to track include:

      - **Application Metrics**: Error rates, latency, response times, and system resource usage (CPU, memory).

      - **Business Metrics**: User engagement, conversion rates, and overall user experience.

   - **Decision Point (Progress or Rollback)**: Based on the monitoring data, a decision is made:

      - **Success**: If the canary performs well, shows no significant errors, and meets all defined criteria, the deployment is considered successful.

      - **Failure**: If the canary shows signs of instability, increased errors, or a negative impact on user experience, the deployment is considered a failure.

   - **Phased Rollout**: If the canary is successful, the traffic to the new version is gradually increased in stages (e.g., 25%, 50%, 75%). Each stage is followed by a period of monitoring and evaluation to ensure continued stability.

   - **Full Rollout or Rollback**:

      - **Full Rollout**: Once the new version is handling 100% of the traffic, and has proven stable, it becomes the new stable version. The old instances of Version 1 can then be decommissioned.

      - **Rollback**: If at any stage a problem is detected, traffic is immediately routed back to the original, stable Version 1. This "rollback" is fast and has a limited impact on the overall user base because only the small percentage of canary users was affected.

- **Advantages and Disadvantages**
   - **Advantages**
      - **Reduced Risk**: The primary benefit is the ability to test a new version in a live production environment without exposing the entire user base to potential bugs or performance issues.

      - **Easy Rollback**: If the new version fails, a rollback is as simple as re-routing the small amount of traffic away from the canary instances. There is no need for a complex, time-consuming redeployment.

      - **Real-world Testing**: Canary deployments provide invaluable feedback by allowing you to observe how a new version behaves under actual production load and with real user interactions. This often uncovers issues that are missed in staging or testing environments.

      - **Performance Validation**: It's an excellent way to validate performance assumptions, test capacity, and see how a new release impacts resource utilization before a full-scale launch.

      - **Seamless User Experience**: Because most users are not affected, the user experience remains stable, which helps to maintain customer trust and satisfaction.

   - **Disadvantages**
      - **Increased Complexity**: This strategy requires more advanced infrastructure to manage traffic splitting and to run two versions of the application concurrently.

      - **Monitoring Requirements**: You must have robust monitoring and alerting systems in place to effectively track and compare the performance of the new and old versions. Without this, the early warning system is ineffective.

      - **Infrastructure Cost**: Running multiple versions of an application in parallel for an extended period can lead to higher infrastructure costs, especially during the phased rollout.

      - **Potential for Inconsistent Experience**: A small percentage of users may have a different, potentially buggy, experience from the rest of the user base.