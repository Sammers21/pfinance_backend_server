#!/usr/bin/env bash
if [ ! "$(sudo docker ps -q -f name=postgres)" ]; then
    # run your container
    sudo docker run --publish 5432:5432 --name postgres -d sammers/fpostgres
fi