FROM java:8-jdk-alpine
MAINTAINER Patrick BEUGRE
COPY ./target/spring-test-1.0.0-RELEASE.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "spring-test-1.0.0-RELEASE.jar"]