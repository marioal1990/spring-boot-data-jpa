FROM openjdk:8
COPY ./target/spring-boot-data-jpa-1.0.jar spring-boot-data-jpa-1.0.jar
ENTRYPOINT ["java", "-jar", "spring-boot-data-jpa-1.0.jar"]