#!/bin/bash

echo "Build service"
./gradlew clean build

echo "Launch service"
java -jar ./build/libs/betclic.jar