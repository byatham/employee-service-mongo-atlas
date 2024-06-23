# Use the official OpenJDK 17 image as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /employee/app

# Copy the executable JAR file to the container
COPY  target/employee-mongo-service.jar  /employee/app/employee-mongo-service.jar

# Make port 9091 available to the world outside this container
EXPOSE 8087

# Run the JAR file
ENTRYPOINT ["java","-jar","employee-mongo-service.jar"]