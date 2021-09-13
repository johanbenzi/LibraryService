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

## Test Reports:

Serenity BDD test reports are available at location below
<pre> target/site/sernity/index.html </pre>

## Contract testing

Stubs are generated for all the apis, this can be used for testing a consumer of these endpoints to test the bounded
context

## Docker

Docker image for the app can be created locally by the following command
<pre>docker build -t library-service --build-arg JAR_FILE=target/library-service-0.0.1-SNAPSHOT.jar .</pre>

## Kubernetes and templating(ytt and kapp)

For executing this you will need a configured remote k8s instance or a local k8s instance such as minikube Also you need
to install ytt and kapp which is part of the k14s stack from carvel
<pre> https://carvel.dev/</pre>

To check the templated kubernetes file for any environment(we have nonprod and prod)
<pre> ./deploy/k8s/tools/render -f deploy/k8s/envs/prod </pre>

To deploy the app using kapp run the following command

<pre>kapp deploy -a library-service-nonprod -c -f <(deploy/k8s/tools/render -f deploy/k8s/envs/nonprod --data-value image.name="$image_name" --data-value image.tag="$image_tag") </pre>
Image name and tag is obtained from the previous docker image that was built
