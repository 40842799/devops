FROM amazoncorretto:17
COPY ./target/semApp-jar-with-dependencies.jar /tmp/seMethods.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethods.jar"]
