FROM adoptopenjdk/openjdk11:alpine

RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /opt
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
