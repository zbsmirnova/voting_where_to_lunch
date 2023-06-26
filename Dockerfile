FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY target/VotingWhereToLunch-1.0.jar .
ENTRYPOINT [ "java", "-jar", "VotingWhereToLunch-1.0.jar"]