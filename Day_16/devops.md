---
title: "Day 16: AWS S3 "
description: "A summary of my 16th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 16
  - Challenge
---

### Table of contents :
- [What is Amazon S3 (Simple Storage Service) ?](#what-is-amazon-s3-simple-storage-service-)
- [Key Characteristics and Benefits of Amazon S3 ](#key-characteristics-and-benefits-of-amazon-s3)
- [Primary Use Cases for Amazon S3](#primary-use-cases-for-amazon-s3)
- [What is an S3 Bucket ?](#what-is-an-s3-bucket-)
- [Steps to Create an S3 Bucket (Using the AWS Management Console)](#steps-to-create-an-s3-bucket-using-the-aws-management-console)

### What is Amazon S3 (Simple Storage Service) ?
Amazon S3 (Simple Storage Service) is a highly scalable, durable, and available object storage service offered by Amazon Web Services (AWS). It's designed to store and retrieve any amount of data from anywhere on the web.

Unlike traditional file systems (which store data in a hierarchical structure of files and folders), S3 stores data as objects within buckets. Each object consists of the data itself (a file, like an image, video, document, or backup) and metadata (information about the object, like size, last modified date, and custom tags).

### Key Characteristics and Benefits of Amazon S3 

- **Scalability**: Offers virtually unlimited storage capacity. You don't need to provision storage in advance; it automatically scales to meet your needs.

- **Durability**: Designed for 99.999999999% (11 nines) of durability of objects over a given year. This is achieved by redundantly storing your data across multiple devices in multiple Availability Zones (AZs) within an AWS Region.

- **Availability**: Provides high availability for your data, ensuring it's accessible when you need it.

- **Security**: Offers robust security features, including encryption at rest and in transit, and fine-grained access control through IAM policies and bucket policies.

- **Performance**: Delivers high throughput and low latency for various workloads.

- **Cost-Effectiveness**: You only pay for the storage you use, with different storage classes optimized for varying access patterns (e.g., frequently accessed, infrequently accessed, archiving).

- **Integration**: Seamlessly integrates with many other AWS services (e.g., EC2, Lambda, CloudFront, Athena, SageMaker).

### Primary Use Cases for Amazon S3

- **Data Lakes & Big Data Analytics**: A common foundation for building data lakes, allowing you to store vast amounts of structured and unstructured data for analysis.

- **Backup and Recovery**: A highly reliable and durable solution for backing up critical data and enabling disaster recovery strategies.

- **Archiving**: Cost-effective long-term storage for data that is rarely accessed, using S3 Glacier storage classes.

- **Static Website Hosting**: S3 can host static websites (HTML, CSS, JavaScript files) directly, providing a highly available and cost-effective hosting solution.

- **Cloud-Native Application Data**: Storage for images, videos, logs, and other content for web, mobile, and enterprise applications.

- **Software and Object Distribution**: Distributing software packages, mobile apps, and other digital assets.

### What is an S3 Bucket ?
An S3 bucket is a fundamental container for objects stored in Amazon S3. You can think of it as a top-level folder or a root directory in a traditional file system, but it's much more. All objects in S3 are stored in buckets.

- **Key characteristics of S3 buckets**:

   - **Globally Unique Name**: Every S3 bucket name must be globally unique across all of AWS, not just within your account. This is because S3 bucket names are part of the URL used to access objects.

   - **Region Specific**: When you create a bucket, you choose an AWS Region for it (e.g., us-east-1, ap-south-1). Objects stored in a bucket never leave that Region unless you explicitly move or replicate them. This helps with latency, costs, and data residency requirements.

   - **Flat Structure (Logically Hierarchical)**: While S3 itself has a flat structure (objects are identified by a unique "key" within the bucket), you can use key prefixes and delimiters (like /) to create a logical hierarchy that appears like folders in the AWS Management Console.

   - **Container for Objects**: Buckets hold your data (objects) and are where you configure various settings like access permissions, versioning, logging, encryption, and lifecycle policies.

   - **Ownership**: Each bucket is owned by the AWS account that created it.

- **Purpose of S3 Buckets**:

   - **Organization**: Provides a way to organize your data logically.

   - **Access Control**: Allows you to define access permissions (who can access what) at the bucket level using Bucket Policies and Access Control Lists (ACLs), as well as at the object level.

   - **Configuration**: Acts as the unit for configuring features like versioning, logging, encryption, and lifecycle management for the objects within it.

   - **Billing**: Your AWS bill is based on the aggregate size and usage of objects within your buckets, along with requests and data transfer.

### Steps to Create an S3 Bucket (Using the AWS Management Console)
Here's a step-by-step guide to creating a new S3 bucket:

1. **Sign in to the AWS Management Console**:

  - Go to https://aws.amazon.com/console/ and sign in with your AWS account credentials.

2. **Navigate to the S3 Service**:

  - In the AWS Management Console, use the "Search services" bar at the top and type "S3". Select "S3 - Scalable Storage in the Cloud" from the results.

  - Alternatively, you can find it under the "Storage" category.

3. **Click "Create bucket"**:

  - On the Amazon S3 console dashboard, you will see a prominent "Create bucket" button. Click it.

4. **Configure Bucket Properties (Step 1: General configuration)**:

  - **AWS Region**: Choose the AWS Region where you want your bucket to be located. Select a Region that is geographically close to your users or applications to minimize latency and data transfer costs, and to meet any data residency requirements. Note: You cannot change a bucket's Region after it's created.

  - **Bucket name**: Enter a globally unique name for your bucket.

       - Names must be between 3 and 63 characters long.

       - Can contain only lowercase letters, numbers, periods (.), and hyphens (-).

       - Must begin and end with a letter or number.

       - Cannot be formatted as an IP address.
 
       - For best compatibility, avoid using periods in bucket names unless it's specifically for static website hosting.

5. **Configure Object Ownership (Step 2: Object Ownership)**:

  - **ACLs enabled (recommended)**: AWS recommends keeping ACLs (Access Control Lists) disabled for most use cases and controlling access primarily through IAM policies and bucket policies. If ACLs are disabled, the bucket owner automatically owns and has full control over all objects. This is the default.

  - You generally keep the default setting (ACLs enabled (recommended)) and then ensure Bucket owner preferred is selected.

6. **Block Public Access settings for this bucket (Step 3: Block Public Access settings)**:

  - **Block all public access (recommended)**: By default, S3 buckets are private. AWS strongly recommends keeping all "Block Public Access" settings enabled to prevent accidental exposure of your data to the internet.

  - **Important**: If you intend to host a static website or allow public reads for specific objects, you will need to disable some of these settings after creating the bucket and then configure a specific bucket policy. For typical data storage, keep them all checked.

7. **Bucket Versioning (Step 4: Bucket Versioning - Optional)**:

  - **Disabled (default)**: By default, versioning is off.

  - **Enable**: If you enable versioning, S3 keeps multiple versions of an object (including all writes and deletes). This is excellent for preventing accidental overwrites or deletions and for data recovery. However, it increases storage costs.

8. **Tags (Optional)**:

  - You can add tags (key-value pairs) to your bucket for cost allocation, organization, and access control.

9. **Default encryption (Step 5: Default encryption)**:

  - **Server-side encryption**: S3 by default encrypts all new objects uploaded to an S3 bucket. You can choose:

      - **Amazon S3 managed keys (SSE-S3)**: AWS manages the encryption keys. This is the default and recommended for most cases.

      - **AWS Key Management Service key (SSE-KMS)**: You use KMS keys that you manage, offering more control over encryption.

10. **Advanced settings (Optional)**:

  - **Object Lock**: Prevents objects from being deleted or overwritten for a fixed amount of time or indefinitely. Useful for regulatory compliance.

  - **Transfer Acceleration**: Speeds up data transfers over long distances.

11. **Review and Create Bucket**:

  - Review all your chosen configurations.

  - Click the "Create bucket" button at the bottom of the page.