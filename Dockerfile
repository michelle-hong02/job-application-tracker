# Dockerfile
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built JAR
COPY build/libs/*SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]