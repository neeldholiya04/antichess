# Antichess – DevSecOps CI/CD Project

## 1. Problem Background & Motivation
Modern CI/CD pipelines often focus only on making builds pass, while ignoring security, provenance, and deployment safety.  
This project was built to demonstrate a **production-grade DevSecOps CI/CD pipeline**, where:
- Security is shifted left
- Artifacts are immutable and trusted
- CI and CD responsibilities are clearly separated
- Kubernetes is used correctly even for non-HTTP workloads

The motivation is to design a pipeline that reflects **real-world industry practices**, not just tool usage.

---

## 2. Application Overview
**Antichess** is a Java-based interactive CLI game (Suicide Chess):
- Runs continuously and waits for terminal input
- No HTTP server or exposed ports
- Users interact via STDIN/STDOUT
- Suitable for demonstrating Kubernetes workloads beyond web services

**Tech Stack**
- Java 21
- Maven
- Docker
- GitHub Actions
- Kubernetes (kind on VM)

---

## 3. CI/CD Workflow Diagram
High-level flow:

```
Developer Push / Tag
        |
        v
GitHub Actions CI
  - Linting
  - SAST (CodeQL)
  - SCA (OWASP)
  - Tests
  - Docker Build
  - Trivy Scan
  - Image Signing (Cosign)
        |
        v
DockerHub (Signed Image)
        |
        v
GitHub Actions CD
  - Image Signature Verification
  - Kubernetes Deployment
        |
        v
Kubernetes Cluster (kind on VM)
```

---

## 4. Security & Quality Controls

### Code Quality
- **Checkstyle** enforces coding standards
- **JUnit tests** validate core chess logic

### DevSecOps Controls
- **SAST (CodeQL):** Detects insecure coding patterns
- **SCA (OWASP Dependency Check):** Identifies vulnerable dependencies
- **Container Scan (Trivy):** Detects OS and library vulnerabilities
- **Cosign (Keyless Signing):** Ensures artifact provenance
- **Cosign Verification in CD:** Zero-trust deployment

---

## 5. Results & Observations
- CI pipeline consistently produces signed, trusted images
- Vulnerabilities are detected early in the lifecycle
- CD deploys only verified artifacts
- Kubernetes successfully runs a long-lived interactive CLI workload
- Clear separation between CI (build & secure) and CD (deploy)

---

## 6. Limitations & Improvements

### Current Limitations
- Single environment (staging)
- Manual interaction via kubectl attach
- Static Kubernetes manifests

### Future Improvements
- Helm or Kustomize for templating
- Multi-environment promotion (staging → prod)
- Automated rollback on deployment failure
- Policy enforcement using OPA/Kyverno
- Centralized logging and monitoring

---

## 7. Final Conclusion
This project demonstrates that **DevOps is about design decisions, not YAML length**.  
By integrating security, artifact trust, and Kubernetes deployment for a non-web workload, the pipeline reflects **real production-grade DevSecOps practices**.

---

## 8. Steps to Run the Project Locally

### Prerequisites
- Java 21
- Maven
- Docker
- kubectl
- kind

### Build & Run Locally
```bash
mvn clean package
java -jar target/antichess.jar
```

### Build Docker Image
```bash
docker build -t antichess:local .
docker run -it antichess:local
```

---

## 9. Kubernetes Deployment (kind)

### Create Cluster
```bash
kind create cluster --name antichess
```

### Deploy Application
```bash
kubectl apply -f k8s/deployment.yaml
```

### Access Game
```bash
kubectl get pods
kubectl attach -it <pod-name>
```

---

## 10. Secrets Configuration (GitHub)

The following GitHub Secrets must be configured:

| Secret Name | Purpose |
|------------|---------|
| DOCKERHUB_USERNAME | DockerHub username |
| DOCKERHUB_TOKEN | DockerHub access token |
| KUBECONFIG | Base64-encoded kubeconfig for cluster |

⚠️ Secrets are **never hardcoded**.

---

## 11. CI Explanation

### CI Responsibilities
- Build application
- Run quality & security checks
- Create Docker image
- Scan image for vulnerabilities
- Push image to registry
- Sign image cryptographically

### CD Responsibilities
- Verify image signature
- Deploy to Kubernetes
- Validate rollout

This follows the principle:
> **Build once, deploy many times**

---

## Repository Structure

```
.
├── .github/workflows
│   ├── ci.yml
│   └── cd.yml
├── k8s
│   └── deployment.yaml
├── src
├── Dockerfile
├── pom.xml
└── README.md
```
