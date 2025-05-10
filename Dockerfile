FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY ./ ./

RUN ./gradlew clean build

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.war app.war

ENTRYPOINT ["java", "-war", "app.war"]
