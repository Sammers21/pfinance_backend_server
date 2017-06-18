#!/usr/bin/env bash
if [ ! "$(sudo docker ps -q -f name=postgres)" ]; then
    # run your container
    sudo docker run --publish 5432:5432 --name postgres -d fpostgres
    while ! nc -z localhost 5432; do
        sleep 0.1 # wait for 1/10 of the second before check again
        echo wait
    done
fi