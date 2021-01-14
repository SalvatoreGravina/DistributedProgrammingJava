#!/bin/sh
mvn clean package && docker build -t com/Payara .
docker rm -f Payara || true && docker run -d -p 9080:9080 -p 9443:9443 --name Payara com/Payara