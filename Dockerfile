# Stage 1: Build JAR
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy all source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run JAR
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the JAR from stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 9192

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
