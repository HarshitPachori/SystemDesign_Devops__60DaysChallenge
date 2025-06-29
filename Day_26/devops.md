---
title: "Day 26: AWS Cloudfront"
description: "A summary of my 26th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 26
  - Challenge
---

### Table of contents :
- [What is AWS CloudFront Invalidation ?](#what-is-aws-cloudfront-invalidation-)
- [How CloudFront Invalidation Works](#how-cloudfront-invalidation-works)
- [How to Configure S3 Invalidation (Steps)](#how-to-configure-s3-invalidation-steps)


### What is AWS CloudFront Invalidation ?
AWS CloudFront Invalidation is the process of removing cached content from CloudFront's edge locations before its time-to-live (TTL) expires. When you invalidate a file, CloudFront marks that file as expired in its edge caches. The next time a viewer requests that file, CloudFront will fetch the latest version directly from your origin server, rather than serving the stale cached version.

- **Why is it needed?**

CloudFront caches content at its edge locations for a specified duration (the TTL). If you update content at your origin (e.g., replace an image, deploy a new JavaScript file, or update an HTML page) before its cached copy's TTL expires, CloudFront will continue to serve the old, stale version until the cache naturally expires.

Invalidation becomes necessary in scenarios like:

- **Urgent Content Updates**: You need a critical fix or new content to go live immediately.

- **Removing Content**: You need to take down content quickly due to legal, security, or compliance reasons.

- **Development/Testing**: When deploying changes in a development environment and you want to see them reflected instantly.

### How CloudFront Invalidation Works
1. **Request Submission**: You submit an invalidation request to CloudFront, specifying the paths of the objects you want to invalidate.

2. **Edge Location Update**: CloudFront receives the request and propagates it to all relevant edge locations.

3. **Cache Mark**: At each edge location, the specified objects are marked as invalid or expired in the cache. They are not immediately deleted from disk, but rather flagged as stale.

4. **Next Request**: When a viewer makes a request for an invalidated object:

  - The edge location checks its cache.

  - It finds the object marked as invalid.

  - It then forwards the request to your origin server to retrieve the latest version of the content.

  - The new content is fetched from the origin, served to the viewer, and then cached at that edge location for future requests.

- **Important Note**: Invalidation is not instantaneous. It typically takes a few minutes for the invalidation request to propagate globally across all edge locations. During this propagation time, some users might still receive the older cached content.

### How to Configure S3 Invalidation (Steps)
You can invalidate content using the AWS Management Console, AWS CLI, or AWS SDKs.

1. **Using the AWS Management Console**:
- **Sign in to the AWS Management Console**: Go to https://aws.amazon.com/console/ and log in.

- **Navigate to CloudFront**: In the "Search services" bar, type "CloudFront" and select it.

- **Select Your Distribution**: On the CloudFront dashboard, select the distribution for which you want to invalidate content.

- **Go to the "Invalidations" Tab**: Click on the "Invalidations" tab.

- **Create Invalidation**: Click the "Create Invalidation" button.

- **Specify Paths**:

  - In the "Object paths" text area, enter one path per line for each object you want to invalidate.

  - **Specific files**: /images/logo.png, /css/style.css, /index.html

  - **Wildcard (for multiple files)**: CloudFront supports a * wildcard at the end of a path.

    - **/images/***: Invalidates all files in the /images/ directory.

    - **/***: Invalidates all content in your distribution (use with caution, as it reloads everything from your origin and incurs higher cost/impact).

- **Create**: Click the "Create Invalidation" button.

- **Monitor Status**: The invalidation request will appear in the list with a "Status" of "InProgress" and then "Completed."

2. **Using the AWS CLI**:
You can use the aws cloudfront create-invalidation command.


```Bash

# Invalidate a single file
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/path/to/your/file.ext"

# Invalidate multiple specific files
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/path/to/file1.ext" "/path/to/file2.ext"

# Invalidate all files under a specific directory (using wildcard)
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/js/*"

# Invalidate ALL content in the distribution (root path with wildcard)
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/*"
```

- Replace YOUR_DISTRIBUTION_ID with your CloudFront distribution's ID (e.g., E123456789ABCDEF).

- **Best Practices & Considerations for Invalidation**
1. **Cost**:

  - AWS provides a free tier of 1,000 invalidation paths per month per distribution.

  - Beyond the free tier, you are charged per invalidation path. Invaliding /* counts as one path, but it forces CloudFront to re-fetch all content, potentially increasing origin load and data transfer costs from your origin.

2. Prefer Versioning over Invalidation:

  - For frequently updated static assets (CSS, JS, images), the recommended best practice is to use file versioning. This involves changing the file name when content changes (e.g., style.css to style-v2.css or script.js to script.12345.js where 12345 is a hash or timestamp).

  - **Benefits of Versioning**:

    - **No Invalidation Cost**: CloudFront treats style-v2.css as a completely new file, so it automatically fetches it from the origin and caches it. No invalidation needed.

    - **Immediate Update**: Users immediately get the new version because the URL they request is different.

    - **Bypasses Browser Caches**: Versioning also helps bypass aggressive browser caches, which might ignore invalidation requests.

3. **Automate Invalidation in CI/CD**:

  - Integrate invalidation requests into your Continuous Integration/Continuous Deployment (CI/CD) pipeline. After a successful deployment to your origin, trigger a CloudFront invalidation for the changed paths.

4. **Be Specific**:

  - Only invalidate the specific files or paths that have actually changed. Avoid /* unless absolutely necessary, to minimize cost and origin load.

5. **Monitor Invalidation Status**:

  - Always verify that your invalidation request has completed in the CloudFront console or via CLI.

