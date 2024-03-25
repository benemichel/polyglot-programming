# Project setup


# Tests
Die erstellten Tests dokumentieren die in Kapitel "Polyglotte Programmierung auf der GraalVM" dargestellten Funktionen.

# Spring Applikation/Swagger
https://springdoc.org/#Introduction

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html




graalpy -m venv venv
source venv/bin/activate
graalpy
>>> print("Hello World!")

pip -V


# run tests
mvn -Dtest=UserTest clean test
[] run all tests with mvn test


# Docker

To check the version of GraalVM and its installed location, run the env command from the Bash prompt:

 ``env``
The output shows the environment variable JAVA_HOME pointing to the installed GraalVM version and location.

To check the Java version, run:

 ``java -version``