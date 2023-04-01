FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar app.jar
# RUN mvn clean package
ENTRYPOINT ["java","-jar","/app.jar"]
