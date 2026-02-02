#!/bin/bash

CONTAINER_NAME="lets-play-db"
MONGO_IMAGE="mongo"
MONGO_PORT="27017:27017"

# check docker existence
if ! command -v docker &> /dev/null; then
    echo -e "Error: Docker is not installed."
    exit 1
fi

# check deamon
if ! docker ps &> /dev/null; then
    echo -e "Please start Docker deamon and try again."
    exit 1
fi

# check container if running
if [ "$(docker ps -q -f "name=^${CONTAINER_NAME}$")" ]; then
    echo -e "MongoDB container '$CONTAINER_NAME' is already running."
    
# Check if the container stopped
elif [ "$(docker ps -aq -f "name=^${CONTAINER_NAME}$")" ]; then
    echo -e "MongoDB container '$CONTAINER_NAME' exists but is stopped. Starting it..."
    docker start $CONTAINER_NAME

# create and run container
else
    echo -e "Creating and starting MongoDB container."
    docker run -d -p $MONGO_PORT --name $CONTAINER_NAME $MONGO_IMAGE
fi

echo -e "--- Database is ready ---"
echo ""

# Start the Spring Boot Application
echo -e "--- Starting Spring Boot application ('mongodb://localhost:27017') ---"

./mvnw spring-boot:run