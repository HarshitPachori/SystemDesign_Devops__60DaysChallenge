---
title: "Day 30: AWS Cloudfront"
description: "A summary of my 30th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 30
  - Challenge
---

### Table of contents :
- [What is Path-Based Routing in CloudFront?](#what-is-path-based-routing-in-cloudfront)


### What is Path-Based Routing in CloudFront?
CloudFront uses Cache Behaviors to determine how it processes incoming requests. Each cache behavior includes a Path Pattern (e.g., /images/*, /api/*, /blog/*) and an associated Origin. When a request comes to CloudFront, it checks the URL path against the defined path patterns in order of Precedence. The first matching path pattern determines which origin CloudFront forwards the request to, and how it handles caching and other settings.

The Default Cache Behavior (*) acts as a fallback and must always exist. It has the lowest precedence.

- **Why Use Path-Based Routing?**
  - **Consolidate Domains**: Serve content from multiple backend services (S3 buckets, EC2 instances, Load Balancers, API Gateways) under a single CloudFront domain or custom domain.

  - **Optimize Performance**: Cache static assets (images, CSS, JS) from an S3 bucket with aggressive caching, while routing dynamic API calls to an ALB or API Gateway with no caching.

  - **Microservices Architecture**: Route requests for different microservices to their respective backend origins (e.g., /users/* to User Service, /orders/* to Order Service).

  - **Blue/Green Deployments**: Route a percentage of traffic or specific user groups to a new version of your application hosted on a different origin.

  - **Cost Optimization**: Leverage the most cost-effective origin for different content types.

- **Steps to Set Up CloudFront Path-Based Routing with Multiple Origins**
Let's assume a common scenario:

  - **Origin 1 (Default)**: An S3 bucket for static website content (e.g., index.html, CSS, JS).

  - **Origin 2**: An Application Load Balancer (ALB) for dynamic API requests.

**Step 1: Prepare Your Origins**
Ensure your backend origins are set up and accessible (though not necessarily publicly accessible, as CloudFront will handle access).

  1. **S3 Bucket (for static content)**:

    - Create an S3 bucket (e.g., my-static-website-bucket).

    - Upload your static files (e.g., index.html, style.css, script.js).

    - Crucially, configure an Origin Access Control (OAC) for this bucket and update the S3 bucket policy to allow CloudFront access. This keeps your S3 bucket private.

  2. **Application Load Balancer (ALB) (for dynamic API)**:

    - Ensure your ALB is set up and routing traffic to your backend EC2 instances or containers.

    - Note down its DNS name (e.g., my-alb-123456789.us-east-1.elb.amazonaws.com).

    - Configure the ALB's security group to allow inbound traffic on the appropriate ports (e.g., 80, 443) only from the CloudFront managed prefix list (com.amazonaws.global.cloudfront.origin-facing). This ensures only CloudFront can reach your ALB.

**Step 2: Create/Update CloudFront Distribution**
  - 1. **Sign in to the AWS Management Console**: Go to https://aws.amazon.com/console/ and sign in.

  - 2. **Navigate to CloudFront**: Search for "CloudFront" and select the service.

  - 3. **Create a New Distribution (or select an existing one)**: Click "Create Distribution" or select an existing distribution ID.

**Step 3: Add Multiple Origins to Your CloudFront Distribution**
If you're creating a new distribution, you'll add your first origin during the initial setup. For existing distributions, you'll add more origins.

1. Go to the "Origins" Tab: In your CloudFront distribution settings, click on the "Origins" tab.

2. **Create Origin (for each backend)**:

  - Click "Create origin".

  - **Origin domain**:

    - **For S3**: Select your S3 bucket from the dropdown. If it's a private bucket, ensure you select "Yes, use OAC (recommended)" and create/select an OAC.

    - **For ALB**: Paste the DNS name of your Application Load Balancer.

    - **For API Gateway**: Paste the invoke URL of your API Gateway.

    - **For Custom Origin (EC2, etc.)**: Enter the domain name or public IP.

  - **Name**: Give it a unique and descriptive name (e.g., s3-static-origin, alb-api-origin). This Name will be the Origin ID used in cache behaviors.

  - **Protocol**: Choose HTTPS only for secure communication with your origin.

  - Configure other settings as needed (e.g., custom headers for security, origin shield).

  - Click "Create origin".

  - Repeat this for all your required origins.

**Step 4: Configure Cache Behaviors (Path-Based Routing)**
This is the core of path-based routing. You define rules that tell CloudFront which origin to send requests to based on the URL path.

  - 1. **Go to the "Behaviors" Tab**: In your CloudFront distribution settings, click on the "Behaviors" tab.

  - 2. **Understand Default Behavior**: You'll see a default behavior with a Path Pattern of *. This behavior handles all requests that don't match any other specific path patterns. By default, it's usually set to the first origin you configured.

  - 3. **Create New Behavior (for each specific path pattern)**:

    - Click "Create behavior".

    - **Path Pattern**: This is the most important setting for path-based routing.

      - Use wildcards (*) to match patterns.

      - **Examples**:

        - /api/* (matches /api/users, /api/products/123, but not /api)

        - /images/*.jpg (matches all .jpg files in the /images/ directory)

        - /blog/*

        - *.html

      - **Order of Precedence**: Behaviors are evaluated from top to bottom. CloudFront uses the first path pattern that a request matches. Therefore, more specific path patterns should have a lower Precedence number (higher priority) than more general ones. The * (default) behavior always has the highest Precedence number (lowest priority).

    - **Origin**: Select the Origin ID (the name you gave in Step 3) that you want to route requests matching this path pattern to.

    - **Viewer protocol policy**: (e.g., Redirect HTTP to HTTPS, HTTPS Only)

    - **Allowed HTTP methods**: (e.g., GET, HEAD for static content; GET, HEAD, OPTIONS, PUT, POST, PATCH, DELETE for APIs).

    - **Cache policy**:

      - For static content (e.g., /images/*), use CachingOptimized or a custom policy with a long TTL.

      - For dynamic content/APIs (e.g., /api/*), use CachingDisabled or a custom policy with a very short TTL and ensure you forward necessary headers, cookies, and query strings.

    - **Origin request policy**:

      - For static content, Managed-CachingOptimized is often sufficient.

      - For APIs, you'll likely need to forward all headers, cookies, and query strings. Use Managed-AllViewerExceptHostHeader or create a custom policy.

    - Configure other settings (e.g., compression, Lambda@Edge functions) as needed.

    - Click "Create behavior".

    - Repeat this for all your specific path patterns.

  4. **Adjust Precedence (if necessary)**:

    - After creating behaviors, you can reorder them by selecting a behavior and using the "Move up" or "Move down" buttons. Remember, lower precedence numbers mean higher priority.

    - Ensure your most specific paths are at the top (lowest precedence number).

**Step 5: Wait for Deployment and Test**
1. Monitor Distribution Status: After making changes, your CloudFront distribution status will change to "Deploying". Wait until it shows "Deployed". This can take 5-15 minutes.

2. Test Your Routes:

  - Access your CloudFront domain (or custom domain).

  - Test requests for different paths to ensure they are routed to the correct origins and behave as expected:

    - https://your-cloudfront-domain.com/index.html (should go to S3)

    - https://your-cloudfront-domain.com/images/logo.png (should go to S3)

    - https://your-cloudfront-domain.com/api/users (should go to ALB/API Gateway)

    - https://your-cloudfront-domain.com/blog/article1 (if you have a blog origin)