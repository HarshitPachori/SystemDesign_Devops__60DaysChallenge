---
title: "Day 18: AWS S3 "
description: "A summary of my 18th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 18
  - Challenge
---

### Table of contents :
- [AWS S3 Website Redirects](#aws-s3-website-redirects)
- [S3 Transfer Acceleration](#s3-transfer-acceleration)
- [How to Enable S3 Transfer Acceleration ?](#how-to-enable-s3-transfer-acceleration-)
- [How to Use S3 Transfer Acceleration:](#how-to-use-s3-transfer-acceleration)

### AWS S3 Website Redirects
You can configure redirects for objects in an S3 static website in two main ways:

1. **Using Object Metadata (for individual object redirects)**:
This is useful if you move a file and want the old URL to point to the new one, or if you want to redirect an object to an external URL.

- **Steps (AWS Console)**:

   - Navigate to your S3 bucket in the AWS Console.
   - Click on the object you want to redirect.
   - Go to the "Properties" tab.
    -Scroll down to "Metadata" and click "Edit".
   - Add a new metadata entry:
       - **Key**: Website Redirect Location
       - **Value**: The full URL (e.g., https://www.example.com/new-page.html) or the S3 key of the target object within the same bucket (e.g., /new-location/index.html).
   - Click "Save changes".
   - Now, when someone accesses the original object's URL, S3 will return a 301 Moved Permanently (if external URL) or 302 Found (if internal S3 key) redirect.

2. **Using Routing Rules (for complex redirects or folder redirects)**:
This is configured at the bucket level in the static website hosting settings. It's powerful for redirecting based on prefixes, specific object keys, or HTTP error codes.

- Steps (AWS Console):

   - Navigate to your S3 bucket in the AWS Console.
   - Go to the "Properties" tab.
   - Scroll down to "Static website hosting" and click "Edit".
   - Ensure "Static website hosting" is enabled.
   - Scroll down to "Routing rules" and click "Edit".
   - Enter a JSON configuration for your rules. Each rule can specify:
        - **Condition**: When the rule should apply (e.g., if a key prefix matches, or a specific HTTP error code occurs).
        - **Redirect**: Where to redirect and what HTTP response code to use.

- **Example Routing Rules (JSON)**:

- **Redirect all requests from oldfolder/ to newfolder/**:


```json
[
    {
        "Condition": {
            "KeyPrefixEquals": "oldfolder/"
        },
        "Redirect": {
            "ReplaceKeyPrefixWith": "newfolder/"
        }
    }
]
```
- **Redirect a specific page (oldpage.html) to an external URL**:

```json
[
    {
        "Condition": {
            "KeyEquals": "oldpage.html"
        },
        "Redirect": {
            "HostName": "www.external-site.com",
            "ReplaceKeyWith": "new-external-page.html",
            "HttpRedirectCode": "301",
            "Protocol": "https"
        }
    }
]
- Click "Save changes".

### How to Change Prefix in Static Website S3
"Changing a prefix" in an S3 static website usually means changing the path segment of your URLs. Since S3 uses object keys as the full path, this typically involves either:

1. **Renaming/Moving Objects within the Bucket**:
This is the most direct way. If you have objects under s3://your-bucket/old-prefix/image.jpg and you want them under s3://your-bucket/new-prefix/image.jpg, you need to copy/move the objects.

   - AWS Console:
      - Navigate to your S3 bucket.
      - Select the folder (prefix) or individual objects you want to move.
      - Click "Actions" -> "Copy" (or "Move").
      - Specify the new destination folder/prefix within the same bucket.
      - After copying, if you used "Copy," remember to delete the old objects/folders if you don't need them.
   - AWS CLI:
```bash

# Copy objects from old-prefix to new-prefix (recursive)
aws s3 cp s3://your-bucket-name/old-prefix/ s3://your-bucket-name/new-prefix/ --recursive

# (Optional) Delete old objects after verification
aws s3 rm s3://your-bucket-name/old-prefix/ --recursive

- Using S3 Website Redirect Routing Rules (as above):
If you don't want to physically move files but want requests to old-prefix/ to redirect to new-prefix/, you'd use a routing rule:


```json
[
    {
        "Condition": {
            "KeyPrefixEquals": "old-prefix/"
        },
        "Redirect": {
            "ReplaceKeyPrefixWith": "new-prefix/"
        }
    }
]
```
This doesn't change the underlying object keys but redirects traffic.

3. **Using CloudFront (for advanced routing and dynamic content)**:
For more complex prefix changes or if you need to serve parts of your site from different S3 prefixes or even different origins (e.g., part from S3, part from an EC2 instance), you would use AWS CloudFront. CloudFront allows you to define behaviors that map URL patterns (prefixes) to different origins.

- Create a CloudFront distribution pointing to your S3 bucket.
- Define a new "Behavior" for the desired URL path pattern (e.g., /old-prefix/*).
- Configure this behavior to map to a new "Origin Path" (e.g., /new-prefix/).
- This provides a powerful way to manage URL structures without changing underlying S3 object keys.

### S3 Transfer Acceleration
- **What it is**:
S3 Transfer Acceleration is an S3 feature that enables fast, easy, and secure transfers of files over long distances between your client and an S3 bucket. It leverages AWS CloudFront's globally distributed edge locations to optimize data paths.

- **How it Works**:

  - When Transfer Acceleration is enabled for a bucket, clients upload/download data to a special CloudFront edge location URL, not directly to the S3 bucket's regional endpoint.
  - The data is routed over the optimized AWS global network backbone to the target S3 bucket, bypassing the public internet for the majority of the transfer path.
  - This minimizes the impact of internet congestion, latency, and routing issues, leading to significantly faster transfer speeds, especially over long distances.

- **Benefits**:

   - **Faster Transfers**: Accelerates data transfers, particularly for geographically di
spersed users.
   - **Reliability**: Uses the robust AWS backbone network, reducing packet loss and improving reliability.
   - **Simplicity**: Easy to enable and use; no client-side software installation is required.

### How to Enable S3 Transfer Acceleration ?

- **Navigate to your S3 bucket**: Sign in to the AWS Management Console, go to S3, and select the bucket you want to accelerate.
- **Go to the "Properties" tab**: Click on the "Properties" tab.
- **Find "Transfer Acceleration"**: Scroll down to the "Transfer Acceleration" section.
- **Click "Edit"**: Change the status from "Disabled" to "Enabled."
- **Save Changes**: Click "Save changes."

### How to Use S3 Transfer Acceleration:

Once enabled, your bucket will have a new, unique transfer acceleration endpoint. You use this specific endpoint in your AWS CLI commands, SDK code, or other applications instead of the regular S3 bucket endpoint.

- **Regular S3 Endpoint**: your-bucket-name.s3.your-region.amazonaws.com
- **Transfer Acceleration Endpoint**: your-bucket-name.s3-accelerate.amazonaws.com
   - (Note: For cross-Region operations, it might use your-bucket-name.s3-accelerate.dualstack.amazonaws.com for IPv6 support.)

**Example Usage (AWS CLI)**:

- **To upload a file using acceleration**:

```Bash

aws s3 cp my-local-file.txt s3://your-accelerated-bucket-name/path/to/file.txt --endpoint-url https://your-accelerated-bucket-name.s3-accelerate.amazonaws.com
```
- **To download a file using acceleration**:

```Bash

aws s3 cp s3://your-accelerated-bucket-name/path/to/file.txt my-local-file.txt --endpoint-url https://your-accelerated-bucket-name.s3-accelerate.amazonaws.com
```

**Important Considerations**:

- **Cost**: S3 Transfer Acceleration incurs additional costs based on the amount of data transferred through the accelerated endpoint. You pay for both the S3 storage and the acceleration usage.
- **Region Support**: Transfer Acceleration works best when your client is geographically distant from your S3 bucket. For clients close to the bucket's Region, the performance benefit might be minimal, and the extra cost might not be justified. AWS automatically checks if acceleration will be beneficial and will sometimes route traffic directly if it's not.
- **DNS Naming**: Using the s3-accelerate endpoint ensures your requests are routed through the CloudFront edge network.
