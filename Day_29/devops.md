---
title: "Day 29: AWS Cloudfront"
description: "A summary of my 29th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 29
  - Challenge
---

### Table of contents :
- [Steps to Create an S3 Private Bucket and Serve it via CloudFront](#steps-to-create-an-s3-private-bucket-and-serve-it-via-cloudfront)


### Steps to Create an S3 Private Bucket and Serve it via CloudFront
- **Step 1: Create a Private S3 Bucket**
  - **Sign in to the AWS Management Console**: Go to https://aws.amazon.com/console/ and sign in.

  - **Navigate to S3**: Search for "S3" in the search bar and select "S3".

  - **Create Bucket**: Click the "Create bucket" button.

  - **Configure Bucket Details**:

    - **Bucket name**: Enter a globally unique name (e.g., my-private-website-content-2025).

    - **AWS Region**: Choose the AWS Region closest to your origin content or your primary users.

    - **Object Ownership**: Select "ACLs enabled" (this is important for CloudFront OAC to work correctly with bucket policies).

    - **Block Public Access settings for this bucket**: Crucially, ensure ALL four checkboxes under "Block all public access" are checked. This is what makes your bucket private.

    - **Bucket Versioning**: (Optional but recommended for data protection) You can enable this if you want to keep multiple versions of your objects.

    - **Default encryption**: (Recommended) Keep "Server-side encryption with Amazon S3 managed keys (SSE-S3)" enabled.

    - Leave other settings as default unless you have specific requirements.

  - **Create Bucket**: Click "Create bucket".

- **Step 2: Upload Your Content to the S3 Bucket**
  - **Navigate to Your New Bucket**: From the S3 buckets list, click on the name of the bucket you just created.

  - **Upload Files**: Click the "Upload" button.

  - **Add Files/Folders**: Drag and drop your website files and folders, or use the "Add files" / "Add folder" buttons.

    - If you're hosting a website, make sure your index.html (or equivalent) is in the root of the bucket.

  - **Upload**: Click "Upload" at the bottom.

- **Step 3: Create a CloudFront Distribution with S3 Origin and OAC**
  - **Navigate to CloudFront:** Search for "CloudFront" in the AWS console and select it.

  - **Create Distribution**: Click the "Create Distribution" button.

  - **Select Origin Domain**:

    - In the "Origin domain" field, click inside the input box. Your newly created S3 bucket should appear in the dropdown list. Select it.

    - **Important**: CloudFront will automatically suggest creating an Origin Access Control (OAC).

      - For "S3 bucket access", select "Yes, use OAC (recommended)".

      - Click "Create new OAC". A pop-up will appear.

      - Give your OAC a descriptive name (e.g., my-website-oac). You can add a description.

      - Click "Create".

      - After the OAC is created, CloudFront will display a "Copy policy" button. Click this button to copy the generated S3 bucket policy. You will need to paste this into your S3 bucket permissions in the next sub-step.

  - **Default Cache Behavior Settings**:

    - **Viewer protocol policy**:

        - For most websites, choose "Redirect HTTP to HTTPS" for security.

    - **Allowed HTTP methods**:

        - For static websites, GET, HEAD is usually sufficient. If your site has forms or APIs that use POST, PUT, etc., select GET, HEAD, OPTIONS, PUT, POST, PATCH, DELETE.

    - **Cache policy**:

        - For static content, CachingOptimized (managed policy) is a good starting point.

        - For dynamic content or specific caching needs, you might choose CachingDisabled or create a custom cache policy.

    - **Origin request policy**:

        - For most static sites, Managed-CORS-S3Origin or Managed-AllViewerExceptHostHeader might be suitable if you need to forward specific headers/cookies/query strings. For simple static sites, Managed-CachingOptimized (which forwards minimal headers) might be fine.

  - **Distribution Settings (Optional but Recommended)**:

    - **Price Class**: Choose based on your target audience's geographic locations to balance performance and cost.

    - **Alternate domain names (CNAMEs)**: If you want to use your own custom domain (e.g., www.example.com), enter it here.

    - **Custom SSL certificate**: If using a custom domain, you must select an SSL certificate from AWS Certificate Manager (ACM) that covers your domain. (Certificates for CloudFront must be provisioned in us-east-1 region).

    - **Default root object**: If your website's main page is index.html, enter index.html here. This means when users go to your CloudFront domain, they'll automatically see index.html.

    - **Web Application Firewall (WAF)**: (Optional but recommended for security) You can associate an AWS WAF Web ACL here for additional protection.

    - **Distribution State**: Ensure it's Enabled.

  - **Create Distribution**: Click the "Create Distribution" button at the bottom.

- **Step 4: Update S3 Bucket Policy with OAC Permissions**
After creating the CloudFront distribution and copying the OAC policy:

  - **Go back to your S3 bucket**: Navigate to your S3 bucket in the AWS S3 console.

  - **Permissions Tab**: Click on the "Permissions" tab.

  - **Bucket Policy**: Scroll down to the "Bucket policy" section and click "Edit".

  - **Paste Policy**: Paste the policy you copied from the CloudFront OAC creation step into the policy editor. This policy grants your CloudFront distribution (via the OAC) permission to read objects from your private S3 bucket.

    - It will look something like this (with your specific bucket ARN and OAC ARN):

```JSON

{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "AllowCloudFrontServicePrincipalReadOnly",
            "Effect": "Allow",
            "Principal": {
                "Service": "cloudfront.amazonaws.com"
            },
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::your-bucket-name/*",
            "Condition": {
                "StringEquals": {
                    "AWS:SourceArn": "arn:aws:cloudfront::YOUR_ACCOUNT_ID:distribution/YOUR_CLOUDFRONT_DISTRIBUTION_ID"
                }
            }
        }
    ]
}
```

  - **Save Changes**: Click "Save changes".

- **Step 5: Verify and Test**
 - Wait for CloudFront Deployment**: Your CloudFront distribution status will change to "Deploying". Wait until it shows "Deployed" (this can take 5-15 minutes).

 - **Get CloudFront Domain Name**: Once deployed, copy the "Distribution domain name" from the CloudFront distribution details (e.g., d123456789abcdef.cloudfront.net).

 - **Test Access via CloudFront**: Open a web browser and navigate to your CloudFront domain name (or your custom domain if configured). You should see your website or content served correctly.

 - **Test Direct S3 Access (Verification of Privacy)**:
    - Try to access an object directly using its S3 URL (e.g., https://your-bucket-name.s3.your-region.amazonaws.com/index.html).

    - You should receive an "Access Denied" (403 Forbidden) error. This confirms your S3 bucket is private and only accessible via CloudFront.
