#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "master" ]; then
  docker --version
  docker build -t rainbowloutre/mep-back .
  docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD";
  docker push rainbowloutre/mep-back;
fi
