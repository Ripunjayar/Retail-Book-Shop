# Learning infrastructure examples

These files are dummy examples for understanding the architecture. They are not production-ready deployment files and are not connected to the current application.

NGINX is intentionally not included.

The examples show these responsibilities:

- `load-balancer/azure-load-balancer.yaml`: conceptual public load balancer
- `api-gateway/api-gateway.yaml`: conceptual routing and gateway policies
- `cache/redis.conf`: local Redis cache settings
- `services/service-routing.yaml`: conceptual private frontend and backend services

Current application status:

- React runs from the project root with Vite.
- Spring Boot runs from `backend/` on port 8080.
- The Spring Boot demo currently uses an in-memory store.
- These files do not provision Azure resources or start Redis.
