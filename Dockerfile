FROM openjdk:11

COPY target/flu-0.0.1.jar /opt/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]