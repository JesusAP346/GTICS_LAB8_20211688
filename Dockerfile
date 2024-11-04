FROM openjdk:24-ea-17-jdk
VOLUME /tmp
EXPOSE 8080
ADD ./target/GTICS_LAB8_20211688-0.0.1-SNAPSHOT.jar proyecto.jar
ENTRYPOINT ["java","-jar","proyecto.jar"]