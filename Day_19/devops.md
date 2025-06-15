---
title: "Day 19: AWS S3 "
description: "A summary of my 19th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 19
  - Challenge
---

### Table of contents :
- [What is S3 Replication ?](#what-is-s3-replication-)
- [Why use S3 Replication ?](#why-use-s3-replication-)
- [How to configure S3 Replication ?](#how-to-configure-s3-replication-)
- [AWS S3 Logging](#aws-s3-logging)

### What is S3 Replication ?
- Amazon S3 Replication is a feature that automatically replicates objects between S3 buckets. This can be within the same AWS Region (Same-Region Replication - SRR) or across different AWS Regions (Cross-Region Replication - CRR).

- When you enable replication, S3 replicates newly uploaded objects, object updates (including metadata and access control lists), and object deletions (though this needs specific configuration for delete markers).

### Why use S3 Replication ?
There are several compelling reasons to use S3 Replication:

1. **Disaster Recovery**:

   - **CRR**: By replicating data to a different geographical region, you create a robust disaster recovery strategy. If a disaster affects your primary AWS Region, you can failover to the replicated data in the secondary region, minimizing downtime and data loss.
   - **SRR**: While not for region-wide disasters, SRR can provide a copy of your data in a different bucket within the same region, offering protection against accidental deletions or application-level issues affecting a specific bucket.
2. **Compliance**: Many regulatory requirements mandate data residency in specific geographical locations or require data to be stored in multiple, geographically distinct locations. CRR helps meet these compliance needs.

3. **Reduced Latency**: For applications with users geographically dispersed, CRR allows you to maintain copies of your data closer to your users, reducing latency and improving application performance.

4. **Log Aggregation**: You can replicate logs from multiple source buckets into a single, centralized destination bucket for easier analysis and auditing.

5. **Live Replica for Testing/Development**: Replicate production data to a separate environment for testing, development, or analytics without impacting live operations.

6. **Data Sovereignty**: Ensure that data remains within a specific country or region for legal or regulatory reasons.

### How to configure S3 Replication ?
You configure S3 Replication using a replication rule on your source S3 bucket. Here's a high-level overview of the steps:

- **Enable Versioning**: Both the source and destination buckets must have versioning enabled. Replication works by replicating specific object versions.

- **Create an IAM Role**: S3 needs permissions to replicate objects on your behalf. You'll create an IAM role that grants S3 the necessary permissions (s3:GetObject, s3:ReplicateObject, s3:ReplicateDelete, etc.) on both the source and destination buckets.

- **Configure Replication Rule on the Source Bucket**:

   - Go to the Properties tab of your source S3 bucket in the AWS Management Console.
   - Find the Replication rules section and click Create replication rule.
   - Source bucket: This is your current bucket.
   - Rule Scope: You can choose to apply the rule to:
       - All objects in the bucket: Replicates everything.
       - Prefixes: Replicates objects with specific prefixes (e.g., logs/, images/).
       - Tags: Replicates objects with specific tags.
   - Destination:
       - Destination bucket: Specify the target S3 bucket (can be in the same or a different region, or even a different AWS account).
       - Change object ownership (optional): You can change the owner of the replicated object in the destination bucket.
       - Encryption (optional): You can replicate with different encryption settings.
   - IAM Role: Select the IAM role you created in step 2.
   - Additional replication options (optional):
       - Replicate delete markers: Determines if delete markers are replicated. By default, they are not.
       - Replication Time Control (RTC): Provides predictable replication times with an SLA.
       - Metrics: Monitor replication progress.
   - Review and save the rule.

**Important Considerations**:

- **Existing Objects**: By default, replication rules apply to new objects uploaded after the rule is configured. To replicate existing objects, you'll need to run an S3 Batch Replication job.
- **Costs**: You pay for storage in both buckets, data transfer costs for replication, and potentially for replication features like RTC.
- **Replication Configuration**: Once configured, any new objects uploaded to the source bucket that match the rule's scope will be automatically replicated.


### AWS S3 Logging
AWS S3 provides several ways to monitor and log access to your S3 buckets, helping you understand who is accessing your data, what operations they are performing, and from where.

1. **S3 Server Access Logging**
- **What it is**: S3 server access logging provides detailed records for the requests that are made to your S3 bucket. Each access log record provides details about the request, such as the requestor, bucket name, request time, request action, response status, and error code, if any.

- **Why use it**:

   - **Security Audits**: Track all requests to your S3 buckets for security and compliance purposes.
   - **Access Monitoring**: Understand who is accessing your data and from where.
   - **Troubleshooting**: Diagnose issues with application access to S3.
   - **Billing Analysis**: Get insights into data transfer patterns.

- **How it works**:

   - You enable server access logging on a source bucket.
   - You specify a target bucket where the logs will be delivered. The target bucket can be the same as the source bucket, but it's generally recommended to use a separate log bucket for better organization and to avoid recursive logging.
   - S3 delivers log files to the target bucket periodically (typically within a few hours of the requests).

**Configuration**:

  - In the S3 console, go to the Properties tab of your bucket.
  - Under Server access logging, click Edit.
  - Choose Enable.
  - Select an existing log delivery target bucket or create a new one.
  - You can optionally specify a target prefix (e.g., logs/my-bucket-access/).
  - Save changes.

**Important Notes**:

  - Log delivery is "best effort," meaning most requests are delivered, but occasional logs might be lost or delayed.
  - Logs are stored as objects in the target bucket and can be analyzed using tools like Amazon Athena, Amazon CloudWatch Logs Insights, or third-party log analysis tools.

2. **AWS CloudTrail**
- **What it is**: AWS CloudTrail is an AWS service that enables governance, compliance, operational auditing, and risk auditing of your AWS account. It records actions taken by a user, role, or an AWS service in S3 (and other AWS services) as events.

- **Why use it**:

  - **Detailed Event History**: Provides a comprehensive history of API calls made to S3 (both console actions and programmatic access).
  - **Security Analysis**: Identify unauthorized access or suspicious activity.
  - **Compliance**: Meet regulatory requirements for auditing and logging.
  - **Operational Troubleshooting**: Pinpoint the cause of issues related to S3 operations.

- **How it works**:

1. CloudTrail captures a wide range of S3 API calls (e.g., GetObject, PutObject, DeleteObject, CreateBucket, DeleteBucket).
2. These events are delivered to an S3 bucket you specify and can also be sent to Amazon CloudWatch Logs for real-time monitoring and alerting.

**Configuration**:

- **Data Events**: To log object-level operations (GetObject, PutObject, DeleteObject), you must explicitly enable "Data Events" for your S3 buckets in CloudTrail. This is done when configuring a CloudTrail trail.
- **Management Events**: Management events (e.g., CreateBucket, DeleteBucket, PutBucketPolicy) are logged by default with basic CloudTrail configurations.

3. **Amazon CloudWatch Metrics for S3**
- **What it is**: Amazon S3 publishes a variety of metrics to Amazon CloudWatch, providing insights into the performance and usage of your S3 buckets.

- **Why use it**:

  - **Performance Monitoring**: Monitor request counts, latency, and error rates.
  - **Capacity Planning**: Track storage usage.
  - **Alerting**: Set up alarms based on metrics (e.g., high error rates, low available storage).

- **How it works**:

  - S3 automatically sends metrics to CloudWatch.
  - You can view these metrics in the CloudWatch console.

**Common Metrics**:

- BucketSizeBytes: The amount of data stored in a bucket.
- NumberOfObjects: The total number of objects stored in a bucket.
- AllRequests: The total number of requests to the bucket.
- GetRequests, PutRequests, DeleteRequests: Specific request types.
- 4xxErrors, 5xxErrors: Client-side and server-side errors.
- FirstByteLatency, TotalRequestLatency: Latency of requests.

