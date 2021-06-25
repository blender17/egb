FROM openjdk:latest
ARG JAR_FILE=target/EGB-0.6.jar
COPY ${JAR_FILE} /egb/
WORKDIR /egb/
EXPOSE 443
EXPOSE 5432
ENTRYPOINT ["java", "-jar", "EGB-0.1.jar"]