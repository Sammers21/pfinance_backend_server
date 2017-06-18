#!/usr/bin/env bash
# run your container
sudo docker run --publish 5432:5432 --name postgres -d sammers/fpostgres
