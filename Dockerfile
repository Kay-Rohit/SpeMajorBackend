FROM openjdk:11
ADD ./SPEMajorBackend/target/SPEMajorBackend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]