---
title: "Day 06: Introduction to EC2 Instance and Elastic Block Storage"
description: "A summary of my 6th day's learning in the 60-day challenge, covering basic cloud concepts , storage and an overview of elastic block storage."
keywords:
  - AWS
  - EC2
  - Static/Elastic IP
  - Elastic Block Storage (EBS)
  - Day 2
  - Challenge
---

### Table of contents :
- [What is Instance Metadata ?](#what-is-instance-metadata-)
- [Instance Metadata and User Data](#instance-metadata-and-user-data)
- [Static IP](#static-ip)
- [Elastic IP](#elastic-ip)
- [What is Elastic Block Storage (EBS) ?](#what-is-elastic-block-storage-ebs-)
- [Use cases of EBS](#use-cases-of-ebs)
- [what is Instance Store ?](#what-is-instance-store-)
- [Use cases of Instance Store ](#use-cases-of-instance-store)


#### What is Instance Metadata ?
In cloud computing, instance metadata refers to data about a running virtual machine (VM) instance that the instance itself can access. It's essentially a set of information that helps the VM understand its own environment and can be used to configure or manage the instance.

### Instance Metadata and User Data

- Instance metadata and user data are ways to configure and manage your cloud instances, but they serve different purposes. They can both be accessed from within the instance itself, allowing the instance to "know" about itself and its environment.

- User Data is custom data that you provide when you launch an instance. It can be in the form of scripts or configuration files. User data is used to perform automated configuration tasks and can even run scripts after the instance starts.

### Static IP
- In a traditional networking sense, a static IP address is an IP address that is manually configured and remains constant for a device. It doesn't change unless you manually reconfigure it.
- In the context of cloud computing (like AWS's default public IPs), the term "static IP" can sometimes be used loosely to refer to an IP that is assigned to an instance upon launch. However, these "default public IPs" in the cloud are often dynamic in nature, meaning they change when the instance is stopped and restarted. This is a crucial distinction from a true static IP.

### Elastic IP
An Elastic IP address (EIP) is a concept primarily used in cloud environments (most famously in AWS, but similar concepts exist in Azure as Public IP and Google Cloud as Static External IP). It's a static, public IPv4 address designed for dynamic cloud computing.



### What is Elastic Block Storage (EBS) ?
- In Amazon Web Services (AWS), when you launch an EC2 (Elastic Compute Cloud) instance, you have two primary options for block-level storage: Elastic Block Storage (EBS) and Instance Store. These differ significantly in terms of persistence, performance, and use cases.

- Amazon EBS provides persistent block-level storage volumes for use with EC2 instances. "Block-level" means it works like a raw, unformatted hard drive or SSD that you can attach to an instance. You can create a file system on top of it, run a database, or use it in any way you would use a local hard drive.

- Key Characteristics:

   - **Persistence** : This is the most crucial characteristic. Data on an EBS volume persists independently from the life of the instance. This means if you stop, hibernate, or terminate your EC2 instance, the data on the attached EBS volume remains intact. You can even detach an EBS volume from one instance and attach it to another (within the same Availability Zone).


   - **Network-Attached Storage** : EBS volumes are not physically on the same host as your EC2 instance. They are network-attached storage, meaning there's a slight network latency involved. However, AWS is highly optimized for this, and many EBS volume types offer very high performance.


   - **Durability and Availability** : EBS volumes are designed for high durability. Data stored on EBS volumes is automatically replicated within its Availability Zone to protect against component failure. AWS offers various volume types with different durability percentages (e.g., io2 Block Express volumes provide 99.999% durability).


  - **Snapshots** : You can create point-in-time snapshots of EBS volumes, which are stored in Amazon S3. These snapshots are incremental (only changed blocks are saved), cost-effective, and can be used for backup, disaster recovery, or to create new volumes/instances.

  - **Scalability** : EBS volumes can be dynamically resized (increased in capacity) and their performance characteristics (IOPS, throughput) can be modified without downtime.

  - **Encryption** : EBS volumes support encryption at rest and in transit, integrating with AWS Key Management Service (KMS).

  - **Volume Types** : EBS offers various volume types optimized for different performance and cost needs:
       - **SSD-backed volumes (for transactional workloads)**:
           - **gp2/gp3 (General Purpose SSD)**: Balance of price and performance for most workloads (boot volumes, dev/test). gp3 offers more flexibility in separately provisioning IOPS and throughput.
           - **io1/io2/io2 Block Express (Provisioned IOPS SSD)** : Designed for high-performance, I/O-intensive, mission-critical applications and databases (e.g., large relational databases, NoSQL databases). io2 Block Express offers the highest performance.
       - **HDD-backed volumes (for throughput-intensive workloads)** : 
           - **st1 (Throughput Optimized HDD)** : For large sequential workloads like big data, log processing, data warehousing.
           - **sc1 (Cold HDD)** : Lowest cost option for less frequently accessed workloads where throughput performance is less critical

### Use cases of EBS 
- Root volumes for most EC2 instances (most common).
- Databases (relational and NoSQL).
- File systems (e.g., for web servers, application data).
- Boot volumes.
- Applications requiring persistent, highly available storage 

### what is Instance Store ?
- Instance store provides temporary block-level storage that is physically attached to the host machine where your EC2 instance runs. It's often referred to as "ephemeral storage" because its data does not persist beyond the life of the instance
- Key Characteristics:

   - **Temporary/Ephemeral** : Data stored on an instance store volume is lost under several conditions:
        - When the instance is stopped.
        - When the instance is terminated.
        - When the underlying disk drive fails.
        - When the instance hibernates.
        - Data does persist if the instance reboots (e.g., due to an operating system restart, not an AWS-initiated stop/start).
   - **Physical Attachment** : Instance store volumes are physically located on the same server that hosts your EC2 instance. This physical proximity is what gives them their performance advantage.
   - **High Performance** : Instance store volumes, especially those backed by NVMe SSDs (available on many modern instance types), offer very high random I/O performance and extremely low latency because the data path is direct and local.
   - **Instance Type Dependent** : Not all EC2 instance types offer instance store volumes. The size, type (SSD/HDD), and number of instance store volumes available are determined by the specific EC2 instance type you choose (often indicated by a 'd' in the instance type name, e.g., r5d.large).

   - **Included in Instance Cost** : The cost of instance store volumes is included in the price of the EC2 instance; there's no separate charge for the storage itself

### Use cases of Instance Store 
- **Temporary data**: Caches, buffers, scratch data, temporary files, transcoding operations.
- **Distributed workloads**: Data that is replicated across multiple instances (e.g., a load-balanced pool of web servers where data is redundant). If one instance fails, the data is not critical because other instances have a copy.
- **High-performance temporary storage**: Applications that require extremely low latency and high I/O throughput for data that doesn't need to persist (e.g., some big data analytics processing).
