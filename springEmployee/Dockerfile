FROM openjdk:20-oracle
COPY target/*.jar employee.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","employee.jar"]