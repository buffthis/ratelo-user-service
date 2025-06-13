FROM openjdk:17-jdk-slim

# install fonts
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        fontconfig \
        fonts-dejavu \
        fonts-liberation \
        fonts-noto \
        fonts-noto-cjk \
        fonts-nanum \
        fonts-nanum-coding \
        fonts-nanum-extra \
        fonts-unfonts-core \
        && \
    fc-cache -f -v && \
    rm -rf /var/lib/apt/lists/*

# JVM headless mode
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]