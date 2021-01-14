@echo off
call mvn clean package
call docker build -t com/Payara .
call docker rm -f Payara
call docker run -d -p 9080:9080 -p 9443:9443 --name Payara com/Payara