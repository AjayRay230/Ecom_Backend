# 1. Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (cache layer)
COPY ecom-proj/pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY ecom-proj/src ./src

# Build jar
RUN mvn clean package -DskipTests -B

# 2. Run stage (lightweight JRE only)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Run with container-friendly JVM settings
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
