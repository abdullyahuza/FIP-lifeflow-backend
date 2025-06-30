# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set workdir inside the container
WORKDIR /app

# Copy source files
COPY pom.xml .
COPY src ./src

# Package the application (skip tests if needed)
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM openjdk:17

# Set workdir inside the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (change if needed)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
