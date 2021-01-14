@echo off
call mvn clean package
call docker build -t com/PROVA .
call docker rm -f PROVA
call docker run -d -p 9080:9080 -p 9443:9443 --name PROVA com/PROVA