FROM maven:3.5.4-jdk-8-alpine as builder

COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

COPY target/spring-boot-product-0.0.1-SNAPSHOT.jar product-1.0.0.jar
ENTRYPOINT ["java","-jar","/product-1.0.0.jar"]

EXPOSE 8082