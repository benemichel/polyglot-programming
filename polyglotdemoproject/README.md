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

# Install graalpython 
From: https://github.com/oracle/graalpython/blob/master/docs/user/Python-Runtime.md


Alternatively, you can download a compressed GraalPy installation file from GitHub releases.

Find the download that matches the pattern graalpy-XX.Y.Z-linux-amd64.tar.gz or graalpy-XX.Y.Z-linux-aarch64.tar.gz (depending on your platform) and download.
Uncompress the file and update your PATH environment variable to include to the graalpy-XX.Y.Z-linux-amd64/bin (or graalpy-XX.Y.Z-linux-aarch64/bin) directory.

``export PATH=path/to/graalpython/bin:$PATH``

# Docker

To check the version of GraalVM and its installed location, run the env command from the Bash prompt:

 ``env``
The output shows the environment variable JAVA_HOME pointing to the installed GraalVM version and location.

To check the Java version, run:

 ``java -version``