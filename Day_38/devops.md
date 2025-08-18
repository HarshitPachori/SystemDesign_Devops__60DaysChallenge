---
title: "Day 38: Bastion Host"
description: "A summary of my 38th day's learning in the 60-day challenge, covering basic concepts of Subnet in Bastion Host."
keywords:
  - AWS
  - Bastion Host
  - Day 38
  - Challenge
---

### Table of contents :
- [How to Set Up and Use a Bastion Host ? ](#how-to-set-up-and-use-a-bastion-host-)


### How to Set Up and Use a Bastion Host ?
You'll need a VPC with both a public subnet and a private subnet configured, along with an Internet Gateway attached to the public subnet's route table.

- **Step 1: Launch the Bastion Host Instance**
  - Launch a new EC2 instance for your bastion host.

  - **Network Settings**: Select your VPC and launch the instance into the public subnet.

  - **Public IP**: Ensure Auto-assign Public IP is enabled.

  - **Key Pair**: Choose or create a new key pair for SSH access. You will use this to connect to the bastion host.

- **Step 2: Configure Security Groups**
This is the most critical step for security. You will need two security groups.

  1. **Bastion Host Security Group**:

    - Create a new security group for the bastion host.

    - **Inbound Rules**: Add an inbound rule for SSH (`Port 22`). For the source, do not use `0.0.0.0/0` (everyone). Instead, specify a narrow CIDR range (e.g., your office's public IP address) or use My IP to allow connections only from your location. This ensures only trusted sources can connect to the bastion.



  2. **Private Instance Security Group**:

    - Create or modify the security group for your private instances.

    - **Inbound Rules**: Add an inbound rule for SSH (`Port 22`). For the source, instead of an IP address, select the Bastion Host's security group. This rule explicitly allows any instance with the bastion host's security group to initiate SSH connections, effectively blocking all other traffic.


- **Step 3: Connect to the Private Instance**
Instead of copying your private key to the bastion host, the most secure method is to use SSH Agent Forwarding. This allows your local machine to forward your private key securely to the bastion host, which then uses it to authenticate with the private instance.

  1. Add Your Key to the SSH Agent (on your local machine):

    - First, start the SSH agent: `eval $(ssh-agent)`

    - Next, add your private key to the agent: `ssh-add /path/to/your-key-file.pem`

  2. SSH to the Bastion Host with Agent Forwarding:

    - Use the -A flag to enable agent forwarding.

    - `ssh -A ec2-user@<bastion_host_public_ip>`

  3. SSH from the Bastion to the Private Instance:

    - Once you are successfully connected to the bastion host, you can connect to your private instance using its private IP address.

    - `ssh ec2-user@<private_instance_private_ip>`

    - Because of SSH Agent Forwarding, you won't need to specify the private key file here. The bastion will use the key stored on your local machine to authenticate.
