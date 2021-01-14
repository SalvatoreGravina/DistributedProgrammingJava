#!/bin/sh
mvn clean package && docker build -t com/PROVA .
docker rm -f PROVA || true && docker run -d -p 9080:9080 -p 9443:9443 --name PROVA com/PROVA