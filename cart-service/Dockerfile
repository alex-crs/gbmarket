FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/cart-service-0.0.1-SNAPSHOT.jar
WORKDIR 'c:\docker\cart\'
COPY ${JAR_FILE} cartService.jar
ENTRYPOINT ["java","-jar","cartService.jar"]