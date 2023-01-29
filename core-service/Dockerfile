FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/core-service-0.0.1-SNAPSHOT.jar
WORKDIR 'c:\docker\core\'
COPY ${JAR_FILE} coreService.jar
ENTRYPOINT ["java","-jar","coreService.jar"]