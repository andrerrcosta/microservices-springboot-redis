FROM openjdk:14 as builder
ADD target/logger-service.jar logger-service.jar
EXPOSE 8580
ENTRYPOINT ["java","-jar","logger-service.jar"]