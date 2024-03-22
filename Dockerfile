FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src
RUN mvn clean install -DskipTests


FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build /app/target/tech-challenge-pos-tech-1.0.0.jar tech-challenge-pos-tech.jar
ENV JAVA_APP_ARGS="--spring.config.location=/src/main/resources/application.properties"
ENTRYPOINT ["java","-jar","tech-challenge-pos-tech.jar", "$JAVA_APP_ARGS"]