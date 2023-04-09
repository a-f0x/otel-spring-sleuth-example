FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY . .
#RUN ./gradlew user-service:bootJar
RUN ./gradlew user-service:bootJar \
    && ./gradlew random-service:bootJar
