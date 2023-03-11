FROM openjdk:17-alpine

WORKDIR /root

ENV LANG=C.UTF-8

RUN apk add curl

COPY entrypoint.sh /root

COPY target/stamps-and-coins-ls-service.jar /root/stamps-and-coins-ls-service.jar

ENTRYPOINT ["/root/entrypoint.sh"]
