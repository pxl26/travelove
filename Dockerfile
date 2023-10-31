FROM maven:3.8.7-openjdk-18-slim AS build
WORKDIR /home/app/
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn clean install -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]