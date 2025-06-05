---
title: "Day 11: AWS EC2 & EBS Snapshot "
description: "A summary of my 11th day's learning in the 60-day challenge, covering basic cloud concepts , VPC, Subnets, etc."
keywords:
  - AWS
  - EC2
  - VPC
  - Day 11
  - Challenge
---

### Table of contents :
- [What is VPC (Virtual Private Cloud) / VNet (Virtual Network) ?](#what-is-vpc-virtual-private-cloud--vnet-virtual-network-)
- [What is Subnets (Public/Private) ?](#what-is-subnets-publicprivate-)
- [What is Internet Gateway (IGW) - (AWS Specific) ?](#what-is-internet-gateway-igw---aws-specific-)
- [What is NAT Gateway (Network Address Translation Gateway) - (AWS Specific) ?](#what-is-nat-gateway-network-address-translation-gateway---aws-specific-)
- [What is Route Tables](#what-is-route-tables)



### What is VPC (Virtual Private Cloud) / VNet (Virtual Network) ? 

An Amazon EBS Snapshot is a point-in-time backup of your Amazon Elastic Block Store (EBS) volumes. When you create a snapshot, AWS stores a copy of your volume's data in Amazon S3.

- What it is: A VPC (AWS) or VNet (Azure) is a logically isolated section of the cloud where you can launch your cloud resources (like virtual machines, databases, etc.) in a virtual network that you define. It's like having your own private data center network within the public cloud, complete with your own IP address ranges, subnets, route tables, and network gateways.
- Purpose: To provide a secure and isolated environment for your cloud resources, giving you full control over your network configuration.
- Key Characteristics:
   - IP Address Range (CIDR Block): You define a private IP address range (e.g., 10.0.0.0/16) for your VPC/VNet. All resources launched within this network will have IP addresses from this range.
   - Logical Isolation: Your VPC/VNet is logically isolated from other customers' virtual networks, even though they might share the same physical hardware.

### What is Subnets (Public/Private) ?
- What it is: A subnet is a logical subdivision of your VPC's/VNet's IP address range. You create subnets within your VPC/VNet to further segment your network and to deploy resources into specific Availability Zones (AZs) within a region. Each subnet must reside entirely within a single AZ.

- Purpose: To organize your network, control traffic flow, and enhance security by isolating different types of resources.

- Types:

   - Public Subnet:

      - Definition: A subnet that has a direct route to an Internet Gateway (IGW). Resources launched in a public subnet can directly communicate with the public internet (both inbound and outbound) if they have a public IP address (or Elastic IP).
      - Use Cases: Web servers, load balancers, bastion hosts (jump servers) – any resource that needs to be directly accessible from the internet.
      - Characteristic: Its route table contains a route that points traffic destined for 0.0.0.0/0 (all internet traffic) to an Internet Gateway.
   - Private Subnet:

      - Definition: A subnet that does NOT have a direct route to an Internet Gateway. Resources in a private subnet cannot be directly accessed from the internet. They can initiate outbound connections to the internet, but only through a NAT Gateway (or NAT instance).
      - Use Cases: Databases, application servers, internal microservices, caching layers – any resource that should not be directly exposed to the internet for security reasons.
      - Characteristic: Its route table typically points internet-bound traffic (0.0.0.0/0) to a NAT Gateway.

### What is Internet Gateway (IGW) - (AWS Specific) ?
- What it is: An Internet Gateway is a horizontally scaled, redundant, and highly available VPC component that allows communication between your VPC and the internet. It provides a target for internet-routable traffic in your VPC's route tables.
- Role:
   - Enables Internet Connectivity: It enables instances in public subnets to connect to the internet and vice versa.
   - NAT Functionality: For IPv4 traffic, it performs a one-to-one Network Address Translation (NAT) for instances with public IP addresses (or Elastic IPs), translating private IP addresses to public ones for internet communication.
   - No Bandwidth Constraints: It doesn't impose any bandwidth constraints on your network traffic.
- Key Point: You attach an IGW to your VPC, but you also need to update the route tables of your public subnets to point internet-bound traffic to this IGW.

### What is NAT Gateway (Network Address Translation Gateway) - (AWS Specific) ?
- What it is: A NAT Gateway is an AWS-managed service that allows instances in a private subnet to initiate outbound connections to the internet (or other AWS services) while preventing external services from initiating a connection with those instances.
- Role:
     - Outbound Internet Access for Private Subnets: Enables resources in private subnets to download updates, patches, or access external APIs.
     - Enhanced Security: By not giving private instances direct public IP addresses, they are protected from unsolicited inbound connections from the internet.
     - High Availability: It's a highly available and scalable AWS-managed service.
- How it works:
     - You deploy a NAT Gateway in a public subnet and assign it an Elastic IP address.
     - The route table for your private subnet is configured to send all internet-bound traffic (0.0.0.0/0) to the NAT Gateway.
     - The NAT Gateway then translates the private IP addresses of the instances in the private subnet to its own public Elastic IP address before forwarding the traffic to the Internet Gateway. Response traffic is then translated back to the private IP.

### What is Route Tables
- What it is: A route table is a set of rules, called routes, that determines where network traffic from your subnets or gateways is directed. Every subnet in your VPC must be associated with a route table.
- Role:
    - Traffic Direction: Defines the paths for incoming and outgoing network traffic within your VPC.
    - Local Routes: Every route table automatically contains a "local" route that allows communication within the VPC's CIDR block.
    - Explicit vs. Implicit Association: You can explicitly associate a subnet with a custom route table. If you don't, it's implicitly associated with the VPC's "main" route table.
- Components of a Route:
   - Destination: The CIDR block for which the traffic is intended (e.g., 0.0.0.0/0 for all internet traffic, or a specific IP range).
   - Target: The gateway, network interface, or connection through which the traffic should be sent (e.g., igw-xxxxxxxx for an Internet Gateway, nat-xxxxxxxx for a NAT Gateway, pcx-xxxxxxxx for a VPC peering connection).
