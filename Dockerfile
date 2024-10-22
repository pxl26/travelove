FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /home/app/
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn clean install -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim-buster
COPY --from=build /home/app/target/*.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]