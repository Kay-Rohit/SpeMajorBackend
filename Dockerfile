# FROM openjdk:11
# ADD ./target/SPEMajorBackend-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 5000
# ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:11
COPY ./target/SPEMajorBackend-0.0.1-SNAPSHOT.jar ./
WORKDIR ./
CMD ["java", "-jar", "SPEMajorBackend-0.0.1-SNAPSHOT.jar"]