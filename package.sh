#!/bin/bash

echo "package start ..."
mvn clean package -DskipTests
rm -rf release/*
unzip -oq target/moviehell-1.0-SNAPSHOT.war -d release/
echo "package done"
#cd release
#scp -r * root@10.166.224.14:/home/lc/moviehell
#echo "deploy done"
