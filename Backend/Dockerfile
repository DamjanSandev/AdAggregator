FROM maven:3.9.9-eclipse-temurin-22 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk
WORKDIR /app

COPY --from=builder /app/target/Backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090 5005

ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["java", "-jar", "app.jar"]
