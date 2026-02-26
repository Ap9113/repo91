# Spring Boot E-Commerce Website

This is a sample interactive e-commerce platform built with Spring Boot, Thymeleaf, Spring Security, JPA, and H2 database.

Features:
- User registration & login
- Browse products
- Add to cart (stored in session)
- Checkout & order history

Getting Started:
1. Ensure Java 17 and Maven are installed.
2. `mvn clean spring-boot:run`
3. Navigate to http://localhost:8080
4. Register a new account, browse products, add to cart, and checkout.

H2 Console is available at http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:ecomdb`, user `sa`, no password).
