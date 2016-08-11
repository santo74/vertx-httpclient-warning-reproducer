#!/bin/bash
while true; do
  curl -v -X PUT -F file=@src/main/resources/pixel.png http://localhost:7000/api/test
  sleep 2
done
