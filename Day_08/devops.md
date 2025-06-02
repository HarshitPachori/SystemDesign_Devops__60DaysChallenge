---
title: "Day 08: EBS Deep Dive"
description: "A summary of my 7th day's learning in the 60-day challenge, covering basic cloud concepts , storage and an overview of elastic block storage. Types of EBS Storage."
keywords:
  - AWS
  - EC2
  - Elastic Block Storage (EBS)
  - Types of EBS
  - Day 8
  - Challenge
---

### Table of contents :
- [Steps to Attach an EBS Volume to an EC2 Instance](#steps-to-attach-an-ebs-volume-to-an-ec2-instance)
- [Steps to Detach an EBS Volume from an EC2 Instance and Attach it to Another](#steps-to-detach-an-ebs-volume-from-an-ec2-instance-and-attach-it-to-another)
- [Types of EBS Storage](#types-of-ebs-storage)
- [Which EBS Volume Type Should I Use?](#which-ebs-volume-type-should-i-use)


### Steps to Attach an EBS Volume to an EC2 Instance
- Before you start, ensure your EBS volume and your EC2 instance are in the same Availability Zone (AZ). If they are in different AZs, you won't be able to attach them.

- **Go to the EC2 Dashboard**:

   - Log in to your AWS Management Console.
   - Navigate to the EC2 Dashboard.

- **Locate the EBS Volume**:

   - In the left-hand navigation pane, under "Elastic Block Store," click on "Volumes."
   - Find the EBS volume you want to attach. Ensure its "State" is available. If it's in-use, it's already attached to an instance.
- **Initiate Attachment**:

   - Select the volume.
   - Click on "Actions" (top right) and choose "Attach volume."
- **Specify Instance and Device Name**:

   - Instance: Start typing the ID of the EC2 instance or select it from the dropdown list. Only instances in the same AZ as the volume will appear.
- **Device name**:
   - For Linux instances, AWS recommends device names like /dev/sdf, /dev/sdg, etc. (or /dev/xvdf, /dev/xvdg on some AMIs). The system will usually suggest one in the "Recommended for data volumes" section.
   - For Windows instances, device names like xvdf, xvdg are common.
   - Important: If this is a root volume (less common for attaching an existing volume, more for launching from an AMI), it might be /dev/sda1 or /dev/xvda. For data volumes, pick an available device name.
- **Attach Volume**:

   - Click "Attach volume."
   - The volume's state will change from available to in-use.
- **Make Volume Available Inside the EC2 Instance (Crucial!)**:

   - SSH into your EC2 instance.
   - List disks: Use lsblk (Linux) or Disk Management (Windows) to see the newly attached device. It will appear with the device name you specified (e.g., /dev/xvdf).
   - Check for existing filesystem: If it's a brand new volume, it won't have a filesystem. If it's an existing volume with data, it will.
       - sudo file -s /dev/xvdf (replace /dev/xvdf with your device name). If it says "data," it's probably empty. If it shows a filesystem type (e.g., "ext4"), it has one.
   - **Create a filesystem (if new volume)**:
       - sudo mkfs -t ext4 /dev/xvdf (or xfs). Be extremely careful! - This will erase all data if the volume is not new.
   - **Create a mount point**:
       - sudo mkdir /data (or any directory you prefer).
   - **Mount the volume**:
       - sudo mount /dev/xvdf /data (replace /dev/xvdf and /data).
   - **Verify**:
       - df -h will show the mounted volume and its available space.

### Steps to Detach an EBS Volume from an EC2 Instance and Attach it to Another
- This is a common process for moving data or troubleshooting.

- **Prepare the Source EC2 Instance (Unmount Safely)**:

   - **SSH into the source EC2 instance**.
   - **Unmount the volume**: Before detaching from the AWS console, you must unmount the filesystem from within the operating system to prevent data corruption.
        - sudo umount /data (replace /data with your mount point).
        - If it says "device is busy," ensure no processes are actively using files on that volume. You might need to stop applications or cd out of that directory.
   - **(For Windows)**: Take the disk offline using Disk Management.
- **Detach from Source EC2 Instance (AWS Console)**:

   - Go to the EC2 Dashboard in the AWS Management Console.
   - In the left-hand navigation pane, under "Elastic Block Store," click on "Volumes."
   - Select the volume you want to detach. Ensure its "State" is in-use.
   - Click on "Actions" and choose "Detach volume."
   - Confirm the detachment.
   - The volume's state will change from in-use back to available.
- **Attach to the Target EC2 Instance**:

   - Ensure the target EC2 instance is in the same Availability Zone as the now available EBS volume.
   - Follow steps 3-6 from the "Steps to Attach an EBS Volume" section above, selecting your target EC2 instance.

### Types of EBS Storage
AWS offers various EBS volume types, each optimized for different performance characteristics and cost. They fall into two main categories:

- **SSD-backed volumes (Solid State Drive)**: Optimized for transactional workloads (frequent small random read/write operations) where IOPS (Input/Output Operations Per Second) is the dominant performance attribute.

    - **General Purpose SSD (gp3)**:
        - Default recommendation for most workloads.
        - Balances price and performance for a wide variety of transactional workloads.
        - Offers a consistent baseline performance (3,000 IOPS and 125 MB/s throughput included, regardless of size).
        - You can provision additional IOPS and throughput independently of storage size, giving great flexibility and cost control.
        - **Use Cases**: Boot volumes, development/test environments, medium-sized databases, virtual desktops, low-latency interactive applications.
    - **General Purpose SSD (gp2)**:
        - Older generation of General Purpose SSD. gp3 is generally preferred now as it's often more cost-effective and offers independent performance scaling.
       - Performance scales with volume size (3 IOPS per GiB).
       - **Use Cases**: Similar to gp3, but consider migrating to gp3 for better cost/performance.
    - **Provisioned IOPS SSD (io2 Block Express / io1)**:
       - Designed for mission-critical, high-performance, I/O-intensive transactional workloads that require very low latency and consistent performance.
       - You explicitly provision the IOPS you need.
       - io2 Block Express offers the highest performance (up to 256,000 IOPS, 4,000 MB/s throughput) and 99.999% durability.
       - **Use Cases**: Large relational and NoSQL databases (e.g., Oracle, SQL Server, SAP HANA, MongoDB, Cassandra), transactional applications that demand extreme performance.
    - **HDD-backed volumes (Hard Disk Drive)**: Optimized for large streaming workloads where throughput (MB/s) is the dominant performance attribute. They are generally lower cost than SSDs but have higher latency. Cannot be used as boot volumes.

       - Throughput Optimized HDD (st1):
       - Designed for frequently accessed, throughput-intensive workloads.
       - Provides burstable performance up to 500 MB/s per volume.
       - Use Cases: Big data analytics, data warehouses, log processing, streaming applications, large sequential reads/writes.
    - **Cold HDD (sc1)**:
        - The lowest cost EBS volume type.
        - Designed for less frequently accessed workloads where cost is prioritized over performance.
Provides burstable performance up to 250 MB/s per volume.
        - **Use Cases**: Archival data, infrequently accessed logs, backup targets.


### Which EBS Volume Type Should I Use?
- Here's a simple decision framework:

- **Start with gp3 (General Purpose SSD)**:

    - This is the default recommendation for most use cases. It provides a great balance of performance and cost.
    - If you're unsure, or it's a development/test environment, a web server, or a small-to-medium database, gp3 is usually the right choice.
    - It's also the only other type (besides io1/io2) that can be used as a boot volume for your EC2 instance.

- **Consider io2 Block Express / io1 for extremely high-performance needs**:

    - If your application requires sub-millisecond latency, extremely high and consistent IOPS (tens or hundreds of thousands), such as large, mission-critical relational databases (e.g., Oracle, SQL Server, SAP HANA), choose these. They come at a higher cost.
- **Consider st1 for large, sequential throughput**:

   - If you're dealing with big data analytics, data warehousing, or log processing where large files are read/written sequentially and throughput (MB/s) is more important than IOPS or low latency, st1 is a cost-effective choice.
- **Consider sc1 for very infrequent access and cost optimization**:

   - If you have cold data, archives, or backups that are rarely accessed but still need to be online, sc1 offers the lowest storage cost.

- **General Rule**: Always start with gp3. If you encounter performance bottlenecks that indicate your storage is the limiting factor, then evaluate if one of the more specialized (and often more expensive) volume types (io2, st1, sc1) better suits your workload's specific I/O characteristics (IOPS vs. Throughput, random vs. sequential, latency sensitivity).

