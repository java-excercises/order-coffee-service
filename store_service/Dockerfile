FROM gradle:8.3-jdk17-focal as base
WORKDIR /app
COPY gradle ./gradle
COPY gradlew gradlew.bat ./
COPY build.gradle settings.gradle gradle.properties ./
COPY src ./src
RUN ls -ail

FROM base as builder
WORKDIR /app
RUN chmod +x gradlew && ./gradlew bootJar

FROM eclipse-temurin:17-jdk-focal
COPY --from=builder /app/build/libs/store_service-0.0.1-SNAPSHOT.jar /app/store_service-0.0.1-SNAPSHOT.jar