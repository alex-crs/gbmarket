FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/gateway-service-0.0.1-SNAPSHOT.jar
WORKDIR 'c:\docker\gateway\'
COPY ${JAR_FILE} gatewayService.jar
ENTRYPOINT ["java","-jar","gatewayService.jar"]