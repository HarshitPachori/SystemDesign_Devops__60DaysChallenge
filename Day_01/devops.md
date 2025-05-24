---
title: "Day 01: Introduction to Cloud Computing and AWS Fundamentals + Account Setup"
description: "A summary of my first day's learning in the 60-day challenge, covering basic cloud concepts and an overview of AWS."
keywords:
  - AWS
  - Cloud Computing
  - Fundamentals
  - Day 1
  - Challenge
  - Introduction
---

### Table of contents :
- [What is AWS ?](#what-is-aws-)
- [What is cloud computing ?](#what-is-cloud-computing-)
- [Service models ( IAAS, PAAS, SAAS)](#service-models--iaas-paas-saas)
- [Deployment models of cloud](#deployment-models-of-cloud)
- [How AWs Charge money ?](#how-aws-charge-money-)
- [What/why is region ?](#whatwhy-is-region-)
- [What is availability zone ?](#what-is-availability-zone-)
- [Local zones](#local-zones)
- [Created my free tier AWS account](#created-my-free-tier-aws-account)

#### What is AWS ?

- **Amazon Web Services (AWS)** is a comprehensive, widely adopted cloud computing platform offered by Amazon. It provides on-demand services like compute power, storage, databases, networking, analytics, machine learning, and more, all accessible over the internet. Users pay only for the resources they consume, making it highly `flexible` and `cost-effective`.

- Traditional computing involves owning and maintaining your own physical hardware, data centers, and IT staff. This requires significant upfront capital investment, manual scaling, and ongoing maintenance. AWS, conversely, eliminates these burdens. It offers:


    - **Pay-as-you-go**: No upfront costs; pay only for what you use.
    - **Scalability**: Instantly scale resources up or down based on demand.
    - **Managed services**: AWS handles infrastructure maintenance, updates, and security.
    - **Global reach**: Deploy applications globally with ease.
    - **Reduced overhead**: Focus on innovation rather than infrastructure management.


#### What is cloud computing ?
Cloud computing is the delivery of computing services—like servers, storage, databases, networking, software, analytics, and intelligence—over the internet ("the cloud"). Instead of owning and maintaining physical data centers, you access these resources on-demand from a cloud provider.


Key benefits include:

- **Cost Savings**: Pay-as-you-go model, eliminating upfront hardware investments.
- **Scalability & Elasticity**: Easily scale resources up or down to meet fluctuating demands.
- **Agility**: Rapidly provision and deploy IT resources, accelerating innovation.
- **Global Reach**: Deploy applications worldwide with minimal effort.
- **Managed Services**: Providers handle infrastructure maintenance, updates, and security.


#### Service models ( IAAS, PAAS, SAAS)
Cloud computing services are categorized into three main "service models," representing different levels of management and control you retain:

- **IaaS (Infrastructure as a Service)**: This is the most basic cloud service. It provides virtualized computing resources like virtual machines, storage, and networks. You manage the operating system, applications, and data, while the cloud provider handles the underlying infrastructure (servers, virtualization, networking hardware). It offers the most flexibility. (e.g., AWS EC2, Azure Virtual Machines)




- **PaaS (Platform as a Service)**: PaaS offers a complete environment for developing, running, and managing applications. The cloud provider manages the underlying infrastructure and the operating system, middleware, and runtime environments. This allows developers to focus purely on coding and deploying applications without worrying about infrastructure management. (e.g., AWS Elastic Beanstalk, Heroku)



- **SaaS (Software as a Service)**: SaaS provides fully functional software applications over the internet. The cloud provider manages all aspects of the application, including infrastructure, platform, and the software itself. Users simply access and use the application via a web browser or app, typically on a subscription basis. It offers the least control but maximum convenience. (e.g., Google Workspace, Salesforce, Microsoft 365)

#### Deployment models of cloud
Cloud deployment models define where and how your cloud infrastructure is located and managed. The primary models are:

- **Public Cloud**: Services are offered by third-party providers (like AWS, Azure) over the public internet. Resources are shared among multiple organizations, offering high scalability, cost-effectiveness (pay-as-you-go), and minimal management overhead. Ideal for general-purpose workloads.

- **Private Cloud**: Dedicated cloud infrastructure exclusively for a single organization. It can be hosted on-premises or by a third-party. Offers maximum control, security, and customization, suitable for sensitive data and strict compliance needs, but involves higher upfront costs and management.

- **Hybrid Cloud**: Combines a public and private cloud, allowing data and applications to move between them. This offers flexibility to run sensitive workloads privately while leveraging the public cloud's scalability for less critical tasks, optimizing cost and control.

- **Community Cloud**: A shared cloud infrastructure used by several organizations that share common concerns (e.g., security, compliance, mission). It's a collaborative environment, balancing shared benefits with specific industry needs.


#### How AWs Charge money ?
AWS primarily charges on a pay-as-you-go model, meaning you only pay for the services you consume, with no upfront commitments or long-term contracts for basic usage. The cost varies based on:

- **Compute**: Charged per hour or second for services like EC2 instances (virtual servers), based on instance type, size, and operating system.
- **Storage**: Charged per GB per month for services like S3 (object storage) or EBS (block storage), with variations depending on storage class and access frequency.
- **Data Transfer**: Outbound data transfer from AWS to the internet or other regions typically incurs charges, while inbound data is mostly free.

#### What/why is region ?
- An AWS Region is a geographical location around the world where Amazon Web Services clusters its data centers. Each Region is composed of multiple, isolated physical data centers called Availability Zones (AZs).

- Why are Regions important?

  - **Latency**: Placing your applications in a Region geographically closer to your users reduces network latency, providing a faster and more responsive experience.
  - **Data Sovereignty & Compliance**: Many countries have regulations requiring data to be stored within their borders. Regions allow you to meet these compliance requirements (e.g., GDPR in Europe, HIPAA in the US).
  - **Disaster Recovery**: Regions are isolated from each other. By deploying your applications across multiple Regions, you can build highly resilient systems that can withstand a major outage in one entire Region, ensuring business continuity.
  - **Service Availability**: While most core AWS services are global, some newer or specialized services might only be available in specific Regions. You choose a Region that offers the services you need.
  - **Cost**: Pricing for AWS services can vary slightly between Regions due to operational costs and local market conditions.

#### What is availability zone ?
- An AWS Availability Zone (AZ) is a distinct, isolated physical data center, or a cluster of data centers, within an AWS Region. Each AZ has independent power, cooling, and networking, designed to be isolated from failures in other AZs within the same Region.


- How they work:

  - **Fault Isolation**: By distributing your applications across multiple AZs within a Region, you protect against single points of failure. If one AZ experiences an issue (like a power outage or natural disaster), your application can seamlessly failover to instances running in other healthy AZs.

  - **Low-Latency Connectivity**: AZs within a Region are interconnected with high-bandwidth, low-latency, and redundant fiber-optic networks. This allows for synchronous data replication and efficient communication between resources in different AZs, crucial for high-availability setups.

  - **High Availability**: Using multiple AZs is a core strategy for building highly available and fault-tolerant applications on AWS, ensuring continuous operation and minimal downtime.

#### Local zones
- AWS Local Zones are extensions of an AWS Region that place compute, storage, and other select AWS services closer to large population, industry, and IT centers. They are designed for applications requiring single-digit millisecond latency to end-users.

- Key characteristics:

  - **Proximity**: They bring a subset of AWS services geographically closer to specific metropolitan areas.
  - **Low Latency**: This proximity significantly reduces network latency for local users, improving real-time application performance.
  - **Extension of a Region**: Local Zones are connected to a parent AWS Region via a high-bandwidth network. You can extend your Amazon Virtual Private Cloud (VPC) into a Local Zone.
  - **Use Cases**: Ideal for interactive applications like real-time gaming, live streaming, virtual workstations, and applications with specific local data residency requirements.
  - **Limited Services**: They offer a subset of AWS services compared to a full AWS Region or Availability Zone.

#### Created my free tier AWS account
Creating an AWS Free Tier account is a straightforward process. Here are the general steps:

- **Visit the AWS Website**: Go to the official AWS website [`www.aws.amazon.com/free`](https://aws.amazon.com/free) and click on "Create a Free Account" or "Sign Up for AWS."

- **Enter Account Information**: Provide your email address, create a password, and choose an AWS account name (this can be changed later). You'll need to verify your email address with a code sent by AWS.

- **Contact Information**: Select your account type (Personal or Business) and fill in your personal/business details, including your full name, phone number, and address. Accept the AWS Customer Agreement.

- **Billing Information**: AWS requires a valid credit/debit card for verification and in case you exceed the Free Tier limits. Enter your payment details. You won't be charged unless you go over the Free Tier usage.

- **Identity Verification**: Verify your phone number via a text message or voice call from AWS. Enter the verification code received.

- **Choose a Support Plan**: Select the "Basic Support - Free" plan. This plan provides customer service for account and billing questions.

- **Complete Sign Up**: Review your details and complete the sign-up process.

