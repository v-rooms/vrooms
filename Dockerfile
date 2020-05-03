FROM openjdk:14.0.1-slim
MAINTAINER Artur Aukhatov <aaukhatov@gmail.com>
ENTRYPOINT ["java", "-jar", "/opt/vrooms.jar", "-Xmx256m"]
EXPOSE 8443

ARG JAR_FILE
COPY ${JAR_FILE} /opt/vrooms.jar