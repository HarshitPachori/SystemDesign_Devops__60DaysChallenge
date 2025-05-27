---
title: "Day 04: Introduction to EC2 Instance and Deploy Hello World App"
description: "A summary of my 4th day's learning in the 60-day challenge, covering basic cloud concepts , security groups and an overview of running spring boot jar on EC2."
keywords:
  - AWS
  - EC2
  - Security Groups
  - Fundamentals
  - Day 2
  - Challenge
---

### Table of contents :
- [What are Security groups ?](#what-are-security-groups-)
- [Steps to install Java OpenJdk 17 and deploy a Spring Boot hello world application on EC2](#steps-to-install-java-openjdk-17-and-deploy-a-spring-boot-hello-world-application-on-ec2)


#### What are Security groups ?
AWS Security Groups act as virtual firewalls for your EC2 instances and other AWS resources. They control inbound (incoming) and outbound (outgoing) network traffic at the instance level. You define rules specifying allowed protocols (e.g., TCP, UDP), port ranges (e.g., 80 for HTTP, 22 for SSH), and source/destination IP addresses or other security groups.
Key characteristics:

- **Stateful**: If you allow inbound traffic, the response traffic is automatically allowed outbound, and vice-versa, without needing a separate outbound rule.
- **Allow-only**: Security groups are always permissive. You can only define rules that allow traffic; there are no explicit "deny" rules. Any traffic not explicitly allowed is implicitly denied.

- **Granular Control**: You can associate one or more security groups with an instance, and all rules across associated groups are evaluated. This provides fine-grained control over network access.

- **Crucial for Security**: They are a fundamental layer of defense, ensuring that only authorized traffic can reach your applications.



#### Steps to install Java OpenJdk 17 and deploy a Spring Boot hello world application on EC2 
Deploying a Spring Boot JAR file on an EC2 instance involves several key steps: installing Java, transferring your application, and setting it up to run.

Prerequisites :
- An EC2 instance (e.g., Amazon Linux 2 or Ubuntu) up and running.
- Your Spring Boot application packaged as an executable JAR file (usually found in your project's target/ directory after running mvn clean package or gradle bootJar).
- Your EC2 instance's Security Group configured to allow inbound traffic on the port your Spring Boot application will use (default is 8080).
- Your EC2 instance's Key Pair (.pem file) for SSH access.- connect to EC@ using SSH from local machine.
- For Ubuntu: ssh -i your-key.pem ubuntu@YOUR_EC2_PUBLIC_IP
- Install OpenJDK 17 on EC2:
```
sudo apt update -y
sudo apt install openjdk-17-jdk -y

```
- Verify installation:
```
java -version
```
- Transfer your Spring Boot JAR file to EC2:
From your local machine, use scp to copy your JAR file to the EC2 instance. Replace your-key.pem, path/to/your-app.jar, ec2-user (or ubuntu), and YOUR_EC2_PUBLIC_IP:

```
scp -i your-key.pem path/to/your-app.jar ec2-user@YOUR_EC2_PUBLIC_IP:/home/ec2-user/your-app.jar
```
- Run the Spring Boot JAR File (Basic Method - for testing):
    - Back on your EC2 instance via SSH
     ```
    java -jar /home/ec2-user/your-app.jar
    ```
    - Your application will start. You should see Spring Boot logs in the terminal.
    - To test, open your browser and go to http://YOUR_EC2_PUBLIC_IP:8080 (or whatever port your app uses).


- `Note:` This command runs the app in the foreground. If you close the SSH session, the application will stop.


