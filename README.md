# Library

A service that is responsible for moving files between storages

## Running locally:

Step 1: Build the app to fetch all dependencies
<pre>mvn clean install</pre>

Step 2: Start up the postgres image using docker compose file
<pre>docker compose up</pre>

Step 3: Run the app through your IDE or run the following command in your terminal, this will also perform db migration
using integrated liquibase
<pre>mvn spring-boot:run</pre>

Swagger URL: http://localhost:8080/swagger-ui.html

Serenity BDD test reports are available at location below
<pre> target/site/sernity/index.html </pre>

Stubs are generated for the contracts, this can be used for testing a consumer of these endpoints to test the bounded
context

Docker image for the app can be created locally by the following command
<pre>docker build -t library-service --build-arg JAR_FILE=target/library-service-0.0.1-SNAPSHOT.jar .</pre>

