FROM openjdk:17.0.2
VOLUME /tmp
ADD target/system-0.0.1.jar app.jar
COPY target/classes/application-dev.yml /
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]