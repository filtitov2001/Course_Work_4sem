FROM openjdk:15
ADD target/mirea-0.0.1-SNAPSHOT.jar app.jar
COPY ./target/classes/ src/main/resources
ENTRYPOINT ["java","-jar","app.jar"]