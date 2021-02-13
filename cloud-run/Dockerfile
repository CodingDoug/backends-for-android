# Use the official gradle image to create a build artifact.
# https://hub.docker.com/_/gradle
FROM gradle:6.8-jdk8 as builder

# Copy local code to the container image.
COPY build.gradle .
COPY src ./src
COPY config/gcloud/logback.xml src/main/resources/logback.xml

# Build a release artifact.
RUN gradle clean shadowJar --no-daemon

# Use an official OpenJDK image for running ktor
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:8-jre-stretch

# Copy the jar to the production image from the builder stage.
COPY --from=builder /home/gradle/build/libs/app-all.jar /app.jar

# Run the web service on container startup.
CMD [ "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app.jar" ]
