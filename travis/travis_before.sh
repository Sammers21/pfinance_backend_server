#!/usr/bin/env bash

# build and push fpostgres image
/etc/init.d/postgresql stop
docker login -u="sammers" -p="$DOCKER_PASSWORD"
docker build --no-cache=true -t fpostgres:latest ./postgres
docker tag fpostgres:latest sammers/fpostgres:lastest
docker push sammers/fpostgres:lastest