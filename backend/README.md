# Retail Book Shop API

Spring Boot backend for the React shop. Run from this directory with `mvn spring-boot:run` after installing Java 17+ and Maven.

Demo login: `reader@example.com` / `password`

The API uses an in-memory store for the first vertical slice. Replace `ShopStore` with JPA repositories and a database before production.

Main endpoints:

- `POST /api/auth/login`
- `POST /api/auth/password-reset`
- `GET /api/books?category=Fiction`
- `GET /api/books?q=java`
- `GET /api/books/{id}`
- `GET /api/cart`
- `POST /api/cart/items`
- `DELETE /api/cart/items/{bookId}`
- `POST /api/orders`
- `GET /api/orders/{orderId}`
- `POST /api/payments/intent`
- `GET /api/inventory/{bookId}`
- `GET /api/orders/{orderId}/delivery`
