FROM openjdk:20
COPY ./target/gpstracker-0.0.1-SNAPSHOT.jar gpstracker.jar
CMD ["java","-jar","gpstracker.jar"]