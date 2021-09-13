FROM adoptopenjdk/openjdk16:latest

# Create appuser to run as non-root for security purposes and /app for app-specific files
RUN groupadd -g 999 appuser \
    && useradd -r -u 999 -g appuser appuser \
    && mkdir /app \
    && chown appuser:appuser /app
USER appuser

ARG JAR_FILE
COPY --chown=appuser:appuser ${JAR_FILE} /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
