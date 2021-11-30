FROM openjdk:17-slim-buster

WORKDIR /root

ENV LANG=C.UTF-8

COPY entrypoint.sh /root

COPY target/stamps-and-coins-batch-*.jar /root/stamps-and-coins-batch.jar

ENTRYPOINT ["/root/entrypoint.sh"]
