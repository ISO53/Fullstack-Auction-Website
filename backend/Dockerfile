# Use the Maven Docker image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory to /app
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the application using Maven
RUN mvn clean package

# Use the OpenJDK Docker image as the base image
FROM openjdk:17-slim

# Set the working directory to /app
WORKDIR /app

# Copy the .jar file from the Maven build stage to the container
COPY --from=build /app/target/bidding-0.0.1-SNAPSHOT.jar .

# Start the application
CMD ["java", "-jar", "bidding-0.0.1-SNAPSHOT.jar"]