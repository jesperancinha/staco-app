FROM openjdk:17-slim-buster

WORKDIR /root

ENV LANG=C.UTF-8

COPY entrypoint.sh /root

COPY target/stamps-and-coins-service-*.jar /root/stamps-and-coins-service.jar

ENTRYPOINT ["/root/entrypoint.sh"]
