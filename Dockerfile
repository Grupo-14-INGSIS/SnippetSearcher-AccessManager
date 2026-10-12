# Multi-stage build

# Stage 1: build
FROM gradle:8.14-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar -x test

# Stage 2: runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN mkdir -p /usr/local/newrelic
# El agente se baja de Maven Central: >= 8.25.0 es necesario para capturar logs de logback 1.5.20+ (Spring Boot 4)
ADD https://repo1.maven.org/maven2/com/newrelic/agent/java/newrelic-agent/8.25.1/newrelic-agent-8.25.1.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

# Usa el nombre exacto del JAR
COPY --from=build /app/build/libs/*SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-javaagent:/usr/local/newrelic/newrelic.jar","-jar","/app/app.jar"]