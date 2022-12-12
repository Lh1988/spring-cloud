FROM openjdk:17-jdk-alpine
VOLUME [ "/tmp" ]
ADD target/system-0.0.1-SNAPSHOT.jar system.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar","/system.jar"]