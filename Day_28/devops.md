---
title: "Day 28: AWS Cloudfront"
description: "A summary of my 28th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 28
  - Challenge
---

### Table of contents :
- [Remove Data from Cloudfront Edge](#remove-data-from-cloudfront-edge)


### Remove Data from Cloudfront Edge

To remove data from CloudFront edge locations, you primarily have two main strategies:

1. **Invalidation**: Explicitly telling CloudFront to remove specific content from its cache.

2. **Versioning**: Changing the name of the file so CloudFront treats it as new content.

While invalidation is a direct way to remove content, versioning is generally the recommended best practice for updating content, especially for frequently changing assets.

**Method 1: Invalidation (Explicit Removal)**
Invalidation is the process of marking cached content as expired in CloudFront's edge locations. The next time a user requests that content, CloudFront will fetch the latest version from your origin.

- **When to use**:

  - For urgent content removal (e.g., sensitive data, legal requirements).

  - When you cannot use versioning (e.g., a root HTML file that cannot have its URL changed).

  - For infrequent updates.

- **Steps to Perform Invalidation**:

  You can perform invalidation via the AWS Management Console, AWS CLI, or AWS SDKs.

- **Option A: Using the AWS Management Console**
1. Sign in to the AWS Management Console: Go to https://aws.amazon.com/console/ and sign in.

2. Navigate to CloudFront: Search for "CloudFront" and select the service.

3. Select Your Distribution: On the CloudFront dashboard, click on the ID of the distribution you want to manage.

4. Go to the "Invalidations" Tab: Click on the "Invalidations" tab.

5. Create Invalidation: Click the "Create Invalidation" button.

6. Specify Object Paths: In the "Object paths" text area, enter the full paths of the objects you want to invalidate, one path per line.

  - To invalidate a single file: /images/logo.png

  - To invalidate all files in a specific directory: /css/*

  - To invalidate ALL content in your distribution (use with caution): /*

    Note: Paths must start with a /.

7. Confirm and Create: Review the paths and click "Create Invalidation".

8. Monitor Status: The invalidation request will show a status of "InProgress" and then "Completed" once propagated. This usually takes a few minutes.

- **Option B: Using the AWS Command Line Interface (CLI)**
1. Ensure AWS CLI is configured: Make sure you have the AWS CLI installed and configured with appropriate credentials.

2. Run the create-invalidation command:

```Bash

# To invalidate a single file
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/path/to/your/file.ext"

# To invalidate multiple specific files
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/path/to/file1.ext" "/path/to/file2.ext"

# To invalidate all files under a specific directory (using wildcard)
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/js/*"

# To invalidate ALL content in the distribution (root path with wildcard)
aws cloudfront create-invalidation --distribution-id YOUR_DISTRIBUTION_ID --paths "/*"
```
Replace YOUR_DISTRIBUTION_ID with the actual ID of your CloudFront distribution (e.g., E123456789ABCDEF).

- **Important Considerations for Invalidation**:

  - **Cost**: AWS offers a free tier of 1,000 invalidation paths per month per distribution. Beyond that, charges apply per invalidation path. Invaliding /* counts as one path but can lead to increased origin load and data transfer costs as CloudFront re-fetches everything.

  - **Propagation Time**: Invalidation is not immediate. It takes time for the request to propagate to all edge locations globally.

  - **Browser Caching**: Invalidation clears CloudFront's cache, but it doesn't clear a user's browser cache or any intermediate proxy caches. For immediate updates, versioning is more effective.

**Method 2: Versioning (Recommended Best Practice for Updates)**
Instead of explicitly removing content, you change the file's name or add a query string parameter to its URL when the content is updated. CloudFront treats this as a new, unique object and fetches it from your origin, caching it.

- **When to use**:

  - For frequently updated static assets (CSS, JavaScript, images, fonts).

  - To ensure immediate updates for users and bypass browser caches.

  - To avoid invalidation costs.

- **How it works**:

1. **Change the File Name**:

  - Instead of style.css, you deploy style-v1.0.css or style.12345.css (where 12345 is a hash of the file content or a timestamp).

  - Your application code or build process then updates all references to this new file name.

  - When a user requests style.12345.css, CloudFront sees it as a new object, fetches it from your origin, and caches it. The old style.oldversion.css remains in cache until its TTL expires but is no longer requested by clients.

2. **Add a Query String Parameter**:

  - Instead of image.jpg, you reference image.jpg?v=1 or image.jpg?timestamp=1678886400.

  - When the content changes, you update the query string parameter (e.g., image.jpg?v=2).

  - **Important**: For this to work, your CloudFront distribution's cache behavior must be configured to forward query strings to the origin and include them in the cache key. This is often achieved by using a cache policy that includes AllViewer or a custom policy that forwards specific query string parameters.

- **Benefits of Versioning**:

  - **No Invalidation Costs**: You don't pay for invalidation requests.

  - **Immediate Updates**: Users immediately request the new URL, ensuring they get the latest content.

  - **Bypasses Browser Caches**: Since the URL is different, browsers also treat it as a new file, preventing stale content from being served from the client-side cache.

  - **Reduced Origin Load**: Only new or updated content is fetched from the origin, not everything.
