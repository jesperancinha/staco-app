FROM openjdk:19-alpine

WORKDIR /root

ENV LANG=C.UTF-8

RUN apk add curl

COPY entrypoint.sh /root

COPY target/stamps-and-coins-batch-*.jar /root/stamps-and-coins-batch.jar

ENTRYPOINT ["/root/entrypoint.sh"]
