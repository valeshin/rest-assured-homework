#!/bin/bash

docker run -it --rm \
  -p 8090:8080 \
  --name wiremock \
  -v $PWD/src/main/resources:/home/wiremock \
  wiremock/wiremock:2.34.0 \
  --verbose