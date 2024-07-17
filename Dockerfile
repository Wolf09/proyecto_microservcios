FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/*.jar eureka-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/eureka-server-0.0.1-SNAPSHOT.jar"]