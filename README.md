# outerlyy-user-service

## Overview

This service handles user's business logic

## Pre-requisite

* Docker to build and run the service

* Gradle (Optional) to build and run the service

* Protoc to compile the protobuf models

* Postman (Optional) for testing the APIs

## How to Build and Run this Service

### Using Docker (Recommended)

#### Build the Image:

```
docker build -t outerlyy-user-service .
```

#### Tag an Image

```
docker tag outerlyy-user-service:latest outerlyy/outerlyy-user-service:latest
```

#### Push to DockerHub

```
docker push outerlyy/user-service:latest
```

### Using Gradle

#### Build the Artifact with the Dependencies

```
./gradlew build
```

#### Run the the Artifact

```
./gradlew bootRun
```
## How to Deploy

### Using Github Actions
