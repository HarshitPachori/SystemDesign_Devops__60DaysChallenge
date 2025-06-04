---
title: "Day 10: AWS EC2 & EBS Snapshot "
description: "A summary of my 9th day's learning in the 60-day challenge, covering basic cloud concepts , storage and an overview of docker and multi stage builds."
keywords:
  - AWS
  - EC2
  - EBS
  - Snapshot
  - Day 10
  - Challenge
---

### Table of contents :
- [What is AWS EBS Snapshot?](#what-is-aws-ebs-snapshot)
- [What are Benefits of EBS Snapshots ?](#what-are-benefits-of-ebs-snapshots-)
- [Steps to Create Your First Snapshot (EBS Backup)](#steps-to-create-your-first-snapshot-ebs-backup)
- [Steps to Automate EBS Volume Backup (Using AWS Data Lifecycle Manager - DLM)](#steps-to-automate-ebs-volume-backup-using-aws-data-lifecycle-manager---dlm)



### What is AWS EBS Snapshot?
An Amazon EBS Snapshot is a point-in-time backup of your Amazon Elastic Block Store (EBS) volumes. When you create a snapshot, AWS stores a copy of your volume's data in Amazon S3.

- **Incremental Nature**: Snapshots are incremental. This means that after the first full snapshot, subsequent snapshots only save the blocks of data that have changed since the last snapshot. This makes them very efficient in terms of storage space and the time it takes to create them.

- **Stored on S3**: Although they are backups of EBS volumes, the snapshots themselves are stored in Amazon S3, leveraging S3's high durability and availability. You are billed for the storage consumed by your snapshots in S3.

- **Volume Restoration**: You can use a snapshot to restore a new EBS volume (which will be an exact replica of the original volume at the time the snapshot was taken). You can also use a snapshot to create a new volume in a different Availability Zone or even a different AWS Region.

### What are Benefits of EBS Snapshots ?
- **Data Backup and Recovery**: The primary benefit is to create reliable backups of your data. In case of accidental deletion, data corruption, or instance failure, you can restore your volume from a snapshot.
- **Disaster Recovery (DR)**: Snapshots can be copied across Availability Zones and AWS Regions. This is crucial for building robust disaster recovery strategies, allowing you to quickly launch new instances and restore data in a different geographic location if a primary region becomes unavailable.
- **Data Migration**: Easily migrate data from one EC2 instance to another, or from one AZ to another, or even to a different AWS Region.
- **Testing and Development**: Create new test or development environments with production-like data by restoring snapshots to new volumes.
- **Scaling and Replication**: Quickly create multiple identical volumes from a single snapshot to scale out your application (e.g., for a new database replica).
- **Cost-Effective**: Due to their incremental nature, snapshots are often more cost-effective for ongoing backups compared to full backups each time.

### Steps to Create Your First Snapshot (EBS Backup)
- **Log in to AWS Management Console**:

   - Go to aws.amazon.com and sign in to your AWS account.
- **Navigate to EC2 Dashboard**:

   - In the search bar at the top, type "EC2" and select "EC2" under Services.
- **Go to Volumes**:

   - In the left-hand navigation pane, under "Elastic Block Store," click on "Volumes."
- **Select the Volume**:

   - Find and select the EBS volume for which you want to create a snapshot. (It can be in-use or available).
- **Initiate Snapshot Creation**:

   - With the volume selected, click on "Actions" (top right) and choose "Create snapshot."
- **Configure Snapshot Details**:

   - **Description (Recommended)**: Provide a meaningful description for your snapshot (e.g., "Daily backup for WebApp DB - 2025-06-04"). This helps you identify it later.
   - **Tags (Recommended)**: Add key-value tags (e.g., Key: BackupType, Value: Daily or Key: Application, Value: WebApp). Tags are crucial for organization, cost tracking, and automation.

   - **Enable Crash Consistent Snapshot (Optional, for Multi-volume)**: If you are taking snapshots of multiple volumes attached to an instance (e.g., a database with data and log volumes) and want them to be application-consistent, you might use AWS Backup or a script to orchestrate this. For a single volume, this option is less relevant.
- **Create Snapshot**:

   - Click the "Create snapshot" button.
- **Monitor Snapshot Progress**:

   - In the left-hand navigation pane, under "Elastic Block Store," click on "Snapshots."
   - You will see your new snapshot listed, initially with a "Pending" status. It will transition to "Completed" once the snapshot is finished. The time it takes depends on the volume size and the amount of data that needs to be copied.


### Steps to Automate EBS Volume Backup (Using AWS Data Lifecycle Manager - DLM)
Manually creating snapshots is fine for one-off backups, but for regular, scheduled backups, you should automate the process using AWS Data Lifecycle Manager (DLM). DLM allows you to create policies that automate the creation, retention, and deletion of snapshots.


- Log in to AWS Management Console:

   - Go to aws.amazon.com and sign in.
- Navigate to Data Lifecycle Manager (DLM):

   - In the search bar, type "DLM" and select "Amazon Data Lifecycle Manager."
- Click "Create Lifecycle Policy":

   - On the DLM dashboard, click the "Create Lifecycle Policy" button.
- Choose Policy Type:

   - Select "EBS Snapshot Policy".
   - Click "Next".
- Configure Policy Details:

   - Description: Give your policy a descriptive name (e.g., "Daily EBS Volume Backup Policy").
   - Target Resources:
        - Choose "Volumes".
        - Specify Tags: This is the most important step for automation. DLM policies target EBS volumes based on tags. You need to add a specific tag to the EBS volumes you want to back up (e.g., Key: Backup, Value: Daily). Only volumes with this tag will be included in the policy.
   - Exclude volumes from launch instance (Optional): You can exclude root volumes if needed.
   - IAM Role: DLM needs permissions to create/delete snapshots. Choose an existing IAM role with AmazonDLMServiceRoleForAmazonEBS permissions, or let AWS create a new one for you.
   - Policy Status: Set to Enabled.
- Configure Schedule:

   - Schedule Name: (e.g., "Daily-Snapshot")
   - Frequency: Choose how often snapshots should be taken (e.g., "Daily").
   - Start Time: Specify the time (UTC) when the snapshot process should begin.
   - Retention Type:
        - Count: Retain a specific number of recent snapshots (e.g., "Keep 7 snapshots").
        - Age: Retain snapshots for a specific period (e.g., "Keep snapshots for 30 days").
   - Fast Snapshot Restore (Optional): Enable FSR for faster volume restoration.
   - Copy tags from volume: Recommended to copy tags from the source volume to the snapshot.
   - Cross-Region Copy (Optional): If you need snapshots copied to another AWS Region for disaster recovery, configure it here.
   - Share Snapshots (Optional): Share snapshots with other AWS accounts.
- Review and Create Policy:

   - Review all your settings.
   - Click "Create Policy."
- Once the policy is active, DLM will automatically create snapshots of all EBS volumes that have the specified tag, according to your defined schedule and retention rules. This significantly reduces manual effort and ensures consistent backup practices.