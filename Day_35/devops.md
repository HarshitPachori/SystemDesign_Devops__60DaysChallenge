---
title: "Day 35: AWS Cloudfront"
description: "A summary of my 35th day's learning in the 60-day challenge, covering basic concepts of Subnet in AWS Virtual Private Cloud (VPC)."
keywords:
  - AWS
  - Subnet in Virtual Private Cloud (VPC)
  - Day 35
  - Challenge
---

### Table of contents :
- [What is a Subnet?](#what-is-a-subnet)
- [Public vs. Private Subnets](#public-vs-private-subnets)
- [Step-by-Step Guide to Creating a Subnet](#step-by-step-guide-to-creating-a-subnet)


### What is a Subnet?
- In AWS, a subnet is a logical subdivision of your Virtual Private Cloud (VPC) that contains a range of IP addresses. Think of your VPC as a private data center in the cloud, and subnets as the individual rooms within that data center. You launch your AWS resources, such as EC2 instances and databases, into these subnets.

- Subnets are tied to a single Availability Zone (AZ), which is a physically separate data center within an AWS Region. To ensure high availability and fault tolerance, it is best practice to create at least one subnet in multiple Availability Zones.

### Public vs. Private Subnets
The key difference between a public and a private subnet is its ability to communicate directly with the internet. This is determined by its route table.

- **Public Subnet**: A public subnet's route table contains a route to an Internet Gateway (IGW). This allows resources within the subnet to receive public IP addresses and communicate directly with the internet. This is where you would place resources like a web server or a load balancer.

- **Private Subnet**: A private subnet's route table does not have a direct route to an Internet Gateway. This means resources in a private subnet are not directly accessible from the internet, which is ideal for sensitive data and internal services like databases and application servers. Resources in a private subnet can still access the internet for things like software updates by routing traffic through a NAT Gateway located in a public subnet.

### Step-by-Step Guide to Creating a Subnet
Follow these steps to create a new subnet in your VPC:

1. **Open the Amazon VPC Console**:

  - Log in to the AWS Management Console.

  - Navigate to the VPC dashboard by searching for "VPC" in the search bar.

2. **Navigate to the Subnets Section**:

  - In the left-hand navigation pane, select Subnets.

  - Click the Create subnet button.

3. **Configure Subnet Settings**:

  - **VPC ID**: From the dropdown menu, select the VPC in which you want to create the subnet.

  - **Subnet name (optional)**: Give your subnet a descriptive name, such as public-subnet-us-east-1a or private-subnet-us-east-1a.

  - **Availability Zone**: Choose a specific Availability Zone for your subnet. It's recommended to create subnets in different Availability Zones for redundancy.

  - **IPv4 CIDR block**: This is a crucial step. You need to specify a CIDR block for your subnet that falls within your VPC's CIDR range but does not overlap with any other subnets. For example, if your VPC has a CIDR of 10.0.0.0/16, you could use 10.0.1.0/24 for your first subnet and 10.0.2.0/24 for your second.

4. **Finalize Creation**:

  - Review your settings.

  - Click Create subnet.
