FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle bootJar -x test

FROM amazoncorretto:17-al2023-jdk
WORKDIR /app
COPY --from=build /app/build/libs/fakedetecting-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
