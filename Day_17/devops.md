---
title: "Day 17: AWS S3 "
description: "A summary of my 17th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 17
  - Challenge
---

### Table of contents :
- [Steps to Allow Public Access to an S3 Bucket](#steps-to-allow-public-access-to-an-s3-bucket)
- [Security Warning](#security-warning)
- [S3 Policy Generator](#s3-policy-generator)
- [S3 Bucket Naming Conventions](#s3-bucket-naming-conventions)
- [Object in AWS S3](#object-in-aws-s3)
- [S3 Versioning](#s3-versioning)

### Steps to Allow Public Access to an S3 Bucket
By default, S3 buckets and their objects are private. AWS strongly recommends keeping them private for security. However, for specific use cases like hosting a static website, you might need to make some or all of your content publicly accessible.

- **The process generally involves two main steps**:

1. **Disable "Block Public Access" settings**:

   - Sign in to the AWS Management Console and navigate to the S3 service.
   - Click on the bucket you want to make public.
   - Go to the "Permissions" tab.
   - Under "Block public access (bucket settings)", click "Edit".
   - Uncheck Block all public access.
   - Confirm your changes by typing confirm in the dialog box and clicking "Confirm".
   - **Caution**: This step alone does not make your bucket public. It only allows the possibility of public access.

2. **Apply a Bucket Policy or ACLs**:

   - **Using a Bucket Policy (Recommended for fine-grained control)**:
      - Still on the "Permissions" tab of your bucket, scroll down to "Bucket policy".
      - Click "Edit".
      - You'll add a JSON policy here. For example, to make all objects in the bucket publicly readable:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::your-bucket-name/*"
        }
    ]
}
```

   - Replace your-bucket-name with the actual name of your bucket.
   - "Principal": "*" means "anyone on the internet."
   - "Action": "s3:GetObject" allows users to download (get) objects.
   - "Resource": "arn:aws:s3:::your-bucket-name/*" applies this to all objects within your bucket. If you only want to make a specific object public, change * to the object key (e.g., index.html).

  - Click "Save changes".

- **Using Object ACLs (Less Recommended, except for specific cases)**:

  - You can set individual objects to "public readable" using Object ACLs. This is generally less flexible than bucket policies and is discouraged by AWS unless you have a specific reason (e.g., legacy applications).
  - To enable this, you must first enable "ACLs" under "Object Ownership" in your bucket settings, and then when you upload or manage an object, you can set its ACL to "Public read."

### Security Warning
Making an S3 bucket or its objects public can expose sensitive data if not done carefully. Always ensure you only expose what is absolutely necessary.

### S3 Policy Generator
The AWS S3 Policy Generator is a useful tool within the AWS Management Console that helps you create JSON policies for your S3 buckets (and other AWS services) without manually writing the entire JSON. It provides a guided interface to select services, actions, resources, and conditions.

- **Steps to use the Policy Generator**:

  - Sign in to the AWS Management Console.
  - Navigate to the IAM service.
  - In the left-hand navigation pane, go to "Policy" -> "Create policy".
  - Choose the "JSON" tab if you want to paste a generated policy, or use the "Visual editor" for a guided experience.
  - (Alternatively) You can directly access the Policy Generator tool via a web search or sometimes linked within the S3 bucket policy editor. The URL is typically https://awspolicygen.s3.amazonaws.com/policygen.html.
  - Select Policy Type: Choose "S3 Bucket Policy" (or "IAM Policy" if creating for users/roles).
  - **Add Statements**:

    - **Effect**: Allow or Deny.
    - **Principal**: Who is allowed/denied (e.g., * for anonymous public access, or a specific IAM User/Role ARN).
    - **AWS Service**: Choose "Amazon S3".
    - **Actions**: Select specific S3 actions (e.g., GetObject, ListBucket, PutObject).
    - **ARN**: Provide the Amazon Resource Name (ARN) of your S3 bucket or objects (e.g., arn:aws:s3:::your-bucket-name or arn:aws:s3:::your-bucket-name/*).
    - **Add Conditions (Optional)**: Define conditions like source IP, date, etc.

  - **Generate Policy**: The tool will generate the JSON policy document.
  - **Copy and Apply**: Copy the generated JSON and paste it into your S3 bucket policy editor (as described in the "Allow Public Access" section) or an IAM policy.

### S3 Bucket Naming Conventions
S3 bucket names have strict rules because they are used as part of the URL to access objects.

- **Uniqueness**: Must be globally unique across all AWS accounts.

- **Length**: Between 3 and 63 characters long.

- **Characters**: Can consist only of lowercase letters, numbers, periods (.), and hyphens (-).

- **Start/End**: Must begin and end with a letter or number.

- **No IP Addresses**: Cannot be formatted as an IP address (e.g., 192.168.1.1).

- **No Underscores or Capital Letters**: Avoid these characters.

- **No Adjacent Periods**: Cannot contain two adjacent periods (e.g., my..bucket).

- **No Dash at End**: Cannot end with a dash.

- **No s3alias suffix**: Cannot end with -s3alias.

- **DNS Compliance**: For static website hosting and other CNAME record uses, bucket names must be DNS compliant. Periods can be used for hierarchical naming in DNS (e.g., my.website.com).

- **Good Examples**: my-unique-app-logs, company-documents-2024, static.website.example.com
- Bad Examples: MyBucket, my_bucket, my..bucket, 192.168.1.1

### Object in AWS S3
In Amazon S3, an object is the fundamental entity that you store.

- **Data + Metadata**: An object consists of the actual data (which can be any kind of file: image, video, document, log file, backup, etc.) and its associated metadata.

- **Metadata**: This is a set of name-value pairs that describe the object. It can include system-defined metadata (like content type, last modified date, size) and user-defined metadata (custom key-value pairs you add).

- **Key**: Each object within a bucket has a unique key (or key name). The key is the full path to the object within the bucket. S3 doesn't have a traditional file system hierarchy; it's a flat structure. However, by using prefixes and delimiters (like / in folder1/subfolder/my-file.txt), you can create a logical hierarchy that appears as folders in the S3 console and other tools.

- **Version ID (if versioning enabled)**: If bucket versioning is enabled, each object has a unique version ID.

- **Maximum Size**: A single S3 object can be up to 5 TB in size. For objects larger than 100 MB, AWS recommends using multi-part uploads.

**Example Object Path**: s3://your-bucket-name/folder1/images/photo.jpg

- your-bucket-name: The bucket.

-  folder1/images/photo.jpg: The object key.

### S3 Versioning
S3 Versioning is a feature that allows you to keep multiple versions of an object in the same bucket. It helps to preserve, retrieve, and restore every version of every object stored in your S3 bucket.

- **How it Works**:

   - When you enable versioning on a bucket, S3 automatically assigns a unique version ID to each new object and each new version of an object.

   - If you upload an object with the same key as an existing object, S3 doesn't overwrite the old object. Instead, it creates a new version of the object with a new version ID. The previous version is preserved.

   - If you delete an object, S3 doesn't permanently remove it. Instead, it places a "delete marker" on the current version. The previous versions are still retrievable. You can also permanently delete specific object versions.

- **Benefits of S3 Versioning**:

   - **Accidental Overwrite Protection**: Prevents unintended overwrites of objects. If someone uploads a new file with the same name, the old one isn't lost.

   - **Accidental Deletion Protection**: If an object is accidentally deleted, you can easily restore a previous version by removing the delete marker or retrieving a specific older version.

   - **Data Recovery**: Allows you to recover from application failures or unintended changes by rolling back to a previous good state of an object.

   - **Auditing and Compliance**: Provides a complete history of object changes, which can be valuable for auditing and meeting compliance requirements.

- **Considerations**:

   - **Increased Storage Costs**: Storing multiple versions of objects consumes more storage, leading to higher costs. You can use S3 Lifecycle policies to manage these costs by automatically transitioning older versions to cheaper storage classes (like Glacier) or permanently deleting them after a certain period.

   - **Once Enabled, Cannot Be Disabled (Only Suspended)**: Once you enable versioning on a bucket, you cannot disable it. You can only "suspend" it. If suspended, new object uploads will not create new versions, but existing versions will remain.

- **To enable S3 Versioning**:

   - Go to your S3 bucket in the AWS Management Console.

   - Click on the "Properties" tab.

   - Scroll down to "Bucket Versioning" and click "Edit".

   - Select "Enable" and click "Save changes".