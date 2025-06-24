---
title: "Day 24: AWS Cloudfront"
description: "A summary of my 24th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 24
  - Challenge
---

### Table of contents :
- [Create AWS Cloudfront Distribution](#create-aws-cloudfront-distribution)


### Create AWS Cloudfront Distribution
- To create your first AWS CloudFront distribution with an EC2 instance as the origin, you'll be setting up a scenario where CloudFront caches and delivers content that is served by a web server running on your EC2 instance.

- This is a common setup for dynamic websites or applications, where CloudFront acts as a global caching layer, improving performance and security.

- **Prerequisites**:
Before you start, you'll need:

  - **An EC2 Instance**: An EC2 instance running and accessible from the internet (either via a public IP/DNS or behind a Load Balancer).
  - **Web Server**: A web server installed and running on your EC2 instance (e.g., Apache, Nginx, IIS) that serves content over HTTP or HTTPS.
  - **Security Group**: The EC2 instance's security group must allow inbound traffic on port 80 (HTTP) or 443 (HTTPS) from anywhere (0.0.0.0/0) or from CloudFront's IP ranges (though using CloudFront's specific IPs can be complex; often a Load Balancer helps simplify this).

- **Note on Origin**: For EC2 instances as origins, it's generally recommended to place the EC2 instance(s) behind an Application Load Balancer (ALB). This provides:

  - High availability (distributes traffic across multiple EC2 instances).
  - Built-in health checks.
  - SSL/TLS termination.
  - Easier management and scalability compared to directly exposing EC2 public IPs.
For this guide, I will assume you are using the public DNS name of your EC2 instance or the DNS name of an ALB in front of it. Using an ALB is the more robust production-ready approach.

- **Steps to Create a CloudFront Distribution with an EC2 Instance Origin**
Here's a step-by-step guide using the AWS Management Console:

  1. **Sign in to the AWS Management Console**:

    - Go to https://aws.amazon.com/console/ and sign in with your AWS account credentials.
  2. **Navigate to the CloudFront Service**:

    - In the "Search services" bar at the top, type "CloudFront" and select "CloudFront".
  3. **Create a New Distribution**:

    - On the CloudFront console dashboard, click the "Create Distribution" button.
  4. **Select Origin Domain (Your EC2 Instance/ALB)**:

    - In the "Select a delivery method for your content" section, choose "Web" (this is the default and typically what you want for EC2). Click "Get Started" (or it might take you directly to the next page in the newer console).
    - **On the "Create Distribution" page, under the "Origin" section**:
        - **Origin domain**: Click inside the input box. AWS will suggest available EC2 instances, Load Balancers, or S3 buckets.
          - **If using an ALB**: Select the DNS name of your Application Load Balancer from the dropdown.
          - **If using an EC2 Instance directly**: You might need to manually paste the public IPv4 DNS or public IP address of your EC2 instance here. Avoid using the instance ID.
        - **Protocol**: Choose the protocol your EC2 web server uses:
            - **HTTP Only**: If your web server only serves over HTTP.
            - **HTTPS Only**: If your web server is configured with SSL/TLS certificates and serves over HTTPS.
            - **Match Viewer**: If CloudFront should use the same protocol as the viewer's request (e.g., if viewer uses HTTPS, CloudFront uses HTTPS to origin). This is often a good choice if your origin supports both.
            - **Recommendation**: For production, HTTPS Only for the origin protocol (and ensure your EC2/ALB has a valid SSL certificate).
        - **HTTP Port/HTTPS Port**: Leave as default (80 and 443 respectively) unless your web server uses non-standard ports.
        - **Origin ID**: This is automatically populated based on your origin domain. You can customize it if needed (e.g., my-ec2-web-server).
  
  5. **Default Cache Behavior Settings**:
    This section controls how CloudFront caches content and handles requests.

    - **Viewer Protocol Policy**:
       - **HTTP and HTTPS**: Allows both.
       - **Redirect HTTP to HTTPS**: Redirects all HTTP requests to HTTPS (Recommended for security).
       - **HTTPS Only**: Only allows HTTPS requests.
    - **Allowed HTTP Methods**:
       - **GET, HEAD**: For static content.
       - **GET, HEAD, OPTIONS**: If your API uses OPTIONS.
       - **GET, HEAD, OPTIONS, PUT, POST, PATCH, DELETE**: For dynamic applications that require all HTTP methods (e.g., APIs). Choose based on your web application's needs.
    - **Cache Policy**:
        - **Managed Policies**: AWS provides pre-defined policies (e.g., CachingOptimized for common static assets, Managed-AllViewerExceptHostHeader for dynamic content).
        - **Custom Policy**: You can create your own to fine-tune caching behavior (e.g., specify TTLs, headers, query strings, cookies to forward).
        - **Recommendation**: Start with CachingOptimized for static files, and consider a Managed-AllViewerExceptHostHeader or custom policy for dynamic content/APIs that you want to cache. If your content is truly dynamic and cannot be cached, select CachingDisabled.
    - **Origin Request Policy**:
        - Controls what information (headers, cookies, query strings) CloudFront forwards to your origin.
        - **Recommendation**: For dynamic content, you often need to forward specific headers, cookies, or all query strings. Use AllViewer or create a custom policy. For static, simpler behavior is fine.
  
  6. **Distribution Settings**:

    - **Price Class**: Determines the AWS edge locations that serve your content (influences cost and global reach).
      - **Use all edge locations (best performance)**: Most expensive, widest global reach.
      - **Use only US, Canada, Europe, & Asia**: Mid-range cost and coverage.
      - **Use only US, Canada, & Europe**: Cheapest, least coverage.
    - **Alternate Domain Names (CNAMEs) - Optional**:
      - If you want to use your own domain name (e.g., www.example.com) instead of the CloudFront domain (e.g., d123456789.cloudfront.net).
      - You'll need to associate an AWS Certificate Manager (ACM) SSL/TLS certificate here for your custom domain if you're using HTTPS.
      - After creation, you'll create a CNAME record in your DNS provider pointing your custom domain to the CloudFront distribution's domain name.
    - **SSL Certificate**:
      - **Default CloudFront Certificate**: Uses the cloudfront.net domain (free).
      - **Custom SSL Certificate (example.com)**: Use if you have a custom domain. Requires a certificate from ACM (free for CloudFront) or imported.
    - **Default Root Object (Optional)**:
      - Specify a default file to serve when a user requests the root URL of your distribution (e.g., index.html).
    - **Logging (Optional)**: Enable logging to an S3 bucket to record all CloudFront access requests. Useful for analytics and security.
    - **IPv6**: Enabled by default (recommended).
    - **Distribution State**: Enabled (to start deploying immediately).
  
  7. **Create Distribution**:

    - Click the "Create Distribution" button at the bottom of the page.

**Deployment and Testing**:
- **Deployment Time**: It takes some time (typically 5-15 minutes, sometimes longer) for the CloudFront distribution to deploy to all edge locations. The "Status" will change from "Deploying" to "Deployed".
- **Distribution Domain Name**: Once deployed, you'll see a "Domain Name" for your distribution (e.g., d123456789.cloudfront.net).
- **Test**: Open this CloudFront domain name in your web browser. You should see the content served from your EC2 instance, but now through the CloudFront CDN.
