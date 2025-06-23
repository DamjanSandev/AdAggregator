FROM maven:3.9.9-eclipse-temurin-22 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

EXPOSE 9090
CMD ["java","-jar","target/Backend-0.0.1-SNAPSHOT.jar"]
