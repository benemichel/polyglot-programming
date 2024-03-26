# $version[-muslib(for native image only)][-$platform][-$buildnumber]
# FROM ghcr.io/graalvm/graalvm-ce:ol7-java-22.3.1
# FROM ghcr.io/graalvm/graalvm-community:22-ol7


FROM ubuntu:20.04

# GraalVM
RUN apt-get update && apt-get install -y curl tar gzip git build-essential
RUN curl -L https://github.com/graalvm/graalvm-ce-builds/releases/download/jdk-21.0.2/graalvm-community-jdk-21.0.2_linux-x64_bin.tar.gz | tar -xzC /opt

ENV JAVA_HOME=/opt/graalvm-community-openjdk-21.0.2+13.1
ENV PATH=$PATH:$JAVA_HOME/bin

RUN echo $PATH
RUN echo $JAVA_HOME

# maven 
ARG MAVEN_VERSION=3.8.8
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref 
RUN curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz
RUN tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1
RUN rm -f /tmp/apache-maven.tar.gz
RUN ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# graalpython
RUN curl -L https://github.com/oracle/graalpython/releases/download/graal-23.1.2/graalpy-community-23.1.2-linux-amd64.tar.gz | tar -xz -C /usr/local
ENV PATH="/usr/local/graalpy-community-23.1.2-linux-amd64/bin:$PATH"

RUN graalpy -m venv venv
RUN . venv/bin/activate && pip install numpy
RUN . venv/bin/activate && python -c "import numpy; print(numpy.__version__)"

# start application
RUN mkdir -p /polyglot_demo_project
COPY . .
EXPOSE 8080
RUN mvn clean verify
CMD [ "mvn", "spring-boot:run" ]