# Dockerfile-auth-server

# STAGE 1 : BUILD TO JAR FILE
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /atpone

COPY . .
RUN mvn -pl auth-server -am clean package -DskipTests

# STAGE 2 : RUN
FROM eclipse-temurin:17-jre-jammy

WORKDIR /atpone

COPY --from=builder /atpone/auth-server/target/*.jar atpone.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","atpone.jar"]