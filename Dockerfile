FROM openjdk:17-alpine

RUN apk add --no-cache tzdata

ENV TZ=Asia/Seoul

ARG JAR_FILE=/build/libs/kekeche-be-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /kekeche-be.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/kekeche-be.jar"]