# 1. Use Maven to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom.xml first (for dependency caching)
COPY ecom-proj/pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY ecom-proj/src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# 2. Use JDK image to run the app
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
