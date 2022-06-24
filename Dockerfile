FROM openjdk:11-slim-buster

WORKDIR /app

COPY . .
#RUN ./gradlew user-service:bootJar
RUN ./gradlew user-service:bootJar \
    && ./gradlew random-service:bootJar
