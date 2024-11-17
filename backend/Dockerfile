# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-18 AS build
WORKDIR /app

# Copy the Maven project files from the backend subfolder
COPY pom.xml .
COPY src ./src

# Build the project using Maven
RUN mvn clean package -DskipTests

# Use an official JDK runtime image to run the application
FROM openjdk:18-jdk-slim
WORKDIR /app

# Copy the built JAR file from the previous build stage
COPY --from=build /app/target/Places-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
