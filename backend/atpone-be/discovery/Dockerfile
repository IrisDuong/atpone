# Dockerfile-discovery

FROM eclipse-temurin:17-jre-jammy

WORKDIR /atpone
ARG JAR_FILE
COPY ${JAR_FILE} atpone.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","atpone.jar"]