FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/front-service-0.0.1-SNAPSHOT.jar
WORKDIR 'c:\docker\front\'
COPY ${JAR_FILE} frontService.jar
ENTRYPOINT ["java","-jar","frontService.jar"]