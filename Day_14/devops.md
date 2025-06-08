---
title: "Day 14: AWS IAM and CLI"
description: "A summary of my 14th day's learning in the 60-day challenge, covering basic concepts of IAM and CLI."
keywords:
  - AWS
  - IAM
  - AWS CLI
  - Day 14
  - Challenge
---

### Table of contents :
- [What are AWS IAM Service Groups?](#what-are-aws-iam-service-groups)
- [Benefits of AWS IAM Service Groups](#benefits-of-aws-iam-service-groups)
- [Password Policy for AWS Users](#password-policy-for-aws-users)
- [How to Configure (Console Steps) ?](#how-to-configure-console-steps-)
- [How to Configure (AWS CLI Example) ?](#how-to-configure-aws-cli-example-)
- [Multi-Factor Authentication (MFA) for Users](#multi-factor-authentication-mfa-for-users)
- [Why MFA is Important ?](#why-mfa-is-important-)
- [How to Enable MFA for an IAM User (Console Steps for Virtual MFA - Most Common) ?](#how-to-enable-mfa-for-an-iam-user-console-steps-for-virtual-mfa---most-common-)
- [What is AWS Command Line Interface (CLI) ?](#what-is-aws-command-line-interface-cli-)
- [How to Install and Configure AWS CLI ?](#how-to-install-and-configure-aws-cli-)
- [Create an EC2 Instance Using AWS CLI](#create-an-ec2-instance-using-aws-cli)

### What are AWS IAM Service Groups?

An IAM user group is a collection of IAM users. It's a way to manage permissions for multiple users simultaneously, rather than attaching policies to each individual user.

- **Logical Collection**: Groups allow you to logically categorize users who share similar job functions or responsibilities (e.g., "Developers," "Marketing," "Database Admins," "Auditors").
- **Container for Users**: A group contains only IAM users; it cannot contain other groups or roles.
- **No Credentials**: You cannot sign in as a group. Groups are solely for permission management.
- **Users can be in multiple groups**: An IAM user can be a member of more than one IAM group, inheriting permissions from all the groups they belong to, as well as any individual policies attached directly to them.

### Benefits of AWS IAM Service Groups

- **Simplified Permission Management**: Instead of individually assigning permissions to each user, you define permissions once on the group, and all users added to that group automatically inherit those permissions. This is a huge time-saver in organizations with many users.
- **Consistency**: Ensures that users with similar roles have consistent access levels, reducing the risk of misconfigurations.
- **Scalability**: As your organization grows, adding new users to appropriate groups is much faster and less error-prone than configuring individual permissions.
- **Auditability**: Makes it easier to review and understand who has what permissions by looking at group policies.

### Password Policy for AWS Users
AWS allows you to define a password policy for IAM users in your account. This policy enforces rules for the passwords that users create and change, enhancing the security of your AWS console logins.


- **How to Configure Password Policy**:

You can configure the password policy via the AWS Management Console or the AWS CLI.

 - **Common Password Policy Options**:

      - **Minimum Password Length**: Specify the minimum number of characters required (e.g., 8, 12, 14 characters).
      - **Require Specific Character Types**:
           - At least one uppercase letter (A-Z)
           - At least one lowercase letter (a-z)
           - At least one number (0-9)
           - At least one non-alphanumeric character (symbol: ! @ # $ % ^ & * ( ) _ + - = [ ] { } | \ ')
      - **Allow Users to Change Their Own Password**: Determines if IAM users can change their own passwords via the AWS console.
      - **Enable Password Expiration**:
           - Set a maximum password age (e.g., 90 days). After this period, users are forced to change their password upon their next login.
           - Optionally, specify that users whose passwords have expired require an administrator to reset them.
      - **Prevent Password Reuse**: Specify the number of previous passwords that a user cannot reuse (e.g., prevent reusing the last 5 passwords).

### How to Configure (Console Steps) ?

- Sign in to the AWS Management Console as a user with IAM administrative permissions.
- Navigate to the IAM service.
- In the left-hand navigation pane, click on Account settings.
- Under the Password policy section, click Edit.
- Configure your desired options and click Save changes.

### How to Configure (AWS CLI Example) ?

```Bash

aws iam update-account-password-policy \
    --minimum-password-length 14 \
    --require-uppercase-characters \
    --require-lowercase-characters \
    --require-numbers \
    --require-symbols \
    --allow-users-to-change-password \
    --max-password-age 90 \
    --password-reuse-prevention 5 \
    --hard-expiry # Forces users to change passwords when expired, cannot be reset by admin
```

### Multi-Factor Authentication (MFA) for Users
Multi-Factor Authentication (MFA) is a security best practice that adds an extra layer of protection on top of a username and password. When MFA is enabled, users are required to provide two or more verification factors to gain access to their AWS account.


- **Something you know**: (e.g., password)
- **Something you have**: (e.g., a physical MFA device, or a virtual MFA app on your phone)
- **Something you are**: (e.g., biometric data, though less common for direct AWS console login)

### Why MFA is Important ?

- Even if an attacker steals a user's password, they cannot access the account without the second factor.
- Significantly reduces the risk of unauthorized access due to compromised credentials.
- Mandatory for compliance with many security standards.

- **Types of MFA Devices Supported by AWS**:

   - **Virtual MFA Device**:
        - Software applications that run on smartphones or computers (e.g., Google Authenticator, Authy, Microsoft Authenticator). They generate a time-based one-time password (TOTP) code.
        - Most common and easiest to set up.
   - **Security Key (U2F/FIDO)**:
        - Physical devices (e.g., YubiKey) that plug into a USB port. They provide a cryptographic challenge-response mechanism.
   - **Hardware MFA Device**:
        - Dedicated physical devices (e.g., Gemalto tokens) that generate codes.

### How to Enable MFA for an IAM User (Console Steps for Virtual MFA - Most Common) ?

- Sign in to the AWS Management Console as an IAM user with permissions to manage MFA (or the root user for root account MFA).
- Navigate to the IAM service.
- In the left-hand navigation pane, click on Users.
- Select the specific IAM user for whom you want to enable MFA.
- On the user's summary page, click the Security credentials tab.
- Under the Multi-Factor Authentication (MFA) section, click Assign MFA device.
- Choose Virtual MFA device and click Continue.
- Install a compatible virtual MFA application (like Google Authenticator) on your smartphone.
- Open the MFA app and scan the QR code displayed in the AWS console. (Alternatively, you can manually enter the secret key).
- The MFA app will start generating 6-digit codes. Enter two consecutive codes from the app into the AWS console's input fields.
- Click Assign MFA.
- (Optional, but recommended) Create an IAM policy that requires MFA for all sensitive actions and attach it to the user or group.


### What is AWS Command Line Interface (CLI) ?

The AWS Command Line Interface (CLI) is an open-source tool that allows you to interact with AWS services using commands in your command-line shell. It provides a unified interface to manage all your AWS services, offering an alternative to the web-based AWS Management Console.

- **Automation**: Ideal for scripting and automating repetitive AWS tasks.
- **Infrastructure as Code (IaC)**: Forms a core part of IaC strategies alongside tools like CloudFormation or Terraform.
- **Programmatic Access**: Enables applications and scripts running outside the AWS console to interact with AWS.

### How to Install and Configure AWS CLI ?

1. **Installation**:

- **Download**: Go to the official AWS CLI documentation for installation instructions specific to your operating system (Windows, macOS, Linux). AWS provides installers.
- **Verify**: After installation, open your terminal/command prompt and run:

```Bash

aws --version
```
- You should see the AWS CLI version information.

2. **Configuration**:

Before you can use the AWS CLI to interact with your AWS account, you need to configure it with your credentials.

- **Get Your Access Keys**:

   - Log in to the AWS Management Console.
   - Navigate to IAM -> Users.
   - Select your IAM user (it's best practice to use an IAM user, not the root user, for CLI access).
   - Go to the Security credentials tab.
   - Under "Access keys," click Create access key.
   - Choose "Command Line Interface (CLI)" as the use case.
   - **IMPORTANT**: Note down your Access Key ID and Secret Access Key. This is the only time you'll see the secret access key. Download the .csv file if you prefer. Treat these like a password.

- **Configure the CLI**:

   - Open your terminal/command prompt and run:

```Bash

aws configure
```
   - The CLI will prompt you for four pieces of information:

       - **AWS Access Key ID [None]**: Paste the Access Key ID you just obtained.
       - **AWS Secret Access Key [None]**: Paste the Secret Access Key you just obtained.
       - **Default region name [None]**: Enter your preferred AWS Region (e.g., us-east-1, ap-south-1). This is the region where your commands will execute by default.
       - **Default output format [None]**: Common choices are json, text, or table. json is usually preferred for scripting.
   - **Example**:

```bash
$ aws configure
AWS Access Key ID [****************ABCD]: AKIAIOSFODNN7EXAMPLE
AWS Secret Access Key [****************ABCD]: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
Default region name [None]: us-east-1
Default output format [None]: json
```

    - These settings are stored in ~/.aws/credentials and ~/.aws/config files.

### Create an EC2 Instance Using AWS CLI
Creating an EC2 instance requires a few prerequisites: an AMI ID, an instance type, a key pair for SSH access, and usually a security group and a subnet within a VPC.

- **Prerequisites (Obtain these first)**:

   - **AMI ID**: Find an appropriate Amazon Machine Image (AMI) ID for your desired OS (e.g., Amazon Linux 2, Ubuntu). You can find these in the EC2 console or by using the CLI:


```Bash

aws ec2 describe-images --owners amazon --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" "Name=state,Values=available" --query "sort_by(Images, &CreationDate)[-1].ImageId" --output text
```
(This example gets the latest Amazon Linux 2 AMI ID).

   - **Key Pair**: For SSH access to your instance. Create one if you don't have one:

```Bash

aws ec2 create-key-pair --key-name MyKeyPairCLI --query 'KeyMaterial' --output text > MyKeyPairCLI.pem
chmod 400 MyKeyPairCLI.pem # Important for Linux/macOS
```

    - MyKeyPairCLI.pem is your private key file.
  - **Security Group**: A firewall for your instance. It controls inbound and outbound traffic. You'll need to allow SSH (port 22) at least. Create one if needed:

```Bash

aws ec2 create-security-group --group-name MySecurityGroupCLI --description "My CLI Security Group" --vpc-id <your-vpc-id>

# Note the GroupId from the output (e.g., sg-0abcdef1234567890)

# Add an inbound rule for SSH from anywhere (0.0.0.0/0)
aws ec2 authorize-security-group-ingress --group-id <your-security-group-id> --protocol tcp --port 22 --cidr 0.0.0.0/0
```

    - Replace <your-vpc-id> and <your-security-group-id> with your actual VPC and security group IDs.
   - **Subnet ID**: The ID of the subnet within your VPC where you want to launch the instance. (You can find this in the VPC console or using aws ec2 describe-subnets).

- **Command to Create an EC2 Instance**:

```Bash

aws ec2 run-instances \
    --image-id <ami-id> \
    --count 1 \
    --instance-type t2.micro \
    --key-name MyKeyPairCLI \
    --security-group-ids <your-security-group-id> \
    --subnet-id <your-subnet-id> \
    --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=MyCliInstance}]'
```

  - **Replace the placeholders**:

     - <ami-id>: The AMI ID you obtained (e.g., ami-0abcdef1234567890).
     - <your-security-group-id>: The ID of the security group you created (e.g., sg-0abcdef1234567890).
     - <your-subnet-id>: The ID of your desired subnet (e.g., subnet-0abcdef1234567890).
  - **Output**:
The command will output a JSON response containing details about the launched instance, including its InstanceId, PublicIpAddress, and PrivateIpAddress.

- You can then use the InstanceId to check its status:

```Bash

aws ec2 describe-instances --instance-ids <your-instance-id>
```
