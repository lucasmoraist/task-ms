FROM maven:3.8.7-eclipse-temurin-19-alpine AS build

WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:19-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV POSTGRES_HOST=${POSTGRES_HOST}

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]