FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY ./ ./

RUN ./gradlew clean build

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
