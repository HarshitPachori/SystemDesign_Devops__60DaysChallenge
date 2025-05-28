---
title: "Day 04: Introduction to EC2 Instance and Deploy Hello World App"
description: "A summary of my 5th day's learning in the 60-day challenge, covering basic cloud concepts , security groups and an overview of types of instances."
keywords:
  - AWS
  - EC2
  - Security Groups
  - Types of Instances
  - Day 2
  - Challenge
---

### Table of contents :
- [What is Instance ?](#what-is-instance-)
- [Types of Instances on AWS](#types-of-instances-on-aws)
- [Reserved v/s On-demand Instances](#reserved-vs-on-demand-instances)
- [What is Spot Instance ?](#what-is-spot-instance-)
- [What is Dedicated Instance ?](#what-is-dedicated-instance-)


#### What is Instance ?
An instance in AWS refers to a virtual server that runs on the Amazon Elastic Compute Cloud (EC2) service. When you "launch an instance," you're essentially creating a virtual machine in AWS's data centers that you can use to run your applications, websites, databases, or any other workload.

### Types of Instances on AWS
AWS offers a wide array of EC2 instance types, each optimized for different workloads by varying combinations of CPU, memory, storage, and networking capacity. They are categorized into several families:


- **General Purpose (M, T, A series)** : Provide a balance of compute, memory, and networking resources. Ideal for most applications like web servers, development environments, and small/medium databases. T-instances are "burstable," great for fluctuating workloads.
- **Compute Optimized (C series)** : Designed for compute-intensive applications requiring high-performance processors. Suitable for scientific modeling, gaming servers, and media transcoding.
- **Memory Optimized (R, X, High-Memory series)** : Best for workloads that process large datasets in memory. Examples include high-performance databases, in-memory caches, and big data analytics.
- **Storage Optimized (I, D, H series)** : Deliver high sequential read/write access to large datasets on local storage. Used for high-transaction NoSQL databases, data warehousing, and distributed file systems.

- **Accelerated Computing (P, G, F, Inf, Trn series)** : Utilize hardware accelerators (GPUs, FPGAs, AWS Inferentia/Trainium) to achieve high performance for tasks like machine learning, graphics rendering, and scientific simulations.

- **High Performance Computing (Hpc series)** : Optimized for demanding HPC workloads that require high-performance networking and tightly coupled clusters.

### Reserved v/s On-demand Instances
In AWS, "instance" refers to a virtual server. The key difference between On-Demand and Reserved Instances lies in their pricing model and commitment:


- **On-Demand Instances** :
    - **What they are** : These are the most flexible EC2 instances. You pay for compute capacity by the hour or second (with a minimum of 60 seconds) with no long-term commitment.
    - **Difference** : They offer immediate availability and allow you to scale up or down as needed without pre-planning. You only pay for what you use, making them ideal for unpredictable workloads, development/testing, or short-term projects. They are generally the most expensive option per hour.

- **Reserved Instances (RIs)** :
    - **What they are** : RIs are not physical instances but a billing discount applied to your On-Demand Instance usage when you commit to a consistent usage for a 1-year or 3-year term.
    - **Difference** : They offer significant cost savings (up to 75% off On-Demand prices) in exchange for a commitment. They are ideal for applications with steady, predictable workloads that run continuously for extended periods (e.g., databases, production servers). While you commit to payment, they provide pricing predictability and can also offer capacity reservation in a specific Availability Zone.

### What is Spot Instance ?
An AWS Spot Instance is an unused EC2 instance that AWS makes available at a significantly reduced price (up to 90% off On-Demand) compared to On-Demand instances. You bid for this spare capacity, and the "Spot Price" fluctuates based on supply and demand. The catch is that AWS can reclaim (terminate or stop) a Spot Instance with a two-minute warning if it needs the capacity back for On-Demand or Reserved Instances.


### What is Dedicated Instance ? 
A Dedicated Instance is an Amazon EC2 instance that runs in your Amazon Virtual Private Cloud (VPC) on hardware that's dedicated to a single AWS customer. This means your instances are physically isolated at the host hardware level from instances that belong to other AWS accounts. While they are still virtualized, the underlying physical server is reserved solely for your account.