---
title: "Day 13: AWS IAM "
description: "A summary of my 13th day's learning in the 60-day challenge, covering basic concepts of IAM."
keywords:
  - AWS
  - IAM
  - Day 13
  - Challenge
---

### Table of contents :
- [What is AWS Identity and Access Management (IAM) ?](#what-is-aws-identity-and-access-management-iam-)
- [Uses of AWS IAM](#uses-of-aws-iam)
- [Benefits of AWS IAM](#benefits-of-aws-iam)
- [Key IAM Components](#key-iam-components)

### What is AWS Identity and Access Management (IAM) ?
- AWS Identity and Access Management (IAM) is a web service that helps you securely control access to AWS resources. With IAM, you can manage who is authenticated (signed in) and authorized (has permissions) to use resources in your AWS account.


- Think of IAM as the gatekeeper and the permission system for your entire AWS environment. It allows you to specify who can access your AWS services (like EC2, S3, RDS, Lambda, etc.) and what actions they can perform on those resources.

- **Core Purpose** :
The core purpose of IAM is to provide fine-grained access control and adhere to the Principle of Least Privilege. This principle states that users and services should only be granted the minimum permissions necessary to perform their tasks, and no more.

### Uses of AWS IAM
IAM is used for a wide range of access control scenarios:

- **Granting Access to Human Users**:

    - Creating individual IAM users for employees, contractors, or partners who need to access the AWS Management Console or programmatic access (CLI/SDK).
    - Organizing users into IAM Groups to simplify permission management for common job functions (e.g., "Developers," "Admins," "Auditors").
- **Granting Access to AWS Services**:

    - Creating IAM Roles that AWS services (like EC2 instances, Lambda functions, ECS tasks) can assume to interact with other AWS services. For example, an EC2 instance might need a role that allows it to read from an S3 bucket or write to a DynamoDB table.
- **Cross-Account Access**:

    - Enabling secure access to resources in one AWS account from another AWS account, typically using IAM Roles.
- **Federated Access**:

    - Allowing users from external identity providers (like corporate directories via Active Directory, Google G Suite, Okta, or even social media logins like Google/Facebook) to access your AWS account using their existing credentials without creating explicit IAM users in AWS. They assume an IAM Role with temporary credentials.

- **Programmatic Access**:

    - Generating access keys (access key ID and secret access key) for applications or scripts that need to interact with AWS services via the AWS CLI or SDKs.
- **Security Best Practices Enforcement**:

    - Setting password policies for IAM users (e.g., minimum length, required characters, password expiration).
    - Enabling Multi-Factor Authentication (MFA) for enhanced security on user logins.

### Benefits of AWS IAM
- **Enhanced Security**:

    - **Least Privilege**: Enforces the principle of least privilege, significantly reducing the attack surface.
    - **Centralized Control**: Provides a single point of control for all access permissions across your AWS environment.
    - **Multi-Factor Authentication (MFA)**: Adds an extra layer of security for user logins.
    - **Temporary Credentials**: IAM Roles provide temporary, short-lived credentials, which are more secure than long-term access keys.
- **Granular Permissions**:

    - You can define very specific permissions (e.g., "allow User A to read only S3 bucket 'my-data-bucket' in Region 'us-east-1'"). This allows precise control over actions and resources.
- **Scalability and Flexibility**:

    - Easily manage access for a small team or thousands of users and hundreds of services.
    - Supports various access patterns (human users, AWS services, federated identities).
- **Operational Efficiency**:

    - **Reduced Overhead**: Simplifies permission management, especially with IAM Groups and Roles.
    - **Automation**: Integrates with other AWS services and automation tools to manage access programmatically.
- **Compliance**:

    - Helps organizations meet various regulatory compliance requirements (e.g., GDPR, HIPAA, PCI DSS) by enforcing strict access controls and providing audit trails (via integration with AWS CloudTrail).
- **No Additional Cost**:

    - IAM itself is a feature of your AWS account and is offered at no additional charge. You only pay for the AWS resources that your users or services interact with.

### Key IAM Components
To understand IAM fully, it's essential to grasp its core components:

- **IAM User**:

    - Represents a person or an application that needs to interact with AWS.
    - Each user has unique credentials (username/password for console, access keys for programmatic access).
    - It's a best practice to create individual IAM users for each person instead of sharing the AWS account root user credentials.
- **IAM Group**:

    - A collection of IAM users.
    - You attach policies to a group, and all users in that group inherit those permissions.
    - **Benefit**: Simplifies permission management for multiple users with similar job functions (e.g., give "Administrators" group full EC2 access, and all users added to that group get it). Users can belong to multiple groups.

- **IAM Role**:

    - An identity that you can create with specific permissions.
    - Unlike a user, a role does not have standard long-term credentials (like passwords or access keys) associated with it. Instead, entities assume a role to obtain temporary security credentials.

    - **Use Cases**:
        - AWS Services: An EC2 instance assumes a role to get permissions to access S3.
        - Cross-Account Access: A user in Account A assumes a role in Account B to access resources in Account B.
        - Federated Users: Users from external identity providers assume a role to gain access.
- **IAM Policy**:

    - A JSON document that defines permissions. It specifies what actions are Allowed or Denyed on which AWS Resources, under what Conditions.
    - **Structure**:
```json
 {
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:ListBucket"
      ],
      "Resource": [
        "arn:aws:s3:::my-example-bucket/*",
        "arn:aws:s3:::my-example-bucket"
      ]
    },
    {
      "Effect": "Deny",
      "Action": "s3:DeleteObject",
      "Resource": "arn:aws:s3:::my-example-bucket/important-data/*"
    }
  ]
}
 
```

- **Types of Policies**:
    - **Identity-based policies**: Attached to IAM users, groups, or roles.
    - **Resource-based policies**: Attached directly to a resource (e.g., an S3 bucket policy, SQS queue policy) to control who can access that specific resource.