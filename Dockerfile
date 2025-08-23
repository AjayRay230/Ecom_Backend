# ==============================
# 1. Build stage using Maven
# ==============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom.xml first for dependency caching
COPY ecom-proj/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# Copy source code
COPY ecom-proj/src ./src

# Build the app (skip tests for faster build)
RUN mvn clean package -DskipTests -B

# ==============================
# 2. Run stage using JDK
# ==============================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
