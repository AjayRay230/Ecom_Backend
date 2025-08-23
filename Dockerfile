# 1. Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first for caching
COPY ecom-proj/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# Copy source
COPY ecom-proj/src ./src

# Build app
RUN mvn clean package -DskipTests -B

# 2. Run stage
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
