---
title: "Day 03: Introduction to EC2 Instance and Deploy Hello World App"
description: "A summary of my 3rd day's learning in the 60-day challenge, covering basic cloud concepts and an overview of EC2."
keywords:
  - AWS
  - EC2
  - Fundamentals
  - Day 2
  - Challenge
---

### Table of contents :
- [What is Nginx ?](#what-is-nginx-)
- [Steps to install nginx and deploy a hello world html file on EC2](#steps-to-install-nginx-and-deploy-a-hello-world-html-file-on-ec2)


#### What is Nginx ?
- Nginx (pronounced "engine-x") is a popular open-source software that serves multiple roles in web infrastructure. While widely known as a web server (serving static content like HTML, CSS, images), its true power lies in its ability to act as a high-performance reverse proxy, load balancer, HTTP cache, and API gateway.


- It's designed for high concurrency and low memory usage, using an asynchronous, event-driven architecture, making it highly efficient for handling large numbers of simultaneous connections. Many high-traffic websites and applications use Nginx to improve performance, scalability, and reliability

#### Steps to install nginx and deploy a hello world html file on EC2 
- Use SSH from your local machine (ensure your key file has correct permissions: chmod 400 your-key.pem):
- For Ubuntu: ssh -i your-key.pem ubuntu@YOUR_EC2_PUBLIC_IP
- Install Nginx on EC2:
```
sudo apt update -y
sudo apt install nginx -y
```
- Start and Enable Nginx:
```
sudo systemctl start nginx
sudo systemctl enable nginx
```
- Deploy "Hello World" HTML:
Nginx serves static files from a default web root directory.
For Ubuntu: The default web root is usually /var/www/html/.
```
cd /var/www/html/
sudo echo "<h1>Hello, World from AWS EC2!</h1>"> index.html
```
- Verify Deployment:

    - Open your web browser.
    - Enter your EC2 instance's Public IPv4 address in the address bar (e.g., http://YOUR_EC2_PUBLIC_IP).
    - You should now see the "Hello, World from AWS EC2!" message.

