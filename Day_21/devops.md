---
title: "Day 21: AWS S3 "
description: "A summary of my 21th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 21
  - Challenge
---

### Table of contents :
- [Optimizing Data Storage in AWS S3 for Cost Efficiency and Performance](#optimizing-data-storage-in-aws-s3-for-cost-efficiency-and-performance)
- [What is CORS? How to Enable CORS in S3 ?](#what-is-cors-how-to-enable-cors-in-s3-)
- [Why is it needed for S3 ?](#why-is-it-needed-for-s3-)
- [How to Enable CORS in S3 ?](#how-to-enable-cors-in-s3-)


### Optimizing Data Storage in AWS S3 for Cost Efficiency and Performance
AWS S3 offers various features to balance cost and performance for your data.

1. **Simplify Data Lifecycle with Cost Efficiency**:

  - **S3 Storage Classes**: This is the primary way to achieve cost efficiency. By matching data to the right storage class based on its access pattern, you pay only for what you need. For example:
      - **S3 Standard**: Frequent access.
      - **S3 Standard-IA / One Zone-IA**: Infrequent access.
      - **S3 Glacier Instant Retrieval / Flexible Retrieval / Deep Archive**: Archiving for different retrieval speeds.
  - **S3 Lifecycle Policies**: Automate the movement of data between these storage classes. Define rules to transition objects to cheaper tiers as they age or become less frequently accessed, or to expire (delete) them after a certain period. This streamlines data management and reduces manual effort.
  - **S3 Intelligent-Tiering**: Automatically optimizes storage costs for data with unknown or changing access patterns without performance impact.

2. **Cost Efficiency and Performance with AWS S3**:

  - **Cost Efficiency**: Achieved through the strategic use of S3 Storage Classes and Lifecycle Policies, ensuring you're not paying for high-performance storage for cold data.
  - **Performance**:
      - **S3 Standard / S3 Express One Zone**: Designed for high throughput and low latency for frequently accessed data.
      - **S3 Transfer Acceleration**: Speeds up transfers over long distances by leveraging AWS Edge Locations.
      - **Multipart Uploads**: For large files, uploading in parts significantly improves transfer speed and resilience.
      - **Parallel Requests**: Making parallel GET and PUT requests to S3 can increase aggregate throughput.
      - **Region Selection**: Storing data in a Region geographically closest to your primary users or compute resources reduces latency.

3. **S3 Intelligent-Tiering**:

  - **What it is**: An S3 storage class that automatically moves objects between access tiers when access patterns change, without any performance impact or operational overhead.
  - **How it works**: It monitors access patterns. If an object hasn't been accessed for 30 consecutive days, it automatically moves it to the infrequent access tier. If it's accessed again, it moves it back to the frequent access tier. It can also include an archive instant access tier.
  - **Benefit**: Ideal for data with unpredictable access patterns, saving costs by automatically moving data to the most cost-effective tier without you having to define manual lifecycle rules or guess access patterns.

### What is CORS? How to Enable CORS in S3 ?
- **What is CORS?**
CORS stands for Cross-Origin Resource Sharing. It's a security mechanism implemented in web browsers that prevents a web page from making requests to a different domain than the one that served the web page. This is a crucial security measure to prevent malicious websites from performing unauthorized actions on behalf of a user.

  - **Origin**: The combination of protocol (e.g., http, https), domain (e.g., example.com), and port (e.g., 80, 443).
  - **Cross-Origin Request**: A request made by a web page to a resource in a different origin. By default, such requests are blocked by browsers.
CORS provides a way for a server (like S3) to explicitly tell a browser that it's permissible for a web page from a different origin to access its resources.

### Why is it needed for S3 ?
If you host a static website on www.mywebsite.com (which might be served via CloudFront or directly from S3), and that website's JavaScript code tries to fetch resources (like images, JSON files, or directly upload files) from an S3 bucket named my-data-bucket.s3.amazonaws.com, the browser will see this as a cross-origin request. Unless my-data-bucket explicitly allows www.mywebsite.com through its CORS configuration, the browser will block the request.

### How to Enable CORS in S3 ?

You enable CORS by adding a CORS configuration (a JSON file) to your S3 bucket.

1. **Navigate to your S3 bucket**:

  - Sign in to the AWS Management Console and go to the S3 service.
  - Select the bucket for which you want to enable CORS.

2. **Go to the "Permissions" tab**:

  - Click on the "Permissions" tab for your bucket.

3. **Scroll to "Cross-origin resource sharing (CORS)"**:

  - Find the "Cross-origin resource sharing (CORS)" section and click "Edit".

4. **Add your CORS configuration**:

  - You will see a text area where you can input a JSON array of CORS rules.

**Example CORS Configuration**:

```JSON

[
    {
        "AllowedHeaders": [
            "*"
        ],
        "AllowedMethods": [
            "GET",
            "PUT",
            "POST",
            "DELETE",
            "HEAD"
        ],
        "AllowedOrigins": [
            "https://www.mywebsite.com",
            "http://localhost:3000" // For local development
        ],
        "ExposeHeaders": [],
        "MaxAgeSeconds": 3000
    },
    {
        "AllowedHeaders": [
            "Authorization" // Example for specific header
        ],
        "AllowedMethods": [
            "GET"
        ],
        "AllowedOrigins": [
            "*" // CAUTION: Allows any origin. Use specific origins if possible.
        ],
        "ExposeHeaders": [],
        "MaxAgeSeconds": 3000
    }
]
```

- **Explanation of fields**:

  - **AllowedHeaders**: Headers that the browser is allowed to send in a preflight request or actual request. * allows all.
  - **AllowedMethods**: HTTP methods allowed for cross-origin requests (e.g., GET, PUT, POST).
  - **AllowedOrigins**: CRITICAL - Specifies the origins (domains) that are allowed to access your S3 resources. Always use specific domains (e.g., https://www.mywebsite.com) rather than * (wildcard) unless strictly necessary for security.
  - **ExposeHeaders**: Headers that clients can access in their application (e.g., ETag).
  - **MaxAgeSeconds**: How long the browser can cache the preflight response (in seconds). This reduces preflight requests.

5. **Save Changes**:

  - Click "Save changes".

- After configuring CORS, browsers will now allow JavaScript from the specified AllowedOrigins to make requests to your S3 bucket, enabling features like dynamic content loading or direct file uploads from your web application.



