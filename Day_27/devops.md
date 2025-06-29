---
title: "Day 27: AWS Cloudfront"
description: "A summary of my 27th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 27
  - Challenge
---

### Table of contents :
- [How to Access EC2 Instance only from Cloudfront ?](#how-to-access-ec2-instance-only-from-cloudfront-)


### How to Access EC2 Instance only from Cloudfront ?
**Recommended Approach**: EC2 Behind an Application Load Balancer (ALB)
This is the most secure and scalable approach for production environments. You restrict traffic to the ALB, and the ALB then forwards traffic to your EC2 instances.

**Architecture Flow**:Internet (User) -> CloudFront -> Application Load Balancer (ALB) -> EC2 Instance(s)

- **Step 1**: Secure Your EC2 Instance Security Group
Your EC2 instance(s) should reside in a private subnet (no public IP) if possible, but even in a public subnet, you must configure its security group correctly.

  - 1. **Create/Modify EC2 Security Group**:

    - **Inbound Rules**:

      - Allow traffic only from the Security Group of your Application Load Balancer on the port(s) your web server uses (e.g., port 80 for HTTP, 443 for HTTPS, or your application's specific port).

      - Do NOT allow 0.0.0.0/0 (anywhere) on these ports.

      - **Example**:

        - **Type**: Custom TCP

        - **Protocol**: TCP

        - **Port Range**: 80 (or 443, or your app port)

        - **Source**: sg-xxxxxxxxxxxxxxxxx (Select the Security Group ID associated with your ALB)

    - **SSH Access (Port 22)**: Only allow SSH from your trusted IP addresses, your office VPN, or use AWS Systems Manager Session Manager for secure access without opening port 22 to the internet at all.

- **Step 2**: Secure Your Application Load Balancer (ALB) Security Group
This is where you prevent direct public access to your ALB, ensuring all traffic comes via CloudFront.

  1. **Create/Modify ALB Security Group**:

    - **Inbound Rules**:

      - Allow inbound traffic on port 80 (HTTP) and/or 443 (HTTPS) only from the AWS-managed prefix list for CloudFront.

      - **How to add the CloudFront prefix list**:

        - In the source field of your security group inbound rule, choose Custom.

        - Start typing com.amazonaws.global.cloudfront.origin-facing (for IPv4) or com.amazonaws.global.cloudfront.ipv6-origin-facing (for IPv6). Select the appropriate entry from the dropdown. This ensures traffic only comes from CloudFront's origin-facing IP ranges.

        - **Example**:

          - **Type**: HTTP (or Custom TCP)

          - **Protocol**: TCP

          - **Port Range**: 80

          - **Source**: pl-xxxxxxxx (com.amazonaws.global.cloudfront.origin-facing prefix list)

          - **Type**: HTTPS (or Custom TCP)

          - **Protocol**: TCP

          - **Port Range**: 443

          - **Source**: pl-xxxxxxxx (com.amazonaws.global.cloudfront.origin-facing prefix list)

  - **Step 3**: Configure CloudFront to Add a Custom Header (Extra Layer of Security)
  While restricting by IP is good, IP ranges can change, and it doesn't guarantee the traffic came from your CloudFront distribution. Adding a custom header provides a stronger verification.

    - **In CloudFront Distribution Settings**:

      - Go to your CloudFront distribution.

      - Go to the Origins tab and select your ALB origin. Click Edit.

      - Under "Add custom header", enter a Header Name (e.g., X-Origin-Verify) and a Value (a long, random, secret string, e.g., my-super-secret-key-123xyz).

      - Save changes.

  2. **On Your ALB/EC2 Instance (Web Server)**:

    - If your ALB has a WAF attached: Create an AWS WAF rule that blocks requests unless they contain this specific custom header and value.

    - If no WAF or for deeper validation on EC2: Configure your web server (e.g., Nginx, Apache) or application code on the EC2 instance to check for this custom header. If the header is missing or its value is incorrect, reject the request (e.g., return a 403 Forbidden).

    - **Example (Nginx on EC2)**:

```Nginx

server {
    listen 80;
    server_name your_domain.com; # Or your EC2 public DNS

    # Check for the custom header from CloudFront
    if ($http_x_origin_verify != "my-super-secret-key-123xyz") {
        return 403; # Forbidden if header is missing or incorrect
    }

    location / {
        # Your application's proxy pass or root directory
        proxy_pass http://localhost:8080; # Example for an app running on 8080
        # root /var/www/html; # Example for static content
        # index index.html;
    }
}
```
(Remember to replace my-super-secret-key-123xyz with your actual secret value)

**Less Recommended**: Direct EC2 Instance (Without ALB)
While possible, this approach has limitations in terms of high availability, scalability, and ease of SSL management.

**Architecture Flow**:
Internet (User) -> CloudFront -> EC2 Instance

**Step 1**: Secure Your EC2 Instance Security Group (Directly with CloudFront IP Prefixes)
  - 1. **Create/Modify EC2 Security Group**:

  - **Inbound Rules**:

    - Allow inbound traffic on port 80 (HTTP) and/or 443 (HTTPS) only from the AWS-managed prefix list for CloudFront.

    - **Example**:

      - **Type**: HTTP (or Custom TCP)

      - **Protocol**: TCP

      - **Port Range**: 80

      - **Source**: pl-xxxxxxxx (com.amazonaws.global.cloudfront.origin-facing prefix list)

      - **Type**: HTTPS (or Custom TCP)

      - **Protocol**: TCP

      - **Port Range**: 443

      - **Source**: pl-xxxxxxxx (com.amazonaws.global.cloudfront.origin-facing prefix list)

  - **SSH Access (Port 22)**: Same as above, restrict to trusted IPs or use Session Manager.

**Step 2**: Configure CloudFront to Add a Custom Header (Crucial for Direct EC2)
This step becomes even more crucial when directly pointing CloudFront to an EC2 instance, as it's the primary way to confirm the request came from your CloudFront distribution, not just any CloudFront IP address (which could be another customer's distribution).

1. **In CloudFront Distribution Settings**:

  - Go to your CloudFront distribution.

  - Go to the Origins tab and select your EC2 instance origin. Click Edit.

  - Under "Add custom header", enter a Header Name (e.g., X-CloudFront-Auth) and a Value (your long, random, secret string).

  - Save changes.

2. **On Your EC2 Instance (Web Server/Application)**:

  - Your web server (e.g., Nginx, Apache) or application code must check for the presence and correct value of this custom header. If the header is invalid or missing, the request should be rejected (e.g., return a 403 Forbidden status). This is your primary defense against direct access.

**Why the ALB Approach is Superior**:
  - **High Availability & Fault Tolerance**: ALBs can distribute traffic across multiple EC2 instances in different Availability Zones, providing redundancy. If one EC2 instance fails, the ALB routes traffic to healthy ones.

  - **Scalability**: ALBs automatically scale to handle varying traffic loads, distributing requests efficiently to your backend EC2 instances.

  - **SSL/TLS Termination**: ALBs can handle SSL/TLS decryption, offloading this compute-intensive task from your EC2 instances. You manage certificates in AWS Certificate Manager (ACM) and associate them with the ALB.

  - **Centralized Security**: Placing WAF rules on the ALB provides an additional layer of security before requests even reach your EC2 instances.

  - **Simplified EC2 Security Groups**: Your EC2 instances only need to trust the ALB's security group, which is simpler and more robust than maintaining a dynamic list of CloudFront IPs on every EC2 security group.




