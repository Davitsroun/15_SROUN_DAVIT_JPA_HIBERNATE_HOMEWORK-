FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY build/libs/_15_SROUN_DAVIT_JPA_HIBERNATE_HOMEWORK-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]
