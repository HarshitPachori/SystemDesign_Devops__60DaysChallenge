---
title: "Day 36: System Design Case Study - Simple Online Judge System (LeetCode-like)"
description: "A summary of my 36th day's learning in the 60-day challenge, System Design Case Study - Simple Online Judge System (LeetCode-like)"
keywords:
  - System Design Case Study - Simple Online Judge System (LeetCode-like)
  - Day 36
  - Challenge
---

### Table of contents :
- [System Design Case Study: Simple Online Judge System (LeetCode-like)](#system-design-case-study-simple-online-judge-system-leetcode-like)



### System Design Case Study: Simple Online Judge System (LeetCode-like)
Designing a simple Online Judge (OJ) system, like LeetCode, involves several key components working together to compile, execute, and test user-submitted code securely and efficiently. The core challenge lies in running untrusted user code without compromising the system's integrity or performance.

1. **Overall Architecture**
A typical online judge system follows a microservices-based architecture to ensure scalability, fault tolerance, and isolation.

Here's a high-level overview of the main components:

- **Frontend (User Interface)**: This is the web application users interact with. It provides problem descriptions, a code editor, submission functionality, and displays results.

- **API Gateway**: Acts as the entry point for all client requests, routing them to the appropriate backend services. It handles authentication and rate limiting.

- **Web Server / Problem Service**: Manages problem statements, test cases, user data, and handles user submissions. It might also serve static content for the frontend.

- **Message Queue (e.g., Kafka, RabbitMQ, SQS)**: Decouples the submission process from the code execution. When a user submits code, the submission details are pushed to a message queue. This allows the system to handle spikes in submissions without overwhelming the execution workers.

- **Code Execution Worker(s)**: These are the heart of the system. They pull submission requests from the message queue, compile, execute, and test the user's code. They operate in isolated, sandboxed environments.

- **Database (e.g., PostgreSQL, MySQL)**: Stores problem details, test cases, user information, and submission results.

- **Object Storage (e.g., S3)**: Can be used to store large test case input/output files and potentially compiled binaries.

2. **Compilation Process**
When a user submits code, the system needs to compile it if it's a compiled language (like C++, Java).

- **Submission Reception**: The Web Server receives the user's code, language, and problem ID.

- **Code Storage**: The submitted code is saved temporarily (e.g., to a local disk on the worker or in a temporary object storage location).

- **Compiler Invocation**: The Code Execution Worker invokes the appropriate compiler for the submitted language within the sandboxed environment.

   - **For C++/Java**: g++ <source_file> or javac <source_file>.

   - **For interpreted languages like Python**: No explicit compilation step, but the interpreter will process the code.

- **Compilation Output Capture**: The compiler's output (success/failure, error messages) is captured.

**Compilation Result**:

   - **Success**: If compilation is successful, an executable binary (or bytecode) is generated. The process moves to execution.

   - **Compilation Error (CE)**: If compilation fails, the error messages are recorded, and the submission status is updated to "Compilation Error."

3. **Execution Environment (Sandboxing)**
This is the most critical part for security and stability. User code is untrusted and could be malicious (e.g., infinite loops, attempts to access system files, network calls). A robust sandboxing mechanism is essential.

**Isolated Environment**: Each code execution should happen in a completely isolated environment. Common approaches include:

   - **Containers (e.g., Docker, LXC)**: Lightweight and widely used. Each submission can run in its own container, which is then destroyed after execution. This provides process, network, and filesystem isolation.

   - **Virtual Machines (VMs)**: Offer stronger isolation than containers but are heavier and slower to provision. Less common for individual code runs due to overhead.

**Resource Limits**: Within the sandbox, strict resource limits are enforced to prevent denial-of-service attacks or excessive resource consumption:

   - **CPU Limit**: Prevents infinite loops from consuming all CPU.

   - **Memory Limit**: Prevents memory leaks or excessive memory usage.

   - **Time Limit**: Sets a maximum execution time (e.g., 1-5 seconds). If exceeded, the process is terminated, and the status is "Time Limit Exceeded" (TLE).

   - **Process Limit**: Limits the number of processes a user's code can spawn.

   - **Network Access Restriction**: Crucial for security. User code should generally not have any network access to prevent exfiltration of data or launching attacks.

   - **Filesystem Access Restriction**: Limit access to only necessary directories (e.g., where the code and test cases reside). Prevent access to sensitive system files.

**Execution**: The compiled code/script is executed within this sandboxed environment.

   - Input for the test case is fed to the program's standard input (stdin).

   - The program's standard output (stdout) and standard error (stderr) are captured.

4. **Testing Mechanism**
After successful compilation and execution, the output of the user's code is compared against the expected output for a set of predefined test cases.

**Test Case Retrieval**: The Code Execution Worker fetches the test cases (input and expected output) for the specific problem from the database or object storage.

**Iterative Execution**: The user's compiled code is run against each test case.

   - **For each test case**:

      - The input is provided to the user's program.

      - The program's output is captured.

**Output Comparison (Diffing)**:

   - The captured output from the user's program is compared with the expected output for that test case.

   - Comparison typically involves ignoring leading/trailing whitespace and line endings for flexibility.

   - **Judge Logic**: The comparison logic determines if the output is "Accepted" (AC), "Wrong Answer" (WA), or other statuses.

**Status Determination**:

   - **Accepted (AC)**: Output matches expected for all test cases.

   - **Wrong Answer (WA)**: Output does not match expected for one or more test cases.

   - **Time Limit Exceeded (TLE)**: Program ran longer than allowed.

   - **Memory Limit Exceeded (MLE)**: Program used more memory than allowed.

   - **Runtime Error (RE)**: Program crashed or terminated abnormally (e.g., division by zero, array out of bounds).

   - **Presentation Error (PE)**: Output is correct but has minor formatting differences (e.g., extra spaces/newlines).

**Result Reporting**: The final status and any relevant details (execution time, memory usage, error messages) are sent back to the Web Server, which then updates the database and informs the user.

5. **Scalability and Reliability**
   - **Horizontal Scaling of Workers**: The Code Execution Workers are stateless and can be scaled horizontally by adding more instances as submission volume increases. Container orchestration platforms (like Kubernetes) are ideal for managing these workers.

   - **Message Queue**: The message queue acts as a buffer, absorbing submission spikes and ensuring that workers can process them at their own pace, preventing system overload.

   - **Load Balancing**: API Gateway and load balancers distribute incoming requests across multiple instances of the Web Server and other services.

   - **Database Scaling**: Use a scalable database solution (e.g., AWS RDS with read replicas, sharding for very high loads) to handle problem and submission data.

6. **Security Considerations**
**Beyond sandboxing, other security measures are crucial**:

   - **Least Privilege**: Ensure that each component (especially execution workers) has only the minimum necessary permissions.

   - **Network Segmentation**: Isolate different services and components using VPCs, subnets, and security groups.

   - **Input Validation**: Sanitize and validate all user inputs to prevent injection attacks.

   - **Logging and Monitoring**: Comprehensive logging of all activities and real-time monitoring for suspicious behavior or resource exhaustion.

   - **Ephemeral Environments**: Destroy and recreate execution environments (containers/VMs) after each run to ensure a clean slate and prevent state leakage between submissions.

Designing an online judge system is a fascinating challenge that combines principles of distributed systems, security, and performance optimization.
