---
title: "Day 20: AWS S3 "
description: "A summary of my 20th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 20
  - Challenge
---

### Table of contents :
- [AWS S3 Storage Classes ?](#aws-s3-storage-classes-)
- [How to Configure S3 Storage Classes ?](#how-to-configure-s3-storage-classes-)

### AWS S3 Storage Classes ?
- Amazon S3 offers a range of storage classes designed for different use cases and access patterns. Each class provides a balance of cost, availability, durability, and performance. Choosing the right storage class can significantly optimize your storage costs.

- **Common AWS S3 Storage Classes**:

  - **S3 Standard**:

    - **Purpose**: General-purpose storage for frequently accessed data.
    - **Characteristics**: High durability (11 nines), high availability (99.99%), low latency. Ideal for dynamic websites, content distribution, mobile/gaming apps, big data analytics.
    - **Cost**: Higher cost per GB compared to other classes, but lowest retrieval costs.

  - **S3 Intelligent-Tiering**:

    - **Purpose**: Automatic cost optimization for data with unknown or changing access patterns.
    - **Characteristics**: Automatically moves objects between two or three access tiers (frequent, infrequent, archive access tiers) based on access patterns, optimizing costs without performance impact.
    - **Cost**: Includes a small monitoring and automation fee.

  - **S3 Standard-Infrequent Access (S3 Standard-IA)**:

    - **Purpose**: For data that is accessed less frequently but requires rapid access when needed.
    - **Characteristics**: Same high durability and low latency as S3 Standard, but lower availability (99.9%). Higher retrieval fees than Standard. Ideal for backups, disaster recovery, long-term storage of analytics data.

  - **S3 One Zone-Infrequent Access (S3 One Zone-IA)**:

    - **Purpose**: For infrequently accessed data that does not require the availability or resilience of multiple Availability Zones. Data is stored in a single AZ.
    - **Characteristics**: Lower durability than other classes (data lost if AZ fails), lower cost. Good for easily re-creatable data, secondary backups.

  - **Amazon S3 Glacier Instant Retrieval**:

    - **Purpose**: Long-lived, rarely accessed data that needs immediate retrieval (milliseconds).
    - **Characteristics**: Very low storage cost, but higher retrieval fees. Designed for archives with sub-second retrieval.

  - **Amazon S3 Glacier Flexible Retrieval (formerly S3 Glacier)**:

    - **Purpose**: Archival data that is accessed infrequently, with retrieval options from minutes to hours.
    - **Characteristics**: Extremely low storage cost. Retrieval options include Expedited (1-5 min), Standard (3-5 hours), and Bulk (5-12 hours). Ideal for long-term backups and archives.

  - **Amazon S3 Glacier Deep Archive**:

    - **Purpose**: Long-term archival of data that is accessed once or twice a year, at the lowest cost.
    - **Characteristics**: Lowest storage cost. Retrieval options from 12 hours (Standard) to 48 hours (Bulk). Suitable for regulatory archives, media asset archives.

  - **S3 Express One Zone**:

    - **Purpose**: For data that requires the lowest latency and highest performance for frequently accessed workloads. Stored in a single AZ.
    - **Characteristics**: Single-digit millisecond latency. Higher cost than S3 Standard, but optimized for throughput and speed for specific applications like AI/ML training, high-performance computing.

### How to Configure S3 Storage Classes ?
You can configure the storage class for your objects in two primary ways:

- **During Object Upload**:

    - **AWS Management Console**: When you upload a file to an S3 bucket, the upload wizard will present an option to choose the storage class for that specific object. "Standard" is the default.
    - **AWS CLI**: Use the --storage-class parameter with aws s3 cp or aws s3 sync commands.
      ```Bash

         # Upload a file to S3 Standard-IA
         aws s3 cp mydocument.pdf s3://my-bucket/documents/mydocument.pdf --storage-class STANDARD_IA

          # Upload a file to S3 Glacier Deep Archive
          aws s3 cp old-logs.zip s3://my-archive-bucket/logs/old-logs.zip --storage-class DEEP_ARCHIVE
      ```

    - **AWS SDKs/APIs**: When using AWS SDKs in your applications (e.g., Java, Python, Node.js), you can specify the StorageClass parameter in your PutObjectRequest.

- **Using S3 Lifecycle Policies (for automated tiering and archiving)**:
This is the recommended approach for managing data cost-effectively over its lifecycle. Lifecycle policies automatically transition objects between storage classes (e.g., from Standard to Standard-IA after 30 days, then to Glacier after 90 days) or expire (delete) objects after a defined period.

 - **Steps (AWS Management Console)**:

    1. Navigate to your S3 bucket in the AWS Console.
    2. Go to the "Management" tab.
    3. Click on "Create lifecycle rule".
    4. Name the rule and define its scope (e.g., apply to all objects, or filter by prefix or tags).
    5. Choose "Transition current versions of objects between storage classes" or "Transition previous versions of objects between storage classes" (if versioning is enabled).
    6. Add transitions: Specify which storage class to transition to and after how many days (e.g., transition to Standard-IA after 30 days, then to Glacier Flexible Retrieval after 90 days).
    7. You can also choose to "Expire current versions of objects" or "Permanently delete previous versions of objects" after a set number of days.
    8. Review and "Create rule".