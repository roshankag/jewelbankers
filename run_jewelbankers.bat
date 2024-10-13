@echo off
cd "C:\Users\Roshan B T\backend"
set JAVA_HOME="C:\Program Files\Java\jdk-17"
set PATH=%JAVA_HOME%\bin;%PATH%
java -jar spring-boot-security-jwt-0.0.1-SNAPSHOT.jar --server.port=8080
pause
