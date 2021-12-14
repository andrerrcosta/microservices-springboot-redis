FROM openjdk:14 as builder
ADD target/gateway-service.jar gateway-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","gateway-service.jar"]