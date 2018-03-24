FROM openjdk:8-jdk-alpine
COPY target/here-to-help-1.0-SNAPSHOT.jar /app.jar
CMD java -jar app.jar
