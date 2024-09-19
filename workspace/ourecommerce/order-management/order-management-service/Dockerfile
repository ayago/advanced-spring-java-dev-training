# Use an official OpenJDK 21 runtime as a parent image
FROM openjdk:21-jdk-slim

# Set a working directory for the application
WORKDIR /app

# Define environment variables
ENV LOG_FILE_PARENT=/logs
ENV POSTGRES_URL=jdbc:postgresql://host.docker.internal:5432/postgres
ENV POSTGRES_USERNAME=postgres
ENV POSTGRES_PASSWORD=guest
ENV RABBITMQ_HOST=host.docker.internal
ENV EUREKA_URI=http://host.docker.internal:8761/eureka

# Create the directory structure for logs
RUN mkdir -p ${LOG_FILE_PARENT}

# Specify the mount point for external logging
VOLUME ${LOG_FILE_PARENT}

# Copy the JAR file from the build context and rename it
COPY target/order-management-service.jar /app/order-management-service.jar

# Expose the port used by the service (8081 for HTTP communication as per application.yml)
EXPOSE 8081

# Set environment variables for JVM memory limits and Spring Boot active profile
ENV JAVA_OPTS="-Xms256m -Xmx1g -DLOG_FILE_PARENT=${LOG_FILE_PARENT} -DPOSTGRES_URL=${POSTGRES_URL} -DPOSTGRES_USERNAME=${POSTGRES_USERNAME} -DPOSTGRES_PASSWORD=${POSTGRES_PASSWORD} -DRABBITMQ_HOST=${RABBITMQ_HOST} -DEUREKA_URI=${EUREKA_URI}"

# Run the Spring Boot application with the specified JAVA_OPTS
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/order-management-service.jar"]
