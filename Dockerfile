# Build stage
FROM maven:3.8.8-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml from subfolder and download dependencies to leverage Docker caching
COPY spring-bfhl/pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code from subfolder and build the package
COPY spring-bfhl/src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (default is 8080)
EXPOSE 8080

# Run the jar with dynamic port support
ENTRYPOINT ["java", "-jar", "app.jar"]
