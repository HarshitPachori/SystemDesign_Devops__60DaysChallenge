---
title: "Day 33: AWS Cloudfront"
description: "A summary of my 33th day's learning in the 60-day challenge, covering basic concepts of Cloudfront."
keywords:
  - AWS
  - Cloudfront
  - Day 33
  - Challenge
---

### Table of contents :
- [How to disable and delete distribution in cloudfront ?](#how-to-disable-and-delete-distribution-in-cloudfront-)


### How to disable and delete distribution in cloudfront ?
To disable and then delete an AWS CloudFront distribution, you need to follow a specific sequence of steps. CloudFront requires a distribution to be disabled before it can be deleted.

- **Why Disable Before Deleting ?**
Disabling a distribution stops CloudFront from serving content from that distribution. It's a safety measure to ensure that traffic is no longer being routed through it before it's permanently removed. It also allows time for any DNS changes (if you're using a custom domain) to propagate before the distribution is gone.

- **Steps to Disable a CloudFront Distribution**
  - **Sign in to the AWS Management Console**:

    - Go to https://aws.amazon.com/console/ and sign in to your AWS account.

  - **Navigate to CloudFront**:

    - In the AWS console, search for "CloudFront" in the search bar and select the service.

  - **Select the Distribution to Disable**:

    - On the CloudFront distributions page, you'll see a list of your distributions.

    - Find the distribution you want to disable.

    - Select the checkbox next to the distribution's ID.

  - **Disable the Distribution**:

    - With the distribution selected, click the "Disable" button at the top of the distributions list.

    - A confirmation dialog box will appear. Click "Disable" again to confirm.

  - **Wait for Deployment**:

    - The status of your distribution will change to "Deploying".

    - You must wait until the status changes to "Disabled". This process can take several minutes (typically 5-15 minutes) as CloudFront propagates the change across its edge locations. Do not proceed to delete until the status is "Disabled".

    - Note on Associated WAF ACLs: If your CloudFront distribution is associated with an AWS WAF Web ACL, you might need to disassociate it first before you can disable the distribution. If you encounter an error related to WebACLId, go to the "General" tab of your distribution, click "Edit", and set "AWS WAF Web ACL" to "None", then save changes. Wait for this change to deploy, then try disabling again.

- **Steps to Delete a CloudFront Distribution**
Once the distribution's status shows "Disabled":

  - **Select the Disabled Distribution**:

    - On the CloudFront distributions page, ensure the distribution you just disabled is still selected (check the box next to its ID).

  - **Delete the Distribution**:

    - Click the "Delete" button at the top of the distributions list.

    - A confirmation dialog box will appear. It will typically ask you to confirm by typing the distribution's ID.

    - Type the distribution ID exactly as prompted and click "Delete" to confirm.

  - **Final Confirmation**:

    - The distribution will now disappear from your list or show a "Deleting" status briefly before being fully removed.

- **Important Considerations**:
  - **DNS Records**: If you were using a custom domain name (CNAME) with your CloudFront distribution, remember to update or delete the corresponding DNS records in your DNS service (e.g., Route 53, GoDaddy, Cloudflare) after the CloudFront distribution is completely deleted. If you don't, your custom domain might point to a non-existent CloudFront distribution, causing DNS resolution issues for your users.

  - **Associated Resources**: Deleting a CloudFront distribution does not automatically delete its associated origins (e.g., S3 buckets, EC2 instances, Load Balancers). You will need to manually delete those resources if they are no longer needed, to avoid incurring further AWS charges.

  - **Cost**: You stop incurring charges for CloudFront data transfer and requests once the distribution is disabled. However, you might still be charged for associated resources (like S3 storage) until they are deleted.


