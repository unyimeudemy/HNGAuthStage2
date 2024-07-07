FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/userAuthStage2-0.0.1-SNAPSHOT.jar ./userAuthStage2.jar
EXPOSE 8080
CMD ["java", "-jar", "userAuthStage2.jar"]