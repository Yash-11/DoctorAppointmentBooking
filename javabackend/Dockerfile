FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/springboot101-0.0.1-SNAPSHOT.jar springboot101.jar
ENTRYPOINT ["java","-jar","/springboot101.jar"]
EXPOSE 8080