---
title: "Day 15: AWS IAM "
description: "A summary of my 15th day's learning in the 60-day challenge, covering basic concepts of IAM and Cloudshell."
keywords:
  - AWS
  - IAM
  - Cloudshell
  - Day 15
  - Challenge
---

### Table of contents :
- [AWS CLI: How to Handle Multiple AWS Accounts ?](#aws-cli-how-to-handle-multiple-aws-accounts-)
- [AWS CLI: Roles and How to Use Without Access ID and Secret Key ?](#aws-cli-roles-and-how-to-use-without-access-id-and-secret-key-)
- [What is CloudShell ?](#what-is-cloudshell-)
- [When to Use CloudShell ?](#when-to-use-cloudshell-)
- [What is IAM Access Advisor ?](#what-is-iam-access-advisor-)
- [What is IAM Credential Report ?](#what-is-iam-credential-report-)

### AWS CLI: How to Handle Multiple AWS Accounts ?
- Managing multiple AWS accounts with the AWS CLI is a common and crucial task for organizations. AWS CLI uses profiles to switch between different sets of credentials and configurations.

- **The Core Concept**: Named Profiles

By default, when you run aws configure, you set up the default profile. To work with multiple accounts (or different roles within the same account), you create named profiles.


- **Technical Difference**:

   - **default profile**: The configuration used when you don't explicitly specify a profile with --profile.
   - **Named profiles**: Separate configurations that you reference using --profile <profile-name> or by setting the AWS_PROFILE environment variable.

- How to Configure Multiple Accounts ?

1. **Install AWS CLI**: Ensure you have AWS CLI installed (preferably v2).

2. **Get Access Keys for Each Account/User**: For each AWS account you want to manage, create an IAM user with appropriate permissions (e.g., AdministratorAccess for initial setup, but later, fine-tune for least privilege). Generate an Access Key ID and Secret Access Key for each of these IAM users.

  - ***Best Practice***: Do NOT use your AWS account root user credentials for CLI access.
3. **Configure Profiles**: Use aws configure --profile <profile-name> for each account.

**Example**:

```Bash

# Configure for Account 1 (e.g., "dev-account")
aws configure --profile dev-account
AWS Access Key ID [None]: AKIAXXXXXXXXXXXXXXXX
AWS Secret Access Key [None]: wJalrXUtnFEMI/K7MDENG/bPxRfiCYXXXXXXXXXXXXXXXXX
Default region name [None]: us-east-1
Default output format [None]: json

# Configure for Account 2 (e.g., "prod-account")
aws configure --profile prod-account
AWS Access Key ID [None]: AKIAYYYYYYYYYYYYYYYY
AWS Secret Access Key [None]: wJalrXUtnFEMI/K7MDENG/bPxRfiCYYYYYYYYYYYYYYYYYY
Default region name [None]: us-east-2
Default output format [None]: json
```

This creates entries in your ~/.aws/credentials and ~/.aws/config files.

- **How to Use Multiple Accounts**:

Once configured, you can specify the profile with each command:

```Bash

# List S3 buckets in the 'dev-account'
aws s3 ls --profile dev-account

# Describe EC2 instances in 'prod-account'
aws ec2 describe-instances --profile prod-account

# Get caller identity (to verify which account/user you're using)
aws sts get-caller-identity --profile dev-account
```

You can also set an environment variable to temporarily use a profile as default for your current shell session:

```Bash

export AWS_PROFILE=prod-account
aws s3 ls # This will now list S3 buckets in 'prod-account'
```

### AWS CLI: Roles and How to Use Without Access ID and Secret Key ?

Using IAM roles with the AWS CLI is a more secure and recommended approach, especially for automated processes or when assuming permissions. You can use the AWS CLI without directly providing long-term access keys and secret keys in several scenarios:

1. **IAM Roles for EC2 Instances (Instance Profiles)**:

- **How it works**: When you launch an EC2 instance, you can assign an IAM role to it via an instance profile. The EC2 instance then automatically obtains temporary credentials for that role.
- **CLI Usage**: Any AWS CLI command executed from that EC2 instance will automatically use the permissions granted to the attached role. You don't need to run aws configure or store explicit keys on the instance.
- **Benefit**: No long-term credentials stored on the instance, reducing the risk of compromise.

2. **Assuming an IAM Role (for Cross-Account Access or Elevated Privileges)**:

- **How it works**: You (or an application) use your initial IAM user credentials (or another role) to call the sts:AssumeRole API operation. This operation returns temporary security credentials (an Access Key ID, Secret Access Key, and Session Token). You then use these temporary credentials for subsequent CLI commands.


- **CLI Configuration (for assume-role profiles)**:
You define a profile in your ~/.aws/config file that points to an IAM role to assume.

```bash 
Ini, TOML

# ~/.aws/config
[profile dev-user]
region = us-east-1
output = json

[profile dev-prod-access]
role_arn = arn:aws:iam::123456789012:role/DevProdAccessRole
source_profile = dev-user # This tells CLI to use 'dev-user' credentials to assume the role
region = us-east-1
output = json
```

- DevProdAccessRole (in account 123456789012) must have a trust policy allowing dev-user to assume it, and dev-user must have an IAM policy allowing sts:AssumeRole on DevProdAccessRole.
- **CLI Usage**:

```Bash

# Use the 'dev-prod-access' profile to implicitly assume the role
aws s3 ls --profile dev-prod-access
```
The AWS CLI handles the sts:AssumeRole call in the background to get temporary credentials.

3. **AWS CloudShell (Discussed below)**:

- **How it works**: CloudShell is pre-authenticated with the credentials of the user who launched it from the console. You don't need to configure keys.
- **Benefits of not using Access ID and Secret Key directly**:

  - **Enhanced Security**: Temporary credentials have a limited lifespan, reducing the window of opportunity for attackers if they are compromised.
  - **Least Privilege**: Roles allow you to grant specific, temporary permissions, aligning with the principle of least privilege.
  - **Centralized Management**: Permissions are managed on the role, not individual credentials.
  - **Auditability**: CloudTrail logs will show that a role was assumed, providing clearer audit trails.

### What is CloudShell ?
- AWS CloudShell is a browser-based, pre-authenticated shell that you can launch directly from the AWS Management Console. It provides a Linux-based environment where you can manage your AWS resources using the AWS CLI and a range of pre-installed development tools.


- **Key Features and Benefits**:

  - **Browser-Based**: No local installation or configuration required. You access it directly from your web browser.
  - **Pre-authenticated**: Automatically uses the credentials of the user who launched it from the console. This means you don't need to configure AWS Access Key ID and Secret Access Key.
  - **Pre-installed Tools**: Comes with AWS CLI (v2), common programming languages (Python, Node.js, PowerShell), Git, pip, npm, jq, vim, etc.
  - **Persistent Home Directory**: You get 1 GB of persistent storage in your home directory ($HOME) per AWS Region, allowing you to store scripts, configuration files, and other data that persists across sessions.
  - **Cost-Effective**: It's offered at no additional cost for interactive use.
  - **Security**: Inherits the security context of your console session. Supports features like Safe Paste for security.
  - **Convenience**: Ideal for quick troubleshooting, running scripts, and managing resources without setting up a local development environment.

### When to Use CloudShell ?

- Quick administrative tasks or troubleshooting.
- Learning AWS CLI commands without a local setup.
- Running small scripts to automate AWS operations.
- When you need a secure, temporary environment with AWS access from anywhere.

### What is IAM Access Advisor ?
- IAM Access Advisor is a feature within AWS IAM that helps you understand and refine permissions by showing you when and which AWS services your IAM users, groups, roles, or policies were last used to access.

- **Purpose and Benefits**:

  - **Implement Least Privilege**: Its primary purpose is to help you reduce overly permissive policies. By showing you which services an entity can access versus which services it actually accessed, you can identify unused permissions.
  - **Security Posture Improvement**: Helps remove unnecessary access, thereby shrinking your attack surface.
  - **Compliance**: Provides data that can be useful for auditing and compliance reporting, demonstrating that you are managing permissions effectively.
  - **Easy Identification of Unused Permissions**: Quickly see if an IAM entity has permissions for services they've never interacted with, indicating potential over-permissioning.

- **How it Works**:

IAM Access Advisor analyzes AWS CloudTrail logs to determine the last time an IAM entity used a particular service. It then presents this information in the IAM console, making it easy to review.

### What is IAM Credential Report ?
- The IAM Credential Report is a comprehensive, downloadable report that lists all IAM users in your AWS account and the status of their various credentials, including passwords, access keys, and MFA devices.

- **Purpose and Benefits**:

   - **Security Auditing**: Provides a snapshot of your account's credential security posture, crucial for regular security audits.
   - **Compliance Reporting**: Helps demonstrate compliance with security best practices and regulatory requirements (e.g., PCI DSS, HIPAA) that often mandate regular review of user credentials.
   - I**dentify Security Risks**:
        - **Unused Credentials**: Helps find access keys or passwords that haven't been used for a long time, which should ideally be disabled or removed.
        - **MFA Status**: Shows which users have MFA enabled or disabled.
        - **Password/Access Key Rotation**: Tracks when passwords and access keys were last changed, helping enforce rotation policies.
        - **Root User Activity**: Highlights if the root user's access keys are active (a major security anti-pattern).

- **How to Generate**:

   - Sign in to the AWS Management Console as an IAM user with administrative permissions.
   - Navigate to the IAM service.
   - In the left-hand navigation pane, click on Credential report.
   - Click Download report. The report is downloaded as a CSV file.