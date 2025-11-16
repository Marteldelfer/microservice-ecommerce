FROM eclipse-temurin:25 as builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package

FROM eclipse-temurin:25

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","app.jar"]
