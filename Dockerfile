FROM java:8-jdk-alpine
MAINTAINER Patrick BEUGRE
COPY ./target/spring-test-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "spring-test-0.0.1-SNAPSHOT.jar"]