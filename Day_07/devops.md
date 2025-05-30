---
title: "Day 07: Creating a New EBS Storage"
description: "A summary of my 7th day's learning in the 60-day challenge, covering basic cloud concepts , storage and an overview of elastic block storage. Created new EBS Storage."
keywords:
  - AWS
  - EC2
  - Static/Elastic IP
  - Elastic Block Storage (EBS)
  - Day 7
  - Challenge
---

### Table of contents :
- [Steps to create EBS Storage](#steps-to-create-ebs-storage)


#### Steps to create EBS Storage
Creating your first Amazon Elastic Block Store (EBS) volume is a straightforward process within the AWS Management Console. An EBS volume is a persistent block storage device that you can attach to an EC2 instance.


- Log in to AWS Management Console:

     - Go to aws.amazon.com and sign in to your AWS account.
- Navigate to EC2 Dashboard:

     - In the search bar at the top, type "EC2" and select "EC2" under Services to go to the EC2 Dashboard.
- Go to Volumes:

     - In the left-hand navigation pane, under the "Elastic Block Store" section, click on "Volumes".
- Click "Create Volume":

     - In the main content area, click the "Create Volume" button.
- Configure Volume Settings:

    - Volume Type: Choose the type of volume you need.
          - gp3 (General Purpose SSD) is a good default for most workloads, offering a balance of price and performance.
          - io2 (Provisioned IOPS SSD) for very high-performance, I/O-intensive workloads.
          - st1 (Throughput Optimized HDD) for frequently accessed, throughput-intensive workloads.
          - sc1 (Cold HDD) for less frequently accessed workloads.
    - Size (GiB): Enter the size of the volume in Gibibytes (GiB). For gp3, the minimum is 1 GiB.
    - IOPS (for gp3, io2): If you chose gp3 or io2, you can provision IOPS (Input/Output Operations Per Second). gp3 volumes include a baseline of 3,000 IOPS and 125 MB/s throughput for any size. You can provision more if needed.
    - Throughput (MB/s for gp3): For gp3 volumes, you can also provision dedicated throughput.
    - Availability Zone: Crucially, select the same Availability Zone (AZ) where your EC2 instance is located or where you plan to launch it. An EBS volume can only be attached to an EC2 instance within the same AZ.
    - Snapshot (Optional): If you're creating a volume from an existing snapshot, select it here. For a new volume, leave this blank.
    - Encryption (Optional but Recommended): Check "Encrypt this volume" for data-at-rest encryption. You can use an AWS Key Management Service (KMS) key.
- Add Tags (Optional but Recommended):

    - Click "Add new tag" to add key-value pairs (e.g., Key: Name, Value: MyFirstEBSVolume). Tags help organize and identify your resources.
- Review and Create Volume:

    - Review all the configurations.
    - Click the "Create Volume" button.

