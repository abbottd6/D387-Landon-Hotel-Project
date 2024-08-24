FROM openjdk:17-jdk
LABEL authors="Dayton Abbott"
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]