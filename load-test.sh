#!/bin/bash
for i in `seq 1 1000`;
do
  curl http://localhost:8080/go &
done 
