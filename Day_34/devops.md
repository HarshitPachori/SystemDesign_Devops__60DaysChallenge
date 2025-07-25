---
title: "Day 34: AWS Cloudfront"
description: "A summary of my 34th day's learning in the 60-day challenge, covering basic concepts of AWS Virtual Private Cloud (VPC)."
keywords:
  - AWS
  - Virtual Private Cloud (VPC)
  - Day 34
  - Challenge
---

### Table of contents :
- [AWS Virtual Private Cloud (VPC): An Introduction and How to Create One](#aws-virtual-private-cloud-vpc-an-introduction-and-how-to-create-one)
- [How to Create a VPC in AWS (Step-by-Step using Console)](#how-to-create-a-vpc-in-aws-step-by-step-using-console)


### AWS Virtual Private Cloud (VPC): An Introduction and How to Create One
Amazon Virtual Private Cloud (VPC) is a foundational AWS service that allows you to provision a logically isolated section of the AWS Cloud where you can launch AWS resources in a virtual network that you define. It gives you complete control over your virtual networking environment, including your own IP address range, subnets, route tables, network gateways, and security settings.

Think of a VPC as your own isolated, private data center in the cloud. You have full control over who can access your resources, how they communicate with each other, and how they connect to the internet or your on-premises networks.

- **Why is AWS VPC Important?**
  - **Isolation and Security**: Your VPC is logically isolated from other AWS customers' VPCs. You can define strict network boundaries and control all inbound and outbound traffic, significantly enhancing the security posture of your applications.

  - **Customization**: You can design your network topology to match your specific needs, creating public-facing subnets for web servers and private subnets for databases, just like in a traditional data center.

  - **Connectivity**: VPCs can be connected to the internet, to your corporate data centers (via VPN or Direct Connect), and even to other VPCs (via VPC Peering or Transit Gateway).

  - **Scalability and Flexibility**: You can easily scale your network infrastructure up or down as your application requirements change, without the physical limitations of an on-premises data center.

  - **Compliance**: The ability to define and control your network environment helps meet various regulatory and compliance requirements.

- **Key Components of an AWS VPC**
To understand a VPC, it's essential to know its core components:

  - **CIDR Block (Classless Inter-Domain Routing)**:

    - **Concept**: This is the IP address range for your VPC, defined in CIDR notation (e.g., 10.0.0.0/16). It determines the total number of private IP addresses available within your VPC.

    - **Role**: It's the overarching IP space from which all your subnets will draw their IP addresses.

  - **Subnets**:

    - **Concept**: Subnets are logical divisions within your VPC's IP address range. Each subnet resides entirely within a single Availability Zone (AZ).

    - **Role**: You launch your AWS resources (like EC2 instances, RDS databases) into subnets.

  - **Public Subnet**: A subnet with a route to an Internet Gateway. Resources in a public subnet can access the internet (if they have a public IP address or an Elastic IP).

  - **Private Subnet**: A subnet without a direct route to an Internet Gateway. Resources in a private subnet are isolated from the internet. They can access the internet via a NAT Gateway if needed.

  - **Route Tables**:

    - **Concept**: A route table contains a set of rules (routes) that determine where network traffic from your subnets or gateways is directed.

    - **Role**: Each subnet must be associated with a route table. The main route table is automatically created with your VPC and controls routing for all subnets not explicitly associated with another route table.

  - **Internet Gateway (IGW)**:

    - **Concept**: A horizontally scaled, redundant, and highly available VPC component that allows communication between instances in your VPC and the internet.

    - **Role**: It provides a target for internet-bound traffic in your public subnets' route tables and performs network address translation (NAT) for instances with public IP addresses.

  - **NAT Gateway (Network Address Translation Gateway) / NAT Instance**:

    - **Concept**: Allows instances in a private subnet to initiate outbound connections to the internet or other AWS services while preventing inbound connections initiated from the internet.

    - **Role**: If you have private databases or application servers that need to download updates or connect to external APIs without being directly exposed to the internet, a NAT Gateway (AWS-managed service, highly recommended) or a NAT Instance (EC2 instance you manage) is used.

  - **Security Groups (SGs)**:

    - **Concept**: Act as a virtual firewall for your EC2 instances (or other resources). They control inbound and outbound traffic at the instance level.

    - **Role**: Security Groups are stateful, meaning if you allow inbound traffic, the outbound response is automatically allowed. You define rules based on protocols, ports, and source/destination IP addresses or other security groups.

  - **Network Access Control Lists (NACLs)**:

    - **Concept**: An optional layer of security that acts as a firewall for your subnets.

    - **Role**: NACLs are stateless, meaning you must explicitly allow both inbound and outbound traffic. They are processed in order of rule number. They can be used to allow or deny specific IP addresses or ranges at the subnet boundary.

### How to Create a VPC in AWS (Step-by-Step using Console)
AWS provides a "VPC wizard" that simplifies the creation of a VPC with common configurations. We'll use the "VPC only" option for a basic setup and then add components.

  - **Step 1: Access the VPC Console**
    - Sign in to the AWS Management Console.

    - In the search bar, type VPC and select VPC under "Services".

  - **Step 2: Create Your VPC**
    - On the VPC dashboard, click "Create VPC".

    - Under "Resources to create", choose "VPC only". This gives you a blank canvas to add components. (The "VPC and more" wizard is also an option for pre-configured setups, but building manually helps understanding).

    - **Name tag**: Enter a descriptive name for your VPC (e.g., MyCustomVPC).

    - **IPv4 CIDR block**: Enter an IPv4 CIDR block for your VPC.

    - **Recommendation**: Use a private IP range (e.g., 10.0.0.0/16, 172.16.0.0/16, or 192.168.0.0/16). A /16 provides 65,536 IP addresses, which is usually sufficient for most needs. Ensure it doesn't overlap with your on-premises network if you plan to connect them.

    - **Example**: 10.0.0.0/16

    - **IPv6 CIDR block**: (Optional) You can choose to add an IPv6 CIDR block if your application requires it. For most basic setups, you can leave this as "No IPv6 CIDR block".

    - **Tenancy**: Keep "Default" (shared hardware). "Dedicated" is for specific compliance needs and is more expensive.

    - Click "Create VPC".

  - **Step 3: Create Subnets**
    - You'll typically create at least one public subnet and one private subnet, ideally across multiple Availability Zones for high availability.

    - In the VPC console, go to "Subnets" in the left navigation pane.

      - Click "Create subnet".

    - **VPC ID**: Select the VPC you just created (MyCustomVPC).

    - **Subnet name**: Give it a descriptive name (e.g., MyCustomVPC-PublicSubnet-AZ1).

    - **Availability Zone**: Select an Availability Zone (e.g., us-east-1a).

    - **IPv4 CIDR block**: Enter a CIDR block for this subnet that is a subset of your VPC's CIDR.

    - **Example for 10.0.0.0/16 VPC**: 10.0.1.0/24 (provides 256 IPs).

    - Click "Create subnet".

    - **Repeat this process to create**:

      - Another public subnet in a different AZ (e.g., MyCustomVPC-PublicSubnet-AZ2, 10.0.2.0/24 in us-east-1b).

      - A private subnet in the first AZ (e.g., MyCustomVPC-PrivateSubnet-AZ1, 10.0.3.0/24 in us-east-1a).

      - Another private subnet in the second AZ (e.g., MyCustomVPC-PrivateSubnet-AZ2, 10.0.4.0/24 in us-east-1b).

  - **Step 4: Create an Internet Gateway (for Public Subnets)**
    - In the VPC console, go to "Internet Gateways" in the left navigation pane.

    - Click "Create internet gateway".

    - **Name tag**: Give it a name (e.g., MyCustomVPC-IGW).

    - Click "Create internet gateway".

    - Once created, select the new Internet Gateway, click "Actions", and then "Attach to VPC".

    - Select your MyCustomVPC from the dropdown and click "Attach internet gateway".

  - **Step 5: Configure Route Tables (for Internet Connectivity)**
    - By default, all subnets are associated with the "Main" route table of the VPC, which only has a local route (allowing communication within the VPC). You need to create a custom route table for your public subnets to route internet traffic.

    - In the VPC console, go to "Route Tables" in the left navigation pane.

    - Click "Create route table".

    - **Name tag**: Give it a name (e.g., MyCustomVPC-PublicRT).

    - **VPC**: Select your MyCustomVPC.

      - Click "Create route table".

      - Select your newly created MyCustomVPC-PublicRT.

      - Go to the "Routes" tab, then click "Edit routes".

    - **Click "Add route"**:

    - **Destination**: 0.0.0.0/0 (represents all internet traffic).

    - **Target**: Select your Internet Gateway (e.g., igw-xxxxxxxxxxxxxxxxx - MyCustomVPC-IGW).

      - Click "Save changes".

      - Now, associate this route table with your public subnets:

      - Still on MyCustomVPC-PublicRT, go to the "Subnet associations" tab.

      - Click "Edit subnet associations".

      - Select your public subnets (e.g., MyCustomVPC-PublicSubnet-AZ1, MyCustomVPC-PublicSubnet-AZ2).

      - Click "Save associations".

  - **Step 6: Enable Auto-Assign Public IP for Public Subnets (Optional, but common for EC2)**
    - For EC2 instances launched in public subnets to automatically receive a public IPv4 address, you need to enable this setting.

    - In the VPC console, go to "Subnets".

    - Select your public subnets (e.g., MyCustomVPC-PublicSubnet-AZ1, MyCustomVPC-PublicSubnet-AZ2).

    - Click "Actions", then "Edit subnet settings".

    - Check the box for "Enable auto-assign public IPv4 address".

    - Click "Save".

  - **Step 7: Create a NAT Gateway (for Private Subnets to access Internet)**
    - If resources in your private subnets need outbound internet access (e.g., to download updates, connect to external APIs), you need a NAT Gateway. This requires an Elastic IP.

    - **Allocate an Elastic IP**:

      - In the VPC console, go to "Elastic IPs" in the left navigation pane.

      - Click "Allocate Elastic IP address".

      - Click "Allocate".

    - **Create NAT Gateway**:

      - In the VPC console, go to "NAT Gateways" in the left navigation pane.

      - Click "Create NAT gateway".

    - **Name tag**: Give it a name (e.g., MyCustomVPC-NATGW-AZ1).

  - **Subnet**: Choose one of your public subnets (e.g., MyCustomVPC-PublicSubnet-AZ1). NAT Gateways must reside in a public subnet.

    - **Elastic IP allocation ID**: Select the Elastic IP you just allocated.

      - Click "Create NAT gateway".

      - Wait for the NAT Gateway status to become Available.

    - **Update Private Subnet Route Tables**:

      - In the VPC console, go to "Route Tables".

      - Select the route table associated with your private subnets (if you didn't explicitly create one, they'll be using the "Main" route table). It's best practice to create a separate route table for private subnets.

      - If you haven't, create a new route table (e.g., MyCustomVPC-PrivateRT-AZ1), associate it with your private subnet(s) in the same AZ as your NAT Gateway.

      - Go to the "Routes" tab, then click "Edit routes".

    - **Click "Add route"**:

    - **Destination**: 0.0.0.0/0

    - **Target**: Select your NAT Gateway (e.g., nat-xxxxxxxxxxxxxxxxx - MyCustomVPC-NATGW-AZ1).

      - Click "Save changes".

  - **Step 8**: Configure Security Groups (Default is created with VPC)
    - A default security group is created with your VPC. You can use it or create new ones.

    - In the VPC console, go to "Security Groups" in the left navigation pane.

    - You'll see a default security group for your VPC.

    - Click "Create security group" to create custom ones for your web servers, databases, etc., defining specific inbound and outbound rules.

