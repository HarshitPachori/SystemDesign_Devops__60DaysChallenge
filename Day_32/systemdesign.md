---
title: "Day 32: Kubernetes Architecture Overview - Master/Worker Nodes, Pods, Deployments"
description: "A summary of my 32th day's learning in the 60-day challenge, Kubernetes Architecture Overview - Master/Worker Nodes, Pods, Deployments"
keywords:
  - Kubernetes Architecture Overview - Master/Worker Nodes, Pods, Deployments
  - Day 32
  - Challenge
---

### Table of contents :
- [Kubernetes Architecture Overview: Master/Worker Nodes, Pods, Deployments](#kubernetes-architecture-overview-masterworker-nodes-pods-deployments)
- [Key Abstractions: Pods and Deployments](#key-abstractions-pods-and-deployments)


### Kubernetes Architecture Overview: Master/Worker Nodes, Pods, Deployments
A Kubernetes cluster consists of a set of machines, called nodes, that run containerized applications. These nodes are divided into two main types: Master Nodes (or Control Plane nodes) and Worker Nodes.

1. **Master Node (Control Plane)**
The Master Node is the "brain" of the Kubernetes cluster. It's responsible for managing the entire cluster, making global decisions about scheduling, detecting and responding to cluster events, and maintaining the desired state of the cluster. In a production environment, you typically have multiple master nodes for high availability.

The key components running on the Master Node are:

   - **Kube-apiserver**:

      - **Function**: This is the front-end of the Kubernetes control plane. It exposes the Kubernetes API, which is the central communication point for all operations within the cluster. All internal and external components (like kubectl, other control plane components, and worker nodes) communicate with the API server to query or modify the cluster state.

      - **Role**: It validates API requests, processes them, and updates the state in etcd. It's essentially the "traffic cop" and "gatekeeper" of the cluster.

   - **etcd**:

      - **Function**: A highly available, distributed, and consistent key-value store. It serves as Kubernetes' backing store for all cluster data. All cluster configuration, state, and metadata (e.g., what nodes exist, what pods should be running, network configurations) are stored here.

      - **Role**: It's the single source of truth for the entire cluster's desired and current state.

   - **Kube-scheduler**:

      - **Function**: Watches for newly created Pods that have no assigned node and selects a suitable node for them to run on.

      - **Role**: It makes scheduling decisions based on various factors like resource requirements (CPU, memory), hardware/software constraints, policy constraints, affinity/anti-affinity rules, data locality, and inter-workload interference.

   - **Kube-controller-manager**:

      - **Function**: Runs various controller processes that regulate the state of the cluster. Each controller is a control loop that continuously monitors the actual state of the cluster through the API server and attempts to move the current state towards the desired state.

      - **Role**: Examples include:

         - **Node Controller**: Notices when nodes go down.

         - **Replication Controller**: Maintains the desired number of Pods for a ReplicaSet.

         - **Endpoints Controller**: Populates the Endpoints object (which joins Services & Pods).

         - **Service Account & Token Controllers**: Create default Service Accounts and API access tokens for new Namespaces.

   - **Cloud-controller-manager (Optional)**:

      - **Function**: Integrates Kubernetes with the underlying cloud provider's API. It runs controllers that are specific to your cloud provider (e.g., AWS, GCP, Azure).

      - **Role**: Handles cloud-specific operations like provisioning load balancers, managing cloud-provider storage volumes, and detecting node failures in the cloud environment.

2. **Worker Nodes (Compute Nodes)**
Worker Nodes are the machines (physical or virtual) where your actual containerized applications run. They are managed by the Master Node and contain the necessary services to run Pods, communicate with the control plane, and handle networking for the containers.

The key components running on each Worker Node are:

   - **Kubelet**:

      - **Function**: An agent that runs on each node. It communicates with the Master Node's API server, receives Pod specifications (PodSpecs), and ensures that the containers described in those PodSpecs are running and healthy on its node.

      - **Role**: It's the primary "node agent" that registers the node with the cluster, reports its status, and manages the lifecycle of Pods and their containers (starting, stopping, monitoring).

   - **Kube-proxy**:

      - **Function**: A network proxy that runs on each node. It maintains network rules on the node, allowing network communication to your Pods from both inside and outside your cluster.

      - **Role**: It implements the Kubernetes Service abstraction, enabling stable network access to Pods even as their IPs change. It handles load balancing for Services.

   - **Container Runtime**:

      - **Function**: The software responsible for running containers (e.g., Docker, containerd, CRI-O).

      - **Role**: It pulls container images from a registry, unpacks them, and runs the application containers on the node.

### Key Abstractions: Pods and Deployments
While the Master and Worker Nodes form the infrastructure, Pods and Deployments are the primary objects you interact with to define and manage your applications.

3. **Pods**
   - **What it is**: A Pod is the smallest and most fundamental deployable unit in Kubernetes. It represents a single instance of a running process in your cluster. A Pod typically encapsulates one (the most common scenario) or more tightly coupled containers that share resources.

   - **Why it's important**:

      - **Co-location**: Containers within a single Pod are always co-located and co-scheduled on the same Worker Node.

      - **Shared Resources**: Containers in a Pod share the same network namespace (meaning they can communicate via localhost), IP address, port space, and can share storage volumes. This is ideal for applications where a "main" container needs a "sidecar" container (e.g., a web server with a logging agent or a data synchronizer).

      - **Atomic Unit**: Kubernetes manages Pods as atomic units. If a container in a multi-container Pod fails, the entire Pod is typically restarted.

      - **Abstraction**: Pods abstract away the underlying container runtime, allowing Kubernetes to manage containers without directly interacting with Docker (or other runtimes).

4. **Deployments**
   - **What it is**: A Deployment is a higher-level Kubernetes object that provides declarative updates for Pods and ReplicaSets. You describe a desired state in a Deployment manifest (e.g., "I want 3 replicas of my web application, using image my-app:v2"), and the Deployment controller works to change the actual state to match the desired state.

   - **How it manages Pods**:

      - **ReplicaSet Management**: A Deployment automatically creates and manages a ReplicaSet. A ReplicaSet ensures that a specified number of identical Pod replicas are running at all times. If a Pod dies, the ReplicaSet detects it and creates a new one.

      - **Declarative Updates**: You define the desired state (e.g., change the container image version). The Deployment controller then orchestrates the rollout of the new version, typically using a rolling update strategy (gradually replacing old Pods with new ones to ensure zero downtime).

      - **Rollbacks**: If a new deployment introduces issues, you can easily roll back to a previous stable version of your application with a single command.

      - **Scaling**: You can easily scale your application up or down by changing the replicas count in your Deployment. The Deployment will then instruct its associated ReplicaSet to create or remove Pods.

      - **Self-Healing**: Through its management of ReplicaSets and integration with probes, Deployments contribute significantly to the self-healing capabilities of Kubernetes, ensuring your application instances are always running and healthy