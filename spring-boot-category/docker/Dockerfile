FROM maven:3.5.4-jdk-8-alpine

COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

COPY target/spring-boot-category-0.0.1-SNAPSHOT.jar category-1.0.0.jar
ENTRYPOINT ["java","-jar","/category-1.0.0.jar"]

EXPOSE 8081