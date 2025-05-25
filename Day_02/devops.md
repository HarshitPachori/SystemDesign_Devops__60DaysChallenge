---
title: "Day 02: Introduction to EC2 Instance and Fundamentals"
description: "A summary of my 2nd day's learning in the 60-day challenge, covering basic cloud concepts and an overview of EC2."
keywords:
  - AWS
  - EC2
  - Fundamentals
  - Day 2
  - Challenge
---

### Table of contents :
- [What is EC2 ?](#what-is-ec2-)
- [Why do we need EC2 ?](#why-do-we-need-ec2-)
- [Steps to create EC2 Instance](#steps-to-create-ec2-instance)


#### What is EC2 ?
EC2, or Amazon Elastic Compute Cloud, is a core service of AWS that provides scalable computing capacity in the cloud. Essentially, it allows you to rent virtual servers (called "instances") on demand. You can launch as many or as few as you need, configure security and networking, and manage storage, paying only for what you use. It's the foundation for running various workloads, from websites to complex applications.

#### Why do we need EC2 ?
We need EC2 because it revolutionizes how we acquire and manage computing resources. Instead of buying and maintaining physical servers, EC2 offers:

- **Elasticity & Scalability**: Quickly scale computing capacity up or down to meet fluctuating demand, paying only for what you use. This avoids over-provisioning and capital expenditure.
- **Flexibility**: Choose from diverse instance types, operating systems, and software configurations to precisely match workload needs (e.g., CPU-intensive, memory-intensive).
- **Cost-effectiveness**: The pay-as-you-go model and options like Reserved Instances and Spot Instances significantly reduce costs compared to traditional IT infrastructure.
- **Global Reach & Reliability**: Deploy instances in various AWS Regions and Availability Zones for low latency, disaster recovery, and high availability.
- **Integration**: Seamlessly integrates with a vast ecosystem of other AWS services for comprehensive solutions.

### Steps to create EC2 Instance
Creating an EC2 instance typically involves these steps via the AWS Management Console:

- **Log in to AWS Console**: Sign in to your AWS account.
- **Navigate to EC2 Dashboard**: Search for "EC2" in the services search bar and click on it.
- **Launch Instance**: On the EC2 Dashboard, click "Launch Instance" to start the wizard.
- **Choose AMI**: Select an Amazon Machine Image (AMI), which is a pre-configured template (OS, software). Opt for a "Free tier eligible" AMI if you're on the free tier.
- **Choose Instance Type**: Select an instance type (e.g., t2.micro, also often free tier eligible) which determines CPU, memory, and networking capacity.
- **Configure Key Pair**: Create a new key pair or choose an existing one. This .pem file is crucial for securely connecting to your instance via SSH. Download and secure it.
- **Network Settings**: Configure network settings, including the Virtual Private Cloud (VPC), subnet, and create/assign a security group (virtual firewall) to control inbound/outbound traffic.
- **Configure Storage**: Add storage (EBS volume) for your instance. The default is usually sufficient for testing, and 30GB is free tier eligible.
- **Review and Launch**: Review all your configurations. If satisfied, click "Launch Instance."
- **Connect**: Once the instance is running, you can connect using SSH (for Linux) or RDP (for Windows) with your key pair.
