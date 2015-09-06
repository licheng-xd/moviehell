#!/bin/bash

mvn clean package -DskipTests
rm -rf release/*
unzip -oq target/moviehell-1.0-SNAPSHOT.war -d release/

echo "package done!"
