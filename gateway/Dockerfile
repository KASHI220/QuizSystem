FROM openjdk:28-ea-oraclelinux10

COPY build/libs/gateway-0.0.1-SNAPSHOT.jar gateway-service.jar

ENTRYPOINT ["java", "-jar", "/gateway-service.jar"]