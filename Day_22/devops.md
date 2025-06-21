---
title: "Day 22: AWS S3 "
description: "A summary of my 22th day's learning in the 60-day challenge, covering basic concepts of S3."
keywords:
  - AWS
  - S3
  - Day 22
  - Challenge
---

### Table of contents :
- [What is a Pre-Signed URL in S3 ?](#what-is-a-pre-signed-url-in-s3-)
- [Server-Side Encryption vs. Client-Side Encryption in S3](#server-side-encryption-vs-client-side-encryption-in-s3)


### What is a Pre-Signed URL in S3 ?
An Amazon S3 pre-signed URL is a way to grant temporary, time-limited access to specific S3 objects to users who do not have AWS credentials or permissions to access your S3 bucket directly.

Essentially, a pre-signed URL contains parameters that include a signature and an expiration date. Anyone with this URL can then perform the action (e.g., download, upload) specified in the URL, as if they were the AWS identity (IAM user or role) that generated it, but only for the duration it is valid.

- **Key Characteristics**:

  - **Temporary Access**: The URL has an expiration time (e.g., minutes, hours, days) after which it becomes invalid.
  - **Limited Scope**: It grants permission for a specific action (e.g., GET to download, PUT to upload, DELETE to delete) on a specific object in a specific bucket. You cannot use it to list bucket contents or perform other broad actions.
  - **Security**: You don't expose your AWS credentials directly. The application generating the URL uses its own AWS credentials to sign the request, and the signature is embedded in the URL.
  - **No AWS SDK Required by Client**: The user (or their browser/application) who receives the pre-signed URL doesn't need to have AWS SDKs configured or understand AWS authentication. They simply make a standard HTTP request to the URL.
- **Common Use Cases**:

  - **Granting temporary download access**: Share a private file with a client or partner without making the bucket public.
  - **Enabling direct uploads from client-side applications**: Allow users to upload files (e.g., profile pictures, documents) directly to S3 from their web browser or mobile app, offloading the upload burden from your backend server. Your backend generates the pre-signed PUT URL, sends it to the client, and the client uploads directly to S3.
  - **Securely sharing content**: Distribute content that should only be accessible for a limited time.
- **How it's Generated**:

Pre-signed URLs are generated programmatically using an AWS SDK (e.g., Boto3 for Python, AWS SDK for Java/Node.js) or the AWS CLI, by an AWS identity that does have the necessary permissions to perform the action on the object.

**Example (Conceptual steps for a PUT pre-signed URL)**:

- A user on a website wants to upload an image.
- The client-side JavaScript sends a request to your backend server (e.g., a Lambda function or EC2 instance).
- Your backend server, using its own secure AWS credentials, generates a pre-signed PUT URL for a specific object key in an S3 bucket with a short expiration time (e.g., 5 minutes).
- The backend sends this pre-signed URL back to the client-side JavaScript.
- The client-side JavaScript then uses this pre-signed URL to perform an HTTP PUT request directly to S3, uploading the image.
- The image is uploaded to S3, and your backend server never had to handle the file data directly.

### Server-Side Encryption vs. Client-Side Encryption in S3
Encryption is crucial for data security. AWS S3 provides both server-side and client-side encryption options to protect your data at rest (when stored in S3).

1. **Server-Side Encryption (SSE)**
With server-side encryption, Amazon S3 encrypts your data at the object level as it writes it to disks in its data centers and decrypts it for you when you access it. This means the encryption and decryption processes happen on the AWS side. You upload unencrypted data, and S3 encrypts it before storing it. When you download, S3 decrypts it before sending it to you.

There are three mutually exclusive server-side encryption options:

a) **SSE-S3 (Server-Side Encryption with S3-managed keys)**:

- **How it works**: S3 handles all aspects of encryption, including key management. Each object is encrypted with a unique key, which is itself encrypted with a master key that is regularly rotated.
- **Control**: AWS manages the encryption keys completely. You simply specify this option.
- **Use Case**: Simplest to use. Recommended if you just need to ensure data is encrypted at rest and don't have specific key management requirements. Since January 2023, all new objects uploaded to S3 are automatically encrypted with SSE-S3 by default if no other encryption is specified.

b) **SSE-KMS (Server-Side Encryption with AWS Key Management Service keys)**:

- **How it works**: S3 encrypts your data with keys managed by AWS Key Management Service (KMS). You specify a KMS key (either AWS-managed or customer-managed) to encrypt your objects. KMS provides an audit trail of key usage.
- **Control**: You have more control over the encryption keys, including defining key usage permissions, auditing key usage, and optionally managing your own customer master keys (CMKs) within KMS.
- **Use Case**: When you need more control over your encryption keys, want to audit key usage, or have specific compliance requirements that mandate the use of KMS. Using S3 Bucket Keys with SSE-KMS can significantly reduce costs for large S3 workloads.

c) **SSE-C (Server-Side Encryption with Customer-Provided keys)**:

- **How it works**: You provide your own encryption key as part of the API request for S3 to use. S3 uses this key to encrypt the object as it's written to disk and decrypts it when you access it. S3 does not store your encryption key permanently.
- **Control**: You manage and provide the encryption key for each operation.
- **Use Case**: When you want to entirely manage your encryption keys outside of AWS while still leveraging S3's encryption capabilities. This adds a management overhead as you must securely deliver the key with every request.

2. **Client-Side Encryption (CSE)**
With client-side encryption, you encrypt your data before you send it to Amazon S3. This means the encryption and decryption processes happen on your side (the client application). S3 receives and stores the data already encrypted; it simply acts as a storage service for the encrypted bytes.

There are generally two approaches for client-side encryption:

a) **Using a master key stored in AWS KMS**:

- **How it works**: Your client application uses the AWS SDK to integrate with AWS KMS. It requests a data key from KMS, uses that data key to encrypt your data locally, and then uploads the encrypted data and the encrypted data key (encrypted by your KMS master key) to S3. When downloading, the process is reversed.
- **Control**: You control the encryption process and key generation (via KMS).
- **Use Case**: When you need end-to-end encryption where AWS never sees your unencrypted data or its unencrypted data keys. This is often driven by strict compliance requirements.

b) **Using a customer-provided master key**:

- **How it works**: You manage your encryption keys entirely outside of AWS. Your client application encrypts the data using your local key, and then uploads the encrypted data to S3. You are solely responsible for managing, securing, and rotating this key.
- **Control**: Full control over your encryption keys, completely independent of AWS services.
- **Use Case**: For highly sensitive data where absolute control over the encryption process and keys is paramount, and you have robust internal key management systems. This option requires significant operational overhead for key management.
