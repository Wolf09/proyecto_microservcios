FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/*.jar cuenta-movimientos-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/cuenta-movimientos-server-0.0.1-SNAPSHOT.jar"]