# EcomBackEnd
Sure! Here's the complete **plain text version** of your backend `README` without the license section — all combined and clean for direct use on GitHub:

---

E-Commerce Backend (Spring Boot)

This is the backend of a simple e-commerce application built with Spring Boot. It provides REST APIs to manage products and supports features like image storage, category filtering, and more.

Features:

* RESTful APIs for product management
* In-memory H2 database
* Auto-loaded sample data using data.sql
* Image support (imageName, imageType, imageData)
* CORS enabled for React frontend

Technologies Used:

* Java 17+
* Spring Boot
* Spring Data JPA
* H2 Database
* Maven

How to Run:

1. Open terminal and navigate to the backend project folder.
2. Run the application with:

   ./mvnw spring-boot\:run

The application will be available at:
[http://localhost:8080](http://localhost:8080)

API Endpoints:

* GET /api/products → Get all products
* GET /api/product/{id} → Get product by ID
* POST /api/products → Add a new product
* PUT /api/product/{id} → Update a product
* DELETE /api/product/{id} → Delete a product

H2 Database Console:

* URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* JDBC URL: jdbc\:h2\:mem\:testdb
* Username: sa
* Password: (leave blank)

Image Fields (in each product):

* imageName: The name of the image file (e.g. "laptop.jpg")
* imageType: The MIME type (e.g. "image/png")
* imageData: The image binary stored as Base64 or byte\[]

Sample SQL (data.sql):
Sample product data is loaded automatically from src/main/resources/data.sql when the app starts.

