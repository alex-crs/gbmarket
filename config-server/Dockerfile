FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/config-service-0.0.1-SNAPSHOT.jar
WORKDIR 'c:\docker\config\'
COPY ${JAR_FILE} configService.jar
ENTRYPOINT ["java","-jar","configService.jar"]