FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/tpadds2024-1.0.0-shaded.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/app.jar"]