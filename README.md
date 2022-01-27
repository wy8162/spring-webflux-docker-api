**Build Docker Image with Dockerfile**

1. Package: mvn package
2. Build Docker image
   docker build -t springio/spring-webflux-docker-api:1.0.0 .
3. Run
   docker run -it -p8081:8080 springio/spring-webflux-docker-api:1.0.0

**Build Docker Image with Sprint Boot Plugin**

This does not need a Dockerfile, which will be ignored if it exists.

   mvn spring-boot:build-image -Dspring-boot.build-image.imageName=springio/spring-webflux-docker-api:1.0.0

**Build Docker Image with Google Jib**
1. Build the Docker image locally
   mvn compile jib:dockerBuild
2. Build and push to Registry
   mvn compile jib:build
3. Using lifecycle
   mvn package
   
**Using Spring Profiles**

docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 spring-webflux-docker-api:1.0.0

**Debug Application Running inside Contaner**

docker run -e "JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" -p 8080:8080 -p 5005:5005 spring-webflux-docker-api:1.0.0
