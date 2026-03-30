# Dockerfile-api-gateway
FROM eclipse-temurin:17-jre-jammy
WORKDIR /atpone
ARG JAR_FILE
COPY ${JAR_FILE} atpone.jar
ENTRYPOINT ["java","-jar","atpone.jar"]