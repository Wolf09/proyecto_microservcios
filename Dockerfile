FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/*.jar cliente-persona-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/cliente-persona-server-0.0.1-SNAPSHOT.jar"]