#!/bin/bash

mvn -e -q compile exec:java -Dexec.mainClass=dk.bringlarsen.DockerRepositoryApp
