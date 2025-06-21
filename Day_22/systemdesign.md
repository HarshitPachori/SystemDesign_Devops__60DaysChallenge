---
title: "Day 22: E-commerce Product Catalog"
description: "A summary of my 22th day's learning in the 60-day challenge, designing a E-commerce Product Catalog"
keywords:
  - E-commerce Product Catalog
  - Day 22
  - Challenge
---

### Table of contents :
- [System Design: Simple E-commerce Product Catalog](#system-design-simple-e-commerce-product-catalog)



### System Design: Simple E-commerce Product Catalog
- **Objective**: To design a scalable, highly available, and performant system for managing and displaying a catalog of products for an e-commerce platform. This system focuses on product information (read-heavy), not order processing or user authentication for shopping.

1. **Functional Requirements (What the system MUST do)**
- **Product Management (Admin Operations)**:
   - **Add Product**: Admin can add new products with details (name, description, price, categories, images, stock quantity).
   - **Edit Product**: Admin can modify existing product details.
   - **Delete Product**: Admin can remove products.
   - **List Products**: Admin can view a list of all products.
   - **Manage Product Images**: Upload, replace, and remove images associated with a product.
- **Category Management (Admin Operations)**:
   - **Create Category**: Admin can define new product categories (e.g., "Electronics", "Clothing").
   - **Edit Category**: Admin can modify category details.
   - **Delete Category**: Admin can remove categories (potentially unassigning products).
   - **Assign/Unassign Products to Categories**: Associate products with one or more categories.
- **Product Catalog (User-Facing Operations - Read-Only)**:
   - **Browse Products**: Users can browse products, typically by category or recent additions.
   - **Search Products**: Users can search products by keywords (name, description).
   - **Filter/Sort Products**: Users can filter by price range, category, and sort by relevance, price, name.
   - **View Product Details**: Users can view a dedicated page for each product showing all its information (images, price, description, stock, user reviews - reviews are displayed but not added by this system).
   - **View Product Images**: Users can view multiple high-resolution images for each product.
2. **Non-Functional Requirements (How well the system MUST perform)**
- **Performance**:
   - **Read Latency**: Product searches and detail page loads should be under 200ms for 95% of requests.
   - **Write Latency**: Product creation/update operations should be under 500ms for 95% of requests.
   - **Throughput**: System should support 1000+ Reads Per Second (RPS) for general Browse/searching and 5-10 Writes Per Second (WPS) for admin operations.
- **Scalability**:
   - **Data Volume**: Must support millions of products and their associated data (e.g., 50GB+).
   - **User Load**: Must scale to handle thousands of concurrent users (read operations).
   - **Elasticity**: Ability to automatically scale resources up/down based on demand.
- **Availability**:
   - **Read Availability**: 99.99% (less than ~52 minutes downtime per year).
   - **Write Availability**: 99.9% (less than ~9 hours downtime per year).
- **Durability**:
   - **Data Durability**: 99.999999999% (11 nines) for stored objects (images), and highly durable for database data.
- **Security**:
   - **Authentication/Authorization**: Secure access control for admin users (product/category management).
   - **Data Encryption**: All data encrypted at rest and in transit (SSL/TLS).
   - **Input Validation**: Protect against common web vulnerabilities (e.g., SQL Injection, XSS).
- **Maintainability & Observability**:
   - Modular architecture for easier development and updates.
   - Comprehensive monitoring, logging, and alerting.
- **Cost-Effectiveness**: Optimize infrastructure costs by using managed services and scaling resources dynamically.
3. **Feasibility Analysis**
- **Technical Feasibility**: Highly feasible. All components (database, search engine, storage, CDN, API Gateway, serverless compute) are mature and readily available as managed cloud services. No custom complex algorithms or bleeding-edge tech required.
- **Operational Feasibility**: Very feasible with managed cloud services (e.g., AWS). This significantly reduces the burden of server provisioning, patching, scaling, and maintenance.
- **Economic Feasibility**: Cloud's pay-as-you-go model makes it economically viable. Start small and scale costs with growth. Serverless components (Lambda, DynamoDB) further optimize costs for fluctuating loads.
- **Schedule Feasibility**: A lean version of this catalog could be deployed relatively quickly (weeks to a few months) using cloud services, with iterative development for advanced features.
4. **High-Level Design Sketch (Architectural Patterns & Concepts Integration)**
**Architectural Choice**: We'll leverage a Microservices-ish / Event-Driven / Layered approach, simplified for a dedicated product catalog, making it scalable and robust.

**Core Components & Technologies**:
1. **Client Layer (Presentation)**:

   - **Web Application (Frontend)**: Built with React/Angular/Vue.js. Interacts with the backend APIs.
   - **Mobile Apps**: Android/iOS apps, also consuming the backend APIs.
   - **Concept Integration**: Users interact via this layer.
2. **API Gateway (Broker Pattern / Facade)**:

   - **AWS API Gateway**: Acts as the single entry point for all client requests.
   - **Responsibilities**: Request routing, authentication/authorization for admin APIs (e.g., using AWS Cognito/IAM), rate limiting, caching, SSL termination.
   - **Concept Integration**: Implements the Broker Pattern by decoupling clients from backend services. Provides a unified facade.
3. **Backend Services (Microservices Pattern / Layered Business Logic)**:

- **Product Service**:
   - **Responsibility**: Manages all CRUD operations for product data.
   - **Implementation**: AWS Lambda functions (Serverless compute, scales automatically) or containers on AWS ECS/EKS.
   - **Concept Integration**: A distinct Microservice providing a specific business capability.
- **Category Service**:
   - **Responsibility**: Manages CRUD operations for product categories and their relationships.
   - **Implementation**: Similar to Product Service (Lambda/Containers).
   - **Concept Integration**: Another dedicated Microservice.
- **Image Processing Service**:
   - **Responsibility**: Handles image uploads, resizing, format conversion for different display needs (thumbnails, high-res).
   - **Implementation**: AWS Lambda triggered by S3 events.
   - **Concept Integration**: Event-Driven Architecture (S3 event triggers Lambda), specialized Microservice.
4. **Databases & Data Stores**:

- **Primary Product Database**:
   - **Choice**: Amazon DynamoDB (NoSQL, Key-Value/Document store).
   - **Why**: Offers extremely high scalability, low-latency performance, and managed service benefits (auto-scaling, backups). Ideal for large volumes of product data with flexible attributes.
   - **Concept Integration**: Highly scalable, managed NoSQL database.
- **Search Index**:
   - **Choice**: Amazon OpenSearch Service (Managed Elasticsearch).
   - **Why**: Provides powerful full-text search, filtering, and aggregation capabilities essential for a product catalog. Optimized for read-heavy search queries.
   - **Data Sync**: Data from DynamoDB will be streamed to OpenSearch (e.g., via DynamoDB Streams -> AWS Lambda -> OpenSearch).
   - **Concept Integration**: Specialized Search Engine, enabling complex queries not efficiently handled by a simple key-value store.
- **Image Storage**:
   - **Choice**: Amazon S3 (Object Storage).
   - **Why**: Highly durable (11 nines), available, scalable, and cost-effective for storing all product images (raw and processed versions).
   - **Concept Integration**: Object Storage, utilizes S3 Storage Classes (e.g., Standard for frequently accessed, Intelligent-Tiering for cost optimization), S3 Transfer Acceleration for faster image uploads by admins.
- **Caching Layer (Performance Optimization)**:

   - **Choice**: Amazon ElastiCache (Redis).
   - **Why**: Caches frequently accessed product details or search results to reduce load on DynamoDB/OpenSearch and improve read latency.
   - **Concept Integration**: Caching for performance, part of the Layered architecture.
- **Content Delivery Network (CDN)**:

   - **Choice**: Amazon CloudFront.
   - **Why**: Caches product images and potentially static website assets at edge locations globally, reducing latency for users and offloading traffic from S3 and backend services.
   - **Concept Integration**: CDN for global content delivery and performance optimization.
- **Asynchronous Communication & Event Bus**:

   - **Choice**: Amazon EventBridge or Amazon SQS/SNS.
   - **Why**: Decouples services. For instance, when a product is added/updated in DynamoDB, a change event can be published. The Search Indexing service (Lambda) subscribes to this event to update OpenSearch.
   - **Concept Integration**: Core of the Event-Driven Architecture and part of the Broker Pattern (EventBridge/SQS as the broker).
- **Monitoring & Logging**:

   - **AWS CloudWatch**: For collecting metrics, logs from all services, and setting up alarms.
   - **AWS CloudTrail: For auditing API calls and actions within the AWS account.
   - **Concept Integration**: Essential for Maintainability & Observability.

**Concept Integration Summary**:
- **Microservices Pattern**: Product, Category, and Image Processing as distinct, independently deployable services.
- **Event-Driven Architecture**: S3 events, DynamoDB Streams, and an Event Bus (EventBridge/SQS) facilitate asynchronous communication and decoupled services.
- **Layered Architecture**: Clear separation between Client, API Gateway, Backend Services, and Data Layers.
- **Broker Pattern**: API Gateway acts as a broker for client requests; Event Bus acts as a broker for inter-service communication.
- **Object Storage (S3)**: Used for highly durable and scalable image storage, leveraging different S3 Storage Classes for cost optimization.
- **CDN (CloudFront)**: Enhances performance and reduces latency for global image delivery.
- **Specialized Databases**: DynamoDB for core product data (high scalability, low latency), OpenSearch for complex full-text search.
- **Caching (ElastiCache)**: Reduces database load and improves read performance.
- **Serverless Compute (Lambda)**: Provides elasticity and cost-efficiency for backend services and event processing.
Security Features (IAM, WAF, Encryption): Integrated across layers for robust protection.


