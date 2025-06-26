---
title: "Day 25: API Design Principles "
description: "A summary of my 25th day's learning in the 60-day challenge, API Design Principles"
keywords:
  - API Design Principles
  - Day 25
  - Challenge
---

### Table of contents :
- [API Design Principles: RESTful API Best Practices](#api-design-principles-restful-api-best-practices)
- [Authentication & Authorization: JWT & OAuth 2.0 (Conceptual Overview)](#authentication--authorization-jwt--oauth-20-conceptual-overview)
- [JWT (JSON Web Tokens)](#jwt-json-web-tokens)
- [OAuth 2.0](#oauth-20)
- [JWT and OAuth 2.0 Relationship](#jwt-and-oauth-20-relationship)



### API Design Principles: RESTful API Best Practices
REST (Representational State Transfer) is an architectural style for designing networked applications. RESTful APIs are a common way for services (microservices or client-server) to communicate.

- **Best Practices**:

1. **Use Resources and Nouns (Not Verbs)**:

   - APIs should expose resources (nouns) that represent data or services, not actions (verbs).

   - **Bad**: /getAllUsers, /createUser

   - **Good**: /users

2. **Use Standard HTTP Methods for Actions**:

   - Map CRUD (Create, Read, Update, Delete) operations to standard HTTP verbs:

      - **GET**: Retrieve a resource or a collection of resources. (Idempotent & Safe)

      - **POST**: Create a new resource, or perform non-idempotent operations. (Not Idempotent, Not Safe)

      - **PUT**: Update/replace an entire resource. (Idempotent)

      - **PATCH**: Partially update a resource. (Not Idempotent)

      - **DELETE**: Remove a resource. (Idempotent)

   - **Idempotency**: An operation is idempotent if executing it multiple times has the same effect as executing it once. GET, PUT, DELETE are typically idempotent. POST is generally not.


   - **Safety**: A method is "safe" if it doesn't change the state of the server. GET is safe.

3. **Use Plural Nouns for Collections**:

   - GET /users (get all users)

   - GET /users/{id} (get a specific user)

4. **Use Clear Status Codes**:

   - Provide meaningful HTTP status codes for responses:

      - **200 OK**: General success.

      - **201 Created**: Resource successfully created (after POST).

      - **204 No Content**: Request processed, no content returned (e.g., successful DELETE).

      - **400 Bad Request**: Client-side error (invalid input).

      - **401 Unauthorized**: Authentication required.

      - **403 Forbidden**: Authenticated but no permission.

      - **404 Not Found**: Resource does not exist.

      - **409 Conflict**: Request conflicts with current state (e.g., trying to create resource that already exists).

      - **500 Internal Server Error**: Server-side error.

5. **Provide Clear Error Messages**:

   - Include a consistent error structure (e.g., JSON) with an error code, message, and possibly details.

6. **Versioning**:

   - APIs evolve. Versioning allows you to introduce changes without breaking existing clients.

   - **URL Versioning (e.g., v1, v2)**: api.example.com/v1/users, api.example.com/v2/users. (Common and clear, but changes URL paths).

   - **Header Versioning (e.g., Accept: application/vnd.example.v2+json)**: Uses custom Accept or X-Api-Version headers. (Clean URLs, but harder to test in browser).

   - **Query Parameter Versioning (e.g., api.example.com/users?version=2)**: Simple, but can conflict with other query parameters.

7. **HATEOAS (Hypermedia As The Engine Of Application State - Optional)**:

   - Resources include links to related resources, guiding the client on possible next actions. Makes APIs more discoverable and self-documenting.

   - Less commonly fully implemented due to added complexity, but a good principle to be aware of.

8. **Filtering, Sorting, Pagination**:

   - Provide query parameters for clients to filter (/users?status=active), sort (/users?sort=name,asc), and paginate (/users?page=2&limit=10).


### Authentication & Authorization: JWT & OAuth 2.0 (Conceptual Overview)
These are fundamental for securing modern applications, especially microservices.

1. **Authentication**
Authentication is the process of verifying who a user is (e.g., checking username and password).

2. **Authorization**
Authorization is the process of determining what an authenticated user is allowed to do (e.g., "Can this user access this resource?", "Can this user perform this action?").

### JWT (JSON Web Tokens)
- **What it is**: A compact, URL-safe means of representing claims (information) to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is digitally signed.


- **How it works**:

1. **Authentication**: User logs in with credentials to an authentication service.

2. **Token Issuance**: If credentials are valid, the authentication service generates a JWT. This JWT typically contains:

   - **Header**: Type of token (JWT) and signing algorithm (e.g., HS256).

   - **Payload**: Claims about the user (e.g., user ID, roles, expiration time). Do not put sensitive data here.

   - **Signature**: Created by hashing the header and payload with a secret key (known only to the issuer) and then signing it. This verifies the token's integrity and authenticity.

3. **Token Transmission**: The JWT is sent back to the client (e.g., in a cookie or Authorization header).

4. **Resource Access**: For subsequent requests, the client includes the JWT.

5. **Token Verification**: Resource services receive the JWT, verify its signature using the same secret key (or a public key if signed with RSA), check its expiration, and then extract claims from the payload to authorize the request.

- **Benefits**:

   - **Stateless**: The server doesn't need to store session information (unlike traditional sessions), making it ideal for microservices and horizontal scaling.

   - **Compact & Self-Contained**: Contains all necessary information, reducing database lookups.

   - **Decoupled**: Different services can validate the token independently.

- **Considerations**:

   - **Security**: If the secret key is compromised, all tokens are compromised. Tokens cannot be easily revoked before expiration (unless explicitly managed with blacklists/short expiry).

   - **Payload Size**: Keep payloads small.

### OAuth 2.0
- **What it is**: An authorization framework that enables an application to obtain limited access to a user's resources on another HTTP service (e.g., Google, Facebook, GitHub). It doesn't deal with authentication (who the user is), but authorization (what the application can do on behalf of the user).

- **How it works (Simplified "Authorization Code" Flow)**:

1. **User wants to grant access**: User uses a "Client Application" (e.g., a photo printing app).

2. **Client requests Authorization**: Client app redirects user to an "Authorization Server" (e.g., Google login page) to request access to a protected resource (e.g., Google Photos).

3. **User grants Authorization**: User logs in to the Authorization Server and grants permission for the client app to access their photos.

4. **Authorization Server redirects with Code**: Authorization Server redirects the user back to the Client app with an "Authorization Code."

5. **Client exchanges Code for Tokens**: Client app sends the Authorization Code to the Authorization Server's token endpoint (along with its client ID/secret).

6. **Authorization Server issues Tokens**: Authorization Server validates the code and issues two tokens:

   - **Access Token**: Short-lived token used by the client app to access the protected "Resource Server" (e.g., Google Photos API).

   - **Refresh Token (Optional)**: Long-lived token used to obtain new Access Tokens without user re-authentication.

7. **Client accesses Resources**: Client app uses the Access Token to make requests to the Resource Server.

8. **Resource Server validates Token**: Resource Server validates the Access Token (often using information from the Authorization Server) and serves the requested resource.

- **Key Roles**:

   - **Resource Owner**: The user who owns the data (e.g., you, on Google Photos).

   - **Client**: The application requesting access (e.g., the photo printing app).

   - **Authorization Server**: Verifies the Resource Owner's identity and issues tokens.

   - **Resource Server**: Hosts the protected resources and accepts Access Tokens.

- **Benefits**:

   - **Delegated Authorization**: User doesn't give their credentials to the client app.

   - **Limited Access**: Client app only gets specific, limited permissions (scopes).

   - **Standardized**: Widely adopted protocol.

   - **Considerations**: Complex to implement correctly, multiple "flows" for different scenarios (e.g., Implicit, Client Credentials, Device Code).

### JWT and OAuth 2.0 Relationship
JWTs are often used as the Access Tokens or Refresh Tokens in an OAuth 2.0 flow. So, OAuth 2.0 is about how an application gets permission to access resources, and JWT is a common format for the tokens used in that process.