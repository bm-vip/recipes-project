FROM openjdk:17-oracle
WORKDIR /app
ADD ./target/recipes-project-1.0.0-SNAPSHOT.jar /app/application.jar
EXPOSE 2022
ENTRYPOINT ["java", "-jar", "application.jar"]