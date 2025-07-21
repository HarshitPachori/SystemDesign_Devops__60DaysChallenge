---
title: "Day 33: GitOps Introduction: Principles (Declarative, Versioned, Pulled, Reconciled)"
description: "A summary of my 33th day's learning in the 60-day challenge, GitOps Introduction: Principles (Declarative, Versioned, Pulled, Reconciled)"
keywords:
  - GitOps Introduction - Principles (Declarative, Versioned, Pulled, Reconciled)
  - Day 33
  - Challenge
---

### Table of contents :
- [GitOps Introduction: Principles (Declarative, Versioned, Pulled, Reconciled)](#gitops-introduction-principles-declarative-versioned-pulled-reconciled)



### GitOps Introduction: Principles (Declarative, Versioned, Pulled, Reconciled)
GitOps is an operational framework that takes DevOps best practices, particularly those from software development, and applies them to infrastructure automation. It's a way of implementing Continuous Delivery for infrastructure, emphasizing the use of Git as the single source of truth for defining and managing infrastructure and applications.

The core idea is that everything that can be described as code – your infrastructure, application configurations, and even operational procedures – is stored in a Git repository. Any changes to the system are made by submitting pull requests to this repository, which then trigger automated processes to apply those changes to your live environment.

GitOps is built upon four fundamental principles:

1. **Declarative**
- **Concept**: In a declarative system, you describe what you want the desired state of your system to be, rather than how to get there. You define the end result, and the system (e.g., Kubernetes, an Infrastructure as Code tool) takes care of the steps to achieve that state.

- **GitOps Application**:

   - All infrastructure and application configurations (e.g., Kubernetes YAML files, Terraform HCL, Ansible playbooks) are written as declarative code.

   - This code is stored in Git.

   - Instead of imperative commands (like "run this script to deploy"), you simply declare the desired state in Git, and the GitOps system ensures that state is reached.

- **Benefit**: Reduces complexity, makes configurations easier to understand, and prevents "configuration drift" (where the actual state deviates from the intended state).

2. **Versioned and Immutable**
- **Concept**: Every change to your system's configuration is tracked, versioned, and immutable within a version control system, typically Git. This means you have a complete history of every modification, who made it, and when.

- **GitOps Application**:

   - The Git repository serves as the single source of truth for your entire system's state.

   - Every change is a commit, providing an audit trail.

   - Branches, pull requests, and code reviews are used for collaboration and approval processes, just like application code.

   - The immutability of commits means you can easily roll back to any previous working state.

- **Benefit**: Provides a robust audit trail, enables easy rollbacks, facilitates collaboration, and enforces consistency across environments.

3. **Pulled (vs. Pushed)**
- **Concept**: In a traditional "push" deployment model, a CI/CD pipeline typically "pushes" changes directly to the infrastructure (e.g., kubectl apply, terraform apply). In a "pull" model, an automated agent or operator running within the target environment "pulls" the desired state from the Git repository.

- **GitOps Application**:

   - A specialized agent (often called a "reconciliation agent" or "operator," like Flux CD or Argo CD for Kubernetes) continuously monitors the Git repository for changes.

   - When a change is detected in Git, the agent pulls the new desired state.

   - The agent then applies these changes to the cluster or infrastructure.

- **Benefit**:

   - **Enhanced Security**: The agent needs read-only access to Git and write access only to its own environment. No external CI/CD pipeline needs direct write access to your production clusters. This reduces the attack surface.

   - **Operational Simplicity**: The deployment mechanism is simpler and more resilient.

   - **Self-healing**: The pull model inherently supports reconciliation (see next point).

4. **Reconciled**
- **Concept**: The system continuously observes the actual state of the infrastructure and compares it against the desired state defined in Git. If any discrepancies are found, the system automatically takes action to bring the actual state back into alignment with the desired state.

- **GitOps Application**:

   - The reconciliation agent constantly monitors the live environment and the Git repository.

   - If a resource is manually changed in the live environment (e.g., someone imperatively scales a deployment), the agent detects this "drift" and automatically reverts it to the state defined in Git.

   - If a component fails or deviates from its declared state, the agent detects this and initiates corrective actions.

- **Benefit**: Ensures that the live environment always matches the versioned configuration in Git, provides self-healing capabilities, and prevents configuration drift, leading to more stable and predictable systems.
